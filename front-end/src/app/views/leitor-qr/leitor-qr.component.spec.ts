import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { CustomMaterialModule } from '../material/material.module';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { TranslateModule, TranslateLoader } from '@ngx-translate/core';
import { HttpClient } from '@angular/common/http';
import { TranslateHttpLoader } from '@ngx-translate/http-loader';

import { LeitorQrComponent } from './leitor-qr.component';
import { LoginService } from 'src/app/services/loginService/login.service';
import { ZXingScannerModule } from '@zxing/ngx-scanner';
import { MatDialog } from '@angular/material';
import { GetIntegracaoService } from 'src/app/services/get-integracao/get-integracao.service';
import { of } from 'rxjs';
import { GetFestaService } from 'src/app/services/get-festa/get-festa.service';
import { SymplaApiService } from 'src/app/services/sympla-api/sympla-api.service';

export function HttpLoaderFactory(http: HttpClient) {
  return new TranslateHttpLoader(http);
}

describe('LeitorQrComponent', () => {
  let component: LeitorQrComponent;
  let fixture: ComponentFixture<LeitorQrComponent>;
  let dialogSpy: MatDialog;

  beforeEach(async(() => {
    dialogSpy = jasmine.createSpyObj('MatDialog', ['open', 'closeAll']);

    TestBed.configureTestingModule({
      declarations: [ LeitorQrComponent ],
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
        ZXingScannerModule
      ],
      providers: [
        { provide: MatDialog, useValue: dialogSpy },
        {provide: GetIntegracaoService, useValue: {
          getIntegracoes: () => of([{
            codInfo: 'testeCod',
            tipoIngressoScanner: 'S'
          }]),
          setFarol: () => false
        }},
        {provide: GetFestaService, useValue: {
          acessarFesta: () => of([{
            codFesta: 'testeCod',
            nomeFesta: 'testeNome'
          }]),
          setFarol: () => false
        }},
        {provide: SymplaApiService, useValue: {
          checkInIngresso: () => of({
          }),
        }},
      ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LeitorQrComponent);
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

  it('should get f to get form controls', () => {
    expect(component.f).toBe(component.form.controls);
  });

  it('should alterarTipoIngresso scanner', () => {
    component.alterarTipoIngresso(true);
    expect(component.tipoIngressoScanner).toBe('S');
    expect(component.tipoIngressoForm).toBe('P');
  });

  it('should alterarTipoIngresso form', () => {
    component.alterarTipoIngresso(false);
    expect(component.tipoIngressoScanner).toBe('P');
    expect(component.tipoIngressoForm).toBe('S');
  });

  it('should limparScanner', () => {
    component.scannerSucesso = true;
    component.scannerErro = true;
    component.scannerLoading = true;

    component.limparScanner();
    expect(component.scannerSucesso).toBeFalsy();
    expect(component.scannerErro).toBeFalsy();
    expect(component.scannerLoading).toBeFalsy();
  });

  it('should open Errodialog through a method', () => {
    component.openErrorDialog('teste');
    expect(dialogSpy.open).toHaveBeenCalled();
  });

  it('should open a Sucessdialog through a method', () => {
    component.openSuccessDialog('teste');
    expect(dialogSpy.open).toHaveBeenCalled();
  });

  it('should resgatarFesta', () => {
    spyOn(component.getFestaService, 'acessarFesta')
    .and
    .callThrough();

    spyOn(component.getFestaService, 'setFarol')
    .and
    .callThrough();

    component.resgatarFesta();
    expect(component.getFestaService.acessarFesta).toHaveBeenCalled();
    expect(component.getFestaService.setFarol).toHaveBeenCalledWith(false);
  });

  it('should resgatarIntegracoes', () => {
    spyOn(component.getIntegracoes, 'getIntegracoes')
    .and
    .callThrough();

    spyOn(component.getIntegracoes, 'setFarol')
    .and
    .callThrough();

    component.resgatarIntegracaoSympla();
    expect(component.getIntegracoes.getIntegracoes).toHaveBeenCalled();
    expect(component.getIntegracoes.setFarol).toHaveBeenCalledWith(false);
  });

  it('should executarCheckInForm Sympla', () => {
    spyOn(component.symplaApi, 'checkInIngresso')
    .and
    .callThrough();

    component.tipoIngressoForm = 'S';
    component.integracaoSympla = {codInfo: 'teste'};

    component.executarCheckInForm('teste');
    expect(component.symplaApi.checkInIngresso).toHaveBeenCalled();
  });

  it('should executarCheckInScanner Sympla', () => {
    spyOn(component.symplaApi, 'checkInIngresso')
    .and
    .callThrough();

    component.tipoIngressoScanner = 'S';
    component.integracaoSympla = {codInfo: 'teste'};

    component.executarCheckInScanner('teste');
    expect(component.symplaApi.checkInIngresso).toHaveBeenCalled();
  });

  it('should executarCheckInForm Sympla error', () => {

    component.tipoIngressoForm = 'S';
    component.integracaoSympla = null;

    component.executarCheckInForm('teste');
    expect(dialogSpy.open).toHaveBeenCalled();
  });

  it('should executarCheckInScanner Sympla error', () => {
    component.tipoIngressoScanner = 'S';
    component.integracaoSympla = null;

    component.executarCheckInScanner('teste');
    expect(dialogSpy.open).toHaveBeenCalled();
  });
});
