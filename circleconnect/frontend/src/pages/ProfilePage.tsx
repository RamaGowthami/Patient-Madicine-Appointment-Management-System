import { useEffect, useState } from 'react'
import api from '../services/api'
import { useAuth } from '../services/AuthContext'

interface Profile { id: number; firebaseUid: string; displayName?: string; photoUrl?: string; points: number; badgesCsv?: string }

export default function ProfilePage() {
  const { user } = useAuth()
  const [profile, setProfile] = useState<Profile | null>(null)

  const load = async () => {
    if (!user) return
    await api.post('/api/me/ensure', { displayName: user.displayName, photoUrl: user.photoURL })
    const res = await api.get('/api/me')
    setProfile(res.data)
  }

  useEffect(() => { load() }, [user?.uid])

  if (!user) return <div>Please sign in.</div>
  if (!profile) return <div>Loading...</div>

  const badges = (profile.badgesCsv || '').split(',').filter(Boolean)

  return (
    <div>
      <h2>My Profile</h2>
      <div style={{ display: 'flex', gap: 12, alignItems: 'center' }}>
        {profile.photoUrl ? <img src={profile.photoUrl} alt="" width={64} height={64} style={{ borderRadius: 8 }} /> : null}
        <div>
          <div><strong>{profile.displayName || user.email}</strong></div>
          <div>{profile.points} points</div>
          <div>Badges: {badges.length ? badges.join(', ') : '—'}</div>
        </div>
      </div>
    </div>
  )
}