import {
  Component,
  EventEmitter,
  Input,
  Output,
  TemplateRef,
  inject,
} from '@angular/core';
import {
  ModalDismissReasons,
  NgbDatepickerModule,
  NgbModal,
  NgbRatingModule,
} from '@ng-bootstrap/ng-bootstrap';
import { FormsModule } from '@angular/forms';
import { ProductReviewService } from '../../services/product-review.service';
import { ProductReview } from '../../models/product-review';

@Component({
  selector: 'make-review-form',
  standalone: true,
  imports: [NgbDatepickerModule, FormsModule, NgbRatingModule],
  templateUrl: './make-review-form.component.html',
  styleUrl: './make-review-form.component.css',
})
export class MakeReviewFormComponent {
  @Input() productId: number = 0;
  @Output() public refreshEvent = new EventEmitter<ProductReview>();
  private modalService;
  private prs;
  title = '';
  reviewContent = '';
  rating = 5;
  closeResult = '';

  constructor(modalService: NgbModal, prs: ProductReviewService) {
    this.modalService = modalService;
    this.prs = prs;
  }

  open(content: TemplateRef<any>) {
    this.modalService
      .open(content, { ariaLabelledBy: 'modal-basic-title' })
      .result.then(
        (result) => {
          this.closeResult = `Closed with: ${result}`;
          this.title = '';
          this.reviewContent = '';
          this.rating = 5;
        },
        (reason) => {
          this.closeResult = `Dismissed ${this.getDismissReason(reason)}`;
          this.title = '';
          this.reviewContent = '';
          this.rating = 5;
        }
      );
  }

  private getDismissReason(reason: any): string {
    switch (reason) {
      case ModalDismissReasons.ESC:
        return 'by pressing ESC';
      case ModalDismissReasons.BACKDROP_CLICK:
        return 'by clicking on a backdrop';
      default:
        return `with: ${reason}`;
    }
  }

  private formIsValid(): boolean {
    return (
      this.title.replace(/\s/g, '') !== '' &&
      this.reviewContent.replace(/\s/g, '') !== ''
    );
  }

  submitForm(): void {
    const review = {
      'title': this.title,
      'content': this.reviewContent,
      rating: this.rating,
    };
    this.prs.createReview(this.productId, review).subscribe({
      next: (value: ProductReview) => {
        this.modalService.dismissAll('Submit');
        this.refreshEvent.emit(value);
        console.log('emitting value');
      },
      error: (err) => {
        console.log(err);
      },
    });
  }

}
