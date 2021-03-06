import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { CustomMaterialModule } from '../material/material.module';
import { TranslateModule, TranslateLoader } from '@ngx-translate/core';
import { TranslateHttpLoader } from '@ngx-translate/http-loader';
import { HttpClient } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { SocialLoginModule, AuthServiceConfig } from 'angular4-social-login';
import { GoogleLoginProvider, FacebookLoginProvider } from 'angular4-social-login';
import { NotificacoesComponent } from './notificacoes.component';
import { AppRoutingModule } from 'src/app/app-routing.module';
import { NgxMaterialTimepickerModule } from 'ngx-material-timepicker';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { FestaDetalhesDialogComponent } from '../festa-detalhes-dialog/festa-detalhes-dialog.component';
import { GerenciadorProdutosComponent } from '../gerenciador-produtos/gerenciador-produtos.component';
import { EstoquePainelComponent } from '../estoque-painel/estoque-painel.component';
import { DistribuicaoPermissoesComponent } from '../distribuicao-permissoes/distribuicao-permissoes.component';
import { EditarGrupoComponent } from '../editar-grupo/editar-grupo.component';
import { GerenciadorMembrosComponent } from '../gerenciador-membros/gerenciador-membros.component';
import { FiltroFestaPipe } from '../menu-festas/filtroFesta.pipe';
import { CriarGrupoComponent } from '../criar-grupo/criar-grupo.component';
import { NotFoundComponent } from '../not-found/not-found.component';
import { EditarFestaComponent } from '../editar-festa/editar-festa.component';
import { CadastroComponent } from '../cadastro/cadastro.component';
import { NavbarComponent } from '../navbar/navbar.component';
import { LoginComponent } from '../login/login.component';
import { IndexComponent } from '../index/index.component';
import { PerfilComponent } from '../perfil/perfil.component';
import { InfoCompleteComponent } from '../info-complete/info-complete.component';
import { MenuFestasComponent } from '../menu-festas/menu-festas.component';
import { FestaPainelControleComponent } from '../festa-painel-controle/festa-painel-controle.component';
import { CriarFestaComponent } from '../criar-festa/criar-festa.component';
import { MomentModule } from 'ngx-moment';
import { PainelSegurancaComponent } from '../painel-seguranca/painel-seguranca.component';
import { RelatoriosPainelComponent } from '../relatorios-painel/relatorios-painel.component';
import { FormsPainelComponent } from '../forms-painel/forms-painel.component';
import { PainelIngressoComponent } from '../painel-ingresso/painel-ingresso.component';
import { CriarLoteComponent } from '../criar-lote/criar-lote.component';
import { EditarLoteComponent } from '../editar-lote/editar-lote.component';
import { VendaIngressosComponent } from '../venda-ingressos/venda-ingressos.component';
import { ThirdPartyPainelComponent } from '../third-party-painel/third-party-painel.component';
import { CheckoutComponent } from '../checkout/checkout.component';
import { LeitorQrComponent } from '../leitor-qr/leitor-qr.component';
import { CheckInComponent } from '../check-in/check-in.component';
import { ZXingScannerModule } from '@zxing/ngx-scanner';
import { MeusIngressosComponent } from '../meus-ingressos/meus-ingressos.component';
import { MaterialFileInputModule } from 'ngx-material-file-input';
import { NgxPayPalModule } from 'ngx-paypal';
import { GerenciadorCuponsComponent } from '../gerenciador-cupons/gerenciador-cupons.component';
import { ControleSidenavComponent } from '../controle-sidenav/controle-sidenav.component';
import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RelatoriosExportComponent } from '../relatorios-export/relatorios-export.component';
import { PermissionFilter } from '../utils/permission-filter.pipe';

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

describe('NotificacoesComponent', () => {
  let component: NotificacoesComponent;
  let fixture: ComponentFixture<NotificacoesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [
        NotificacoesComponent,
        NavbarComponent,
        LoginComponent,
        CadastroComponent,
        IndexComponent,
        PerfilComponent,
        InfoCompleteComponent,
        MenuFestasComponent,
        FestaPainelControleComponent,
        EditarFestaComponent,
        NotFoundComponent,
        CriarGrupoComponent,
        FiltroFestaPipe,
        GerenciadorMembrosComponent,
        EditarGrupoComponent,
        DistribuicaoPermissoesComponent,
        EstoquePainelComponent,
        GerenciadorProdutosComponent,
        FestaDetalhesDialogComponent,
        NotificacoesComponent,
        CriarFestaComponent,
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
        RelatoriosExportComponent,
        PermissionFilter
      ],
      imports: [
        MomentModule,
        CustomMaterialModule,
        NgxMaterialTimepickerModule,
        AppRoutingModule,
        HttpClientTestingModule,
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
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(NotificacoesComponent);
    component = fixture.componentInstance;
    component.loginService.usuarioInfo = {codUsuario: '1', nomeUser: 'Teste', nomesexo: null, dtNasc: null};
    component.notifService.loginService.usuarioInfo = {codUsuario: '1'};
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should dataEmissaoSort', () => {
    expect(component.dataEmissaoSort({dataEmissao: '2020-02-16T12:30:00'}, {dataEmissao: '2020-02-16T12:30:10'})).toBe(1);
    expect(component.dataEmissaoSort({dataEmissao: '2020-02-16T12:30:10'}, {dataEmissao: '2020-02-16T12:30:00'})).toBe(-1);
  });
});
