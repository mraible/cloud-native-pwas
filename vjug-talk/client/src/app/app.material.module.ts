import { MdListModule, MdProgressBarModule, MdToolbarModule } from '@angular/material';
import { NgModule } from '@angular/core';

@NgModule({
  imports: [MdListModule, MdProgressBarModule, MdToolbarModule],
  exports: [MdListModule, MdProgressBarModule, MdToolbarModule],
})
export class AppMaterialModule { }
