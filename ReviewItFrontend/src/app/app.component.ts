import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink, RouterOutlet } from '@angular/router';
import { DecimalRatingStarsComponent } from './components/decimal-rating-stars/decimal-rating-stars.component';
import { NgbCollapseModule } from '@ng-bootstrap/ng-bootstrap';
import { FormsModule } from '@angular/forms';
import { AuthService } from './auth/services/auth.service';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [
    CommonModule,
    RouterOutlet,
    RouterLink,
    DecimalRatingStarsComponent,
    NgbCollapseModule,
    FormsModule,
  ],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css',
})
export class AppComponent implements OnInit {
  title = 'ReviewIt!';
  brandName = this.title;
  searchQuery = '';
  isMenuCollapsed = true;
  userIsLoggedIn;

  constructor(private authService: AuthService) {
    this.userIsLoggedIn = authService.userIsLoggedIn;
  }

  ngOnInit(): void {
    this.authService.userIsLoggedInEvent.subscribe({
      next: (status: boolean) => (this.userIsLoggedIn = status),
    });
  }

  signOut(): void {
    this.authService.deauthorizeUser();
  }

  toggleMenuCollapsed(): void {
    this.isMenuCollapsed = !this.isMenuCollapsed;
  }
}
