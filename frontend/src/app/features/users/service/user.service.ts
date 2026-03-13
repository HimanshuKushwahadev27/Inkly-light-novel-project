import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';


export interface userProfile{
  displayName: string;
  profileImageurl ?: string;
  bio ?: string;
}
@Injectable({
  providedIn: 'root',
})
export class UserService {
  
  private http = inject(HttpClient);

  getCurrentUser(): Observable<userProfile>{
    return this.http.get<userProfile>('/api/user/get');
  }
}
