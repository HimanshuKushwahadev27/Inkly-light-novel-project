export interface Book {
  id: string;
  bookId: string;
  title: string;
  description: string;
  price: number;
  authorName: string[];
  genreName: string[];
  freePreview: boolean;
  publishedAt: number;
  updatedAt: number;
  lifeCycleStatus: 'DRAFT' | 'ONGOING' | 'COMPLETED';
  visibilityStatus: 'PRIVATE' | 'PUBLIC' | 'DELETE';
}