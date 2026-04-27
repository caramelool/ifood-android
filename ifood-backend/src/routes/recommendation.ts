import { Router, Request, Response } from 'express';
import { meals } from '../mock/meals';

export const recommendationRouter = Router();

recommendationRouter.get('/', (req: Request, res: Response) => {
  const { userName, mealType } = req.query;
  const preferences = req.query.preferences
    ? Array.isArray(req.query.preferences)
      ? (req.query.preferences as string[])
      : [req.query.preferences as string]
    : [];

  if (!userName || typeof userName !== 'string') {
    return res.status(400).json({ error: 'Missing required parameter: userName' });
  }

  if (!mealType || typeof mealType !== 'string') {
    return res.status(400).json({ error: 'Missing required parameter: mealType' });
  }

  if (preferences.length === 0) {
    return res.status(400).json({ error: 'Missing required parameter: preferences' });
  }

  const candidates = meals.filter((m) => m.mealType === mealType);

  if (candidates.length === 0) {
    return res.status(404).json({ error: 'No meal found matching your preferences.' });
  }

  const pick = candidates[Math.floor(Math.random() * candidates.length)];

  return res.status(200).json({
    userName: userName.trim(),
    mealType: mealType,
    placeName: pick.placeName,
    placeAddress: pick.placeAddress,
    mealName: pick.mealName,
    mealDescription: pick.mealDescription,
    mealPrice: pick.mealPrice,
    mealImageUrl: pick.mealImageUrl,
    preferences: preferences
  });
});
