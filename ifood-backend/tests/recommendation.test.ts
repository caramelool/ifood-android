import request from 'supertest';
import app from '../src/app';

// T010: happy path with preferences → 200 + all response fields
it('returns 200 with all fields when valid preferences supplied', async () => {
  const res = await request(app)
    .get('/recommendation')
    .query({ userName: 'Lucas', mealType: 'lunch', mealTime: '12:30', preferences: ['vegan'] });

  expect(res.status).toBe(200);
  expect(res.body).toMatchObject({
    userName: 'Lucas',
    mealType: 'lunch',
    placeName: expect.any(String),
    placeAddress: expect.any(String),
    mealName: expect.any(String),
    mealDescription: expect.any(String),
    mealPrice: expect.any(Number),
    preferences: expect.any(Array),
  });
});

// T011: happy path with no preferences → 200
it('returns 200 when no preferences supplied', async () => {
  const res = await request(app)
    .get('/recommendation')
    .query({ userName: 'Ana', mealType: 'breakfast', mealTime: '08:00' });

  expect(res.status).toBe(200);
  expect(res.body.userName).toBe('Ana');
});

// T012: missing userName → 400
it('returns 400 when userName is missing', async () => {
  const res = await request(app)
    .get('/recommendation')
    .query({ mealType: 'lunch', mealTime: '12:30' });

  expect(res.status).toBe(400);
  expect(res.body.error).toMatch(/userName/);
});

// T013: invalid mealType → 400
it('returns 400 when mealType is invalid', async () => {
  const res = await request(app)
    .get('/recommendation')
    .query({ userName: 'Pedro', mealType: 'brunch', mealTime: '10:00' });

  expect(res.status).toBe(400);
  expect(res.body.error).toMatch(/mealType/);
});

// T014: no matching meal → 404
it('returns 404 when no meal matches the given time window', async () => {
  const res = await request(app)
    .get('/recommendation')
    .query({ userName: 'Maria', mealType: 'dinner', mealTime: '03:00' });

  expect(res.status).toBe(404);
  expect(res.body.error).toBeDefined();
});
