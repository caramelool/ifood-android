import type { FastifyPluginAsync } from 'fastify'
import { z } from 'zod'
import db from '../db/sqlite/client.js'

const CreateUserSchema = z.object({
  name: z.string().min(1),
  email: z.string().email(),
})

const UpdateUserSchema = CreateUserSchema.partial()

const relationalRoutes: FastifyPluginAsync = async (app) => {
  app.get('/users', async () => {
    return db.prepare('SELECT * FROM users ORDER BY id').all()
  })

  app.get('/users/:id', async (req, reply) => {
    const { id } = req.params as { id: string }
    const user = db.prepare('SELECT * FROM users WHERE id = ?').get(Number(id))
    if (!user) return reply.status(404).send({ error: 'User not found' })
    return user
  })

  app.post('/users', async (req, reply) => {
    const body = CreateUserSchema.parse(req.body)
    try {
      const result = db
        .prepare('INSERT INTO users (name, email) VALUES (?, ?)')
        .run(body.name, body.email)
      const user = db
        .prepare('SELECT * FROM users WHERE id = ?')
        .get(Number(result.lastInsertRowid))
      return reply.status(201).send(user)
    } catch (err: unknown) {
      const msg = err instanceof Error ? err.message : String(err)
      if (msg.includes('UNIQUE')) {
        return reply.status(409).send({ error: 'Email already in use' })
      }
      throw err
    }
  })

  app.put('/users/:id', async (req, reply) => {
    const { id } = req.params as { id: string }
    const body = UpdateUserSchema.parse(req.body)

    const existing = db.prepare('SELECT id FROM users WHERE id = ?').get(Number(id))
    if (!existing) return reply.status(404).send({ error: 'User not found' })

    const fields = Object.entries(body).filter(([, v]) => v !== undefined)
    if (fields.length === 0) {
      return reply.status(400).send({ error: 'No fields provided' })
    }

    const setClauses = fields.map(([k]) => `${k} = ?`).join(', ')
    const values: unknown[] = [...fields.map(([, v]) => v), Number(id)]
    db.prepare(`UPDATE users SET ${setClauses} WHERE id = ?`).run(...values)

    return db.prepare('SELECT * FROM users WHERE id = ?').get(Number(id))
  })

  app.delete('/users/:id', async (req, reply) => {
    const { id } = req.params as { id: string }
    const result = db.prepare('DELETE FROM users WHERE id = ?').run(Number(id))
    if (result.changes === 0) return reply.status(404).send({ error: 'User not found' })
    return reply.status(204).send()
  })
}

export default relationalRoutes
