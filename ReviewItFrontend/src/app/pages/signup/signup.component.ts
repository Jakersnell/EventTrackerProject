import { CommonModule } from '@angular/common';
import { Component, ElementRef, ViewChild } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../../auth/services/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-signup',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './signup.component.html',
  styleUrl: './signup.component.css',
})
export class SignupComponent {
  @ViewChild('formError') formError!: ElementRef<HTMLDivElement>;

  @ViewChild('firstNameError') firstNameError!: ElementRef<HTMLDivElement>;
  firstName = '';
  @ViewChild('lastNameError') lastNameError!: ElementRef<HTMLDivElement>;
  lastName = '';
  @ViewChild('usernameError') usernameError!: ElementRef<HTMLDivElement>;
  username = '';
  @ViewChild('emailError') emailError!: ElementRef<HTMLDivElement>;
  email = '';
  @ViewChild('passwordOneError') passwordOneError!: ElementRef<HTMLDivElement>;
  passwordOne = '';
  @ViewChild('passwordTwoError') passwordTwoError!: ElementRef<HTMLDivElement>;
  passwordTwo = '';

  showPassword = false;

  constructor(private authService: AuthService, private router: Router) {}

  validateFirstName(): boolean {
    const isValid =
      this.firstName.replace(/\s/g, '') != '' && this.firstName.length < 50;
    if (!isValid) {
      this.firstNameError.nativeElement.innerHTML = `
      First name must not be blank.
      `;
    } else {
      this.firstNameError.nativeElement.innerHTML = ``;
    }
    return isValid;
  }

  validateLastName(): boolean {
    const isValid =
      this.lastName.replace(/\s/g, '') != '' && this.firstName.length < 50;
    if (!isValid) {
      this.lastNameError.nativeElement.innerHTML = `
    Last name must not be blank.
    `;
    } else {
      this.lastNameError.nativeElement.innerHTML = ``;
    }
    return isValid;
  }

  validateUsername(): boolean {
    let strippedUsername = this.username.replace(/\s/g, '');
    let isValid =
      strippedUsername != '' &&
      this.username.length === strippedUsername.length;
    if (!isValid) {
      this.usernameError.nativeElement.innerHTML = `
      Username must not be blank and must contain no spaces;
      `;
    } else {
      this.usernameError.nativeElement.innerHTML = ``;
    }
    return isValid;
  }

  validateEmail(): boolean {
    let isValid =
      String(this.email)
        .toLowerCase()
        .match(
          /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|.(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/
        ) !== null;
    if (!isValid) {
      this.emailError.nativeElement.innerHTML = `
      Email is not valid.
      `;
    } else {
      this.emailError.nativeElement.innerHTML = ``;
    }
    return isValid;
  }

  validatePassword1(): boolean {
    let isValid =
      String(this.passwordOne).match(
        /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d\W]{8,}$/
      ) !== null;
    if (!isValid) {
      this.passwordOneError.nativeElement.innerHTML = `
      Password must have no spaces, a minimum of eight characters, at least one letter and one number.
      `;
    } else {
      this.passwordOneError.nativeElement.innerHTML = ``;
    }
    return isValid;
  }
  validatePassword2(): boolean {
    let isValid = this.passwordOne === this.passwordTwo;
    if (!isValid) {
      this.passwordTwoError.nativeElement.innerHTML = `
      Passwords must match.
      `;
    } else {
      this.passwordTwoError.nativeElement.innerHTML = ``;
    }
    return isValid;
  }

  validateAll(): boolean {
    // done as let statements so that each validator will 100% run
    // logical && does short circuiting
    let firstNameIsValid = this.validateFirstName();
    let lastNameIsValid = this.validateLastName();
    let emailIsValid = this.validateEmail();
    let usernameIsValid = this.validateUsername();
    let passwordOneIsValid = this.validatePassword1();
    let passwordTwoIsValid = this.validatePassword2();

    let isValid =
      firstNameIsValid &&
      lastNameIsValid &&
      emailIsValid &&
      usernameIsValid &&
      passwordOneIsValid &&
      passwordTwoIsValid;
    return isValid;
  }

  submitForm(): void {
    if (this.validateAll()) {
      const user = {
        firstName: this.firstName,
        lastName: this.lastName,
        username: this.username,
        email: this.email,
        password: this.passwordOne,
      };
      this.authService.createUser(user).subscribe({
        next: (_) => {
          this.router.navigateByUrl('login');
        },
        error: (_) => {
          this.formError.nativeElement.innerHTML = 'Something went wrong...';
        },
      });
    } else {
      this.formError.nativeElement.innerHTML = 'Form is not valid...';
    }
  }
}
