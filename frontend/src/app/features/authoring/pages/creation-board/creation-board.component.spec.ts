import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreationBoardComponent } from './creation-board.component';

describe('CreationBoardComponent', () => {
  let component: CreationBoardComponent;
  let fixture: ComponentFixture<CreationBoardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CreationBoardComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CreationBoardComponent);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
