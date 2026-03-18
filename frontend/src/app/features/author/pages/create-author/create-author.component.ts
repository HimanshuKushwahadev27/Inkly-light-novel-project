import { HttpClient } from '@angular/common/http';
import { Component, inject } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import {  MatButtonModule } from '@angular/material/button';
import {  MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-create-author',
  imports:
  [
        ReactiveFormsModule,
        MatFormFieldModule,
        MatInputModule,
        MatButtonModule
  ],
  templateUrl: './create-author.component.html',
  styleUrl: './create-author.component.scss',
})
export class CreateAuthorComponent {

  private fb = inject(FormBuilder);
  private http = inject(HttpClient);
  private router = inject(Router);
  private toastr = inject(ToastrService);

  authorForm = this.fb.group({
      penName: ['', [Validators.required, Validators.minLength(3)]],
      bio: ['']
  })

  createAuthor(){
    if(this.authorForm.invalid) return;

    this.http.post('/api/authors/register', this.authorForm.value)
      .subscribe({
        next: () => {
          this.toastr.success(
            'Author profile completed successfully',
            'Success'
          );

          this.router.navigate(['/'])
        },
        error: (err) => {
           this.toastr.error(
            'Failed to create user profile',
            'Error'
          );
       }
    })
  }
}
