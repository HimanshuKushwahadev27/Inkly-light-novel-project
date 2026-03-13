import { HttpClient } from '@angular/common/http';
import { Component, inject } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import {  MatButtonModule } from '@angular/material/button';
import {  MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';

@Component({
  selector: 'app-create-genre-page',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule
  ],
  templateUrl: './create-genre-page.component.html',
  styleUrl: './create-genre-page.component.scss',
})
export class CreateGenrePageComponent {

  private fb = inject( FormBuilder);
  private http = inject( HttpClient);

  genreForm = this.fb.group({
    name: ['', Validators.required],
    description: ['', Validators.required]
  })

  createGenre(){
    if(this.genreForm.invalid)return;

    this.http.post('/api/book/genres/create', this.genreForm.value)
      .subscribe({
        next: () =>{
          alert("Genre created successfully");
        },
        error:() =>{
          alert("Failed to create genre");
        }
      });
  }
}
