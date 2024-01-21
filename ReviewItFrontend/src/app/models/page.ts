export class Page<T> {
  public pageNum: number;
  public pageSize: number;
  public numElements: number;
  public totalNumPages: number;
  public totalNumElements: number;
  public searchQuery: string;
  public content: T[];

  constructor(
    pageNum: number = 0,
    pageSize: number = 0,
    numElements: number = 0,
    totalNumPages: number = 0,
    totalNumElements: number = 0,
    searchQuery: string = '',
    content: T[] = []
  ) {
    this.pageNum = pageNum;
    this.pageSize = pageSize;
    this.numElements = numElements;
    this.totalNumPages = totalNumPages;
    this.totalNumElements = totalNumElements;
    this.searchQuery = searchQuery;
    this.content = content;
  }
}
