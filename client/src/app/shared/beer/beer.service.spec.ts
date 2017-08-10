import { TestBed, inject } from '@angular/core/testing';

import { BeerService } from './beer.service';
import { BaseRequestOptions, ConnectionBackend, Http } from '@angular/http';
import { MockBackend } from '@angular/http/testing';
import { OktaAuthService } from '../okta/okta.service';

describe('BeerService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [BeerService, OktaAuthService, {
        provide: Http, useFactory: (backend: ConnectionBackend, defaultOptions: BaseRequestOptions) => {
          return new Http(backend, defaultOptions);
        },
        deps: [MockBackend, BaseRequestOptions]
      },
        {provide: MockBackend, useClass: MockBackend},
        {provide: BaseRequestOptions, useClass: BaseRequestOptions}]
    });
  });

  it('should be created', inject([BeerService], (service: BeerService) => {
    expect(service).toBeTruthy();
  }));
});
