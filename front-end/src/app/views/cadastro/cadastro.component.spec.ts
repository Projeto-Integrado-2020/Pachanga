import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CadastroComponent } from './cadastro.component';

import { CustomMaterialModule } from '../material/material.module';

import {NavbarComponent} from '../navbar/navbar.component';

import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from '../../app-routing.module';
import { LoginComponent } from '../login/login.component';
import { IndexComponent } from '../index/index.component';

describe('CadastroComponent', () => {
  let component: CadastroComponent;
  let fixture: ComponentFixture<CadastroComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [
        CadastroComponent,
        NavbarComponent,
        LoginComponent,
        IndexComponent
      ],
      imports: [
        CustomMaterialModule,
        BrowserAnimationsModule,
        BrowserModule,
        AppRoutingModule
      ],
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CadastroComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
