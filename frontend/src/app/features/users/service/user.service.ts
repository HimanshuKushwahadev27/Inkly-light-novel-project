import { HttpClient } from '@angular/common/http';
import { inject, Injectable, signal } from '@angular/core';
import { UUID } from 'crypto';
import { Observable } from 'rxjs';


export interface userProfile{
  id: UUID;
  displayName: string;
  imageurl ?: string;
  bio ?: string;
  createdAt: Date;
}
@Injectable({
  providedIn: 'root',
})
export class UserService {
  
  currentUser = signal<any | null>(null);

  private http = inject(HttpClient);

  getCurrentUser(): Observable<userProfile>{
    return this.http.get<userProfile>('/api/user/get');
  }

  loadUser(){
    this.getCurrentUser().subscribe({
      next: (user) => this.currentUser.set(user),
      error: () => this.currentUser.set(null)
    })
  }
}
