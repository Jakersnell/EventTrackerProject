export class Product {
  id: number;
  name: string;
  description: string;
  imageUrl: string;
  averageRating: number;
  numberOfReviews: number;

  constructor(
    id = 0,
    name = '',
    description = '',
    imageUrl = '',
    averageRating = 0,
    numberOfReviews = 0
  ) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.imageUrl = imageUrl;
    this.averageRating = averageRating;
    this.numberOfReviews = numberOfReviews;
  }
}
