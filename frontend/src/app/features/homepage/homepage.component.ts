import { Component, inject } from '@angular/core';
import { BookSectionComponent } from '../../shared/component/book-section/book-section.component';
import { HomepageService } from './homepage.service';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { AsyncPipe } from '@angular/common';
@Component({
  selector: 'app-homepage',
  standalone: true,
  imports: [
    BookSectionComponent,
    MatCardModule,
    MatButtonModule,
    MatIconModule,
    AsyncPipe
      ],
  templateUrl: './homepage.component.html',
  styleUrl: './homepage.component.scss',
})
export class HomepageComponent {

  private homepageService = inject(HomepageService);
    newestBooks$ = this.homepageService.getNewestBooks();
    romanceBooks$ = this.homepageService.getRomanceBooks();
    thrillerBooks$ = this.homepageService.getThrillerBooks();
    ongoingBooks$ = this.homepageService.getOngoingBooks();
    completedBooks$ = this.homepageService.getCompletedBooks();
}
