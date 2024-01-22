import { HttpParams } from '@angular/common/http';
import { Category } from './category';

export class ProductSearchParams {
  public pageNum: number;
  public pageSize: number;
  public searchQuery: string | null;
  public groupBy: string | null;
  public orderBy: string | null;
  public discontinued: boolean | null;
  public minRating: number | null;
  public categories: Category[];

  constructor(
    pageNum: number = 0,
    pageSize: number = 9,
    searchQuery: string | null = null,
    groupBy: string | null = null,
    orderBy: string | null = null,
    discontinued: boolean | null = null,
    minRating: number | null = 0, // for some reason this is disabling products with no reviews!
    categories: Category[] = []
  ) {
    this.pageNum = pageNum + 1;
    this.pageSize = pageSize;
    this.searchQuery = searchQuery;
    this.groupBy = groupBy;
    this.orderBy = orderBy;
    this.discontinued = discontinued;
    this.minRating = minRating;
    this.categories = categories;
  }

  public addCategory(category: Category): void {
    if (this.categories === null) {
      this.categories = [];
    }
    this.categories.push(category);
  }

  public toUrlParams(): any {
    let params: any = {};
    params.pageNum = this.pageNum - 1;
    params.pageSize = this.pageSize;
    if (this.searchQuery != null && this.searchQuery.replace(/\s/g, '') != '') {
      params.searchQuery = this.searchQuery;
    }
    if (this.groupBy != null && this.groupBy.replace(/\s/g, '') != '') {
      params.groupBy = this.groupBy;
    }
    if (this.orderBy != null && this.orderBy.replace(/\s/g, '') != '') {
      params.orderBy = this.orderBy;
    }
    if (this.discontinued != null) {
      params.discontinued = this.discontinued;
    }
    if (this.minRating != null && 0 < this.minRating) {
      params.minRating = this.minRating;
    }
    if (this.categories != null && this.categories.length != 0) {
      const ids = this.categories.map((cat: Category) => cat.id);
      params.categories = ids.toString();
    }
    return params;
  }
}

export enum SortGroups {
  popularity = 'POPULARITY',
  reviews = 'NUM_REVIEWS',
  ratings = 'NUM_RATINGS',
}

export enum SortOrder {
  ascending = 'ASC',
  descending = 'DESC',
}
