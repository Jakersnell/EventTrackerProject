import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'search-bar',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './search-bar.component.html',
  styleUrl: './search-bar.component.css',
})
export class SearchBarComponent {
  searchQuery = '';

  constructor(private router: Router) {}

  submitSearch(searchQuery: string | null) {
    if (searchQuery?.match(/^\s*$/)) {
      searchQuery = null;
    }
    this.router.navigate(['home'], { queryParams: { query: searchQuery } });
  }
}
