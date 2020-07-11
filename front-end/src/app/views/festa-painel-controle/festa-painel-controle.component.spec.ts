import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FestaPainelControleComponent } from './festa-painel-controle.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import { TranslateModule, TranslateLoader } from '@ngx-translate/core';
import { TranslateHttpLoader } from '@ngx-translate/http-loader';
import { HttpClient, HttpClientModule } from '@angular/common/http';

import { CustomMaterialModule } from '../material/material.module';
import { ReactiveFormsModule } from '@angular/forms';

export function HttpLoaderFactory(http: HttpClient) {
  return new TranslateHttpLoader(http);
}

describe('FestaPainelControleComponent', () => {
  let component: FestaPainelControleComponent;
  let fixture: ComponentFixture<FestaPainelControleComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FestaPainelControleComponent ],
      imports: [
        CustomMaterialModule,
        HttpClientModule,
        BrowserAnimationsModule,
        ReactiveFormsModule,
        TranslateModule.forRoot({
          loader: {
            provide: TranslateLoader,
            useFactory: HttpLoaderFactory,
            deps: [HttpClient]
          }
        }),
      ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FestaPainelControleComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
