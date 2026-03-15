import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UserBookmarksComponent } from './user-bookmarks.component';

describe('UserBookmarksComponent', () => {
  let component: UserBookmarksComponent;
  let fixture: ComponentFixture<UserBookmarksComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [UserBookmarksComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UserBookmarksComponent);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
