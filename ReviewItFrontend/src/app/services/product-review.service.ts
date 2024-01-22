import { AuthService } from './../auth/services/auth.service';
import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { HttpClient } from '@angular/common/http';
import { Observable, catchError, throwError } from 'rxjs';
import { ProductReview } from '../models/product-review';

@Injectable({
  providedIn: 'root',
})
export class ProductReviewService {
  constructor(private http: HttpClient, private authService: AuthService) {}

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

  public createReview(
    productId: number,
    review: any
  ): Observable<ProductReview> {
    const auth = this.authService.getAuthToken()?.token;
    const endpoint = `${environment.API_URL}/products/${productId}/reviews?auth=${auth}`;
    return this.http.post<ProductReview>(endpoint, review).pipe(
      catchError((err: any) => {
        console.log(err);
        return throwError(
          () =>
            new Error(`
            ProductReviewService.createReview(productId: number, review: any): Observable<ProductReview[]>;
            Error while attempting POST to ${endpoint}.
            BODY: ${JSON.stringify(review)}
            ERROR: ${err}
            `)
        );
      })
    );
  }
}
