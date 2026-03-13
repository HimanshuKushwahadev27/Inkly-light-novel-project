import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { UserService } from '../../features/users/service/user.service';
import { catchError, map, of } from 'rxjs';
import { OAuthService } from 'angular-oauth2-oidc';

export const profileGuard: CanActivateFn = (route, state) => {

  const userService = inject(UserService)
  const router = inject(Router);
  const oauthService = inject(OAuthService);

  if(!oauthService.hasValidAccessToken()){
    oauthService.initLoginFlow();
    return of(false);
  }
  return userService.getCurrentUser().pipe(
    map(() => true),
    catchError(() => {
      router.navigate(['/create-user']);
      return of(false);
    })
    )
};
