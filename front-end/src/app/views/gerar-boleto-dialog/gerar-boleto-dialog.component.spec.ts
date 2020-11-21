import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { CustomMaterialModule } from '../material/material.module';
import { NgxMaterialTimepickerModule } from 'ngx-material-timepicker';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { TranslateModule, TranslateLoader } from '@ngx-translate/core';
import { HttpClient } from '@angular/common/http';
import { LoginService } from 'src/app/services/loginService/login.service';
import { MAT_DIALOG_DATA, MatDialog } from '@angular/material';
import { TranslateHttpLoader } from '@ngx-translate/http-loader';
import { CriarEstoqueService } from 'src/app/services/criar-estoque/criar-estoque.service';
import { of } from 'rxjs';

import { PagSeguroService } from 'src/app/services/pag-seguro/pag-seguro.service';

import { GerarBoletoDialogComponent } from './gerar-boleto-dialog.component';

export function HttpLoaderFactory(http: HttpClient) {
  return new TranslateHttpLoader(http);
}

describe('GerarBoletoDialogComponent', () => {
  let component: GerarBoletoDialogComponent;
  let fixture: ComponentFixture<GerarBoletoDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GerarBoletoDialogComponent ],
      imports: [
        CustomMaterialModule,
        NgxMaterialTimepickerModule,
        FormsModule,
        ReactiveFormsModule,
        HttpClientTestingModule,
        BrowserAnimationsModule,
        TranslateModule.forRoot({
          loader: {
            provide: TranslateLoader,
            useFactory: HttpLoaderFactory,
            deps: [HttpClient]
          }
        })
      ],
      providers: [
        {provide: PagSeguroService, useValue: {
          gerarBoleto: () => of({})
        }},
        { provide: MAT_DIALOG_DATA, useValue: {festa: {nomeFesta: 'Teste'}, preco: 1000 }}
      ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GerarBoletoDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should get f to get form controls', () => {
    expect(component.f).toBe(component.form.controls);
  });

  it('should gerarForm', () => {
    expect(component.form.get('nomePagador')).toBeTruthy();
    expect(component.form.get('identificacao')).toBeTruthy();
    expect(component.form.get('email')).toBeTruthy();
    expect(component.form.get('pais')).toBeTruthy();
    expect(component.form.get('estado')).toBeTruthy();
    expect(component.form.get('cidade')).toBeTruthy();
    expect(component.form.get('cep')).toBeTruthy();
    expect(component.form.get('bairro')).toBeTruthy();
    expect(component.form.get('rua')).toBeTruthy();
    expect(component.form.get('numero')).toBeTruthy();
  });

  it('should gerarBoleto', () => {
    spyOn(component.pagSeguroService, 'gerarBoleto')
    .and
    .callThrough();

    component.gerarBoleto();

    expect(component.pagSeguroService.gerarBoleto).toHaveBeenCalled();
  });
});
