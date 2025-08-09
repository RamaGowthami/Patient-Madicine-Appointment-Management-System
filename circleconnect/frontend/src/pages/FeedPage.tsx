import { useEffect, useState } from 'react'
import api from '../services/api'

interface Post {
  id: number
  authorUid: string
  groupId?: number
  type: 'PHOTO' | 'STORY'
  content?: string
  imageUrl?: string
  createdAt: string
}

export default function FeedPage() {
  const [posts, setPosts] = useState<Post[]>([])
  const [content, setContent] = useState('')

  const load = async () => {
    const res = await api.get('/api/posts')
    setPosts(res.data.content)
  }

  useEffect(() => { load() }, [])

  const submit = async () => {
    if (!content.trim()) return
    await api.post('/api/posts', { type: 'STORY', content })
    setContent('')
    await load()
  }

  return (
    <div>
      <h2>Community Feed</h2>
      <div style={{ display: 'flex', gap: 8 }}>
        <input value={content} onChange={e => setContent(e.target.value)} placeholder="Share a short story..." style={{ flex: 1 }} />
        <button onClick={submit}>Post</button>
      </div>
      <ul>
        {posts.map(p => (
          <li key={p.id} style={{ borderBottom: '1px solid #ddd', padding: '8px 0' }}>
            <div style={{ fontSize: 12, color: '#555' }}>{new Date(p.createdAt).toLocaleString()} — {p.authorUid}</div>
            {p.type === 'STORY' ? (
              <div>{p.content}</div>
            ) : (
              <div>
                {p.imageUrl ? <img src={p.imageUrl} alt="" style={{ maxWidth: '100%' }} /> : null}
                {p.content}
              </div>
            )}
          </li>
        ))}
      </ul>
    </div>
  )
}