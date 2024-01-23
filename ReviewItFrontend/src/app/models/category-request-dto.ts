import { Category } from './category';

export class CategoryRequestDTO {
  public pageNum: number;
  public pageSize: number;
  public searchQuery: string;
  public excludedCategories: Category[];

  constructor(
    pageNum: number = 0,
    pageSize: number = 1, // must not be null
    searchQuery: string = '',
    excludedCategories: Category[] = []
  ) {
    this.pageNum = pageNum;
    this.pageSize = pageSize;
    this.searchQuery = searchQuery;
    this.excludedCategories = excludedCategories;
  }

  toUrlParams(): any {
    let params: any = Object.assign({}, this);
    params.pageNum = this.pageNum;
    params.pageSize = this.pageSize;
    if (this.searchQuery.replace(/\s/g, '') != '') {
      params.searchQuery = this.searchQuery;
    }
    if (this.excludedCategories.length != 0) {
      let ids = this.excludedCategories.map((cat: Category)=>cat.id);
      params.excludedCategories = ids.toString();
    }
    return params;
  }
}
