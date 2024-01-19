import { ProductsControlsSidebarComponent } from '../../components/products-controls-sidebar/products-controls-sidebar.component';
import { Component } from '@angular/core';
import { NgbAccordionModule } from '@ng-bootstrap/ng-bootstrap';
import { ProductService } from '../../services/product.service';
import { Product } from '../../models/product';
import { PaginationControlComponent } from '../../components/pagination-control/pagination-control.component';
import { CommonModule, NgOptimizedImage } from '@angular/common';
import { RouterLink } from '@angular/router';
import { DecimalRatingStarsComponent } from '../../components/decimal-rating-stars/decimal-rating-stars.component';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [
    CommonModule,
    NgbAccordionModule,
    ProductsControlsSidebarComponent,
    DecimalRatingStarsComponent,
    PaginationControlComponent,
    NgOptimizedImage,
    RouterLink,
  ],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css',
})
export class HomeComponent {
  page = 1;
  displayAmount = 18;
  totalNumProducts = 0; // change to use api/products/summary.size
  products: Product[] = [];

  constructor(private productsSevice: ProductService) {
    productsSevice.getAll().subscribe({
      next: (products: Product[]) => (this.products = products),
    });
    this.totalNumProducts = this.products.length; // change to use api/products/summary.size
  }

  displayPagination(): boolean {
    return this.displayAmount < this.products.length;
  }
}
