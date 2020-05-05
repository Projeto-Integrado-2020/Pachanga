import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import { CustomMaterialModule } from './views/material/material.module'

import { NavbarComponent } from './views/navbar/navbar.component';
import { IndexComponent } from './views/index/index.component';
import { LoginComponent } from './views/login/login.component';
import { CadastroComponent } from './views/cadastro/cadastro.component';

interface Food {
  value: string;
  viewValue: string;
}

export class SelectOverviewExample {
  foods: Food[] = [
    {value: 'steak-0', viewValue: 'Steak'},
    {value: 'pizza-1', viewValue: 'Pizza'},
    {value: 'tacos-2', viewValue: 'Tacos'}
  ];
}

import {FormsModule} from '@angular/forms';
import { AppRoutingModule } from './app-routing.module';


@NgModule({
  declarations: [
    AppComponent,
    NavbarComponent,
    IndexComponent,
    LoginComponent,
    CadastroComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    CustomMaterialModule,
    FormsModule,
    AppRoutingModule
  ],
  bootstrap: [AppComponent]
})
export class AppModule { };