import { Component, inject, OnInit, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { HeaderComponent } from './shared/header/header.component';
import { AuthService } from './core/auth/service/auth.service';
import { HomepageComponent } from './features/homepage/homepage.component';
@Component({
  selector: 'app-root',
  imports: [RouterOutlet, HeaderComponent, HomepageComponent],
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
