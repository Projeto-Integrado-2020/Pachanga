import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CadastroComponent } from './cadastro.component';

import { CustomMaterialModule } from '../material/material.module';

import {NavbarComponent} from '../navbar/navbar.component';

import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from '../../app-routing.module';
import { LoginComponent } from '../login/login.component';
import { IndexComponent } from '../index/index.component';
import { MenuFestasComponent } from '../menu-festas/menu-festas.component';
import { FestaPainelControleComponent } from '../festa-painel-controle/festa-painel-controle.component';
import { CriarFestaComponent } from '../criar-festa/criar-festa.component';

import { SocialLoginModule, AuthServiceConfig } from 'angular4-social-login';
import { GoogleLoginProvider, FacebookLoginProvider } from 'angular4-social-login';

import { TranslateModule, TranslateLoader } from '@ngx-translate/core';
import { TranslateHttpLoader } from '@ngx-translate/http-loader';
import {HttpClient } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';

import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { PerfilComponent } from '../perfil/perfil.component';
import { InfoCompleteComponent } from '../info-complete/info-complete.component';
import { EditarFestaComponent } from '../editar-festa/editar-festa.component';
import { NotFoundComponent } from '../not-found/not-found.component';

import { NgxMaterialTimepickerModule } from 'ngx-material-timepicker';
import { CriarGrupoComponent } from '../criar-grupo/criar-grupo.component';
import { FiltroFestaPipe } from '../menu-festas/filtroFesta.pipe';
import { GerenciadorMembrosComponent } from '../gerenciador-membros/gerenciador-membros.component';
import { EditarGrupoComponent } from '../editar-grupo/editar-grupo.component';
import { DistribuicaoPermissoesComponent } from '../distribuicao-permissoes/distribuicao-permissoes.component';

export function HttpLoaderFactory(http: HttpClient) {
  return new TranslateHttpLoader(http);
}

const config = new AuthServiceConfig([
  {
    id: GoogleLoginProvider.PROVIDER_ID,
    provider: new GoogleLoginProvider('215875672881-jp0n01npv48op3j0c67mm0jlauoov3hb.apps.googleusercontent.com')
  },
  {
    id: FacebookLoginProvider.PROVIDER_ID,
    provider: new FacebookLoginProvider('620215655237701')
  }
]);

export function provideConfig() {
  return config;
}

