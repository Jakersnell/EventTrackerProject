import { inject } from '@angular/core';
import { CanActivateFn } from '@angular/router';
import { ProductService } from '../services/product.service';


export const productExistsGuard: CanActivateFn = (route, state) => {
  const productService = inject(ProductService);
  const productId: number = parseInt(route.params['id']);
  return !isNaN(productId) && productService.productExists(productId);
};
