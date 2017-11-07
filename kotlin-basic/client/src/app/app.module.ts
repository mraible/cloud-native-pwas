import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { BeerListComponent } from './beer-list/beer-list.component';
import { HttpClientModule } from '@angular/common/http';
import { MatButtonModule, MatCardModule, MatInputModule, MatListModule, MatToolbarModule } from '@angular/material';
import { RouterModule, Routes } from '@angular/router';
import { BeerEditComponent } from './beer-edit/beer-edit.component';
import { FormsModule } from '@angular/forms';
import { BeerService, GiphyService } from './shared';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

const appRoutes: Routes = [
  { path: '', redirectTo: '/beer-list', pathMatch: 'full' },
  {
    path: 'beer-list',
    component: BeerListComponent
  },
  {
    path: 'beer-add',
    component: BeerEditComponent
  },
  {
    path: 'beer-edit/:id',
    component: BeerEditComponent
  }
];

@NgModule({
  declarations: [
    AppComponent,
    BeerListComponent,
    BeerEditComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    FormsModule,
    HttpClientModule,
    MatButtonModule, MatCardModule, MatInputModule, MatListModule, MatToolbarModule,
    RouterModule.forRoot(appRoutes),
  ],
  providers: [BeerService, GiphyService],
  bootstrap: [AppComponent]
})
export class AppModule { }
