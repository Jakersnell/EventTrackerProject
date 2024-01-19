import { Component, Input } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';
import {
  NgbCollapseModule,
} from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [NgbCollapseModule, RouterLink, FormsModule],
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.css',
})
export class NavbarComponent {
  @Input() brandName = '';
  searchQuery = '';
  isMenuCollapsed = true;

  toggleMenuCollapsed(): void {
    this.isMenuCollapsed = !this.isMenuCollapsed;
  }
}
