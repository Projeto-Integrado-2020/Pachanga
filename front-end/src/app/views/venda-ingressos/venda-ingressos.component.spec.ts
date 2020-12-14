import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { VendaIngressosComponent } from './venda-ingressos.component';
import { CustomMaterialModule } from '../material/material.module';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { TranslateModule, TranslateLoader } from '@ngx-translate/core';
import { HttpLoaderFactory } from '../edit-dialog/edit-dialog.component.spec';
import { HttpClient } from '@angular/common/http';
import { MatDialog } from '@angular/material';
import { LoginService } from 'src/app/services/loginService/login.service';

describe('VendaIngressosComponent', () => {
  let component: VendaIngressosComponent;
  let fixture: ComponentFixture<VendaIngressosComponent>;

  let dialogSpy: MatDialog;

  beforeEach(async(() => {
    dialogSpy = jasmine.createSpyObj('MatDialog', ['open', 'closeAll']);

    TestBed.configureTestingModule({
      declarations: [ VendaIngressosComponent ],
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
      ],
      providers: [
        { provide: MatDialog, useValue: dialogSpy },
      ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(VendaIngressosComponent);
    component = fixture.componentInstance;
    const service: LoginService = TestBed.get(LoginService);
    service.usuarioInfo = {codUsuario: '1'};
    component.festa = {
      nomeFesta: 'Teste',
      codFesta: 'Teste',
      horarioInicioFesta: '2020-09-23 19:10:25',
      horarioFimFesta: '2020-09-23 19:10:25',
      codEnderecoFesta: null,
      descricaoFesta: null,
      urlImagem: null
    };
    component.cupomDesc = [{
      codCupom: '1',
      nomeCupom: 'teste',
      codFesta: '1'
    }];
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

  it('should format date from datetime', () => {
    const result = component.getDateFromDTF('2020-09-23 19:10:25');
    expect(result).toBe('23/09/2020');
  });

  it('should formar time from datetime', () => {
    const result = component.getTimeFromDTF('2020-09-23 19:10:25');
    expect(result).toBe('19:10:25');
  });

  it('should validate purchase buttom', () => {
    const result = component.validationFormButton();
    expect(result).toBe(true);
  });

  it('should buildForm', () => {
    component.lotes = [{
      codLote: '1',
      nomeLote: 'Teste1'
      },
      {
      codLote: '2',
      nomeLote: 'Teste2'
    }];
    component.buildForm();
    expect(component.form.get('quantidade-1')).toBeTruthy();
    expect(component.form.get('quantidade-2')).toBeTruthy();
  });

  it('should validate purchase buttom', () => {
    component.lotes = [{
      codLote: '1',
      nomeLote: 'Teste'
    }];

    component.buildForm();
    expect(component.validationFormButton()).toBeTruthy();
    component.form.get('quantidade-1').setValue('3');
    expect(component.validationFormButton()).toBeFalsy();
  });

  it('should get f to get form controls', () => {
    expect(component.f).toBe(component.form.controls);
  });

  it('should redirectUrl', () => {
    spyOn(component.router, 'navigate')
    .and
    .callThrough();

    component.redirectUrl();
    expect(component.router.navigate).toHaveBeenCalledWith(['../teste&Teste/venda-ingressos/venda-checkout']);
  });

  it('should aplicarCupom', () => {
    spyOn(component.getCupons, 'getCupomUnico')
    .and
    .callThrough();

    spyOn(component.getCupons, 'setFarol')
    .and
    .callThrough();

    component.cupomDesc = [{
      codCupom: '1',
      nomeCupom: 'teste',
      codFesta: '1'
    }];

    component.aplicarCupom('teste');

    component.redirectUrl();
    expect(component.getCupons.getCupomUnico).toHaveBeenCalled();
  });

  it('should checkout', () => {
    spyOn(component.buyIngresso, 'addIngresso')
    .and
    .callThrough();

    spyOn(component.buyIngresso, 'addPrecoTotal')
    .and
    .callThrough();

    spyOn(component, 'redirectUrl')
    .and
    .callThrough();

    component.lotes = [{
      codLote: '1',
      nomeLote: 'Teste'
    }];

    component.buildForm();
    component.form.get('quantidade-1').setValue('3');
    component.checkout();
    expect(component.buyIngresso.addIngresso).toHaveBeenCalled();
    expect(component.buyIngresso.addPrecoTotal).toHaveBeenCalled();
    expect(component.redirectUrl).toHaveBeenCalled();
  });

  it('should parserFloat', () => {
    const result = component.parserFloat(1);
    expect(result).toBe('1.00');
  });

});
