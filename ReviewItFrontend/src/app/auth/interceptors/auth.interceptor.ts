import { HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { AuthService } from '../services/auth.service';

export const authInterceptor: HttpInterceptorFn = (req, next) => {
  const authService = inject(AuthService);
  if (authService.userIsLoggedIn) {
    req = req.clone({
      setHeaders: {
        Authorization: `Basic ${authService.getUserEncodedAuth()}`,
      },
    });
  }
  return next(req);
};
