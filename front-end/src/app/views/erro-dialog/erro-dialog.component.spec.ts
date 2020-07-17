import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ErroDialogComponent } from './erro-dialog.component';

import { TranslateModule, TranslateLoader } from '@ngx-translate/core';
import { HttpClient } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { TranslateHttpLoader } from '@ngx-translate/http-loader';

import {MAT_DIALOG_DATA} from '@angular/material';

export function HttpLoaderFactory(http: HttpClient) {
  return new TranslateHttpLoader(http);
}

describe('ErroDialogComponent', () => {
  let component: ErroDialogComponent;
  let fixture: ComponentFixture<ErroDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ErroDialogComponent ],
      imports: [
      HttpClientTestingModule,
      TranslateModule.forRoot({
        loader: {
          provide: TranslateLoader,
          useFactory: HttpLoaderFactory,
          deps: [HttpClient]
        }
      })],
      providers: [
        { provide: MAT_DIALOG_DATA, useValue: {erro: '1'} },
    ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ErroDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
