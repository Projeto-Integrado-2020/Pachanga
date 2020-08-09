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

export function HttpLoaderFactory(http: HttpClient) {
  return new TranslateHttpLoader(http);
}

describe('DistribuicaoPermissoesComponent', () => {
  let component: DistribuicaoPermissoesComponent;
  let fixture: ComponentFixture<DistribuicaoPermissoesComponent>;
  let dialogSpy: MatDialog;

  beforeEach(async(() => {
    dialogSpy = jasmine.createSpyObj('MatDialog', ['open']);

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
        { provide: MatDialog, useValue: dialogSpy }
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
    expect(component.relacaoGrupoMembros).toEqual([['teste1']]);
    expect(component.form.get('testeteste1').value).toBeTruthy();
  });
});
