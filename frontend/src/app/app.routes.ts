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
 }
];
