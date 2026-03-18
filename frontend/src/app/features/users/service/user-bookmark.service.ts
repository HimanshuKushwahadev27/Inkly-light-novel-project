import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { UUID } from 'crypto';
import { Observable } from 'rxjs';

export interface bookmarkResponse{
  id: UUID;
  bookId: UUID;
  chapterId: UUID;
  createdAt: Date;
}

export interface bookmarkRequest{
  bookId: UUID;
  chapterId: UUID;
}

@Injectable({
  providedIn: 'root',
})
export class UserBookmarkService {
  
  private http = inject(HttpClient);

  createBookmark(request: bookmarkRequest): Observable<bookmarkResponse>{
    return this.http.post<bookmarkResponse>('/api/user/bookmark/create', request);
  }

  getBookmarks(): Observable<bookmarkResponse[]>{
    return this.http.get<bookmarkResponse[]>('/api/user/bookmark/get')
  }

  deleteBookmark(id: UUID): Observable<string>{
    return this.http.delete<string>(`/api/user/bookmark/delete/${id}`)
  }

}
