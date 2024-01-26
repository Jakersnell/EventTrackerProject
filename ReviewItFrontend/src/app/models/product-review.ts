export class ProductReview {
  id: number;
  title: string;
  content: string;
  rating: number;
  createdOn: string;
  lastUpdated: string;
  authorUsername: string;
  productId: number;
  authorId: number;

  constructor(
    id: number = 0,
    title: string = '',
    content: string = '',
    rating: number = 0,
    createdOn: string = '',
    lastUpdated: string = '',
    authorUsername: string = '',
    productId: number = 0,
    authorId: number = 0
  ) {
    this.id = id;
    this.title = title;
    this.content = content;
    this.rating = rating;
    this.createdOn = createdOn;
    this.lastUpdated = lastUpdated;
    this.authorUsername = authorUsername;
    this.productId = productId;
    this.authorId = authorId;
  }
}
