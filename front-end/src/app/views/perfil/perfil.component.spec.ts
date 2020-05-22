import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CustomMaterialModule } from '../material/material.module';

import { PerfilComponent } from './perfil.component';
import { TranslateModule, TranslateLoader } from '@ngx-translate/core';
import { HttpClientModule, HttpClient } from '@angular/common/http';
import { HttpLoaderFactory } from '../cadastro/cadastro.component.spec';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

describe('PerfilComponent', () => {
  let component: PerfilComponent;
  let fixture: ComponentFixture<PerfilComponent>;

  beforeEach(async(() => {
    jasmine.DEFAULT_TIMEOUT_INTERVAL = 100000;
    TestBed.configureTestingModule({
      declarations: [ PerfilComponent ],
      imports: [
        BrowserAnimationsModule,
        CustomMaterialModule,
        HttpClientModule,
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
    jasmine.DEFAULT_TIMEOUT_INTERVAL = 100000;
    fixture = TestBed.createComponent(PerfilComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
