import { Component, inject } from '@angular/core';
import { UserService } from '../service/user.service';
import { MatCardModule } from '@angular/material/card';


@Component({
  selector: 'app-profile',
  imports: [MatCardModule],
  templateUrl: './profile.component.html',
  styleUrl: './profile.component.scss',
})
export class ProfileComponent {

  public userService = inject(UserService)
}
