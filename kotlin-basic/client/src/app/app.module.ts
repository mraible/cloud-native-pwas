import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { AppComponent } from './app.component';
import { CarListComponent } from './car-list/car-list.component';
import { HttpClientModule } from '@angular/common/http';
import { CarService, GiphyService } from './shared';
import { MatListModule, MatToolbarModule } from '@angular/material';

@NgModule({
  declarations: [
    AppComponent,
    CarListComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    MatListModule, MatToolbarModule
  ],
  providers: [CarService, GiphyService],
  bootstrap: [AppComponent]
})
export class AppModule { }
