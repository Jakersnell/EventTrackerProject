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
  private static BASE_URL: string = 'http://localhost:8085';
  private static URL: string = ProductService.BASE_URL + '/api/products';
  private productBuffer: Product[] = [];

  clearBuffer(): void {
    this.productBuffer = [];
  }

  checkBufferById(id: number): Product | null {
    return this.productBuffer.filter((product) => product.id === id)[0] || null;
  }

  constructor(private http: HttpClient) {}
  // private products: Product[] = [
  //   new Product(
  //     1,
  //     'Nintendo Switch',
  //     this.loremIpsum,
  //     'https://m.media-amazon.com/images/I/51Gz7IimgoL._AC_UF894,1000_QL80_.jpg',
  //     3
  //   ),
  //   new Product(
  //     2,
  //     'Liquid Death',
  //     this.loremIpsum,
  //     'https://i5.walmartimages.com/asr/4e20b37b-8b55-4de3-afdf-5445c5a39243.8832d0abd57e0da8d907ca605719553b.jpeg?odnHeight=768&odnWidth=768&odnBg=FFFFFF',
  //     4
  //   ),
  //   new Product(
  //     3,
  //     'Gameboy Advance',
  //     this.loremIpsum,
  //     'https://i.etsystatic.com/15746134/r/il/913526/1643427731/il_fullxfull.1643427731_mu24.jpg',
  //     5
  //   ),
  //   new Product(
  //     5,
  //     'Sony Playstation 5',
  //     this.loremIpsum,
  //     'https://media.direct.playstation.com/is/image/sierialto/PS5-Hero-Packshot-us',
  //     3
  //   ),
  // ];

  // public getAll(data: any = {}): Product[] {
  //   return [...this.products];
  // }

  // public getProductById(id: number): Product {
  //   return this.products.filter((product: Product) => id === product.id)[0];
  // }

  // public productExists(id: number): boolean {
  //   return (
  //     (this.products.filter((product: Product) => id === product.id)[0] ||
  //       null) !== null
  //   );
  // }

  public getAll(params: any = {}): Observable<Product[]> {
    const url = ProductService.URL;
    return this.http.get<Product[]>(url);
  }

  public getProductById(id: number): Observable<Product> {
    let product = this.checkBufferById(id);
    let observable;
    if (product) {
      observable = of(product);
    } else {
      const url = `${ProductService.URL}/${id}`;
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
    const endpoint = `${environment.API_URL}/api/products-staging?${params.toUrlParams()}`;
    return this.http
      .get<Page<Product>>(endpoint)
      .pipe(
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
