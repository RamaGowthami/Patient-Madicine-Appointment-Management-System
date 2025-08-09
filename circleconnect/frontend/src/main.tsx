import React from 'react'
import ReactDOM from 'react-dom/client'
import { createBrowserRouter, RouterProvider } from 'react-router-dom'
import App from './App'
import { AuthProvider } from './services/AuthContext'
import FeedPage from './pages/FeedPage'
import GroupsPage from './pages/GroupsPage'
import ChallengesPage from './pages/ChallengesPage'
import EventsPage from './pages/EventsPage'
import ProfilePage from './pages/ProfilePage'
import LoginPage from './pages/LoginPage'

const router = createBrowserRouter([
  {
    path: '/',
    element: <App />,
    children: [
      { index: true, element: <FeedPage /> },
      { path: 'groups', element: <GroupsPage /> },
      { path: 'challenges', element: <ChallengesPage /> },
      { path: 'events', element: <EventsPage /> },
      { path: 'profile', element: <ProfilePage /> },
      { path: 'login', element: <LoginPage /> }
    ]
  }
])

ReactDOM.createRoot(document.getElementById('root')!).render(
  <React.StrictMode>
    <AuthProvider>
      <RouterProvider router={router} />
    </AuthProvider>
  </React.StrictMode>
)