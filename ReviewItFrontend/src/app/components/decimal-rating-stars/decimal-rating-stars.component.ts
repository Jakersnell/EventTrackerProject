import { Component, Input } from '@angular/core';
import { NgbRatingModule } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'stars',
  standalone: true,
  imports: [NgbRatingModule],
  templateUrl: './decimal-rating-stars.component.html',
  styleUrl: './decimal-rating-stars.component.css'
})
export class DecimalRatingStarsComponent {
  @Input() rating!: number;
  @Input() size = '1rem';


  ariaValueText(current: number, max: number) {
    return `${current} out of ${max} hearts`;
  }
}
