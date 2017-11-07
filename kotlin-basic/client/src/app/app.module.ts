import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { BeerListComponent } from './beer-list/beer-list.component';
import { HttpClientModule } from '@angular/common/http';
import { MatListModule, MatToolbarModule, MatButtonModule } from '@angular/material';
import { RouterModule, Routes } from '@angular/router';

@NgModule({
  declarations: [
    AppComponent,
    BeerListComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    MatListModule, MatToolbarModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
