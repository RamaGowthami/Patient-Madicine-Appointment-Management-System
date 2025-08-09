import { Link, Outlet, useNavigate } from 'react-router-dom'
import { useAuth } from './services/AuthContext'

export default function App() {
  const { user, signOut } = useAuth()
  const navigate = useNavigate()

  return (
    <div style={{ fontFamily: 'Inter, system-ui, Arial', margin: '0 auto', maxWidth: 920, padding: 16 }}>
      <header style={{ display: 'flex', gap: 16, alignItems: 'center', justifyContent: 'space-between' }}>
        <nav style={{ display: 'flex', gap: 12 }}>
          <Link to="/">Feed</Link>
          <Link to="/groups">Groups</Link>
          <Link to="/challenges">Challenges</Link>
          <Link to="/events">Events</Link>
          <Link to="/profile">Profile</Link>
        </nav>
        <div>
          {user ? (
            <button onClick={() => { signOut(); navigate('/login') }}>Sign out</button>
          ) : (
            <Link to="/login">Sign in</Link>
          )}
        </div>
      </header>
      <main style={{ marginTop: 16 }}>
        <Outlet />
      </main>
    </div>
  )
}