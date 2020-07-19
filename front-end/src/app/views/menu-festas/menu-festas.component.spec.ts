import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MenuFestasComponent } from './menu-festas.component';

import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import { TranslateModule, TranslateLoader } from '@ngx-translate/core';
import { TranslateHttpLoader } from '@ngx-translate/http-loader';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { HttpClient } from '@angular/common/http';

import { CustomMaterialModule } from '../material/material.module';

import { RouterModule } from '@angular/router';

import { LoginService } from 'src/app/services/loginService/login.service';

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
        HttpClientTestingModule,
        BrowserAnimationsModule,
        TranslateModule.forRoot({
          loader: {
            provide: TranslateLoader,
            useFactory: HttpLoaderFactory,
            deps: [HttpClient]
          }
        }),
        RouterModule.forRoot([]),
      ],
      providers: [
        { provide: LoginService, useValue: {usuarioInfo: {codUsuario: '1'}} },
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

  it('should check funcionalidade is equal ORGANIZADOR', () => {
    expect(component.isAdmin({funcionalidade: 'ORGANIZADOR'})).toBeTruthy();
    expect(component.isAdmin({funcionalidade: ''})).toBeFalsy();
  });

  it('should format date from datetime', () => {
    const result = component.getDateFromDTF('2020-02-01T12:00:00');
    expect(result).toBe('01/02/2020');
  });

  it('should formar time from datetime', () => {
    const result = component.getTimeFromDTF('2020-02-01T12:00:00');
    expect(result).toBe('12:00:00');
  });

  it('should create url with party name and id', () => {
    const result = component.createUrl('festa teste', '21');
    expect(result).toBe('../festas/festa-teste&21/painel');
  });

  it('should setPageSizeOptions', () => {
    component.setPageSizeOptions('1, 2, 3, 4, 5');
    expect(component.pageSizeOptions).toEqual([1, 2, 3, 4, 5]);
  });

  it('should update page index', () => {
    component.festas = [{
      nomeFesta: 'Teste1'
    }, {
      nomeFesta: 'Teste2'
    }, {
      nomeFesta: 'Teste3'
    }, {
      nomeFesta: 'Teste4'
    }, {
      nomeFesta: 'Teste5'
    }, {
      nomeFesta: 'Teste6'
    }];
    component.onPageChange({pageIndex: 1, pageSize: 3, length: 6});
    expect(component.festasMostradas).toEqual([Object({nomeFesta: 'Teste4'}), Object({nomeFesta: 'Teste5'}),
                                              Object({nomeFesta: 'Teste6'})]);
  });

});
