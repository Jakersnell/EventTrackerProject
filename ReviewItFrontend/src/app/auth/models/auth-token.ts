export class AuthToken {
  token: string;
  userId: number;
  expiresOn: string;

  constructor(token: string = '', userId: number = 0, expiresOn: string = '') {
    this.token = token;
    this.userId = userId;
    this.expiresOn = expiresOn;
  }
}
