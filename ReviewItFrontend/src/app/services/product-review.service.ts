import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { HttpClient } from '@angular/common/http';
import { Observable, catchError, throwError } from 'rxjs';
import { ProductReview } from '../models/product-review';

@Injectable({
  providedIn: 'root',
})
export class ProductReviewService {
  constructor(private http: HttpClient) {}

  public getReviewsForProduct(productId: number): Observable<ProductReview[]> {
    const endpoint = `${environment.API_URL}/products/${productId}/reviews`;
    return this.http.get<ProductReview[]>(endpoint).pipe(
      catchError((err: any) => {
        console.log(err);
        return throwError(
          () =>
            new Error(`
            ProductReviewService.getReviewsForProduct(productId: number): Observable<ProductReview[]>;
            Error while attempting GET to ${endpoint}.
            ERROR: ${err}
            `)
        );
      })
    );
  }
}
