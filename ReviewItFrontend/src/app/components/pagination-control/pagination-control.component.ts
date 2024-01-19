import { Component, EventEmitter, Input, Output } from '@angular/core';
import { NgbPaginationModule } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-pagination-control',
  standalone: true,
  imports: [NgbPaginationModule],
  templateUrl: './pagination-control.component.html',
  styleUrl: './pagination-control.component.css'
})

export class PaginationControlComponent {
  @Input() numPages = 0;
  @Input() currentPage = 1;
  @Output() triggerPageRequest = new EventEmitter<number>();
}
