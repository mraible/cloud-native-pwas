import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

@Injectable()
export class BeerService {
  public API = '//localhost:8081';
  public BEER_API = '//localhost:8080/beers';

  constructor(private http: HttpClient) {}

  getAll(): Observable<any> {
    return this.http.get(this.API + '/good-beers');
  }

  get(id: string) {
    return this.http.get(this.BEER_API + '/' + id);
  }

  save(beer: any): Observable<any> {
    let result: Observable<Object>;
    if (beer['href']) {
      result = this.http.put(beer.href, beer);
    } else {
      result = this.http.post(this.BEER_API, beer)
    }
    return result;
  }

  remove(href: string) {
    return this.http.delete(href);
  }
}
