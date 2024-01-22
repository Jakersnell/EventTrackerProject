import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MakeReviewModalComponent } from './make-review-modal.component';

describe('MakeReviewModalComponent', () => {
  let component: MakeReviewModalComponent;
  let fixture: ComponentFixture<MakeReviewModalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MakeReviewModalComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(MakeReviewModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
