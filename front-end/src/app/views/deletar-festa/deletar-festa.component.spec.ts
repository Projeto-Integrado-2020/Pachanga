import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DeletarFestaComponent } from './deletar-festa.component';

import { TranslateModule, TranslateLoader } from '@ngx-translate/core';
import { TranslateHttpLoader } from '@ngx-translate/http-loader';
import { HttpClient } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { CustomMaterialModule } from '../../views/material/material.module';
import {MAT_DIALOG_DATA} from '@angular/material';

import { RouterModule } from '@angular/router';

export function HttpLoaderFactory(http: HttpClient) {
  return new TranslateHttpLoader(http);
}

describe('DeletarFestaComponent', () => {
  let component: DeletarFestaComponent;
  let fixture: ComponentFixture<DeletarFestaComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DeletarFestaComponent ],
      imports: [
        CustomMaterialModule,
        HttpClientTestingModule,
        TranslateModule.forRoot({
          loader: {
            provide: TranslateLoader,
            useFactory: HttpLoaderFactory,
            deps: [HttpClient]
          }
        }),
        RouterModule.forRoot([])
      ],
      providers: [
        { provide: MAT_DIALOG_DATA, useValue: {festa: {nomeFesta: 'teste', codFesta: '1'}} },
      ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DeletarFestaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
