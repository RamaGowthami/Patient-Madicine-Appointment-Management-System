import { useEffect, useState } from 'react'
import api from '../services/api'

interface Group { id: number; name: string; description?: string; city?: string }

export default function GroupsPage() {
  const [groups, setGroups] = useState<Group[]>([])
  const [name, setName] = useState('')
  const [city, setCity] = useState('')

  const load = async () => {
    const res = await api.get('/api/groups')
    setGroups(res.data)
  }

  useEffect(() => { load() }, [])

  const create = async () => {
    if (!name.trim()) return
    await api.post('/api/groups', { name, city })
    setName('')
    setCity('')
    await load()
  }

  return (
    <div>
      <h2>Local Groups</h2>
      <div style={{ display: 'flex', gap: 8 }}>
        <input placeholder="Group name" value={name} onChange={e => setName(e.target.value)} />
        <input placeholder="City" value={city} onChange={e => setCity(e.target.value)} />
        <button onClick={create}>Create</button>
      </div>
      <ul>
        {groups.map(g => (
          <li key={g.id}>
            <strong>{g.name}</strong>{g.city ? ` — ${g.city}` : ''}
          </li>
        ))}
      </ul>
    </div>
  )
}