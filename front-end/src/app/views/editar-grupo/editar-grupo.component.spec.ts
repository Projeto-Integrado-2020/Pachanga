import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EditarGrupoComponent } from './editar-grupo.component';
import { CustomMaterialModule } from '../material/material.module';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NgxMaterialTimepickerModule } from 'ngx-material-timepicker';

import { TranslateModule, TranslateLoader } from '@ngx-translate/core';
import { TranslateHttpLoader } from '@ngx-translate/http-loader';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { HttpClient } from '@angular/common/http';

import { FormControl, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';

import { RouterModule } from '@angular/router';
import { MatDialog } from '@angular/material';
import { LoginService } from 'src/app/services/loginService/login.service';
import { GetFestaService } from 'src/app/services/get-festa/get-festa.service';
import { of } from 'rxjs';
import { GetGruposService } from 'src/app/services/get-grupos/get-grupos.service';
import { GetPermissoesService } from 'src/app/services/get-permissoes/get-permissoes.service';
import { EditarGrupoService } from 'src/app/services/editar-grupo/editar-grupo.service';

export function HttpLoaderFactory(http: HttpClient) {
  return new TranslateHttpLoader(http);
}

describe('EditarGrupoComponent', () => {
  let component: EditarGrupoComponent;
  let fixture: ComponentFixture<EditarGrupoComponent>;
  let dialogSpy: MatDialog;

  beforeEach(async(() => {
    dialogSpy = jasmine.createSpyObj('MatDialog', ['open']);

    TestBed.configureTestingModule({
      declarations: [ EditarGrupoComponent ],
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
        {provide: GetGruposService, useValue: {
          getGrupoUnico: () => of({permissoesTO: [{codPermissao: 'teste1'}, {codPermissao: 'teste2'}]}),
          setFarol: () => false,
        }},
        {provide: GetPermissoesService, useValue: {
          getPermissoes: () => of([{descPermissao: 'Teste1'}, {descPermissao: 'Teste2'}]),
          setFarol: () => false,
        }},
        {provide: EditarGrupoService, useValue: {
          editarGrupo: () => of([{descPermissao: 'Teste1'}, {descPermissao: 'Teste2'}]),
          setFarol: () => false,
        }},
      ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EditarGrupoComponent);
    component = fixture.componentInstance;
    const service: LoginService = TestBed.get(LoginService);
    service.usuarioInfo = {codUsuario: '1'};
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

  it('should setFormValues', () => {
    component.grupo = {nomeGrupo: 'Teste'};
    component.permissoesGrupo = [2];
    component.permissoes = [
      {descPermissao: 'teste1', codPermissao: 1}, {descPermissao: 'teste2', codPermissao: 2}
    ];
    component.buildForm();
    component.setFormValues();
    expect(component.f.teste1.value).toBeFalsy();
    expect(component.f.teste2.value).toBeTruthy();
  });

  it('should resgatarFesta', () => {
    spyOn(component.getFestaService, 'acessarFesta')
    .and
    .callThrough();

    spyOn(component.getFestaService, 'setFarol')
    .and
    .callThrough();

    spyOn(component, 'resgatarGrupo')
    .and
    .callThrough();

    component.resgatarFesta();

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
    expect(component.resgatarGrupo).toHaveBeenCalled();
  });

  it('should resgatarGrupo', () => {
    spyOn(component.getGrupo, 'getGrupoUnico')
    .and
    .callThrough();

    spyOn(component.getGrupo, 'setFarol')
    .and
    .callThrough();

    spyOn(component, 'setFormValues')
    .and
    .callThrough();

    const grupo = {
      permissoesTO: [{codPermissao: 'teste1'}, {codPermissao: 'teste2'}]
    };

    component.resgatarGrupo();
    expect(component.getGrupo.getGrupoUnico).toHaveBeenCalled();
    expect(component.getGrupo.setFarol).toHaveBeenCalledWith(false);
    expect(component.grupo).toEqual(grupo);
    expect(component.permissoesGrupo).toEqual(['teste1', 'teste2', 'teste1', 'teste2']);
    expect(component.setFormValues).toHaveBeenCalled();
  });

  it('should resgatarPermissoes', () => {
    spyOn(component.getPermissaoService, 'getPermissoes')
    .and
    .callThrough();

    spyOn(component.getPermissaoService, 'setFarol')
    .and
    .callThrough();

    spyOn(component, 'buildForm')
    .and
    .callThrough();

    component.resgatarPermissoes();

    expect(component.getPermissaoService.getPermissoes).toHaveBeenCalled();
    expect(component.getPermissaoService.setFarol).toHaveBeenCalledWith(false);
    expect(component.permissoes).toEqual([{descPermissao: 'Teste1'}, {descPermissao: 'Teste2'}]);
    expect(component.buildForm).toHaveBeenCalled();
  });

  it('should updateListaPermissao', () => {
    const group = {nomeGrupo: new FormControl('', Validators.required)};
    component.permissoes = [
      {descPermissao: 'teste1', codPermissao: 1},
      {descPermissao: 'teste2', codPermissao: 2},
      {descPermissao: 'teste3', codPermissao: 3},
      {descPermissao: 'teste4', codPermissao: 4},
      {descPermissao: 'teste5', codPermissao: 5}
    ];

    for (const permissao of component.permissoes) {
      group[permissao.descPermissao] = new FormControl(false);
    }
    component.form = component.formBuilder.group(group);
    component.permissoesGrupo = [];
    component.updateListaPermissao({descPermissao: 'teste1', codPermissao: 1});
    expect(component.permissoesGrupo).toEqual([1]);
    component.updateListaPermissao({descPermissao: 'teste2', codPermissao: 2});
    expect(component.permissoesGrupo).toEqual([1, 2]);
    component.form.get('teste1').setValue(true);
    component.updateListaPermissao({descPermissao: 'teste1', codPermissao: 1});
    expect(component.permissoesGrupo).toEqual([2]);
  });

  it('should buildForm', () => {
    component.permissoes = [{descPermissao: 'Teste1'}, {descPermissao: 'Teste2'}];

    expect(component.form.get('Teste1')).toBeTruthy();
    expect(component.form.get('Teste2')).toBeTruthy();
  });

  it('should editarGrupo', () => {
    spyOn(component.editGrupo, 'editarGrupo')
    .and
    .callThrough();

    spyOn(component.editGrupo, 'setFarol')
    .and
    .callThrough();

    spyOn(component, 'openDialogSuccess')
    .and
    .callThrough();

    component.permissoesGrupo = ['teste'];
    component.grupo = {codGrupo: 'teste'};
    component.festa = {codFesta: 'teste'};
    const grupo = {
      codGrupo: 'teste',
      codFesta: 'teste',
      nomeGrupo: 'teste',
      permissoes: ['teste']
    };

    component.editarGrupo('teste');
    expect(component.editGrupo.editarGrupo).toHaveBeenCalledWith(grupo);
    expect(component.editGrupo.setFarol).toHaveBeenCalledWith(false);
    expect(component.openDialogSuccess).toHaveBeenCalledWith('GRUPOALT');
  });
});
