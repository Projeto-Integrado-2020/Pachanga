import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EditarLoteComponent } from './editar-lote.component';
import { CustomMaterialModule } from '../material/material.module';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NgxMaterialTimepickerModule } from 'ngx-material-timepicker';

import { TranslateModule, TranslateLoader } from '@ngx-translate/core';
import { TranslateHttpLoader } from '@ngx-translate/http-loader';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { HttpClient } from '@angular/common/http';

import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { RouterModule } from '@angular/router';
import { MatDialog } from '@angular/material';
import { LoginService } from 'src/app/services/loginService/login.service';
import { GetCategoriasService } from 'src/app/services/get-categorias/get-categorias.service';
import { of } from 'rxjs';
import { GetLoteService } from 'src/app/services/get-lote/get-lote.service';
import { GetLoteUnicoService } from 'src/app/services/get-lote-unico/get-lote-unico.service';
import { EditarLoteService } from 'src/app/services/editar-lote/editar-lote.service';

export function HttpLoaderFactory(http: HttpClient) {
  return new TranslateHttpLoader(http);
}

describe('EditarLoteComponent', () => {
  let component: EditarLoteComponent;
  let fixture: ComponentFixture<EditarLoteComponent>;
  let dialogSpy: MatDialog;

  beforeEach(async(() => {
    dialogSpy = jasmine.createSpyObj('MatDialog', ['open']);
    TestBed.configureTestingModule({
      declarations: [ EditarLoteComponent ],
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
        }),
        RouterModule.forRoot([])
      ],
      providers: [
        { provide: MatDialog, useValue: dialogSpy },
        { provide: GetLoteUnicoService, useValue: {
          getLoteUnico: () => of({
            codLote: 'Teste',
            codFesta: 'Teste',
            numeroLote: 'Teste',
            quantidade: 'Teste',
            preco: 'Teste',
            nomeLote: 'Teste',
            descLote: 'Teste',
            horarioInicio: '2020-02-01T12:00:00',
            horarioFim: '2020-02-06T18:00:00'
          }),
          setFarol: () => false,
        }},
        {provide: EditarLoteService, useValue: {
          editarLote: () => of({}),
          setFarol: () => false,
        }},
      ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EditarLoteComponent);
    component = fixture.componentInstance;
    const service: LoginService = TestBed.get(LoginService);
    service.usuarioInfo = {codUsuario: '1'};
    component.lote = {
      codLote: 'Teste',
      codFesta: 'Teste',
      numeroLote: 'Teste',
      quantidade: 'Teste',
      preco: 'Teste',
      nomeLote: 'Teste',
      descLote: 'Teste',
      horarioInicio: '2020-02-01T12:00:00',
      horarioFim: '2020-02-06T18:00:00'
    };
    component.gerarForm();
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should get f to get form controls', () => {
    expect(component.f).toBe(component.form.controls);
  });

  it('should open a dialog through a method', () => {
    component.openDialogSuccess('teste');
    expect(dialogSpy.open).toHaveBeenCalled();
  });

  it('should getLoteUnico when loteUnico', () => {
    spyOn(component, 'getLoteUnico');
    component.getLoteUnico();
    expect(component.getLoteUnico).toHaveBeenCalled();
  });

  it('should getLoteUnico when atualizarLote', () => {
    component.festa = {codFesta: 'Teste'};
    spyOn(component, 'editLote');
    component.editLote('1', '2', 'teste', 'teste', 'teste', 'teste', 'teste', 'teste', 'teste');
    expect(component.editLote).toHaveBeenCalled();
  });

  it('should getTimeFromDTF to format time from datetime', () => {
    expect(component.getTimeFromDTF('2020-02-01T12:00:00')).toBe('12:00');
  });

  it('should setFormValues', () => {
    component.lote = {
      codLote: 'Teste',
      codFesta: 'Teste',
      numeroLote: 'Teste',
      quantidade: 'Teste',
      preco: 'Teste',
      nomeLote: 'Teste',
      descLote: 'Teste',
      horarioInicio: '2020-02-01T12:00:00',
      horarioFim: '2020-02-06T18:00:00'
    };

    component.setFormValues();

    expect(component.f.numeroLote.value).toBe('Teste');
    expect(component.f.quantidade.value).toBe('Teste');
    expect(component.f.preco.value).toBe('Teste');
    expect(component.f.nomeLote.value).toBe('Teste');
    expect(component.f.descLote.value).toBe('Teste');
    expect(component.f.inicioData.value).toEqual(new Date(component.lote.horarioInicio));
    expect(component.f.fimData.value).toEqual(new Date(component.lote.horarioFim));
    expect(component.f.dthrInicio.value).toBe('12:00');
    expect(component.f.dthrFim.value).toBe('18:00');
    expect(component.minDate).toEqual(new Date(component.lote.horarioInicio));
  });

  it('should buildForm', () => {
    expect(component.form.get('numeroLote')).toBeTruthy();
    expect(component.form.get('quantidade')).toBeTruthy();
    expect(component.form.get('preco')).toBeTruthy();
    expect(component.form.get('nomeLote')).toBeTruthy();
    expect(component.form.get('descLote')).toBeTruthy();
    expect(component.form.get('inicioData')).toBeTruthy();
    expect(component.form.get('fimData')).toBeTruthy();
    expect(component.form.get('dthrInicio')).toBeTruthy();
    expect(component.form.get('dthrFim')).toBeTruthy();
  });

  it('should getLote', () => {
    spyOn(component.loteUnico, 'getLoteUnico')
    .and
    .callThrough();

    spyOn(component.loteUnico, 'setFarol')
    .and
    .callThrough();

    spyOn(component, 'setFormValues')
    .and
    .callThrough();

    component.getLoteUnico();

    const loteResult = {
      codLote: 'Teste',
      codFesta: 'Teste',
      numeroLote: 'Teste',
      quantidade: 'Teste',
      preco: 'Teste',
      nomeLote: 'Teste',
      descLote: 'Teste',
      horarioInicio: '2020-02-01T12:00:00',
      horarioFim: '2020-02-06T18:00:00'
    };

    expect(component.loteUnico.getLoteUnico).toHaveBeenCalled();
    expect(component.loteUnico.setFarol).toHaveBeenCalledWith(false);
    expect(component.lote).toEqual(loteResult);
    expect(component.setFormValues).toHaveBeenCalled();
  });

  it('should editarLote', () => {
    spyOn(component.loteEdit, 'editarLote')
    .and
    .callThrough();

    spyOn(component.loteEdit, 'setFarol')
    .and
    .callThrough();


    spyOn(component, 'openDialogSuccess')
    .and
    .callThrough();

    component.festa = {
      codFesta: 'teste',
      nomeFesta: 'teste'
    };

    component.editLote('teste', '2', 'teste', 'teste', 'teste', 'teste', 'teste', 'teste', 'teste');

    expect(component.loteEdit.editarLote).toHaveBeenCalledWith({ codFesta: 'Teste',
                                                                  codLote: 'Teste',
                                                                  numeroLote: 'teste',
                                                                  quantidade: 'teste',
                                                                  preco: 'teste',
                                                                  nomeLote: '2',
                                                                  descLote: 'teste',
                                                                  horarioInicio: '-te-teTteste',
                                                                  horarioFim: '-te-teTteste'
                                                                });
    expect(component.loteEdit.setFarol).toHaveBeenCalledWith(false);
    expect(component.openDialogSuccess).toHaveBeenCalledWith('LOTEALT');
  });

});
