import express from 'express';
import { recommendationRouter } from './routes/recommendation';

const app = express();

app.use(express.json());
app.use('/recommendation', recommendationRouter);

export default app;
