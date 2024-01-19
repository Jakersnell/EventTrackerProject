import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css',
})
export class LoginComponent {
  showPassword = false;
  username = '';
  password = '';

  submitLogin(): void {
    this.showPassword = false;
    console.log(this.username);
    this.username = '';
    console.log(this.password);
    this.password = '';
  }
}
