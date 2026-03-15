import { Routes } from '@angular/router';
import { profileGuard } from './core/guards/profile-guard';

export const routes: Routes = [
 {path : 'genres', 
  canActivate: [profileGuard],
  loadComponent: () =>   import('./features/catalog/pages/genre-page/genre-page.component')
      .then(m => m.GenrePageComponent)
 },
 {path : 'admin/create-genre',
  loadComponent: () => import('./features/catalog/pages/create-genre-page/create-genre-page.component')
    .then(m => m.CreateGenrePageComponent)
 },
 {path: 'create-user',
  loadComponent: () => import('./features/users/pages/create-user/create-user.component')
    .then(m => m.CreateUserComponent)
 },
 {
  canActivate: [profileGuard],
  path: 'user-profile',
  loadComponent: () => import('./features/users/pages/profile/profile.component')
    .then(m => m.ProfileComponent)
 },
 {
  canActivate: [profileGuard],
  path: 'user-profile/update',
  loadComponent: () => import('./features/users/pages/profile-update/profile-update.component')
    .then(m=>m.ProfileUpdateComponent)
 },
 {
  canActivate: [profileGuard],
  path: 'register-author',
  loadComponent: () => import('./features/author/create-author/create-author.component')
    .then(m => m.CreateAuthorComponent)
 },
 {
  canActivate: [profileGuard],
  path: 'user-library',
  loadComponent: () => import('./features/users/pages/user-library/user-library.component')
    .then(m => m.UserLibraryComponent)
 },
  {
  canActivate: [profileGuard],
  path: 'user-bookmark',
  loadComponent: () => import('./features/users/pages/user-bookmarks/user-bookmarks.component')
    .then(m => m.UserBookmarksComponent)
 }
];
