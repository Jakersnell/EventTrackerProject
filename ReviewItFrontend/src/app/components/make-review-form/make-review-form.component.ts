import { Component, EventEmitter, Input, Output, TemplateRef, inject } from '@angular/core';
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
  @Output() refreshEvent = new EventEmitter<ProductReview>();
  private modalService = inject(NgbModal);
  private prs = inject(ProductReviewService);
  title = '';
  content = '';
  rating = 5;
  closeResult = '';

  open(content: TemplateRef<any>) {
    this.modalService
      .open(content, { ariaLabelledBy: 'modal-basic-title' })
      .result.then(
        (result) => {
          this.closeResult = `Closed with: ${result}`;
        },
        (reason) => {
          this.closeResult = `Dismissed ${this.getDismissReason(reason)}`;
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

  private submitForm(): void {
    const review = {
      title: this.title,
      content: this.content,
      rating: this.rating,
    };
  }
}
