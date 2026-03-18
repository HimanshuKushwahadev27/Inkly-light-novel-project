import { HttpClient } from '@angular/common/http';
import { Component, inject } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import {  MatButtonModule } from '@angular/material/button';
import {  MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { AuthorService } from '../../service/author-service.service';

@Component({
  selector: 'app-update-author-profile',
  imports:
  [
    ReactiveFormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule
  ]
  ,
  templateUrl: './update-author-profile.component.html',
  styleUrl: './update-author-profile.component.scss',
})
export class UpdateAuthorProfileComponent {

  private fb = inject(FormBuilder);
  private http = inject(HttpClient);
  private router = inject(Router);
  private toastr = inject(ToastrService)
  private authorService = inject(AuthorService);

  updateForm = this.fb.group({
    penName: ['', [Validators.required, Validators.minLength(3)]],
    bio:['']
  })

  updateAuthor(){
    if(this.updateForm.invalid)return;

    this.http.patch(`/api/authors/update/${this.authorService.currentAuthor()?.id}`, this.updateForm.value)
  }
}
