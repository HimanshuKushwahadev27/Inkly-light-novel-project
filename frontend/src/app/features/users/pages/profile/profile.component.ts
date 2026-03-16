import { Component, inject } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { UserService } from '../../service/user.service';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { RouterLink } from '@angular/router';
import { DatePipe } from '@angular/common';

@Component({
  selector: 'app-profile',
  imports: [MatCardModule, MatButtonModule, MatIconModule, RouterLink, DatePipe],
  templateUrl: './profile.component.html',
  styleUrl: './profile.component.scss',
})
export class ProfileComponent {

  public userService = inject(UserService)


}
