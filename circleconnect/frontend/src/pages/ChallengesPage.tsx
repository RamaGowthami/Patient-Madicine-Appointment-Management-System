import { useEffect, useState } from 'react'
import api from '../services/api'

interface Challenge { id: number; title: string; description?: string; pointsReward: number }

export default function ChallengesPage() {
  const [challenge, setChallenge] = useState<Challenge | null>(null)
  const [story, setStory] = useState('')
  const [imageUrl, setImageUrl] = useState('')

  const load = async () => {
    const res = await api.get('/api/challenges/current')
    setChallenge(res.status === 204 ? null : res.data)
  }

  useEffect(() => { load() }, [])

  const submit = async () => {
    if (!challenge) return
    await api.post(`/api/challenges/${challenge.id}/submit`, { story, imageUrl: imageUrl || null })
    setStory('')
    setImageUrl('')
    alert('Submitted! Points awarded.')
  }

  if (!challenge) return <div><h2>Weekly Challenge</h2><p>No active challenge yet.</p></div>

  return (
    <div>
      <h2>Weekly Challenge</h2>
      <h3>{challenge.title}</h3>
      <p>{challenge.description}</p>
      <p>Reward: {challenge.pointsReward} pts</p>
      <div style={{ display: 'flex', flexDirection: 'column', gap: 8, maxWidth: 600 }}>
        <textarea placeholder="Your story" value={story} onChange={e => setStory(e.target.value)} />
        <input placeholder="Image URL (optional)" value={imageUrl} onChange={e => setImageUrl(e.target.value)} />
        <button onClick={submit}>Submit</button>
      </div>
    </div>
  )
}