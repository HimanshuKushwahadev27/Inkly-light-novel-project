import {  Injectable, signal } from '@angular/core';
import {  OAuthService } from 'angular-oauth2-oidc';
import { authConfig } from '../auth.config';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  
  isAuthenticated = signal(false);
  
  constructor(private oauthService: OAuthService) {
    this.configure();
  }

  
  configure(){
    this.oauthService.configure(authConfig);
     this.oauthService
    .loadDiscoveryDocumentAndTryLogin()
    .then(() => {
      this.isAuthenticated.set(
        this.oauthService.hasValidAccessToken()
      )
    });
  }

  login(){
    this.oauthService.initCodeFlow();
  }

  logout(){
    this.oauthService.logOut();
    this.isAuthenticated.set(false);
  }

  getToken(){
    return this.oauthService.getAccessToken();
  }

  isLoggedIn(){
    return this.oauthService.hasValidAccessToken();
  }

  getUser() {
    const claims: any = this.oauthService.getIdentityClaims();
    console.log(claims);
  }

  register(){
    this.oauthService.initCodeFlow(undefined, {
     kc_action : 'register' 
    })
  }

}
