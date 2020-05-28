import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SuccessDialogComponent } from './success-dialog.component';

import { CustomMaterialModule } from '../material/material.module';

import { TranslateModule, TranslateLoader } from '@ngx-translate/core';
import { HttpClientModule, HttpClient } from '@angular/common/http';
import { TranslateHttpLoader } from '@ngx-translate/http-loader';

import {MAT_DIALOG_DATA} from '@angular/material';

export function HttpLoaderFactory(http: HttpClient) {
  return new TranslateHttpLoader(http);
}

describe('SuccessDialogComponent', () => {
  let component: SuccessDialogComponent;
  let fixture: ComponentFixture<SuccessDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SuccessDialogComponent ],
      imports: [
      HttpClientModule,
      CustomMaterialModule,
      TranslateModule.forRoot({
        loader: {
          provide: TranslateLoader,
          useFactory: HttpLoaderFactory,
          deps: [HttpClient]
        }
      })],
      providers: [
        { provide: MAT_DIALOG_DATA, useValue: {message: '1'} },
    ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SuccessDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
