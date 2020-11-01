import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EditarFestaComponent } from './editar-festa.component';

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
import { GetFestaService } from 'src/app/services/get-festa/get-festa.service';
import { EditarFestaService } from 'src/app/services/editar-festa/editar-festa.service';

export function HttpLoaderFactory(http: HttpClient) {
  return new TranslateHttpLoader(http);
}

describe('EditarFestaComponent', () => {
  let component: EditarFestaComponent;
  let fixture: ComponentFixture<EditarFestaComponent>;
  let dialogSpy: MatDialog;

  beforeEach(async(() => {
    dialogSpy = jasmine.createSpyObj('MatDialog', ['open']);
    TestBed.configureTestingModule({
      declarations: [ EditarFestaComponent ],
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
        {provide: GetCategoriasService, useValue: {
          getCategorias: () => of([ {categoria: 'Teste1'}, {categoria: 'Teste2'} ]),
          setFarol: () => false,
        }},
        {provide: GetFestaService, useValue: {
          acessarFesta: () => of({
            nomeFesta: 'Teste',
            descricaoFesta: 'Teste',
            codEnderecoFesta: 'Teste',
            organizador: 'Teste',
            descOrganizador: 'Teste',
            horarioInicioFesta: '2020-02-01T12:00:00',
            horarioFimFesta: '2020-02-06T18:00:00',
            categoriaPrimaria: {
              codCategoria: 2,
              nomeCategoria: 'RAVEAFIM'
            },
            categoriaSecundaria: null
          }),
          setFarol: () => false,
        }},
        {provide: EditarFestaService, useValue: {
          atualizarFesta: () => of({}),
          setFarol: () => false,
        }},
      ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EditarFestaComponent);
    component = fixture.componentInstance;
    const service: LoginService = TestBed.get(LoginService);
    service.usuarioInfo = {codUsuario: '1'};
    component.festa = {
      nomeFesta: 'Teste',
      descricaoFesta: 'Teste',
      codEnderecoFesta: 'Teste',
      organizador: 'Teste',
      descOrganizador: 'Teste',
      horarioInicioFesta: '2020-02-01T12:00:00',
      horarioFimFesta: '2020-02-06T18:00:00',
      categoriaPrimaria: {
        codCategoria: 2,
        nomeCategoria: 'RAVEAFIM'
      },
      categoriaSecundaria: null
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

  it('should callServiceGet when resgatarFesta', () => {
    spyOn(component, 'callServiceGet');
    component.resgatarFesta();
    expect(component.callServiceGet).toHaveBeenCalled();
  });

  it('should callServiceGet when atualizarFesta', () => {
    component.festa = {codFesta: 'Teste'};
    spyOn(component, 'callServiceAtualizacao');
    component.atualizarFesta('teste', 'teste', 'teste', 'teste', 'teste', 'teste', 'teste', 'teste', 'teste');
    expect(component.callServiceAtualizacao).toHaveBeenCalled();
  });

  it('should getTimeFromDTF to format time from datetime', () => {
    expect(component.getTimeFromDTF('2020-07-19T12:00:00')).toBe('12:00');
  });

  it('should buildForm', () => {
    expect(component.form.get('nomeFesta')).toBeTruthy();
    expect(component.form.get('descFesta')).toBeTruthy();
    expect(component.form.get('endereco')).toBeTruthy();
    expect(component.form.get('inicioData')).toBeTruthy();
    expect(component.form.get('fimData')).toBeTruthy();
    expect(component.form.get('inicioHora')).toBeTruthy();
    expect(component.form.get('fimHora')).toBeTruthy();
    expect(component.form.get('categoriaPrincipal')).toBeTruthy();
    expect(component.form.get('categoriaSecundaria')).toBeTruthy();
    expect(component.form.get('organizador')).toBeTruthy();
    expect(component.form.get('descOrganizador')).toBeTruthy();
  });

  it('should setFormValues', () => {
    component.festa = {
      nomeFesta: 'Teste',
      descricaoFesta: 'Teste',
      codEnderecoFesta: 'Teste',
      organizador: 'Teste',
      descOrganizador: 'Teste',
      horarioInicioFesta: '2020-02-01T12:00:00',
      horarioFimFesta: '2020-02-06T18:00:00',
      categoriaPrimaria: {
        codCategoria: 2,
        nomeCategoria: 'RAVEAFIM'
      },
      categoriaSecundaria: null
    };

    component.setFormValues();

    expect(component.f.nomeFesta.value).toBe('Teste');
    expect(component.f.descFesta.value).toBe('Teste');
    expect(component.f.endereco.value).toBe('Teste');
    expect(component.f.organizador.value).toBe('Teste');
    expect(component.f.descOrganizador.value).toBe('Teste');
    expect(component.f.inicioData.value).toEqual(new Date(component.festa.horarioInicioFesta));
    expect(component.f.fimData.value).toEqual(new Date(component.festa.horarioFimFesta));
    expect(component.f.inicioHora.value).toBe('12:00');
    expect(component.f.fimHora.value).toBe('18:00');
    expect(component.minDate).toEqual(new Date(component.festa.horarioInicioFesta));
  });

  it('should resgatarCategorias', () => {
    spyOn(component.getCategoria, 'getCategorias')
    .and
    .callThrough();

    spyOn(component.getCategoria, 'setFarol')
    .and
    .callThrough();

    component.resgatarCategorias();

    expect(component.getCategoria.getCategorias).toHaveBeenCalled();
    expect(component.getCategoria.setFarol).toHaveBeenCalledWith(false);
    expect(component.categorias).toEqual([{categoria: 'Teste1'}, {categoria: 'Teste2'}]);
  });

  it('should callServiceGet', () => {
    spyOn(component.getFestaService, 'acessarFesta')
    .and
    .callThrough();

    spyOn(component.getFestaService, 'setFarol')
    .and
    .callThrough();

    spyOn(component, 'setFormValues')
    .and
    .callThrough();

    component.callServiceGet('teste');

    const festaResult = {
      nomeFesta: 'Teste',
      descricaoFesta: 'Teste',
      codEnderecoFesta: 'Teste',
      organizador: 'Teste',
      descOrganizador: 'Teste',
      horarioInicioFesta: '2020-02-01T12:00:00',
      horarioFimFesta: '2020-02-06T18:00:00',
      categoriaPrimaria: {
        codCategoria: 2,
        nomeCategoria: 'RAVEAFIM'
      },
      categoriaSecundaria: null
    };

    expect(component.getFestaService.acessarFesta).toHaveBeenCalled();
    expect(component.getFestaService.setFarol).toHaveBeenCalledWith(false);
    expect(component.festa).toEqual(festaResult);
    expect(component.setFormValues).toHaveBeenCalled();
  });

  it('should callServiceAtualizacao', () => {
    spyOn(component.festaService, 'atualizarFesta')
    .and
    .callThrough();

    spyOn(component.festaService, 'setFarol')
    .and
    .callThrough();


    spyOn(component, 'openDialogSuccess')
    .and
    .callThrough();

    component.callServiceAtualizacao({nomeFesta: 'teste'});

    expect(component.festaService.atualizarFesta).toHaveBeenCalledWith({nomeFesta: 'teste'});
    expect(component.festaService.setFarol).toHaveBeenCalledWith(false);
    expect(component.openDialogSuccess).toHaveBeenCalledWith('FESTAALT');
  });

});
