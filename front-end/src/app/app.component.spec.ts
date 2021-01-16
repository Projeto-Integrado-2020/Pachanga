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
import { EstoquePainelComponent } from './views/estoque-painel/estoque-painel.component';
import { ClicarForaDirective } from './views/navbar/clicar-fora-fecha.directive';
import { GerenciadorProdutosComponent } from './views/gerenciador-produtos/gerenciador-produtos.component';
import { LoginService } from './services/loginService/login.service';
import { NotificacoesComponent } from './views/notificacoes/notificacoes.component';
import { MomentModule } from 'ngx-moment';
import { PainelSegurancaComponent } from './views/painel-seguranca/painel-seguranca.component';
import { RelatoriosPainelComponent } from './views/relatorios-painel/relatorios-painel.component';
import { FormsPainelComponent } from './views/forms-painel/forms-painel.component';
import { PainelIngressoComponent } from './views/painel-ingresso/painel-ingresso.component';
import { CriarLoteComponent } from './views/criar-lote/criar-lote.component';
import { EditarLoteComponent } from './views/editar-lote/editar-lote.component';
import { VendaIngressosComponent } from './views/venda-ingressos/venda-ingressos.component';
import { ThirdPartyPainelComponent } from './views/third-party-painel/third-party-painel.component';
import { CheckoutComponent } from './views/checkout/checkout.component';
import { CheckInComponent } from './views/check-in/check-in.component';
import { LeitorQrComponent } from './views/leitor-qr/leitor-qr.component';
import { ZXingScannerModule } from '@zxing/ngx-scanner';
import { MeusIngressosComponent } from './views/meus-ingressos/meus-ingressos.component';
import { MaterialFileInputModule } from 'ngx-material-file-input';
import { NgxPayPalModule } from 'ngx-paypal';
import { GerenciadorCuponsComponent } from './views/gerenciador-cupons/gerenciador-cupons.component';
import { ControleSidenavComponent } from './views/controle-sidenav/controle-sidenav.component';
import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RelatoriosExportComponent } from './views/relatorios-export/relatorios-export.component';


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
        DistribuicaoPermissoesComponent,
        EstoquePainelComponent,
        ClicarForaDirective,
        GerenciadorProdutosComponent,
        NotificacoesComponent,
        PainelSegurancaComponent,
        RelatoriosPainelComponent,
        FormsPainelComponent,
        PainelIngressoComponent,
        CriarLoteComponent,
        EditarLoteComponent,
        VendaIngressosComponent,
        ThirdPartyPainelComponent,
        CheckoutComponent,
        LeitorQrComponent,
        CheckInComponent,
        MeusIngressosComponent,
        GerenciadorCuponsComponent,
        ControleSidenavComponent,
        RelatoriosExportComponent
      ],
      imports: [
        MomentModule,
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
        SocialLoginModule,
        ZXingScannerModule,
        MaterialFileInputModule,
        NgxPayPalModule
      ],
      providers: [
        {
          provide: AuthServiceConfig,
          useFactory: provideConfig
        }
      ],
      schemas: [ CUSTOM_ELEMENTS_SCHEMA ]
    }).compileComponents();
  }));

  it('should create the app', () => {
    const service: LoginService = TestBed.get(LoginService);
    service.usuarioInfo = {codUsuario: '1'};
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.debugElement.componentInstance;
    expect(app).toBeTruthy();
  });

  it(`should have as title 'Pachanga'`, () => {
    const service: LoginService = TestBed.get(LoginService);
    service.usuarioInfo = {codUsuario: '1'};
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.debugElement.componentInstance;
    expect(app.title).toEqual('Pachanga');
  });
});
