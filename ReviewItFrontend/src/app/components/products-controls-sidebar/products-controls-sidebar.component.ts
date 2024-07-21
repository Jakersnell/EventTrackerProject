import { CategoryService } from './../../services/category.service';
import {
  Component,
  EventEmitter,
  HostListener,
  OnInit,
  Output,
  ViewChild,
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
import { CategoryRequestDTO } from '../../models/category-request-dto';
import { Page } from '../../models/page';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-products-controls-sidebar',
  standalone: true,
  imports: [
    CommonModule,
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

  searchParams: ProductSearchParams = new ProductSearchParams(0, 6);
  categoryParams: CategoryRequestDTO = new CategoryRequestDTO(0, 6);
  sortOrder: string = this.ordering.default;
  categoryPage: Page<Category> = new Page<Category>();

  constructor(private categoryService: CategoryService) {}

  ngOnInit(): void {
    this.reloadAll();
  }

  reloadAll(): void {
    this.reloadCategories();
    this.updateOrdering();
    this.setProductCategories();
    this.emitEvent();
  }

  setProductCategories(): void {
    this.searchParams.categories = [...this.categoryParams.selectedCategories];
  }

  unselectCategory(category: Category): void {
    const filteredElements = this.categoryParams.selectedCategories.filter(
      (item: Category) =>
        item.id !== category.id && item !== null && item !== undefined
    );
    this.categoryParams.selectedCategories = filteredElements;
    this.reloadCategories();
  }

  selectCategory(category: Category): void {
    this.categoryParams.selectedCategories.push(category);
    this.reloadCategories();
  }

  emitEvent(): void {
    this.setProductCategories();
    this.controlSubmitEvent.emit(this.searchParams);
  }

  reloadCategories(): void {
    this.categoryService.getCategories(this.categoryParams).subscribe({
      next: (page: Page<Category>) => {
        this.categoryPage = Object.assign({}, page);
      },
    });
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
    this.searchParams.groupBy = grouping;
    this.searchParams.orderBy = ordering;
  }

  selectedIsEmpty(): boolean {
    return this.categoryParams.selectedCategories.length === 0;
  }

  // Was having a weird issue with null and undefined being added to the array
  categoryIsClean(category: Category) {
    return category !== null && category !== undefined;
  }

  categoryIsLast(category: Category): boolean {
    return (
      this.categoryPage.content[this.categoryPage.content.length - 1] ===
      category
    );
  }


  // Make controls panel open on desktop constantly, and closable on mobile
  isMobile(): boolean {
    /// matches the bootstrap breakpoints
    /// https://getbootstrap.com/docs/5.0/layout/breakpoints/
    return !window.matchMedia('(min-width: 768px)').matches;
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
