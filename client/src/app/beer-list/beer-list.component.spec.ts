import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BeerListComponent } from './beer-list.component';
import { AppMaterialModule } from '../app.material.module';
import { BeerService } from '../shared/beer/beer.service';
import { BaseRequestOptions, ConnectionBackend, Http } from '@angular/http';
import { MockBackend } from '@angular/http/testing';
import { GiphyService } from '../shared/giphy/giphy.service';
import { OktaAuthService } from '../shared/okta/okta.service';

describe('BeerListComponent', () => {
  let component: BeerListComponent;
  let fixture: ComponentFixture<BeerListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BeerListComponent ],
      imports: [AppMaterialModule],
      providers: [BeerService, GiphyService, OktaAuthService, {
        provide: Http, useFactory: (backend: ConnectionBackend, defaultOptions: BaseRequestOptions) => {
          return new Http(backend, defaultOptions);
        },
        deps: [MockBackend, BaseRequestOptions]
      },
        {provide: MockBackend, useClass: MockBackend},
        {provide: BaseRequestOptions, useClass: BaseRequestOptions}]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BeerListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
