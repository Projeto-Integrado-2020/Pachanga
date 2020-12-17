/* tslint:disable:no-unused-variable */
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { MeusIngressosComponent } from './meus-ingressos.component';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { MatDialog } from '@angular/material';
import { RouterModule } from '@angular/router';
import { LoginService } from 'src/app/services/loginService/login.service';
import { TranslateLoader, TranslateModule } from '@ngx-translate/core';
import { HttpLoaderFactory } from 'src/app/app.module';
import { HttpClient } from '@angular/common/http';
import { IngressosService } from 'src/app/services/ingressos/ingressos.service';
import { of } from 'rxjs';

describe('MeusIngressosComponent', () => {
  let component: MeusIngressosComponent;
  let fixture: ComponentFixture<MeusIngressosComponent>;
  let dialogSpy: MatDialog;

  beforeEach(async(() => {
    dialogSpy = jasmine.createSpyObj('MatDialog', ['open']);

    TestBed.configureTestingModule({
      declarations: [ MeusIngressosComponent ],
      imports: [
      HttpClientTestingModule,
      RouterModule.forRoot([]),
      TranslateModule.forRoot({
        loader: {
          provide: TranslateLoader,
          useFactory: HttpLoaderFactory,
          deps: [HttpClient]
        }
      }),
      ],
      providers: [
        { provide: MatDialog, useValue: dialogSpy },
        { provide: IngressosService, useValue: {
          listaIngressos: () => of([
            {
              codLote: '1',
              codIngresso: '1',
              statusCompra: 'P',
              dataCompra: '2020-11-10T12:00:00',
              codFesta: '1',
              nomeLote: 'Ingresso VIP 1',
              preco: 40,
              festa: {
                codFesta: '1',
                statusFesta: 'P',
                horarioInicioFesta: '2020-11-10 12:00:00',
                horarioFimFesta: '2020-11-15 12:00:00',
                nomeFesta: 'Teste1'
              }
            },
            {
              codLote: '2',
              codIngresso: '2',
              statusCompra: 'P',
              dataCompra: '2020-11-10T12:00:00',
              codFesta: '2',
              nomeLote: 'Ingresso VIP 2',
              preco: 41,
              festa: {
                codFesta: '1',
                statusFesta: 'F',
                horarioInicioFesta: '2020-11-10 12:00:00',
                horarioFimFesta: '2020-11-15 12:00:00',
                nomeFesta: 'Teste2'
              }
            }
          ])
        }},
      ],
      schemas: [ CUSTOM_ELEMENTS_SCHEMA ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MeusIngressosComponent);
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

  it('should abrirQRDialog', () => {

    const ingresso = {
      codLote: '1',
      codIngresso: '1',
      statusCompra: 'P',
      dataCompra: '2020-11-10T12:00:00',
      codFesta: '1',
      nomeLote: 'Ingresso VIP 1',
      preco: 40,
      festa: {
        statusFesta: 'P',
        horarioInicioFesta: '2020-11-10 12:00:00',
        horarioFimFesta: '2020-11-15 12:00:00',
        nomeFesta: 'Teste1'
      }
    }

    component.abrirQRDialog(ingresso);
    expect(dialogSpy.open).toHaveBeenCalled();
  });

  it('should processardatetime', () => {
    const result = component.processardatetime('2020-11-10 12:00:00');
    expect(result).toEqual('10/11/2020, 12:00');
  });

  it('should processarDataCompra', () => {
    const result = component.processarDataCompra('2020-11-10T12:00:00');
    expect(result).toEqual('10/11/2020, 12:00');
  });

  it('should navegarURL', () => {
    spyOn(component.router, 'navigateByUrl')
    .and
    .callThrough();
    component.navegarURL('teste.com');
    expect(component.router.navigateByUrl).toHaveBeenCalledWith('teste.com');
  });

  it('should listarIngressos', () => {
    spyOn(component.ingressosService, 'listaIngressos')
    .and
    .callThrough();

    component.listarIngressos();
    expect(component.ingressosService.listaIngressos).toHaveBeenCalled();
  });

  it('should call criarPaginaPDF at gerarIngressoPDF', () => {
    spyOn(component, 'criarPaginaPDF')
    .and
    .callThrough();

    const jsonIngressos = [
      {
        codLote: '1',
        codIngresso: '1',
        statusCompra: 'P',
        dataCompra: '2020-11-10T12:00:00',
        codFesta: '1',
        nomeLote: 'Ingresso VIP 1',
        preco: 40,
        festa: {
          statusFesta: 'P',
          horarioInicioFesta: '2020-11-10 12:00:00',
          horarioFimFesta: '2020-11-15 12:00:00',
          nomeFesta: 'Teste1'
        }
      },
      {
        codLote: '2',
        codIngresso: '2',
        statusCompra: 'P',
        dataCompra: '2020-11-10T12:00:00',
        codFesta: '2',
        nomeLote: 'Ingresso VIP 2',
        preco: 41,
        festa: {
          statusFesta: 'F',
          horarioInicioFesta: '2020-11-10 12:00:00',
          horarioFimFesta: '2020-11-15 12:00:00',
          nomeFesta: 'Teste2'
        }
      }
    ];
    component.gerarIngressoPDF(jsonIngressos);
    expect(component.criarPaginaPDF).toHaveBeenCalled();
  });
});
