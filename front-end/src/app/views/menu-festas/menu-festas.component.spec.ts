import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MenuFestasComponent } from './menu-festas.component';
import { MenuFestasService } from 'src/app/services/menu-festa/menu-festas.service';

import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import { TranslateModule, TranslateLoader } from '@ngx-translate/core';
import { TranslateHttpLoader } from '@ngx-translate/http-loader';
import { HttpClient, HttpClientModule } from '@angular/common/http';

import { CustomMaterialModule } from '../material/material.module';

import { RouterModule } from '@angular/router';


export function HttpLoaderFactory(http: HttpClient) {
  return new TranslateHttpLoader(http);
}

describe('MenuFestasComponent', () => {
  let component: MenuFestasComponent;
  let fixture: ComponentFixture<MenuFestasComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MenuFestasComponent ],
      imports: [
        CustomMaterialModule,
        HttpClientModule,
        BrowserAnimationsModule,
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
        { provide: MenuFestasService, useValue: {codUsuario: 1} },
      ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MenuFestasComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
