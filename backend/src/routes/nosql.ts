import type { FastifyPluginAsync } from 'fastify'
import { randomUUID } from 'crypto'
import { z } from 'zod'
import { dbPromise } from '../db/lowdb/client.js'

const CreateNoteSchema = z.object({
  title: z.string().min(1),
  content: z.string(),
  tags: z.array(z.string()).optional().default([]),
})

const UpdateNoteSchema = CreateNoteSchema.partial()

const nosqlRoutes: FastifyPluginAsync = async (app) => {
  app.get('/notes', async (req) => {
    const lowdb = await dbPromise
    await lowdb.read()
    const { tag } = req.query as { tag?: string }
    return tag
      ? lowdb.data.notes.filter((n) => n.tags.includes(tag))
      : lowdb.data.notes
  })

  app.get('/notes/:id', async (req, reply) => {
    const lowdb = await dbPromise
    await lowdb.read()
    const { id } = req.params as { id: string }
    const note = lowdb.data.notes.find((n) => n.id === id)
    if (!note) return reply.status(404).send({ error: 'Note not found' })
    return note
  })

  app.post('/notes', async (req, reply) => {
    const lowdb = await dbPromise
    await lowdb.read()
    const body = CreateNoteSchema.parse(req.body)

    const note = {
      id: randomUUID(),
      title: body.title,
      content: body.content,
      tags: body.tags,
      createdAt: new Date().toISOString(),
    }

    lowdb.data.notes.push(note)
    await lowdb.write()
    return reply.status(201).send(note)
  })

  app.put('/notes/:id', async (req, reply) => {
    const lowdb = await dbPromise
    await lowdb.read()
    const { id } = req.params as { id: string }
    const body = UpdateNoteSchema.parse(req.body)

    const idx = lowdb.data.notes.findIndex((n) => n.id === id)
    if (idx === -1) return reply.status(404).send({ error: 'Note not found' })

    lowdb.data.notes[idx] = { ...lowdb.data.notes[idx], ...body }
    await lowdb.write()
    return lowdb.data.notes[idx]
  })

  app.delete('/notes/:id', async (req, reply) => {
    const lowdb = await dbPromise
    await lowdb.read()
    const { id } = req.params as { id: string }

    const idx = lowdb.data.notes.findIndex((n) => n.id === id)
    if (idx === -1) return reply.status(404).send({ error: 'Note not found' })

    lowdb.data.notes.splice(idx, 1)
    await lowdb.write()
    return reply.status(204).send()
  })
}

export default nosqlRoutes
