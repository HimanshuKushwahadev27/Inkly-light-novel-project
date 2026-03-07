import { AuthConfig } from "angular-oauth2-oidc";

export const authConfig : AuthConfig = {

  issuer: 'http://localhost:8181/realms/Library-realm',
  redirectUri: window.location.origin,
  clientId: 'library-frontend',
  responseType: 'code',
  scope: 'openid profile email',
  showDebugInformation: true,
  requireHttps: false
}