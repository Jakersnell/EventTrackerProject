import { ProductsControlsSidebarComponent } from '../../components/products-controls-sidebar/products-controls-sidebar.component';
import { Component, OnInit } from '@angular/core';
import {
  NgbAccordionModule,
  NgbPaginationModule,
} from '@ng-bootstrap/ng-bootstrap';
import { ProductService } from '../../services/product.service';
import { Product } from '../../models/product';
import { CommonModule, NgOptimizedImage } from '@angular/common';
import { RouterLink } from '@angular/router';
import { DecimalRatingStarsComponent } from '../../components/decimal-rating-stars/decimal-rating-stars.component';
import { ProductSearchParams } from '../../models/product-search-params';
import { Page } from '../../models/page';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [
    CommonModule,
    NgbAccordionModule,
    ProductsControlsSidebarComponent,
    DecimalRatingStarsComponent,
    NgOptimizedImage,
    RouterLink,
    NgbPaginationModule,
  ],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css',
})
export class HomeComponent implements OnInit {
  products: Product[] = [];
  params: ProductSearchParams = new ProductSearchParams();
  page: Page<Product> = new Page();

  constructor(private productService: ProductService) {}

  ngOnInit(): void {
    this.reload();
  }

  reload(): void {
    const params = this.params;
    this.productService.makePageRequest(params).subscribe({
      next: (page) => {
        this.refreshContent(page);
      },
    });
  }

  refreshContent(page: Page<Product>): void {
    this.page = page;
  }

  displayPagination(): boolean {
    return this.page.totalNumElements < this.products.length;
  }

  updateFiltering(params: ProductSearchParams): void {
    this.params.pageSize = params.pageSize;
    this.params.groupBy = params.groupBy;
    this.params.orderBy = params.orderBy;
    this.params.discontinued = params.discontinued;
    this.params.minRating = params.minRating;
    this.params.categories = [...params.categories];
    // DO NOT INCLUDE SEARCH QUERY OR PAGE NUM
    this.reload();
  }
}
