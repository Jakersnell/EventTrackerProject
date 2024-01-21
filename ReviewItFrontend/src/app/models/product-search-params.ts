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
  public categories: Category[] | null;

  constructor(
    pageNum: number = 1,
    pageSize: number = 9,
    searchQuery: string | null = null,
    groupBy: string | null = null,
    orderBy: string | null = null,
    discontinued: boolean | null = null,
    minRating: number | null = 0, // for some reason this is disabling products with no reviews!
    categories: Category[] | null = null
  ) {
    this.pageNum = pageNum;
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

  public toUrlParams(): string {
    let urlParams = this.pageNum.toString();
    for (let key in this) {
      urlParams += ``;
    }
    return Object.keys(this)
      .map((key) => {
        let val: any = (this as any)[key];
        return val === null ? '' : `${key}=${val.toString()}`;
      })
      .filter((item) => item !== '')
      .join('&');
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
