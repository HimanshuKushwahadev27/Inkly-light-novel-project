import { Component, inject } from '@angular/core';
import { UserBookmarkService } from '../../service/user-bookmark.service';

@Component({
  selector: 'app-user-bookmarks',
  imports: [],
  templateUrl: './user-bookmarks.component.html',
  styleUrl: './user-bookmarks.component.scss',
})
export class UserBookmarksComponent {

  public bookmarkService = inject(UserBookmarkService);


  
}
