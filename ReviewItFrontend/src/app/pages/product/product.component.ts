import { Component, OnInit } from '@angular/core';
import { Title } from '@angular/platform-browser';
import { ActivatedRoute } from '@angular/router';
import { Product } from '../../models/product';
import { ProductService } from '../../services/product.service';
import { NgOptimizedImage } from '@angular/common';
import { NgbRatingModule, NgbScrollSpy } from '@ng-bootstrap/ng-bootstrap';
import { DecimalRatingStarsComponent } from '../../components/decimal-rating-stars/decimal-rating-stars.component';

@Component({
  selector: 'app-product',
  standalone: true,
  imports: [
    NgOptimizedImage,
    DecimalRatingStarsComponent,
    NgbRatingModule,
    NgbScrollSpy,
  ],
  templateUrl: './product.component.html',
  styleUrl: './product.component.css',
})
export class ProductComponent implements OnInit {
  product: Product = new Product();

  constructor(
    private route: ActivatedRoute,
    private titleService: Title,
    private productService: ProductService
  ) {}

  ngOnInit(): void {
    // 'id' || '0' is used to satisfy type checker but this shouldnt ever be null due to the guard
    const productId = parseInt(this.route.snapshot.paramMap.get('id') || '0');
    this.productService.getProductById(productId).subscribe({
      next: (product: Product) => {
        this.product = product;
      },
    });
    const title = `Product - ${this.product.name}`;
    this.titleService.setTitle(title);
  }
}
