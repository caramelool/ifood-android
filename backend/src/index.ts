import 'dotenv/config'
import Fastify from 'fastify'
import cors from '@fastify/cors'
import { ZodError } from 'zod'
import { runMigrations } from './db/sqlite/migrations.js'
import relationalRoutes from './routes/relational.js'
import nosqlRoutes from './routes/nosql.js'

const app = Fastify({ logger: true })

app.setErrorHandler((error, _req, reply) => {
  if (error instanceof ZodError) {
    return reply.status(400).send({ error: 'Validation error', issues: error.issues })
  }
  app.log.error(error)
  return reply.status(500).send({ error: 'Internal server error' })
})

await app.register(cors, { origin: true })
runMigrations()
await app.register(relationalRoutes, { prefix: '/api' })
await app.register(nosqlRoutes, { prefix: '/api' })

const port = Number(process.env.PORT) || 3000
await app.listen({ port, host: '0.0.0.0' })
