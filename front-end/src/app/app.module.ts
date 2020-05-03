import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import {
  MatToolbarModule, 
  MatIconModule, 
  MatButtonModule,
  MatGridListModule
} from '@angular/material';
import { NavbarComponent } from './navbar/navbar.component';
import { IndexComponent } from './index/index.component';

@NgModule({
  declarations: [
    AppComponent,
    NavbarComponent,
    IndexComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    MatToolbarModule,
    MatButtonModule,
    MatIconModule,
    MatGridListModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { };