import { JSONFilePreset } from 'lowdb/node'
import { mkdirSync } from 'fs'
import { join } from 'path'
import type { Schema } from './schema.js'

const dataDir = join(process.cwd(), 'data')
mkdirSync(dataDir, { recursive: true })

export const dbPromise = JSONFilePreset<Schema>(
  join(dataDir, 'db.json'),
  { notes: [] }
)
