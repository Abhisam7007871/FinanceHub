// LoginButtons.jsx - Social login buttons component
import React from 'react';

export const LoginButtons = () => {
  return (
    <div className="social-login-container">
      <h2>Login to Project Ledger</h2>
      <div className="social-buttons">
        <a 
          href="http://localhost:8080/oauth2/authorization/google" 
          className="social-btn google-btn"
        >
          <svg width="20" height="20" viewBox="0 0 24 24">
            <path fill="#4285F4" d="M22.56 12.25c0-.78-.07-1.53-.2-2.25H12v4.26h5.92c-.26 1.37-1.04 2.53-2.21 3.31v2.77h3.57c2.08-1.92 3.28-4.74 3.28-8.09z"/>
            <path fill="#34A853" d="M12 23c2.97 0 5.46-.98 7.28-2.66l-3.57-2.77c-.98.66-2.23 1.06-3.71 1.06-2.86 0-5.29-1.93-6.16-4.53H2.18v2.84C3.99 20.53 7.7 23 12 23z"/>
            <path fill="#FBBC05" d="M5.84 14.09c-.22-.66-.35-1.36-.35-2.09s.13-1.43.35-2.09V7.07H2.18C1.43 8.55 1 10.22 1 12s.43 3.45 1.18 4.93l2.85-2.22.81-.62z"/>
            <path fill="#EA4335" d="M12 5.38c1.62 0 3.06.56 4.21 1.64l3.15-3.15C17.45 2.09 14.97 1 12 1 7.7 1 3.99 3.47 2.18 7.07l3.66 2.84c.87-2.6 3.3-4.53 6.16-4.53z"/>
          </svg>
          Continue with Google
        </a>
        
        <a 
          href="http://localhost:8080/oauth2/authorization/facebook" 
          className="social-btn facebook-btn"
        >
          <svg width="20" height="20" viewBox="0 0 24 24">
            <path fill="#1877F2" d="M24 12.073c0-6.627-5.373-12-12-12s-12 5.373-12 12c0 5.99 4.388 10.954 10.125 11.854v-8.385H7.078v-3.47h3.047V9.43c0-3.007 1.792-4.669 4.533-4.669 1.312 0 2.686.235 2.686.235v2.953H15.83c-1.491 0-1.956.925-1.956 1.874v2.25h3.328l-.532 3.47h-2.796v8.385C19.612 23.027 24 18.062 24 12.073z"/>
          </svg>
          Continue with Facebook
        </a>
        
        <a 
          href="http://localhost:8080/oauth2/authorization/apple" 
          className="social-btn apple-btn"
        >
          <svg width="20" height="20" viewBox="0 0 24 24">
            <path fill="#000" d="M12.152 6.896c-.948 0-2.415-1.078-3.96-1.04-2.04.027-3.91 1.183-4.961 3.014-2.117 3.675-.546 9.103 1.519 12.09 1.013 1.454 2.208 3.09 3.792 3.039 1.52-.065 2.09-.987 3.935-.987 1.831 0 2.35.987 3.96.948 1.637-.026 2.676-1.48 3.676-2.948 1.156-1.688 1.636-3.325 1.662-3.415-.039-.013-3.182-1.221-3.22-4.857-.026-3.04 2.48-4.494 2.597-4.559-1.429-2.09-3.623-2.324-4.39-2.376-2-.156-3.675 1.09-4.61 1.09zM15.53 3.83c.843-1.012 1.4-2.427 1.245-3.83-1.207.052-2.662.805-3.532 1.818-.78.896-1.454 2.338-1.273 3.714 1.338.104 2.715-.688 3.559-1.701"/>
          </svg>
          Sign in with Apple
        </a>
      </div>
    </div>
  );
};

// OAuth2Redirect.jsx - Handle OAuth2 callback
import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';

export const OAuth2Redirect = () => {
  const navigate = useNavigate();
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const handleOAuth2Callback = async () => {
      try {
        // Check if user is authenticated via cookies
        const response = await fetch('http://localhost:8080/api/oauth/profile', {
          credentials: 'include'
        });
        
        if (response.ok) {
          const data = await response.json();
          if (data.loggedIn) {
            // Store user data in context/state management
            localStorage.setItem('user', JSON.stringify(data.user));
            navigate('/dashboard');
          } else {
            setError('Authentication failed');
            setTimeout(() => navigate('/login'), 2000);
          }
        } else {
          setError('Authentication failed');
          setTimeout(() => navigate('/login'), 2000);
        }
      } catch (err) {
        setError('Network error occurred');
        setTimeout(() => navigate('/login'), 2000);
      } finally {
        setLoading(false);
      }
    };

    handleOAuth2Callback();
  }, [navigate]);

  if (loading) {
    return (
      <div className="oauth-redirect-container">
        <div className="spinner"></div>
        <p>Completing your sign-in...</p>
      </div>
    );
  }

  if (error) {
    return (
      <div className="oauth-redirect-container">
        <div className="error-message">
          <p>{error}</p>
          <p>Redirecting to login...</p>
        </div>
      </div>
    );
  }

  return null;
};

// UserProfile.jsx - Display user profile with OAuth2 data
import React, { useState, useEffect } from 'react';

