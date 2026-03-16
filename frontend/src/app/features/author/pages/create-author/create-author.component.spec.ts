import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateAuthorComponent } from './create-author.component';

describe('CreateAuthorComponent', () => {
  let component: CreateAuthorComponent;
  let fixture: ComponentFixture<CreateAuthorComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CreateAuthorComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CreateAuthorComponent);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
