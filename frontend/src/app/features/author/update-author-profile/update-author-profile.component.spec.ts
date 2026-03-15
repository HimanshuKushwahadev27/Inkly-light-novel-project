import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UpdateAuthorProfileComponent } from './update-author-profile.component';

describe('UpdateAuthorProfileComponent', () => {
  let component: UpdateAuthorProfileComponent;
  let fixture: ComponentFixture<UpdateAuthorProfileComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [UpdateAuthorProfileComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UpdateAuthorProfileComponent);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
