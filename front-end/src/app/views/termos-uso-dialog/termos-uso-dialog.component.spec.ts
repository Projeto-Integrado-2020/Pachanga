import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { CustomMaterialModule } from '../material/material.module';

import { TranslateModule, TranslateLoader } from '@ngx-translate/core';
import { HttpClient } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { TranslateHttpLoader } from '@ngx-translate/http-loader';
import { TermosUsoDialogComponent } from './termos-uso-dialog.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

export function HttpLoaderFactory(http: HttpClient) {
  return new TranslateHttpLoader(http);
}

describe('TermosUsoDialogComponent', () => {
  let component: TermosUsoDialogComponent;
  let fixture: ComponentFixture<TermosUsoDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TermosUsoDialogComponent ],
      imports: [
        HttpClientTestingModule,
        CustomMaterialModule,
        BrowserAnimationsModule,
        TranslateModule.forRoot({
          loader: {
            provide: TranslateLoader,
            useFactory: HttpLoaderFactory,
            deps: [HttpClient]
          }
        })
      ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TermosUsoDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
