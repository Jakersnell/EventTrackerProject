import {
  Component,
  EventEmitter,
  HostListener,
  OnInit,
  Output,
  ViewChild
} from '@angular/core';
import { FormsModule } from '@angular/forms';
import {
  NgbAccordionButton,
  NgbAccordionDirective,
  NgbAccordionItem,
  NgbAccordionModule,
} from '@ng-bootstrap/ng-bootstrap';
import {
  ProductSearchParams,
  SortGroups,
  SortOrder,
} from '../../models/product-search-params';
import { Category } from '../../models/category';

@Component({
  selector: 'app-products-controls-sidebar',
  standalone: true,
  imports: [
    NgbAccordionModule,
    NgbAccordionItem,
    NgbAccordionButton,
    FormsModule,
  ],
  templateUrl: './products-controls-sidebar.component.html',
  styleUrl: './products-controls-sidebar.component.css',
})
export class ProductsControlsSidebarComponent implements OnInit {
  ordering = {
    default: 'NONE',
    MOST_POPULAR: 'MOST_POPULAR',
    LEAST_POPULAR: 'LEAST_POPULAR',
    MOST_REVIEWS: 'MOST_REVIEWS',
    LEAST_REVIEWS: 'LEAST_REVIEWS',
    MOST_RATINGS: 'MOST_RATINGS',
    LEAST_RATINGS: 'LEAST_RATINGS',
  };

  @Output() public controlSubmitEvent = new EventEmitter<ProductSearchParams>(); // star of the show

  @ViewChild('accordion') accordion!: NgbAccordionDirective;
  @ViewChild('first') first!: NgbAccordionItem;

  params: ProductSearchParams = new ProductSearchParams();
  sortOrder: string = this.ordering.default;

  unselectedCategories: Category[] = [];

  ngOnInit(): void {
    this.updateOrdering();
  }

  isMobile(): boolean {
    /// matches the bootstrap breakpoints
    /// https://getbootstrap.com/docs/5.0/layout/breakpoints/
    return !window.matchMedia('(min-width: 768px)').matches;
  }

  emitEvent(): void {
    this.controlSubmitEvent.emit(this.params);
  }

  updateOrdering(): void {
    let grouping;
    let ordering;
    switch (this.sortOrder) {
      case this.ordering.MOST_POPULAR:
        grouping = SortGroups.popularity;
        ordering = SortOrder.descending;
        break;
      case this.ordering.LEAST_POPULAR:
        grouping = SortGroups.popularity;
        ordering = SortOrder.ascending;
        break;
      case this.ordering.MOST_REVIEWS:
        grouping = SortGroups.reviews;
        ordering = SortOrder.descending;
        break;
      case this.ordering.LEAST_REVIEWS:
        grouping = SortGroups.reviews;
        ordering = SortOrder.ascending;
        break;
      case this.ordering.MOST_RATINGS:
        grouping = SortGroups.ratings;
        ordering = SortOrder.descending;
        break;
      case this.ordering.LEAST_RATINGS:
        grouping = SortGroups.ratings;
        ordering = SortOrder.descending;
        break;
      default:
        grouping = null;
        ordering = null;
    }
    this.params.groupBy = grouping;
    this.params.orderBy = ordering;
    this.emitEvent();
  }

  @HostListener('window:resize', ['$event'])
  handleResize(event: Event): void {
    const isMobile = this.isMobile();
    const isExpanded = this.accordion.isExpanded('first');
    const toggleForDesktop = !isMobile && !isExpanded;
    const toggleForMobile = isMobile && isExpanded;
    if (toggleForDesktop || toggleForMobile) {
      this.accordion.toggle('first');
    }
  }
}
