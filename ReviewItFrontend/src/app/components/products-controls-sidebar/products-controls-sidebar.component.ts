import {
  AfterViewInit,
  Component,
  HostListener,
  ViewChild,
} from '@angular/core';
import {
  NgbAccordionButton,
  NgbAccordionDirective,
  NgbAccordionItem,
  NgbAccordionModule,
} from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-products-controls-sidebar',
  standalone: true,
  imports: [NgbAccordionModule, NgbAccordionItem, NgbAccordionButton],
  templateUrl: './products-controls-sidebar.component.html',
  styleUrl: './products-controls-sidebar.component.css',
})
export class ProductsControlsSidebarComponent implements AfterViewInit {
  @ViewChild('accordion') accordion!: NgbAccordionDirective;
  @ViewChild('first') first!: NgbAccordionItem;
  isAfterViewInit = false;

  ngAfterViewInit(): void {
    this.isAfterViewInit = true;
  }

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
