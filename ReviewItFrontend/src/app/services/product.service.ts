import { Injectable } from '@angular/core';
import { Product } from '../models/product';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable, catchError, of, throwError } from 'rxjs';
import { ProductSearchParams } from '../models/product-search-params';
import { Page } from '../models/page';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root',
})
export class ProductService {
  private BASE_URL = environment.API_URL;
  private PUB_URL: string = this.BASE_URL + '/api/products';
  private ADMIN_URL: string = this.BASE_URL + '/api/admin/products';
  private productBuffer: Product[] = [];

  clearBuffer(): void {
    this.productBuffer = [];
  }

  checkBufferById(id: number): Product | null {
    return this.productBuffer.filter((product) => product.id === id)[0] || null;
  }

  constructor(private http: HttpClient) {}

  public getProductById(id: number): Observable<Product> {
    let product = this.checkBufferById(id);
    let observable;
    if (product) {
      observable = of(product);
    } else {
      const url = `${this.PUB_URL}/${id}`;
      observable = this.http.get<Product>(url);
    }
    return observable;
  }

  public productExists(id: number): boolean {
    return true;
  }

  public makePageRequest(
    params: ProductSearchParams
  ): Observable<Page<Product>> {
    const endpoint = `${environment.API_URL}/api/products`;
    const urlParams = params.toUrlParams();
    return this.http.get<Page<Product>>(endpoint, { params: urlParams }).pipe(
      catchError((err: any) => {
        console.log(err);
        return throwError(
          () =>
            new Error(
              `
                ProductService.makePageRequest(params: ProductSearchParams):
                Error while attempting GET with params to the endpoint ${endpoint}.
                Error: ${err}.`
            )
        );
      })
    );
  }
}
