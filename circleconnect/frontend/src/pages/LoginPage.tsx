import { useNavigate } from 'react-router-dom'
import { useAuth } from '../services/AuthContext'

export default function LoginPage() {
  const { signInWithGoogle } = useAuth()
  const navigate = useNavigate()

  const handleLogin = async () => {
    await signInWithGoogle()
    navigate('/')
  }

  return (
    <div>
      <h2>Welcome to CircleConnect</h2>
      <p>Sign in to join groups, share stories, and participate in weekly challenges.</p>
      <button onClick={handleLogin}>Continue with Google</button>
    </div>
  )
}