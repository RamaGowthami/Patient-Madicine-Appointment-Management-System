import axios from 'axios'
import { auth } from './firebase'

const api = axios.create({
  baseURL: import.meta.env.VITE_API_BASE_URL || 'http://localhost:8080'
})

api.interceptors.request.use(async (config) => {
  const user = auth.currentUser
  if (user) {
    const token = await user.getIdToken()
    config.headers = config.headers || {}
    config.headers['Authorization'] = `Bearer ${token}`
  } else {
    // Dev fallback
    config.headers = config.headers || {}
    config.headers['Authorization'] = 'Dev uid=demo-user'
  }
  return config
})

export default api