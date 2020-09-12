import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DistribuicaoPermissoesComponent } from './distribuicao-permissoes.component';
import { RouterModule } from '@angular/router';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { CustomMaterialModule } from '../material/material.module';
import { FormsModule, ReactiveFormsModule, FormControl, FormBuilder, FormGroup } from '@angular/forms';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { TranslateModule, TranslateLoader } from '@ngx-translate/core';
import { TranslateHttpLoader } from '@ngx-translate/http-loader';
import { HttpClient } from '@angular/common/http';
import { LoginService } from 'src/app/services/loginService/login.service';
import { MatDialog } from '@angular/material';
import { of } from 'rxjs';
import { GetGruposService } from 'src/app/services/get-grupos/get-grupos.service';
import { GetFestaService } from 'src/app/services/get-festa/get-festa.service';

export function HttpLoaderFactory(http: HttpClient) {
  return new TranslateHttpLoader(http);
}

describe('DistribuicaoPermissoesComponent', () => {
  let component: DistribuicaoPermissoesComponent;
  let fixture: ComponentFixture<DistribuicaoPermissoesComponent>;
  let dialogSpy: MatDialog;

  beforeEach(async(() => {
    dialogSpy = jasmine.createSpyObj('MatDialog', ['open', 'closeAll']);

    TestBed.configureTestingModule({
      declarations: [ DistribuicaoPermissoesComponent ],
      imports: [
        RouterModule.forRoot([]),
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
        })
      ],
      providers: [
        { provide: MatDialog, useValue: dialogSpy },
        {provide: GetGruposService, useValue: {
          getGrupos: () => of([{codGrupo: 'teste', usuariosTO: [{codUsuario: 'teste1'}]}]),
          setFarol: () => false,
        }},
        {provide: GetFestaService, useValue: {
          acessarFesta: () => of({codFesta: 'teste', usuarios: [{codUsuario: 'teste1'}, {codUsuario: 'teste2'}]}),
          setFarol: () => false,
        }}
      ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DistribuicaoPermissoesComponent);
    component = fixture.componentInstance;
    const service: LoginService = TestBed.get(LoginService);
    service.usuarioInfo = {codUsuario: '1'};
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should open a assing dialog through a method', () => {
    component.festa = {codFesta: '1', usuarios: []};
    component.relacaoGrupoMembros = [['1']];
    component.openAssingDialog(0, {});
    expect(dialogSpy.open).toHaveBeenCalled();
  });

  it('should buildForm', () => {
    component.festa = {codFesta: '1', usuarios: [{codUsuario: 'teste1'}, {codUsuario: 'teste2'}]};
    component.grupos = [{codGrupo: 'teste', usuariosTO: [{codUsuario: 'teste1'}]}];
    component.buildForm();
    expect(component.form.get('testeteste1')).toBeTruthy();
    expect(component.form.get('testeteste2')).toBeTruthy();
  });

  it('should gerarRelacao', () => {
    component.festa = {codFesta: '1', usuarios: [{codUsuario: 'teste1'}, {codUsuario: 'teste2'}]};
    component.grupos = [{codGrupo: 'teste', usuariosTO: [{codUsuario: 'teste1'}]}];
    component.form = new FormBuilder().group({
      testeteste1: new FormControl(false),
      testeteste2: new FormControl(false)
    });
    component.gerarRelacao();
    expect(component.relacaoGrupoMembros).toEqual([[ 'teste1' ], [ 'teste1' ]]);
    expect(component.form.get('testeteste1').value).toBeTruthy();
  });

  it('should resgatarFesta', () => {
    spyOn(component.getFesta, 'acessarFesta')
    .and
    .callThrough();

    spyOn(component.getFesta, 'setFarol')
    .and
    .callThrough();

    spyOn(component, 'resgatarGrupo')
    .and
    .callThrough();

    let idFesta = component.router.url;
    idFesta = idFesta.slice(idFesta.indexOf('&') + 1, idFesta.indexOf('/', idFesta.indexOf('&')));
    component.resgatarFesta();
    expect(component.getFesta.acessarFesta).toHaveBeenCalledWith(idFesta);
    expect(component.getFesta.setFarol).toHaveBeenCalledWith(false);
    expect(component.resgatarGrupo).toHaveBeenCalled();
    expect(component.festa).toEqual({usuarios: [{codUsuario: 'teste1'}, {codUsuario: 'teste2'}], codFesta: 'teste'});
  });

  it('should resgatarGrupo', () => {
    spyOn(component.getGrupos, 'getGrupos')
    .and
    .callThrough();

    spyOn(component.getGrupos, 'setFarol')
    .and
    .callThrough();

    spyOn(component, 'buildForm')
    .and
    .callThrough();

    let idFesta = component.router.url;
    idFesta = idFesta.slice(idFesta.indexOf('&') + 1, idFesta.indexOf('/', idFesta.indexOf('&')));
    component.resgatarGrupo();
    expect(component.getGrupos.getGrupos).toHaveBeenCalledWith(idFesta);
    expect(component.getGrupos.setFarol).toHaveBeenCalledWith(false);
    expect(component.buildForm).toHaveBeenCalled();
    expect(component.grupos).toEqual([{codGrupo: 'teste', usuariosTO: [{codUsuario: 'teste1'}]}]);
  });

  it('should updateRelacao', () => {
    component.relacaoGrupoMembros = [[]];
    component.updateRelacao(0, 'teste');
    expect(component.relacaoGrupoMembros[0]).toEqual(['teste']);
    component.updateRelacao(0, 'teste');
    expect(component.relacaoGrupoMembros[0]).toEqual([]);
  });
});
