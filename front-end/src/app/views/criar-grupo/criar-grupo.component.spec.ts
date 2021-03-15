import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TranslateModule, TranslateLoader } from '@ngx-translate/core';
import { TranslateHttpLoader } from '@ngx-translate/http-loader';
import { HttpClient } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { CustomMaterialModule } from '../../views/material/material.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { CriarGrupoComponent } from './criar-grupo.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { RouterModule } from '@angular/router';
import { FormControl, Validators } from '@angular/forms';
import { LoginService } from 'src/app/services/loginService/login.service';
import { GetPermissoesService } from 'src/app/services/get-permissoes/get-permissoes.service';
import { of } from 'rxjs';
import { GetFestaService } from 'src/app/services/get-festa/get-festa.service';
import { CriarGrupoService } from 'src/app/services/criar-grupo/criar-grupo.service';
import { PermissionFilter } from '../utils/permission-filter.pipe';

export function HttpLoaderFactory(http: HttpClient) {
  return new TranslateHttpLoader(http);
}

describe('CriarGrupoComponent', () => {
  let component: CriarGrupoComponent;
  let fixture: ComponentFixture<CriarGrupoComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CriarGrupoComponent, PermissionFilter ],
      imports: [
        HttpClientTestingModule,
        CustomMaterialModule,
        FormsModule,
        ReactiveFormsModule,
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
        {provide: GetPermissoesService, useValue: {
          getPermissoes: () => of([
            {descPermissao: 'Teste1', tipPermissao: 'GE'},
            {descPermissao: 'Teste2', tipPermissao: 'GE'}
          ]),
          setFarol: () => false,
        }},
        {provide: GetFestaService, useValue: {
          acessarFesta: () => of({nomeFesta: 'testeFesta'}),
          setFarol: () => false,
        }},
        {provide: CriarGrupoService, useValue: {
          adicionarGrupo: () => of({}),
          setFarol: () => false,
        }}
      ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CriarGrupoComponent);
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

  it('should callServiceGet when resgatarFesta', () => {
    spyOn(component, 'callServiceGet');
    component.resgatarFesta();
    expect(component.callServiceGet).toHaveBeenCalled();
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
    component.updateListaPermissao({descPermissao: 'teste1', codPermissao: 1});
    expect(component.permissoesGrupo).toEqual([1]);
    component.updateListaPermissao({descPermissao: 'teste2', codPermissao: 2});
    expect(component.permissoesGrupo).toEqual([1, 2]);
    component.form.get('teste1').setValue(true);
    component.updateListaPermissao({descPermissao: 'teste1', codPermissao: 1});
    expect(component.permissoesGrupo).toEqual([2]);
  });

  it('should resgatarPermissoes', () => {
    spyOn(component.getPermissaoService, 'getPermissoes')
    .and
    .callThrough();

    spyOn(component, 'buildForm')
    .and
    .callThrough();

    component.resgatarPermissoes();

    expect(component.getPermissaoService.getPermissoes).toHaveBeenCalled();
    expect(component.permissoes).toEqual([
      {descPermissao: 'Teste1', tipPermissao: 'GE'}, {descPermissao: 'Teste2', tipPermissao: 'GE'}
    ]);
    expect(component.buildForm).toHaveBeenCalled();
  });

  it('should callServiceGet', () => {
    spyOn(component.getFestaService, 'acessarFesta')
    .and
    .callThrough();

    component.callServiceGet('teste');

    expect(component.getFestaService.acessarFesta).toHaveBeenCalledWith('teste');
    expect(component.festa).toEqual({nomeFesta: 'testeFesta'});
  });

  it('should criarGrupo', () => {
    spyOn(component.addGrupoService, 'adicionarGrupo')
    .and
    .callThrough();

    spyOn(component.addGrupoService, 'setFarol')
    .and
    .callThrough();

    const grupo = {
      nomeGrupo: 'teste',
      codFesta: 'teste',
      permissoes: []
    };
    component.festa = {codFesta: 'teste'};
    component.permissoesGrupo = [];
    component.criarGrupo('teste');

    expect(component.addGrupoService.adicionarGrupo).toHaveBeenCalledWith(grupo);
    expect(component.addGrupoService.setFarol).toHaveBeenCalledWith(false);
  });

  it('should buildForm', () => {
    component.permissoes = [{descPermissao: 'Teste1'}, {descPermissao: 'Teste2'}];

    expect(component.form.get('Teste1')).toBeTruthy();
    expect(component.form.get('Teste2')).toBeTruthy();
  });

  it('should selecionarTudo', () => {
    const group = {nomeGrupo: new FormControl('', Validators.required)};
    component.permissoes = [
      {descPermissao: 'teste1', codPermissao: 1, tipPermissao: 'GE'},
      {descPermissao: 'teste2', codPermissao: 2, tipPermissao: 'GE'},
      {descPermissao: 'teste3', codPermissao: 3, tipPermissao: 'GE'},
      {descPermissao: 'teste4', codPermissao: 4, tipPermissao: 'GE'},
      {descPermissao: 'teste5', codPermissao: 5, tipPermissao: 'GE'}
    ];

    for (const permissao of component.permissoes) {
      group[permissao.descPermissao] = new FormControl(false);
    }
    component.form = component.formBuilder.group(group);
    component.selecionarTudo('GE');
    expect(component.permissoesGrupo).toEqual([1, 2, 3, 4, 5]);
  });
});
