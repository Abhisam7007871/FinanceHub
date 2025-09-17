# OAuth2 Social Login Integration Guide

## 🚀 Complete Setup Instructions

### 1. Provider Setup (Get Client IDs & Secrets)

#### Google OAuth2 Setup
1. Go to [Google Cloud Console](https://console.cloud.google.com/)
2. Create a new project or select existing
3. Enable Google+ API
4. Go to Credentials → Create OAuth 2.0 Client ID
5. Set redirect URI: `http://localhost:8080/login/oauth2/code/google`
6. Copy Client ID and Client Secret

#### Facebook OAuth2 Setup
1. Go to [Facebook Developers](https://developers.facebook.com/)
2. Create a new app
3. Add Facebook Login product
4. Set redirect URI: `http://localhost:8080/login/oauth2/code/facebook`
5. Copy App ID and App Secret

#### Apple Sign In Setup
1. Go to [Apple Developer](https://developer.apple.com/)
2. Create a Services ID
3. Configure Sign In with Apple
4. Set redirect URI: `http://localhost:8080/login/oauth2/code/apple`
5. Generate private key (.p8 file)
6. Note: Apple requires JWT client secret generation (see AppleClientSecretGenerator.java)

### 2. Update Configuration

Replace placeholders in `application.properties`:

```properties
# Replace with your actual values
spring.security.oauth2.client.registration.google.client-id=YOUR_ACTUAL_GOOGLE_CLIENT_ID
spring.security.oauth2.client.registration.google.client-secret=YOUR_ACTUAL_GOOGLE_CLIENT_SECRET

spring.security.oauth2.client.registration.facebook.client-id=YOUR_ACTUAL_FACEBOOK_APP_ID
spring.security.oauth2.client.registration.facebook.client-secret=YOUR_ACTUAL_FACEBOOK_APP_SECRET

spring.security.oauth2.client.registration.apple.client-id=YOUR_ACTUAL_APPLE_CLIENT_ID
spring.security.oauth2.client.registration.apple.client-secret=YOUR_ACTUAL_APPLE_CLIENT_SECRET

# Change JWT secret in production
jwt.secret=your-super-secret-jwt-key-change-in-production
```

### 3. Build & Run

```bash
# Build the project
mvn clean install

# Run the application
mvn spring-boot:run
```

### 4. Test OAuth2 Flow

#### Option A: Browser Testing
1. Open: `http://localhost:8080/oauth2/authorization/google`
2. Complete Google login
3. You'll be redirected to frontend with cookies set
4. Check profile: `http://localhost:8080/api/oauth/profile`

#### Option B: Frontend Integration
1. Use the provided React components in `react-oauth2-components.jsx`
2. Or use the HTML page in `frontend-oauth2-integration.html`

#### Option C: Postman Testing
1. Import `postman-oauth2-collection.json`
2. Use browser for OAuth flow
3. Test API endpoints with cookies

### 5. Integration with Existing Controllers

Your existing controllers will now work with OAuth2 users:

```java
// Your existing AuthController at /api/auth still works
// OAuth2 controller is at /api/oauth (no conflict)

// Dashboard, Accounts, Transactions controllers will work with OAuth2 users
// JWT filter handles both cookie and Authorization header authentication
```

### 6. Database Schema

The OAuth2 integration adds these tables:
- `app_user` - OAuth2 user data
- `refresh_token` - JWT refresh tokens

Your existing `users` table remains unchanged and is linked via `app_user.user_id`.

### 7. Frontend Integration Examples

#### React Login Button
```jsx
<a href="http://localhost:8080/oauth2/authorization/google">
  Login with Google
</a>
```

#### Check Authentication Status
```javascript
const response = await fetch('/api/oauth/profile', {
  credentials: 'include' // Important for cookies
});
const data = await response.json();
if (data.loggedIn) {
  // User is authenticated
}
```

#### Call Protected APIs
```javascript
// Works automatically with cookies
const accounts = await fetch('/api/accounts', {
  credentials: 'include'
});

// Or with Authorization header
const accounts = await fetch('/api/accounts', {
  headers: {
    'Authorization': `Bearer ${accessToken}`
  }
});
```

### 8. Production Checklist

- [ ] Use HTTPS and set `cookie.setSecure(true)`
- [ ] Store JWT secret in environment variables
- [ ] Update OAuth redirect URIs to production domain
- [ ] Enable CSRF protection for cookie-based auth
- [ ] Set proper CORS origins
- [ ] Rotate JWT signing keys regularly
- [ ] Implement rate limiting on auth endpoints

### 9. API Endpoints

#### OAuth2 Endpoints
- `GET /oauth2/authorization/google` - Start Google login
- `GET /oauth2/authorization/facebook` - Start Facebook login  
- `GET /oauth2/authorization/apple` - Start Apple login
- `GET /api/oauth/profile` - Get user profile
- `POST /api/oauth/refresh` - Refresh access token
- `POST /api/oauth/logout` - Logout

#### Your Existing Endpoints (now work with OAuth2)
- `POST /api/auth/login` - Traditional login
- `GET /api/dashboard/profile` - Dashboard
- `GET /api/accounts` - User accounts
- `GET /api/transactions` - User transactions

### 10. Troubleshooting

#### Common Issues:
1. **CORS errors**: Check `cors.allowed-origins` in application.properties
2. **Cookie not sent**: Use `credentials: 'include'` in fetch
3. **OAuth redirect fails**: Verify redirect URIs in provider console
4. **JWT errors**: Check JWT secret configuration
5. **Database errors**: Ensure H2 console is accessible at `/h2-console`

#### Debug Steps:
1. Check application logs for OAuth2 flow
2. Verify cookies are set in browser dev tools
3. Test JWT token parsing with online JWT decoder
4. Check database for user creation in `app_user` table

### 11. Apple Sign In Special Setup

For Apple Sign In, you need to generate JWT client secret:

```java
// Use AppleClientSecretGenerator.java
String clientSecret = appleGenerator.generateClientSecret(
    "YOUR_KEY_ID", 
    "YOUR_TEAM_ID", 
    "YOUR_CLIENT_ID", 
    Path.of("AuthKey_YOUR_KEY_ID.p8")
);
```

## 🎉 You're Ready!

Your Spring Boot app now supports:
- ✅ Google OAuth2 login
- ✅ Facebook OAuth2 login  
- ✅ Apple Sign In
- ✅ JWT token management
- ✅ Cookie-based authentication
- ✅ Integration with existing user system
- ✅ Frontend components
- ✅ Postman collection for testing

The OAuth2 system works alongside your existing authentication - users can login via social providers OR your traditional email/password system.