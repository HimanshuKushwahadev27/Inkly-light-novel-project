import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateGenrePageComponent } from './create-genre-page.component';

describe('CreateGenrePageComponent', () => {
  let component: CreateGenrePageComponent;
  let fixture: ComponentFixture<CreateGenrePageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CreateGenrePageComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CreateGenrePageComponent);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
