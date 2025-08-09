import { useEffect, useState } from 'react'
import api from '../services/api'

interface Event { id: number; title: string; description?: string; location?: string; startsAt: string }

export default function EventsPage() {
  const [events, setEvents] = useState<Event[]>([])
  const [title, setTitle] = useState('')
  const [location, setLocation] = useState('')

  const load = async () => {
    const res = await api.get('/api/events')
    setEvents(res.data)
  }

  useEffect(() => { load() }, [])

  const create = async () => {
    if (!title.trim()) return
    await api.post('/api/events', { title, location, startsAt: new Date().toISOString() })
    setTitle('')
    setLocation('')
    await load()
  }

  return (
    <div>
      <h2>Community Events</h2>
      <div style={{ display: 'flex', gap: 8 }}>
        <input placeholder="Event title" value={title} onChange={e => setTitle(e.target.value)} />
        <input placeholder="Location" value={location} onChange={e => setLocation(e.target.value)} />
        <button onClick={create}>Create</button>
      </div>
      <ul>
        {events.map(e => (
          <li key={e.id}>
            <strong>{e.title}</strong> — {e.location} — {new Date(e.startsAt).toLocaleString()}
          </li>
        ))}
      </ul>
    </div>
  )
}