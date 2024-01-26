import { EventEmitter, Injectable } from '@angular/core';
import { environment } from '../../../environments/environment';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, catchError, map, of, tap, throwError } from 'rxjs';
import { User } from '../models/user';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private key: string = 'loggedUser';
  public userIsLoggedIn;
  public userIsLoggedInEvent = new EventEmitter<boolean>();
  urlRoot = `${environment.API_URL}/auth`;

  constructor(private http: HttpClient) {
    this.userIsLoggedIn = sessionStorage.getItem(this.key) !== null;
  }

  public authorizeUser(username: string, password: string): Observable<void> {
    const endpoint = `${this.urlRoot}/authorize`;

    const headers = new HttpHeaders({
      'Content-Type': 'application/json', // You can add more headers as needed
      Authorization: `Basic ${this.encodeAuthDetails(username, password)}`, // Example of an Authorization header
    });
    return this.http
      .get<User>(endpoint, { headers: headers })
      .pipe(
        catchError((err: any) => {
          console.log(err);
          return throwError(
            () =>
              new Error(
                `
              AuthService.authorizeUser(username: string, password: string): Observable<User>;
              Error while attempting POST to endpoint '${endpoint}'.
              With body:
                ${JSON.stringify(headers)}
              `
              )
          );
        })
      )
      .pipe(
        tap((user: User) => {
          user.password = password;
          this.setLoggedUser(user);
        })
      )
      .pipe(map(() => {}));
  }

  public createUser(userData: any): Observable<User> {
    const endpoint = `${this.urlRoot}/register`;
    return this.http
      .post<User>(endpoint, userData)
      .pipe(
        catchError((err: any) => {
          console.log(err);
          return throwError(
            () =>
              new Error(
                `
              AuthService.createUser(userData: any): Observable<User>;
              Error while attempting POST to endpoint ${endpoint}.
              With body:
                ${JSON.stringify(userData)}
              `
              )
          );
        })
      )
      .pipe(
        tap((user: User) => {
          this.setLoggedUser(user);
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
    sessionStorage.removeItem(this.key);
    this.emitAuthChange(false);
  }

  private setLoggedUser(user: User): void {
    const stringified = JSON.stringify(user);
    sessionStorage.setItem(this.key, stringified);
    this.emitAuthChange(true);
  }

  public getUserEncodedAuth(): string | null {
    let details = null;
    const user = sessionStorage.getItem(this.key);
    if (user !== null) {
      const parsed = JSON.parse(user) as User;
      details = this.encodeAuthDetails(parsed.username, parsed.password);
    }
    return details;
  }

  public getUserRole(): string | null {
    let details = null;
    const user = sessionStorage.getItem(this.key);
    if (user !== null) {
      details = (JSON.parse(user) as User).role;
    }
    return details;
  }

  public encodeAuthDetails(username: string, password: string): string {
    const usernameAndPassword = `${username}:${password}`;
    return btoa(usernameAndPassword);
  }
}
