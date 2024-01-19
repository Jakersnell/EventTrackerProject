export class Token {
  token: string;
  expiresOn: Date;

  constructor(token: string, expiresOn: Date) {
    this.token = token;
    this.expiresOn = expiresOn;
  }
}
