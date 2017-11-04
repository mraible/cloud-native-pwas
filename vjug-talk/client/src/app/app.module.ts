import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { BeerListComponent } from './beer-list/beer-list.component';
import { BeerService } from './shared/beer/beer.service';
import { HttpModule } from '@angular/http';
import { GiphyService } from './shared/giphy/giphy.service';
import { AppMaterialModule } from './app.material.module';
import { AppShellModule } from '@angular/app-shell';

@NgModule({
  declarations: [
    AppComponent,
    BeerListComponent
  ],
  imports: [
    BrowserModule,
    HttpModule,
    AppMaterialModule,
    AppShellModule.runtime()
  ],
  providers: [BeerService, GiphyService],
  bootstrap: [AppComponent]
})
export class AppModule {
}
