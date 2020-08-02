import { TestBed, async } from '@angular/core/testing';
import { AppComponent } from './app.component';
import { NavbarComponent } from './views/navbar/navbar.component';
import { IndexComponent } from './views/index/index.component';
import { CustomMaterialModule } from './views/material/material.module';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { BrowserModule } from '@angular/platform-browser';
import { LoginComponent } from './views/login/login.component';
import { CadastroComponent } from './views/cadastro/cadastro.component';
import { MenuFestasComponent } from './views/menu-festas/menu-festas.component';
import { FestaPainelControleComponent } from './views/festa-painel-controle/festa-painel-controle.component';
import { CriarFestaComponent } from './views/criar-festa/criar-festa.component';
import { AppRoutingModule } from './app-routing.module';

import { TranslateModule, TranslateLoader } from '@ngx-translate/core';
import { TranslateHttpLoader } from '@ngx-translate/http-loader';
import { HttpClient } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';

import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { SocialLoginModule, AuthServiceConfig } from 'angular4-social-login';
import { GoogleLoginProvider, FacebookLoginProvider } from 'angular4-social-login';
import { ErroDialogComponent } from './views/erro-dialog/erro-dialog.component';
import { PerfilComponent } from './views/perfil/perfil.component';
import { InfoCompleteComponent } from './views/info-complete/info-complete.component';
import { EditarFestaComponent } from './views/editar-festa/editar-festa.component';
import { NotFoundComponent } from './views/not-found/not-found.component';

import { NgxMaterialTimepickerModule } from 'ngx-material-timepicker';
import { CriarGrupoComponent } from './views/criar-grupo/criar-grupo.component';
import { FiltroFestaPipe } from './views/menu-festas/filtroFesta.pipe';
import { GerenciadorMembrosComponent } from './views/gerenciador-membros/gerenciador-membros.component';
import { EditarGrupoComponent } from './views/editar-grupo/editar-grupo.component';
import { DistribuicaoPermissoesComponent } from './views/distribuicao-permissoes/distribuicao-permissoes.component';


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

describe('AppComponent', () => {
  beforeEach(async(() => {
    jasmine.DEFAULT_TIMEOUT_INTERVAL = 100000;
    TestBed.configureTestingModule({
      declarations: [
        AppComponent,
        NavbarComponent,
        IndexComponent,
        LoginComponent,
        CadastroComponent,
        ErroDialogComponent,
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
        BrowserModule,
        BrowserAnimationsModule,
        CustomMaterialModule,
        NgxMaterialTimepickerModule,
        AppRoutingModule,
        HttpClientTestingModule,
        FormsModule,
        ReactiveFormsModule,
        TranslateModule.forRoot({
          loader: {
            provide: TranslateLoader,
            useFactory: HttpLoaderFactory,
            deps: [HttpClient]
          }
        }),
        SocialLoginModule
      ],
      providers: [
        {
          provide: AuthServiceConfig,
          useFactory: provideConfig
        }
      ]
    }).compileComponents();
  }));

  it('should create the app', () => {
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.debugElement.componentInstance;
    expect(app).toBeTruthy();
  });

  it(`should have as title 'Pachanga'`, () => {
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.debugElement.componentInstance;
    expect(app.title).toEqual('Pachanga');
  });
});
