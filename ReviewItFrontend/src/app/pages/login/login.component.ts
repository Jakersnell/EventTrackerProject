import { AuthService } from './../../auth/services/auth.service';
import { CommonModule } from '@angular/common';
import { Component, ElementRef, ViewChild } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css',
})
export class LoginComponent {
  @ViewChild('errorDisplay') errorDisplay!: ElementRef<HTMLDivElement>;
  showPassword = false;
  username = '';
  password = '';

  constructor(private authService: AuthService, private router: Router) {}

  submitLogin(): void {
    this.showPassword = false;
    this.authService.authorizeUser(this.username, this.password).subscribe({
      // throw away auth token.
      // not needed here but its nice to have it returned
      // for other places, and future usecases.
      next: (_) => this.onSuccessfulLogin(),
      error: (_) => this.onFailedLogin(),
    });
  }

  onSuccessfulLogin(): void {
    this.router.navigate(['home']);
  }

  onFailedLogin(): void {
    this.clearErrorDisplay();
    this.setErrorDisplay('Incorrect Username or Password.');
  }

  clearErrorDisplay(): void {
    this.errorDisplay.nativeElement.innerHTML = '';
  }

  setErrorDisplay(content: string): void {
    this.errorDisplay.nativeElement.innerHTML = `
      <h3>${content}</h3>
    `;
  }
}
