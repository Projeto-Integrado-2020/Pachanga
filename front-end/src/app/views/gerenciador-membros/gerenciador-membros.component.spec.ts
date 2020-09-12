import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { TranslateModule, TranslateLoader } from '@ngx-translate/core';
import { TranslateHttpLoader } from '@ngx-translate/http-loader';
import { HttpClient } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { CustomMaterialModule } from '../material/material.module';
import { ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { MatDialog, MatTableDataSource } from '@angular/material';
import { LoginService } from 'src/app/services/loginService/login.service';
import { GerenciadorMembrosComponent } from './gerenciador-membros.component';
import { GetGruposService } from 'src/app/services/get-grupos/get-grupos.service';
import { of } from 'rxjs';
import { GetFestaService } from 'src/app/services/get-festa/get-festa.service';

export function HttpLoaderFactory(http: HttpClient) {
  return new TranslateHttpLoader(http);
}

describe('GerenciadorMembrosComponent', () => {
  let component: GerenciadorMembrosComponent;
  let fixture: ComponentFixture<GerenciadorMembrosComponent>;
  let dialogSpy: MatDialog;

  beforeEach(async(() => {
    dialogSpy = jasmine.createSpyObj('MatDialog', ['open']);
    TestBed.configureTestingModule({
      declarations: [ GerenciadorMembrosComponent ],
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
          getGrupos: () => of([{
            usuariosTO: [
              {nomeUser: 'teste1', codUsuario: 'teste1'}
            ],
            convidadosTO: [
              {email: 'teste2', codConvidado: 'teste2'}
            ]
            }]),
          setFarol: () => false,
        }},
      ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GerenciadorMembrosComponent);
    component = fixture.componentInstance;
    const service: LoginService = TestBed.get(LoginService);
    service.usuarioInfo = {codUsuario: '1'};
    component.festa = {codFesta: '1'};
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should open a invite dialog through a method', () => {
    component.festa = {codFesta: '1'};
    component.openDialogInvite('teste');
    expect(dialogSpy.open).toHaveBeenCalled();
  });

  it('should open a delete dialog through a method', () => {
    component.festa = {codFesta: '1'};
    component.openDialogDeleteMembro('teste', 'teste');
    expect(dialogSpy.open).toHaveBeenCalled();
  });

  it('should open a delete dialog through a method', () => {
    component.festa = {codFesta: '1'};
    component.openDialogEdit('teste', 'teste', 'teste');
    expect(dialogSpy.open).toHaveBeenCalled();
  });

  it('should open a delete dialog through a method', () => {
    component.festa = {codFesta: '1'};
    component.openDialogDeleteGrupo('teste');
    expect(dialogSpy.open).toHaveBeenCalled();
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
    spyOn(component.getGruposService, 'getGrupos')
    .and
    .callThrough();

    spyOn(component.getGruposService, 'setFarol')
    .and
    .callThrough();

    const grupos = [{
      usuariosTO: [
        {nomeUser: 'teste1', codUsuario: 'teste1'}
      ],
      convidadosTO: [
        {email: 'teste2', codConvidado: 'teste2'}
      ]
    }];

    component.festa = {codFesta: 'teste'};
    component.resgatarGrupo();
    expect(component.getGruposService.getGrupos).toHaveBeenCalledWith('teste');
    expect(component.getGruposService.setFarol).toHaveBeenCalledWith(false);
    expect(component.grupos).toEqual(grupos);
  });

});
