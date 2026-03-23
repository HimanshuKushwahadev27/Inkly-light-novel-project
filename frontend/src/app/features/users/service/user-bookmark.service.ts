import { HttpClient } from '@angular/common/http';
import { inject, Injectable, signal } from '@angular/core';
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

  bookmarks = signal<bookmarkResponse[]>([]);

  createBookmark(request: bookmarkRequest): Observable<bookmarkResponse>{
    return this.http.post<bookmarkResponse>('/api/user/bookmark/create', request);
  }

  getBookmarks(){
    return this.http.get<bookmarkResponse[]>('/api/user/bookmark/get')
      .subscribe({
        next: (data) => this.bookmarks.set(data),
        error: () => this.bookmarks.set([])
      })
  }

  deleteBookmark(id: UUID): Observable<string>{
    return this.http.delete<string>(`/api/user/bookmark/delete/${id}`)
  }

}