export const UserProfile = () => {
  const [user, setUser] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    fetchUserProfile();
  }, []);

  const fetchUserProfile = async () => {
    try {
      const response = await fetch('http://localhost:8080/api/oauth/profile', {
        credentials: 'include'
      });
      
      if (response.ok) {
        const data = await response.json();
        if (data.loggedIn) {
          setUser(data.user);
        }
      }
    } catch (error) {
      console.error('Failed to fetch profile:', error);
    } finally {
      setLoading(false);
    }
  };

  const handleLogout = async () => {
    try {
      await fetch('http://localhost:8080/api/oauth/logout', {
        method: 'POST',
        credentials: 'include'
      });
      
      localStorage.removeItem('user');
      window.location.href = '/login';
    } catch (error) {
      console.error('Logout failed:', error);
    }
  };

  const refreshToken = async () => {
    try {
      const response = await fetch('http://localhost:8080/api/oauth/refresh', {
        method: 'POST',
        credentials: 'include'
      });
      
      if (response.ok) {
        alert('Token refreshed successfully!');
      } else {
        alert('Token refresh failed');
      }
    } catch (error) {
      console.error('Token refresh failed:', error);
    }
  };

  if (loading) return <div>Loading profile...</div>;
  if (!user) return <div>No user data available</div>;

  return (
    <div className="user-profile">
      <h2>User Profile</h2>
      <div className="profile-info">
        {user.pictureUrl && (
          <img 
            src={user.pictureUrl} 
            alt="Profile" 
            className="profile-picture"
          />
        )}
        <div className="profile-details">
          <p><strong>Name:</strong> {user.name || 'N/A'}</p>
          <p><strong>Email:</strong> {user.email || 'N/A'}</p>
          <p><strong>Provider:</strong> {user.provider}</p>
          <p><strong>User ID:</strong> {user.id}</p>
          {user.phone && <p><strong>Phone:</strong> {user.phone}</p>}
        </div>
      </div>
      
      <div className="profile-actions">
        <button onClick={refreshToken} className="btn-secondary">
          Refresh Token
        </button>
        <button onClick={handleLogout} className="btn-danger">
          Logout
        </button>
      </div>
    </div>
  );
};

// AuthContext.jsx - Context for managing authentication state
import React, { createContext, useContext, useState, useEffect } from 'react';

const AuthContext = createContext();

export const useAuth = () => {
  const context = useContext(AuthContext);
  if (!context) {
    throw new Error('useAuth must be used within an AuthProvider');
  }
  return context;
};

export const AuthProvider = ({ children }) => {
  const [user, setUser] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    checkAuthStatus();
  }, []);

  const checkAuthStatus = async () => {
    try {
      const response = await fetch('http://localhost:8080/api/oauth/profile', {
        credentials: 'include'
      });
      
      if (response.ok) {
        const data = await response.json();
        if (data.loggedIn) {
          setUser(data.user);
        }
      }
    } catch (error) {
      console.error('Auth check failed:', error);
    } finally {
      setLoading(false);
    }
  };

  const logout = async () => {
    try {
      await fetch('http://localhost:8080/api/oauth/logout', {
        method: 'POST',
        credentials: 'include'
      });
      setUser(null);
    } catch (error) {
      console.error('Logout failed:', error);
    }
  };

  const value = {
    user,
    loading,
    logout,
    checkAuthStatus
  };

  return (
    <AuthContext.Provider value={value}>
      {children}
    </AuthContext.Provider>
  );
};

// CSS Styles for OAuth2 components
export const oauth2Styles = `
.social-login-container {
  max-width: 400px;
  margin: 50px auto;
  padding: 30px;
  border-radius: 10px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  text-align: center;
}

.social-buttons {
  display: flex;
  flex-direction: column;
  gap: 15px;
  margin-top: 30px;
}

.social-btn {
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 12px;
  padding: 12px 20px;
  border-radius: 8px;
  text-decoration: none;
  font-weight: 600;
  font-size: 16px;
  transition: all 0.3s ease;
  border: 2px solid transparent;
}

.google-btn {
  background: #fff;
  color: #333;
  border-color: #dadce0;
}

.google-btn:hover {
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.15);
}

.facebook-btn {
  background: #1877f2;
  color: white;
}

.facebook-btn:hover {
  background: #166fe5;
}

.apple-btn {
  background: #000;
  color: white;
}

.apple-btn:hover {
  background: #333;
}

.oauth-redirect-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  min-height: 50vh;
  text-align: center;
}

.spinner {
  width: 40px;
  height: 40px;
  border: 4px solid #f3f3f3;
  border-top: 4px solid #3498db;
  border-radius: 50%;
  animation: spin 1s linear infinite;
  margin-bottom: 20px;
}

@keyframes spin {
  0% { transform: rotate(0deg); }
  100% { transform: rotate(360deg); }
}

.user-profile {
  max-width: 600px;
  margin: 20px auto;
  padding: 30px;
  border-radius: 10px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
}

.profile-info {
  display: flex;
  gap: 20px;
  margin-bottom: 30px;
}

.profile-picture {
  width: 80px;
  height: 80px;
  border-radius: 50%;
  object-fit: cover;
}

.profile-details p {
  margin: 8px 0;
}

.profile-actions {
  display: flex;
  gap: 15px;
}

.btn-secondary {
  background: #6c757d;
  color: white;
  border: none;
  padding: 10px 20px;
  border-radius: 5px;
  cursor: pointer;
}

.btn-danger {
  background: #dc3545;
  color: white;
  border: none;
  padding: 10px 20px;
  border-radius: 5px;
  cursor: pointer;
}

.error-message {
  color: #dc3545;
  font-weight: 600;
}
`;