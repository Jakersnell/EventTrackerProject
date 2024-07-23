import { Routes } from '@angular/router';
import { HomeComponent } from './pages/home/home.component';
import { LoginComponent } from './pages/login/login.component';
import { SignupComponent } from './pages/signup/signup.component';
import { ProductComponent } from './pages/product/product.component';
import { productExistsGuard } from './guards/product-exists.guard';

export const routes: Routes = [
  { path: '', redirectTo: 'home', pathMatch: 'full' },
  { path: 'home', title: 'Home Page', component: HomeComponent },
  { path: 'login', title: 'Login Page', component: LoginComponent },
  { path: 'signup', title: 'Signup Page', component: SignupComponent },
  {
    path: 'products/:id',
    canActivate: [productExistsGuard],
    component: ProductComponent,
    data: { title: 'Product' },
  },
];
