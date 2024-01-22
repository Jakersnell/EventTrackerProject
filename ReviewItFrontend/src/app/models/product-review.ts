export class ProductReview {
  id: number;
  title: string;
  content: string;
  rating: number;
  createdOn: string;
  lastUpdated: string;
  username: string;

  constructor(
    id: number = 0,
    title: string = '',
    content: string = '',
    rating: number = 0,
    createdOn: string = '',
    lastUpdated: string = '',
    username: string = ''
  ) {
    this.id = id;
    this.title = title;
    this.content = content;
    this.rating = rating;
    this.createdOn = createdOn;
    this.lastUpdated = lastUpdated;
    this.username = username;
  }
}