describe('CadastroComponent', () => {
  let component: CadastroComponent;
  let fixture: ComponentFixture<CadastroComponent>;

  beforeEach(async(() => {
    jasmine.DEFAULT_TIMEOUT_INTERVAL = 100000;
    TestBed.configureTestingModule({
      declarations: [
        CadastroComponent,
        NavbarComponent,
        LoginComponent,
        IndexComponent,
        PerfilComponent,
        InfoCompleteComponent,
        MenuFestasComponent,
        FestaPainelControleComponent,
        CriarFestaComponent,
        EditarFestaComponent,
        NotFoundComponent,
        CriarGrupoComponent,
        FiltroFestaPipe,
        GerenciadorMembrosComponent,
        EditarGrupoComponent,
        DistribuicaoPermissoesComponent
      ],
      imports: [
        CustomMaterialModule,
        NgxMaterialTimepickerModule,
        BrowserAnimationsModule,
        BrowserModule,
        AppRoutingModule,
        SocialLoginModule,
        HttpClientTestingModule,
        ReactiveFormsModule,
        FormsModule,
        TranslateModule.forRoot({
          loader: {
            provide: TranslateLoader,
            useFactory: HttpLoaderFactory,
            deps: [HttpClient]
          }
        })
      ],
      providers: [
        {
          provide: AuthServiceConfig,
          useFactory: provideConfig
        }
      ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    jasmine.DEFAULT_TIMEOUT_INTERVAL = 100000;
    fixture = TestBed.createComponent(CadastroComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('form invalid when empty', () => {
    expect(component.form.valid).toBeFalsy();
  });

  it('form valid when correctly filled', () => {
    const field1 = 'nome';
    const field2 = 'dtnasc';
    const field3 = 'sexo';
    const field4 = 'email';
    const field5 = 'senha';
    const field6 = 'confirmacaoSenha';
    const field7 = 'termos';

    const nome = component.form.controls[field1];
    const dtnasc = component.form.controls[field2];
    const sexo = component.form.controls[field3];
    const email = component.form.controls[field4];
    const senha = component.form.controls[field5];
    const confirmacaoSenha = component.form.controls[field6];
    const termos = component.form.controls[field7];

    nome.setValue('Teste de Form');
    dtnasc.setValue(new Date());
    sexo.setValue('M');
    email.setValue('teste@teste.com');
    senha.setValue('123123Aa!');
    confirmacaoSenha.setValue('123123Aa!');
    termos.setValue(true);

    expect(component.form.valid).toBeTruthy();
  });

  it('email required', () => {
    const field = 'email';

    const email = component.form.controls[field];


    let errors = {};
    errors = email.errors || {};

    const errorName = 'required';

    expect(errors[errorName]).toBeTruthy();

    email.setValue('teste@teste.com');

    errors = {};
    errors = email.errors || {};

    expect(errors[errorName]).toBeFalsy();
  });

  it('email format', () => {
    const field = 'email';

    const email = component.form.controls[field];

    email.setValue('teste@teste.com');

    let errors = {};
    errors = email.errors || {};

    const errorName = 'email';

    expect(errors[errorName]).toBeFalsy();

    email.setValue('teste');

    errors = {};
    errors = email.errors || {};

    expect(errors[errorName]).toBeTruthy();

  });

  it('nome valid', () => {
    const field = 'nome';

    const nome = component.form.controls[field];

    expect(nome.valid).toBeFalsy();

    nome.setValue('Teste');

    expect(nome.valid).toBeTruthy();
  });

  it('sexo valid', () => {
    const field = 'sexo';

    const sexo = component.form.controls[field];

    expect(sexo.valid).toBeFalsy();

    sexo.setValue('M');

    expect(sexo.valid).toBeTruthy();
  });

  it('dtnasc valid', () => {
    const field = 'dtnasc';

    const dtnasc = component.form.controls[field];

    expect(dtnasc.valid).toBeFalsy();

    dtnasc.setValue(new Date());

    expect(dtnasc.valid).toBeTruthy();
  });

  it('termos valid', () => {
    const field = 'termos';

    const termos = component.form.controls[field];

    expect(termos.valid).toBeFalsy();

    termos.setValue(true);

    expect(termos.valid).toBeTruthy();
  });

  it('senha required', () => {
    const field = 'senha';

    const senha = component.form.controls[field];


    let errors = {};
    errors = senha.errors || {};

    const errorName = 'required';

    expect(errors[errorName]).toBeTruthy();

    senha.setValue('123');

    errors = {};
    errors = senha.errors || {};

    expect(errors[errorName]).toBeFalsy();
  });

  it('senha pattern', () => {
    const field = 'senha';

    const senha = component.form.controls[field];
    senha.setValue('123');

    let errors = {};
    errors = senha.errors || {};

    const errorName = 'pattern';

    expect(errors[errorName]).toBeTruthy();

    senha.setValue('123123Aa!');

    errors = {};
    errors = senha.errors || {};

    expect(errors[errorName]).toBeFalsy();
  });

  it('confirmacao senha required', () => {
    const field = 'senha';

    const confirmacaoSenha = component.form.controls[field];


    let errors = {};
    errors = confirmacaoSenha.errors || {};

    const errorName = 'required';

    expect(errors[errorName]).toBeTruthy();

    confirmacaoSenha.setValue('123');

    errors = {};
    errors = confirmacaoSenha.errors || {};

    expect(errors[errorName]).toBeFalsy();
  });

  it('match password validity false', () => {
    const field1 = 'senha';
    const field2 = 'confirmacaoSenha';
    const senha = component.form.controls[field1];
    const confirmacaoSenha = component.form.controls[field2];

    senha.setValue('123123Aa!');
    confirmacaoSenha.setValue('123123A!');

    let errors = {};
    errors = confirmacaoSenha.errors || {};

    const errorName = 'mustMatch';

    expect(errors[errorName]).toBeTruthy();
  });

  it('should call cadastrar_se at SignUpWithPachanga', () => {
    spyOn(component, 'cadastrar_se');
    component.signUpWithPachanga('teste', 'teste', 'teste', 'teste', 'teste');
    expect(component.cadastrar_se).toHaveBeenCalled();
  });

  it('should get f to get form controls', () => {
    expect(component.f).toBe(component.form.controls);
  });

});
