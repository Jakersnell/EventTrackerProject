import { Category } from './category';

export class CategoryRequestDTO {
  public pageNum: number;
  public pageSize: number;
  public searchQuery: string;
  public selectedCategories: Category[];

  constructor(
    pageNum: number = 0,
    pageSize: number = 1, // must not be null
    searchQuery: string = '',
    excludedCategories: Category[] = []
  ) {
    this.pageNum = pageNum;
    this.pageSize = pageSize;
    this.searchQuery = searchQuery;
    this.selectedCategories = excludedCategories;
  }

  toUrlParams(): any {
    let params: any = Object.assign({}, this);
    params.pageNum = this.pageNum;
    params.pageSize = this.pageSize;
    if (this.searchQuery.replace(/\s/g, '') != '') {
      params.searchQuery = this.searchQuery;
    }
    if (this.selectedCategories.length != 0) {
      let ids = this.selectedCategories.map((cat: Category)=>cat.id);
      params.excludedCategories = ids.toString();
    }
    return params;
  }
}
