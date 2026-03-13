import { HttpClient } from '@angular/common/http';
import { Component, inject } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import {  MatButtonModule } from '@angular/material/button';
import {  MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { Router } from '@angular/router';

@Component({
  selector: 'app-create-user',
  standalone: true,
  imports: [
        ReactiveFormsModule,
        MatFormFieldModule,
        MatInputModule,
        MatButtonModule
  ],
  templateUrl: './create-user.component.html',
  styleUrl: './create-user.component.scss',
})
export class CreateUserComponent {

  private fb = inject(FormBuilder);
  private http = inject(HttpClient);
  private router = inject(Router);
  
  userForm = this.fb.group({
    displayName: ['', [Validators.required, Validators.minLength(3)]],
    profileImgUrl: [''],
    bio: ['']
  });

  createUser(){
    if(this.userForm.invalid)return;

    this.http.post('/api/user/create', this.userForm.value)
      .subscribe({
        next: () =>{
          this.router.navigate(['/']);
        }
      })
  }

}
