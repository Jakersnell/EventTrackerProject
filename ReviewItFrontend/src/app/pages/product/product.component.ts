import { ProductReviewService } from './../../services/product-review.service';
import { Component, OnInit } from '@angular/core';
import { Title } from '@angular/platform-browser';
import { ActivatedRoute, Router } from '@angular/router';
import { Product } from '../../models/product';
import { ProductService } from '../../services/product.service';
import { CommonModule, NgOptimizedImage } from '@angular/common';
import { NgbRatingModule, NgbScrollSpy } from '@ng-bootstrap/ng-bootstrap';
import { DecimalRatingStarsComponent } from '../../components/decimal-rating-stars/decimal-rating-stars.component';
import { ProductReview } from '../../models/product-review';
import { ReviewsListComponent } from '../../components/reviews-list/reviews-list.component';
import { MakeReviewFormComponent } from '../../components/make-review-form/make-review-form.component';
import { AuthService } from '../../auth/services/auth.service';

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
    MakeReviewFormComponent,
  ],
  templateUrl: './product.component.html',
  styleUrl: './product.component.css',
})
export class ProductComponent implements OnInit {
  product: Product = new Product();
  reviews: ProductReview[] = [];
  topReviews: ProductReview[] = [];
  isLoggedIn: boolean;

  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private titleService: Title,
    private productService: ProductService,
    private prs: ProductReviewService,
    private authService: AuthService
  ) {
    this.isLoggedIn = authService.userIsLoggedIn;
    authService.userIsLoggedInEvent.subscribe((status: boolean) => {
      this.isLoggedIn = status;
    });
    this.product.id = parseInt(this.route.snapshot.paramMap.get('id') || '0');
  }

  ngOnInit(): void {
    this.reloadProduct();
  }

  refresh(event: ProductReview) {
    console.log('refresh event triggered');
    this.reloadProduct();
  }

  reloadProduct(): void {
    this.productService.getProductById(this.product.id).subscribe({
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
        this.reviews = reviews;
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

  redirectToLogin(): void {
    this.router.navigateByUrl('login');
  }
}
