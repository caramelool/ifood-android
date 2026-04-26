import request from 'supertest';
import app from '../src/app';

// T010: happy path → 200 + all response fields, mealType echoed back
it('returns 200 with all fields and echoes mealType', async () => {
  const res = await request(app)
    .get('/recommendation')
    .query({ userName: 'Lucas', mealType: 'lunch', preferences: ['vegan'] });

  expect(res.status).toBe(200);
  expect(res.body).toMatchObject({
    userName: 'Lucas',
    mealType: 'lunch',
    placeName: expect.any(String),
    placeAddress: expect.any(String),
    mealName: expect.any(String),
    mealDescription: expect.any(String),
    mealPrice: expect.any(Number),
  });
});

// T011: known mealType is accepted and echoed back
it('accepts and echoes known mealType', async () => {
  const res = await request(app)
    .get('/recommendation')
    .query({ userName: 'Ana', mealType: 'dinner', preferences: ['italian'] });

  expect(res.status).toBe(200);
  expect(res.body.mealType).toBe('dinner');
});

// T012: missing userName → 200 with null userName
it('returns 200 with null userName when omitted', async () => {
  const res = await request(app)
    .get('/recommendation')
    .query({ mealType: 'lunch', preferences: ['healthy'] });

  expect(res.status).toBe(200);
  expect(res.body.userName).toBeNull();
});

// T013: missing mealType → 400
it('returns 400 when mealType is missing', async () => {
  const res = await request(app)
    .get('/recommendation')
    .query({ userName: 'Pedro', preferences: ['vegan'] });

  expect(res.status).toBe(400);
  expect(res.body.error).toMatch(/mealType/);
});

// T014: unknown mealType → 404
it('returns 404 when no meal matches the given mealType', async () => {
  const res = await request(app)
    .get('/recommendation')
    .query({ userName: 'Maria', mealType: 'brunch', preferences: ['vegan'] });

  expect(res.status).toBe(404);
  expect(res.body.error).toBeDefined();
});

// T015: afternoon_snack → 200, mealType echoed
it('returns 200 for afternoon_snack and echoes mealType', async () => {
  const res = await request(app)
    .get('/recommendation')
    .query({ mealType: 'afternoon_snack', preferences: ['healthy'] });

  expect(res.status).toBe(200);
  expect(res.body.mealType).toBe('afternoon_snack');
});

// T016: unknown mealType → 404
it('returns 404 when mealType does not exist in mocks', async () => {
  const res = await request(app)
    .get('/recommendation')
    .query({ mealType: 'supper', preferences: ['spicy'] });

  expect(res.status).toBe(404);
  expect(res.body.error).toBeDefined();
});

// T017: missing preferences → 400
it('returns 400 when preferences is missing', async () => {
  const res = await request(app)
    .get('/recommendation')
    .query({ mealType: 'lunch' });

  expect(res.status).toBe(400);
  expect(res.body.error).toMatch(/preferences/);
});

// T018: empty preferences array → 400
it('returns 400 when preferences is an empty array', async () => {
  const res = await request(app)
    .get('/recommendation')
    .query({ mealType: 'lunch', preferences: [] });

  expect(res.status).toBe(400);
  expect(res.body.error).toMatch(/preferences/);
});
