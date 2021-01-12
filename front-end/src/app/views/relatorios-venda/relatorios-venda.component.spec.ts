import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CustomMaterialModule } from '../material/material.module';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { TranslateModule, TranslateLoader } from '@ngx-translate/core';
import { HttpLoaderFactory } from '../edit-dialog/edit-dialog.component.spec';
import { HttpClient } from '@angular/common/http';
import { LoginService } from 'src/app/services/loginService/login.service';
import { of } from 'rxjs';
import { NgxChartsModule } from '@swimlane/ngx-charts';

import { RelatoriosVendaComponent } from './relatorios-venda.component';
import { RelatorioVendaService } from 'src/app/services/relatorios/relatorio-venda.service';

describe('RelatoriosVendaComponent', () => {
  let component: RelatoriosVendaComponent;
  let fixture: ComponentFixture<RelatoriosVendaComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RelatoriosVendaComponent ],
      imports: [
        CustomMaterialModule,
        HttpClientTestingModule,
        BrowserAnimationsModule,
        ReactiveFormsModule,
        RouterModule.forRoot([]),
        TranslateModule.forRoot({
          loader: {
            provide: TranslateLoader,
            useFactory: HttpLoaderFactory,
            deps: [HttpClient]
          }
        }),
        NgxChartsModule
      ],
      providers: [
        {provide: RelatorioVendaService, useValue: {
          ingressosFesta: () => of({ingressos: {test: 1}}),
          ingressosFestaCompradosPagos: () => of({ingressosCompradosPagos: {loteteste: {1 : 2}}})
        }},
      ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RelatoriosVendaComponent);
    component = fixture.componentInstance;
    const service: LoginService = TestBed.get(LoginService);
    service.usuarioInfo = {codUsuario: '1'};
    const token = {
      timeToken: '2020-09-21T01:14:04.028+0000',
      token: 'teste'
    };
    localStorage.setItem('token', JSON.stringify(token));
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should get getRelatorioIngressos', () => {
    spyOn(component.relatorioVendaService, 'ingressosFesta')
    .and
    .callThrough();

    component.getRelatorioIngressos();
    expect(component.relatorioVendaService.ingressosFesta).toHaveBeenCalled();
  });

  it('should get getRelatorioIngressosPagos', () => {
    spyOn(component.relatorioVendaService, 'ingressosFestaCompradosPagos')
    .and
    .callThrough();

    component.getRelatorioIngressosPagos();
    expect(component.relatorioVendaService.ingressosFestaCompradosPagos).toHaveBeenCalled();
  });
});
