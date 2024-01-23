import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { HttpClient } from '@angular/common/http';
import { Observable, catchError, throwError } from 'rxjs';
import { Page } from '../models/page';
import { Category } from '../models/category';
import { CategoryRequestDTO } from '../models/category-request-dto';

@Injectable({
  providedIn: 'root',
})
export class CategoryService {
  private url = `${environment.API_URL}/api/categories`;
  constructor(private http: HttpClient) {}

  public getCategories(params: CategoryRequestDTO): Observable<Page<Category>> {
    const endpoint = this.url;
    const urlParams = params.toUrlParams();
    return this.http.get<Page<Category>>(endpoint, { params: urlParams }).pipe(
      catchError((err: any) => {
        console.log(err);
        return throwError(
          () => new Error(`
          CategoryService.getCategories(params: CategoryRequestDTO): Observable<Page<Category>>;
          Error while attempting GET to ${endpoint}.
          With params: ${JSON.stringify(urlParams)}.
          ${err}
          `)
        );
      })
    );
  }
}
