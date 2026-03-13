import { Component, Input } from '@angular/core';
import { Book } from '../../../core/model/book.model';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
@Component({
  selector: 'app-book-section',
  imports: [MatCardModule, MatButtonModule, MatIconModule],
  templateUrl: './book-section.component.html',
  styleUrl: './book-section.component.scss',
})
export class BookSectionComponent {

    @Input() title!: string;
    @Input() books: Book[] =[] ;
    @Input() icon?: string;
}
