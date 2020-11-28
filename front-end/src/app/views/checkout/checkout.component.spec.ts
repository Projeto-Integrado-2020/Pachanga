import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { CustomMaterialModule } from '../material/material.module';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { TranslateModule, TranslateLoader } from '@ngx-translate/core';
import { HttpLoaderFactory } from '../edit-dialog/edit-dialog.component.spec';
import { HttpClient } from '@angular/common/http';
import { MatDialog } from '@angular/material';
import { LoginService } from 'src/app/services/loginService/login.service';
import { CheckoutComponent } from './checkout.component';
import { NgxPayPalModule } from 'ngx-paypal';
import { DadosCompraIngressoService } from 'src/app/services/dados-compra-ingresso/dados-compra-ingresso.service';

describe('CheckoutComponent', () => {
  let component: CheckoutComponent;
  let fixture: ComponentFixture<CheckoutComponent>;

  let dialogSpy: MatDialog;

  beforeEach(async(() => {
    dialogSpy = jasmine.createSpyObj('MatDialog', ['open', 'closeAll']);

    TestBed.configureTestingModule({
      declarations: [ CheckoutComponent ],
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
        NgxPayPalModule,
        FormsModule,
        ReactiveFormsModule
      ],
      providers: [
        { provide: MatDialog, useValue: dialogSpy },
      ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CheckoutComponent);
    component = fixture.componentInstance;
    const service: LoginService = TestBed.get(LoginService);
    const serviceDados: DadosCompraIngressoService = TestBed.get(DadosCompraIngressoService);
    service.usuarioInfo = {codUsuario: '1'};
    component.festa = {
      nomeFesta: 'Teste',
      codFesta: 'Teste',
      horarioInicioFesta: '2020-09-23T19:10:25',
      horarioFimFesta: '2020-09-23T19:10:25',
      codEnderecoFesta: null,
      descricaoFesta: null
    };
    component.ingressos = [{
      quantidade: ['1', '1'],
      precoUnico: '35.00',
      lote: {
          codLote: '1',
          codFesta: '1',
          nomeLote: 'Ingresso VIP',
          preco: '40.00'
      }
    }];
    const token = {
      timeToken: '2020-09-21T01:14:04.028+0000',
      token: 'teste'
    };
    serviceDados.addIngresso(component.ingressos);
    localStorage.setItem('token', JSON.stringify(token));
    /* tslint:disable */
    component.initConfig = () => {null};
    /* tslint:enable */
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should format date from datetime', () => {
    const result = component.getDateFromDTF('2020-09-23T19:10:25');
    expect(result).toBe('23/09/2020');
  });

  it('should formar time from datetime', () => {
    const result = component.getTimeFromDTF('2020-09-23T19:10:25');
    expect(result).toBe('19:10:25');
  });

  it('should gerarItems', () => {
    const items = [{
      name: 'Ingresso VIP',
      quantity: 2,
      unit_amount: {
          currency_code: 'BRL',
          value: '35.00'
      }
    }];
    component.ingressos = [{
      quantidade: ['1', '1'],
      precoUnico: '35.00',
      lote: {
          codLote: '1',
          codFesta: '1',
          nomeLote: 'Ingresso VIP',
          preco: '40.00'
      }
    }];
    expect(component.gerarItems()).toEqual(items);
  });

  it('should open a dialog through a method', () => {
    component.openDialogBoleto();
    expect(dialogSpy.open).toHaveBeenCalled();
  });

  it('should open a SuccessDialog through a method', () => {
    component.openDialogSuccess('teste');
    expect(dialogSpy.open).toHaveBeenCalled();
  });

  it('should gerarForm', () => {
    component.ingressos = [{
      quantidade: ['1', '1'],
      precoUnico: '35.00',
      lote: {
          codLote: '1',
          codFesta: '1',
          nomeLote: 'Ingresso VIP',
          preco: '40.00'
      }
    }];

    component.gerarForm();
    expect(component.form.get('nome1-0')).toBeTruthy();
    expect(component.form.get('email1-0')).toBeTruthy();
    expect(component.form.get('nome1-1')).toBeTruthy();
    expect(component.form.get('email1-1')).toBeTruthy();
  });

  it('should open a dialog through a method', () => {
    component.getIngressos();
    expect(component.getIngressos()).toEqual();
  });

});
