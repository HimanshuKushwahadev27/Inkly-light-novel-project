import { HttpClient } from '@angular/common/http';
import { Component, inject } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import {  MatButtonModule } from '@angular/material/button';
import {  MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { UserService } from '../../service/user.service';


@Component({
  selector: 'app-profile-update',
  standalone: true,
  imports: 
  [        
    ReactiveFormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule
  ],
  templateUrl: './profile-update.component.html',
  styleUrl: './profile-update.component.scss',
})
export class ProfileUpdateComponent {

  private fb = inject(FormBuilder);
  private http = inject(HttpClient);
  private router = inject(Router);
  private toastr = inject(ToastrService)
  private userService = inject(UserService);

  updateForm = this.fb.group({
    id : this.userService.currentUser()?.id,
    displayName: ['', [Validators.required, Validators.minLength(3)]],
    imageUrl: [''],
    bio: ['']
  })

  updateUser(){
    if(this.updateForm.invalid)return;

    this.http.post('/api/user/update', this.updateForm.value)
      .subscribe({
        next: () =>{
       this.toastr.success(
        'User profile updated successfully',
        'Success'
      );

      this.router.navigate(['/']);
        },
         error: (err) => {
      this.toastr.error(
        'Failed to update user profile',
        'Error'
      );
    }
      })   
  }
}
