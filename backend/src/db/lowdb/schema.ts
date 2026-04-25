export interface Note {
  id: string
  title: string
  content: string
  tags: string[]
  createdAt: string
}

export interface Schema {
  notes: Note[]
}
