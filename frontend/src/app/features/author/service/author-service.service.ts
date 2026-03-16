import { HttpClient } from '@angular/common/http';
import { inject, Injectable, signal } from '@angular/core';
import { error } from 'console';
import { UUID } from 'crypto';
import { Observable } from 'rxjs';


export interface authorProfile{
  id: UUID;
  penName: string;
  bio ?: string;
  isVerified: boolean;
  createdAt: Date;
}
@Injectable({
  providedIn: 'root',
})
export class AuthorService {
  
  currentAuthor = signal<any | null>(null);

  private http = inject(HttpClient);

  getCurrentAuthor(): Observable<authorProfile>{
    return this.http.get<authorProfile>('/api/authors/');
  }

  loadAuthor(){
    this.getCurrentAuthor().subscribe({
      next: (author) => this.currentAuthor.set(author),
      error: () => this.currentAuthor.set(null)
    })
  }
}
