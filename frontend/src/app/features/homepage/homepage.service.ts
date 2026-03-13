import { HttpClient, HttpParams } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { PageResponse } from '../../core/model/page-response.model';
import { Book } from '../../core/model/book.model';

@Injectable({
  providedIn: 'root',
})
export class HomepageService {

  private http = inject(HttpClient);

  private search(params: Record<string, string | number>) {
    let httpParams = new HttpParams();

    Object.entries(params).forEach(([key, value]) => {
      httpParams = httpParams.set(key, value.toString());
    });

    return this.http.get<PageResponse<Book>>('/api/search', { params: httpParams });
  }

  getNewestBooks() {
    return this.search({
      sort: 'publishedAt,desc',
      page: 0,
      size: 10,
      visibilityStatus: 'PUBLIC',
      lifeCycleStatus: 'PUBLISHED'
    });
  }

  getRomanceBooks() {
    return this.search({
      genre: 'romance',
      page: 0,
      size: 10,
      visibilityStatus: 'PUBLIC',
      lifeCycleStatus: 'PUBLISHED'
    });
  }

  getThrillerBooks() {
    return this.search({
      genre: 'thriller',
      page: 0,
      size: 10,
      visibilityStatus: 'PUBLIC',
      lifeCycleStatus: 'PUBLISHED'
    });
  }

  getFreePreviewBooks() {
    return this.search({
      freePreview: 'true',
      page: 0,
      size: 10,
      visibilityStatus: 'PUBLIC',
      lifeCycleStatus: 'PUBLISHED'
    });
  }

  getOngoingBooks() {
    return this.search({
      lifeCycleStatus: 'ONGOING',
      page: 0,
      size: 10,
      visibilityStatus: 'PUBLIC'
    });
  }

  getCompletedBooks() {
    return this.search({
      lifeCycleStatus: 'COMPLETED',
      page: 0,
      size: 10,
      visibilityStatus: 'PUBLIC'
    });
  }

}