import { EventEmitter, Injectable } from '@angular/core';
import { environment } from '../../../environments/environment';
import { HttpClient } from '@angular/common/http';
import { Observable, catchError, of, tap, throwError } from 'rxjs';
import { AuthToken } from '../models/auth-token';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  public userIsLoggedIn;
  public userIsLoggedInEvent = new EventEmitter<boolean>();
  urlRoot = `${environment.API_URL}/api/auth`;

  constructor(private http: HttpClient) {
    this.userIsLoggedIn = sessionStorage.getItem('auth') !== null;
  }

  public authorizeUser(
    username: string,
    password: string
  ): Observable<AuthToken> {
    const endpoint = `${this.urlRoot}/login`;
    const params = {
      username: username,
      password: password,
    };
    return this.http
      .post<AuthToken>(endpoint, params)
      .pipe(
        catchError((err: any) => {
          console.log(err);
          return throwError(
            () =>
              new Error(
                `
              AuthService.authorizeUser(username: string, password: string): Observable<AuthToken>;
              Error while attempting POST to endpoint '${endpoint}'.
              With body:
                ${JSON.stringify(params)}
              `
              )
          );
        })
      )
      .pipe(
        tap((token: AuthToken) => {
          this.setAuthToken(token);
        })
      );
  }

  public createUser(userData: any): Observable<AuthToken> {
    const endpoint = `${this.urlRoot}/signup`;
    return this.http
      .post<AuthToken>(endpoint, userData)
      .pipe(
        catchError((err: any) => {
          console.log(err);
          return throwError(
            () =>
              new Error(
                `
              AuthService.createUser(userData: any): Observable<AuthToken>;
              Error while attempting POST to endpoint ${endpoint}.
              With body:
                ${JSON.stringify(userData)}
              `
              )
          );
        })
      )
      .pipe(
        tap((token: AuthToken) => {
          this.setAuthToken(token);
        })
      );
  }

  emitAuthChange(status: boolean): void {
    this.userIsLoggedIn = status;
    this.userIsLoggedInEvent.emit(status);
  }

  /// this exists as it does for future expansion
  public deauthorizeUser(): Observable<string> {
    this.clearAuthToken();
    return of('Successfully logged out.');
  }

  private clearAuthToken(): void {
    sessionStorage.removeItem('auth');
    this.emitAuthChange(false);
  }

  private setAuthToken(auth: AuthToken): void {
    const stringified = JSON.stringify(auth);
    sessionStorage.setItem('auth', stringified);
    this.emitAuthChange(true);
  }

  public getAuthToken(): AuthToken | null {
    const auth = sessionStorage.getItem('auth');
    let token = null;
    if (auth !== null) {
      token = JSON.parse(auth);
    }
    return null;
  }
}
