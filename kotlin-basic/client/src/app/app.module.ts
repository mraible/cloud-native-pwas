import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { AppComponent } from './app.component';
import { CarListComponent } from './car-list/car-list.component';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { MatButtonModule, MatCardModule, MatListModule, MatToolbarModule } from '@angular/material';

import { OktaAuthModule, OktaCallbackComponent } from '@okta/okta-angular';
import { RouterModule, Routes } from '@angular/router';
import { AuthInterceptor } from './shared';

const config = {
  issuer: 'https://dev-158606.oktapreview.com/oauth2/default',
  redirectUri: 'http://localhost:4200/implicit/callback',
  clientId: '0oac85oh5p2VqZJ7c0h7'
};

const appRoutes: Routes = [
  {
    path: 'implicit/callback',
    component: OktaCallbackComponent
  }
];

@NgModule({
  declarations: [
    AppComponent,
    CarListComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    MatButtonModule, MatCardModule, MatListModule, MatToolbarModule,
    RouterModule.forRoot(appRoutes),
    OktaAuthModule.initAuth(config)
  ],
  providers: [CarService, GiphyService,
    { provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
