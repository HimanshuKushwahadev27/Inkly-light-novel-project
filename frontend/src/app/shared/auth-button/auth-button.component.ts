import { Component, inject, OnInit} from '@angular/core';
import { AuthService } from '../../core/auth/service/auth.service';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import {MatMenuModule} from '@angular/material/menu';
import {MatDividerModule} from '@angular/material/divider';
import { UserService } from '../../features/users/service/user.service';
import { RouterLink } from '@angular/router';
import { AuthorService } from '../../features/author/service/author-service.service';

@Component({
  selector: 'app-auth-button',
  standalone: true,
  imports: 
  [
    MatIconModule,
    MatButtonModule ,
    MatMenuModule,
    MatDividerModule,
    RouterLink
  ],
  templateUrl: './auth-button.component.html',
  styleUrl: './auth-button.component.scss',
})
export class AuthButtonComponent implements OnInit{

  constructor(public authService: AuthService, public userService: UserService) {}

  public authorService = inject(AuthorService);

  ngOnInit(): void {
    this.authorService.loadAuthor();
  }

  login(){
    this.authService.login();
  }

  logout(){
    this.authService.logout();
  }

  avatarFallback(event: Event) {
    (event.target as HTMLImageElement).src = 'assets/default-avatar.png';
  }

}
