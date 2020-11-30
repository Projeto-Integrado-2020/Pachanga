import { TestBed } from '@angular/core/testing';
import { HttpClientModule } from '@angular/common/http';
import { CustomMaterialModule } from '../../views/material/material.module';
import { MatDialog, MatDialogRef } from '@angular/material';
import { DadosCompraIngressoService } from './dados-compra-ingresso.service';
import { RouterTestingModule } from '@angular/router/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';

describe('DadosCompraIngressoService', () => {
  let dialogSpy: MatDialog;
  let service: DadosCompraIngressoService;

  beforeEach(() => {
    dialogSpy = jasmine.createSpyObj('MatDialog', ['open', 'closeAll']);
    TestBed.configureTestingModule({
      imports: [
        HttpClientModule,
        CustomMaterialModule,
        RouterTestingModule.withRoutes([]),
      ],
      providers: [

        { provide: MatDialog, useValue: dialogSpy },
        { provide: MatDialogRef, useValue: {} }
      ]
    });
    const ingressos = {
      quantidade: '1',
      precoUnico: '35.00',
      lote: {
        codLote: '1',
        codFesta: '1',
        nomeLote: 'Ingresso VIP',
        preco: '40.00'
        }
    };
    const precoTotal = {
      precoTotal: '1'
    };
    const token = {
      timeToken: '2020-09-21T01:14:04.028+0000',
      token: 'teste'
    };
    localStorage.setItem('token', JSON.stringify(token));
    localStorage.setItem('ingressos', JSON.stringify(ingressos));
    localStorage.setItem('precoTotal', JSON.stringify(precoTotal));
    service = TestBed.get(DadosCompraIngressoService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should be addPrecoTotal', () => {
    const ingresso = {
      quantidade: '1',
      precoUnico: '35.00',
      lote: {
          codLote: '1',
          codFesta: '1',
          nomeLote: 'Ingresso VIP',
          preco: '40.00'
      }
    };
    service.addIngresso(ingresso);
    expect(service.getIngressos()).toEqual(ingresso);
  });

  it('should be addPrecoTotal', () => {
    const precoTotal = '1';
    service.addPrecoTotal(precoTotal);
    expect(service.getPrecoTotal()).toEqual(precoTotal);
  });

  it('should getIngressos', () => {
    service.ingresso = {
      quantidade: '1',
      precoUnico: '35.00',
      lote: {
          codLote: '1',
          codFesta: '1',
          nomeLote: 'Ingresso VIP',
          preco: '40.00'
      }
    };
    expect(service.getIngressos()).toBeTruthy();
  });

  it('should getPrecoTotal', () => {
    service.precoTotal = '1';
    expect(service.getPrecoTotal()).toBeTruthy();
  });

});
