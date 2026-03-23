import { Component, inject, OnInit, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { HeaderComponent } from './shared/header/header.component';
import { AuthService } from './core/auth/service/auth.service';
import { MatSidenavModule } from '@angular/material/sidenav';
import { UserBookmarksComponent } from './features/users/pages/user-bookmarks/user-bookmarks.component';

@Component({
  selector: 'app-root',
  imports: [
    RouterOutlet,
    HeaderComponent,
    MatSidenavModule,
    HeaderComponent,
    RouterOutlet,
    UserBookmarksComponent],
  templateUrl: './app.html',
  styleUrl: './app.scss'
})
export class App implements OnInit{
  protected readonly title = signal('Library-frontend');

  private oauthService = inject(AuthService)
  ngOnInit(): void {
    this.oauthService.initLogin();
  }
}
