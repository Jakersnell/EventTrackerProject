import { CommonModule } from '@angular/common';
import { Component, Input } from '@angular/core';
import { ProductReview } from '../../models/product-review';
import { DecimalRatingStarsComponent } from '../decimal-rating-stars/decimal-rating-stars.component';

@Component({
  selector: 'reviews-list',
  standalone: true,
  imports: [CommonModule, DecimalRatingStarsComponent],
  templateUrl: './reviews-list.component.html',
  styleUrl: './reviews-list.component.css',
})
export class ReviewsListComponent {
  @Input() reviews: ProductReview[] = [];

  reviewIsLast(review: ProductReview, list: ProductReview[]): boolean {
    return list[list.length - 1] === review;
  }
}
