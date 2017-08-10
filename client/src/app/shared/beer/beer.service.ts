import { Injectable } from '@angular/core';
import { Http, Headers, RequestOptions, Response } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import { OktaAuthService } from '../okta/okta.service';

@Injectable()
export class BeerService {

  constructor(private http: Http, private oktaService: OktaAuthService) { }

  getAll(): Observable<any> {
    const headers: Headers = new Headers();
    if (this.oktaService.isAuthenticated()) {
      const accessToken = this.oktaService.signIn.tokenManager.get('accessToken');
      headers.append('Authorization', accessToken.tokenType + ' ' + accessToken.accessToken);
    }
    const options = new RequestOptions({ headers: headers });
    console.log('headers', headers);
    return this.http.get('http://localhost:8081/good-beers', options)
      .map((response: Response) => response.json());
  }
}
