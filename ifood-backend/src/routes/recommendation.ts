import { Router, Request, Response } from 'express';
import { meals, MockMeal } from '../mock/meals';

export const recommendationRouter = Router();

const MEAL_TYPES = ['breakfast', 'lunch', 'dinner'] as const;
const TIME_RE = /^\d{2}:\d{2}$/;

function toMinutes(hhmm: string): number {
  const [h, m] = hhmm.split(':').map(Number);
  return h * 60 + m;
}

function scoreMatch(meal: MockMeal, preferences: string[]): number {
  if (preferences.length === 0) return 0;
  return preferences.filter((p) => meal.tags.includes(p)).length;
}

recommendationRouter.get('/', (req: Request, res: Response) => {
  const { userName, mealType, mealTime } = req.query;
  const preferences: string[] = req.query.preferences
    ? Array.isArray(req.query.preferences)
      ? (req.query.preferences as string[])
      : [req.query.preferences as string]
    : [];

  if (!userName || typeof userName !== 'string' || userName.trim() === '') {
    return res.status(400).json({ error: 'Missing required parameter: userName' });
  }
  if (!mealType || !MEAL_TYPES.includes(mealType as (typeof MEAL_TYPES)[number])) {
    return res.status(400).json({
      error: `Invalid or missing mealType. Must be one of: ${MEAL_TYPES.join(', ')}`,
    });
  }
  if (!mealTime || typeof mealTime !== 'string' || !TIME_RE.test(mealTime)) {
    return res.status(400).json({ error: 'Invalid or missing mealTime. Expected format: HH:MM' });
  }

  const requestedMinutes = toMinutes(mealTime);

  const candidates = meals.filter(
    (m) =>
      m.mealType === mealType &&
      requestedMinutes >= toMinutes(m.mealTimeStart) &&
      requestedMinutes <= toMinutes(m.mealTimeEnd),
  );

  if (candidates.length === 0) {
    return res.status(404).json({ error: 'No meal found matching your preferences.' });
  }

  const best = candidates.reduce((top, current) =>
    scoreMatch(current, preferences) >= scoreMatch(top, preferences) ? current : top,
  );

  const matchedPreferences = preferences.filter((p) => best.tags.includes(p));

  return res.status(200).json({
    userName: userName.trim(),
    mealType,
    placeName: best.placeName,
    placeAddress: best.placeAddress,
    mealName: best.mealName,
    mealDescription: best.mealDescription,
    mealPrice: best.mealPrice,
    preferences: matchedPreferences,
  });
});
