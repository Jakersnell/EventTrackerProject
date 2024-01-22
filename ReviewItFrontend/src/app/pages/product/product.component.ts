import { ProductReviewService } from './../../services/product-review.service';
import { Component, OnInit } from '@angular/core';
import { Title } from '@angular/platform-browser';
import { ActivatedRoute } from '@angular/router';
import { Product } from '../../models/product';
import { ProductService } from '../../services/product.service';
import { CommonModule, NgOptimizedImage } from '@angular/common';
import { NgbRatingModule, NgbScrollSpy } from '@ng-bootstrap/ng-bootstrap';
import { DecimalRatingStarsComponent } from '../../components/decimal-rating-stars/decimal-rating-stars.component';
import { ProductReview } from '../../models/product-review';
import { ReviewsListComponent } from '../../components/reviews-list/reviews-list.component';
import { MakeReviewFormComponent } from '../../components/make-review-form/make-review-form.component';

@Component({
  selector: 'app-product',
  standalone: true,
  imports: [
    NgOptimizedImage,
    DecimalRatingStarsComponent,
    NgbRatingModule,
    NgbScrollSpy,
    CommonModule,
    ReviewsListComponent,
    MakeReviewFormComponent
  ],
  templateUrl: './product.component.html',
  styleUrl: './product.component.css',
})
export class ProductComponent implements OnInit {
  product: Product = new Product();
  reviews: ProductReview[] = [];
  topReviews: ProductReview[] = [];

  constructor(
    private route: ActivatedRoute,
    private titleService: Title,
    private productService: ProductService,
    private prs: ProductReviewService
  ) {}

  ngOnInit(): void {
    this.reloadProduct();
  }

  reloadProduct(): void {
    const productId = parseInt(this.route.snapshot.paramMap.get('id') || '0');
    this.productService.getProductById(productId).subscribe({
      next: (product: Product) => {
        this.product = product;
        this.reloadReviews();
      },
    });
    const title = `Product - ${this.product.name}`;
    this.titleService.setTitle(title);
  }

  reloadReviews(): void {
    this.prs.getReviewsForProduct(this.product.id).subscribe({
      next: (reviews: ProductReview[]) => {
        for (let i = 0; i < 3; i++) {
          if (reviews.length <= i) {
            break;
          }
          const review = reviews.pop() || new ProductReview();
          this.topReviews.push(review);
        }
      },
    });
  }

  getReviewCount(): string {
    const numReviews = this.topReviews.length + this.reviews.length;
    let count;
    if (numReviews == 0) {
      count = '';
    } else if (numReviews == 1) {
      count = '1 review';
    } else {
      count = numReviews + ' reviews';
    }
    return count;
  }

}
