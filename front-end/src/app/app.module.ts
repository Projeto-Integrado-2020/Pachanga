import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import { CustomMaterialModule } from './views/material/material.module';

// Imports para a ttradução da página
import { TranslateModule, TranslateLoader, TranslateService } from '@ngx-translate/core';
import { TranslateHttpLoader } from '@ngx-translate/http-loader';
import { HttpClientModule, HttpClient } from '@angular/common/http';
import { AppRoutingModule } from './app-routing.module';

import { NavbarComponent } from './views/navbar/navbar.component';
import { IndexComponent } from './views/index/index.component';
import { LoginComponent } from './views/login/login.component';
import { InviteDialogComponent } from './views/invite-dialog/invite-dialog.component';
import { CadastroComponent } from './views/cadastro/cadastro.component';
import { PerfilComponent } from './views/perfil/perfil.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

export function HttpLoaderFactory(http: HttpClient) {
  return new TranslateHttpLoader(http);
}

// Imports  para login via Facebook e Gmail
import { SocialLoginModule, AuthServiceConfig, AuthService } from 'angular4-social-login';
import { GoogleLoginProvider, FacebookLoginProvider } from 'angular4-social-login';
import { SocialLoginBaseComponent } from './views/social-login-base/social-login-base.component';

import { LayoutModule } from '@angular/cdk/layout';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatButtonModule } from '@angular/material/button';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatIconModule } from '@angular/material/icon';
import { MatListModule } from '@angular/material/list';
import { AuthGuard } from './guard/auth.guard';
import { LoginService } from './services/loginService/login.service';
import { ErroDialogComponent } from './views/erro-dialog/erro-dialog.component';
import { LogService } from './services/logging/log.service';
import { InfoCompleteComponent } from './views/info-complete/info-complete.component';
import { EditAccountService } from './services/editAccountService/edit-account.service';
import { EditDialogComponent } from './views/edit-dialog/edit-dialog.component';
import { SuccessDialogComponent } from './views/success-dialog/success-dialog.component';
import { MenuFestasComponent } from './views/menu-festas/menu-festas.component';
import { MenuFestasService } from './services/menu-festa/menu-festas.service';
import { FestaPainelControleComponent } from './views/festa-painel-controle/festa-painel-controle.component';
import { ServiceWorkerModule } from '@angular/service-worker';
import { environment } from '../environments/environment';
import { CriarFestaComponent } from './views/criar-festa/criar-festa.component';
import { NgxMaterialTimepickerModule } from 'ngx-material-timepicker';

import { CadastrarFestaService } from './services/cadastro-festa/cadastrar-festa.service';
import { EditarFestaComponent } from './views/editar-festa/editar-festa.component';
import { MatPaginatorIntl } from '@angular/material';
import { MatPaginatorPtBr } from './views/menu-festas/mat-paginator-ptbr';
import { NotFoundComponent } from './views/not-found/not-found.component';
import { DeletarFestaComponent } from './views/deletar-festa/deletar-festa.component';
import { CriarGrupoComponent } from './views/criar-grupo/criar-grupo.component';
import { GerenciadorMembrosComponent } from './views/gerenciador-membros/gerenciador-membros.component';
import { StatusDialogComponent } from './views/status-dialog/status-dialog.component';
import { FiltroFestaPipe } from './views/menu-festas/filtroFesta.pipe';
import { DeleteMembroDialogComponent } from './views/delete-membro-dialog/delete-membro-dialog.component';
import { EditGrupoMembroComponent } from './views/edit-grupo-membro/edit-grupo-membro.component';
import { DeletarGrupoComponent } from './views/deletar-grupo/deletar-grupo.component';
import { EditarGrupoComponent } from './views/editar-grupo/editar-grupo.component';
import { DistribuicaoPermissoesComponent } from './views/distribuicao-permissoes/distribuicao-permissoes.component';
import { EstoquePainelComponent } from './views/estoque-painel/estoque-painel.component';
import { ClicarForaDirective } from './views/navbar/clicar-fora-fecha.directive';
import { DistribuicaoDialogComponent } from './views/distribuicao-dialog/distribuicao-dialog.component';
import { DeleteEstoqueDialogComponent } from './views/delete-estoque-dialog/delete-estoque-dialog.component';
import { EditEstoqueDialogComponent } from './views/edit-estoque-dialog/edit-estoque-dialog.component';
import { CriarEstoqueDialogComponent } from './views/criar-estoque-dialog/criar-estoque-dialog.component';
import { DeletarConvidadoDialogComponent } from './views/deletar-convidado-dialog/deletar-convidado-dialog.component';
import { GerenciadorProdutosComponent } from './views/gerenciador-produtos/gerenciador-produtos.component';
import { CriarProdutoDialogComponent } from './views/criar-produto-dialog/criar-produto-dialog.component';
import { EditarProdutoDialogComponent } from './views/editar-produto-dialog/editar-produto-dialog.component';
import { DeletarProdutoDialogComponent } from './views/deletar-produto-dialog/deletar-produto-dialog.component';
import { CriarProdutoEstoqueDialogComponent } from './views/criar-produto-estoque-dialog/criar-produto-estoque-dialog.component';
import { DeletarProdutoEstoqueDialogComponent } from './views/deletar-produto-estoque-dialog/deletar-produto-estoque-dialog.component';
import { EditarProdutoEstoqueDialogComponent } from './views/editar-produto-estoque-dialog/editar-produto-estoque-dialog.component';
import { FestaDetalhesDialogComponent } from './views/festa-detalhes-dialog/festa-detalhes-dialog.component';
import { RecargaProdutoEstoqueDialogComponent } from './views/recarga-produto-estoque-dialog/recarga-produto-estoque-dialog.component';
import { NotificacoesComponent } from './views/notificacoes/notificacoes.component';
import { AlertaEstoqueComponent } from './views/alerta-estoque/alerta-estoque.component';
import { PerdaProdutoEstoqueDialogComponent } from './views/perda-produto-estoque-dialog/perda-produto-estoque-dialog.component';
import { RemoverUnidadeDoseDialogComponent } from './views/remover-unidade-dose-dialog/remover-unidade-dose-dialog.component';
import { MomentModule } from 'ngx-moment';
import { TermosUsoDialogComponent } from './views/termos-uso-dialog/termos-uso-dialog.component';
import { PainelSegurancaComponent } from './views/painel-seguranca/painel-seguranca.component';
import { CriarAreaSegurancaDialogComponent } from './views/criar-area-seguranca-dialog/criar-area-seguranca-dialog.component';
import { DeleteAreaSegurancaDialogComponent } from './views/delete-area-seguranca-dialog/delete-area-seguranca-dialog.component';
import { EditarAreaSegurancaDialogComponent } from './views/editar-area-seguranca-dialog/editar-area-seguranca-dialog.component';
import { RelatoriosPainelComponent } from './views/relatorios-painel/relatorios-painel.component';
import { FormsPainelComponent } from './views/forms-painel/forms-painel.component';
import { EditarFormDialogComponent } from './views/editar-form-dialog/editar-form-dialog.component';
import { DeletarFormDialogComponent } from './views/deletar-form-dialog/deletar-form-dialog.component';
import { AdicionarFormDialogComponent } from './views/adicionar-form-dialog/adicionar-form-dialog.component';
import { RelatarProblemaDialogComponent } from './views/relatar-problema-dialog/relatar-problema-dialog.component';
import { PainelIngressoComponent } from './views/painel-ingresso/painel-ingresso.component';
import { CriarLoteComponent } from './views/criar-lote/criar-lote.component';
import { EditarLoteComponent } from './views/editar-lote/editar-lote.component';
import { DeletarLoteDialogComponent } from './views/deletar-lote-dialog/deletar-lote-dialog.component';
import { AlertaSegurancaComponent } from './views/alerta-seguranca/alerta-seguranca.component';
import { DatePipe } from '@angular/common';
import { VendaIngressosComponent } from './views/venda-ingressos/venda-ingressos.component';
import { AdicionarIntegracoesDialogComponent } from './views/adicionar-integracoes-dialog/adicionar-integracoes-dialog.component';
import { EditarIntegracoesDialogComponent } from './views/editar-integracoes-dialog/editar-integracoes-dialog.component';
import { DeletarIntegracoesDialogComponent } from './views/deletar-integracoes-dialog/deletar-integracoes-dialog.component';
import { DetalhesProblemaDialogComponent } from './views/detalhes-problema-dialog/detalhes-problema-dialog.component';
import { ThirdPartyPainelComponent } from './views/third-party-painel/third-party-painel.component';
import { CheckoutComponent } from './views/checkout/checkout.component';
import { CheckInComponent } from './views/check-in/check-in.component';

// Scanner QR Code
import { LeitorQrComponent } from './views/leitor-qr/leitor-qr.component';
import { ZXingScannerModule } from '@zxing/ngx-scanner';
import { MeusIngressosComponent } from './views/meus-ingressos/meus-ingressos.component';

import { MaterialFileInputModule } from 'ngx-material-file-input';

// Paypal
import { NgxPayPalModule } from 'ngx-paypal';

import { QrcodeDialogComponent } from './views/qrcode-dialog/qrcode-dialog.component';
import { NgxQRCodeModule } from '@techiediaries/ngx-qrcode';
import { GerarBoletoDialogComponent } from './views/gerar-boleto-dialog/gerar-boleto-dialog.component';
import { GerenciadorCuponsComponent } from './views/gerenciador-cupons/gerenciador-cupons.component';
import { CriarCupomDialogComponent } from './views/criar-cupom-dialog/criar-cupom-dialog.component';
import { EditarCupomDialogComponent } from './views/editar-cupom-dialog/editar-cupom-dialog.component';
import { DeletarCupomDialogComponent } from './views/deletar-cupom-dialog/deletar-cupom-dialog.component';

import { NgxMaskModule, IConfig } from 'ngx-mask';
import { ProcessingDialogComponent } from './views/processing-dialog/processing-dialog.component';
import { ControleSidenavComponent } from './views/controle-sidenav/controle-sidenav.component';
import { DialogIngressosMesmaFestaComponent } from './views/dialog-ingressos-mesma-festa/dialog-ingressos-mesma-festa.component';
import { GoogleSheetsDbService } from 'ng-google-sheets-db';

// pdf generator
import { PdfMakeWrapper } from 'pdfmake-wrapper';
import pdfFonts from 'pdfmake/build/vfs_fonts';
import { ImagemAreaProblemaDialogComponent } from './views/imagem-area-problema-dialog/imagem-area-problema-dialog.component';
PdfMakeWrapper.setFonts(pdfFonts);

export const options: Partial<IConfig> | (() => Partial<IConfig>) = null;

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

@NgModule({
  declarations: [
    AppComponent,
    NavbarComponent,
    IndexComponent,
    LoginComponent,
    CadastroComponent,
    SocialLoginBaseComponent,
    PerfilComponent,
    ErroDialogComponent,
    InfoCompleteComponent,
    EditDialogComponent,
    SuccessDialogComponent,
    MenuFestasComponent,
    FestaPainelControleComponent,
    CriarFestaComponent,
    InviteDialogComponent,
    EditarFestaComponent,
    NotFoundComponent,
    DeletarFestaComponent,
    CriarGrupoComponent,
    GerenciadorMembrosComponent,
    StatusDialogComponent,
    FiltroFestaPipe,
    DeleteMembroDialogComponent,
    EditGrupoMembroComponent,
    DistribuicaoPermissoesComponent,
    DeletarGrupoComponent,
    EditarGrupoComponent,
    DistribuicaoPermissoesComponent,
    EstoquePainelComponent,
    ClicarForaDirective,
    DistribuicaoDialogComponent,
    DeleteEstoqueDialogComponent,
    EditEstoqueDialogComponent,
    CriarEstoqueDialogComponent,
    DeletarConvidadoDialogComponent,
    GerenciadorProdutosComponent,
    CriarProdutoDialogComponent,
    EditarProdutoDialogComponent,
    DeletarProdutoDialogComponent,
    CriarProdutoEstoqueDialogComponent,
    DeletarProdutoEstoqueDialogComponent,
    EditarProdutoEstoqueDialogComponent,
    FestaDetalhesDialogComponent,
    RecargaProdutoEstoqueDialogComponent,
    NotificacoesComponent,
    AlertaEstoqueComponent,
    PerdaProdutoEstoqueDialogComponent,
    RemoverUnidadeDoseDialogComponent,
    TermosUsoDialogComponent,
    PainelSegurancaComponent,
    CriarAreaSegurancaDialogComponent,
    DeleteAreaSegurancaDialogComponent,
    EditarAreaSegurancaDialogComponent,
    RelatoriosPainelComponent,
    FormsPainelComponent,
    EditarFormDialogComponent,
    DeletarFormDialogComponent,
    AdicionarFormDialogComponent,
    RelatarProblemaDialogComponent,
    PainelIngressoComponent,
    CriarLoteComponent,
    EditarLoteComponent,
    DeletarLoteDialogComponent,
    AlertaSegurancaComponent,
    VendaIngressosComponent,
    AdicionarIntegracoesDialogComponent,
    EditarIntegracoesDialogComponent,
    DeletarIntegracoesDialogComponent,
    DetalhesProblemaDialogComponent,
    ThirdPartyPainelComponent,
    CheckoutComponent,
    CheckInComponent,
    LeitorQrComponent,
    MeusIngressosComponent,
    QrcodeDialogComponent,
    GerarBoletoDialogComponent,
    GerenciadorCuponsComponent,
    CriarCupomDialogComponent,
    EditarCupomDialogComponent,
    DeletarCupomDialogComponent,
    ProcessingDialogComponent,
    ControleSidenavComponent,
    DialogIngressosMesmaFestaComponent,
    ImagemAreaProblemaDialogComponent
  ],
  entryComponents: [
    LoginComponent,
    CadastroComponent,
    ErroDialogComponent,
    EditDialogComponent,
    SuccessDialogComponent,
    InviteDialogComponent,
    DeletarFestaComponent,
    StatusDialogComponent,
    DeleteMembroDialogComponent,
    EditGrupoMembroComponent,
    DeletarGrupoComponent,
    DistribuicaoDialogComponent,
    DeleteEstoqueDialogComponent,
    EditEstoqueDialogComponent,
    CriarEstoqueDialogComponent,
    DeletarConvidadoDialogComponent,
    CriarProdutoDialogComponent,
    EditarProdutoDialogComponent,
    DeletarProdutoDialogComponent,
    CriarProdutoEstoqueDialogComponent,
    DeletarProdutoEstoqueDialogComponent,
    EditarProdutoEstoqueDialogComponent,
    FestaDetalhesDialogComponent,
    RecargaProdutoEstoqueDialogComponent,
    AlertaEstoqueComponent,
    PerdaProdutoEstoqueDialogComponent,
    RemoverUnidadeDoseDialogComponent,
    TermosUsoDialogComponent,
    CriarAreaSegurancaDialogComponent,
    DeleteAreaSegurancaDialogComponent,
    EditarAreaSegurancaDialogComponent,
    EditarFormDialogComponent,
    DeletarFormDialogComponent,
    AdicionarFormDialogComponent,
    RelatarProblemaDialogComponent,
    DeletarLoteDialogComponent,
    AlertaSegurancaComponent,
    AdicionarIntegracoesDialogComponent,
    EditarIntegracoesDialogComponent,
    DeletarIntegracoesDialogComponent,
    DetalhesProblemaDialogComponent,
    QrcodeDialogComponent,
    GerarBoletoDialogComponent,
    CriarCupomDialogComponent,
    DeletarCupomDialogComponent,
    EditarCupomDialogComponent,
    ProcessingDialogComponent,
    DialogIngressosMesmaFestaComponent,
    ImagemAreaProblemaDialogComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    CustomMaterialModule,
    NgxMaterialTimepickerModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    AppRoutingModule,
    TranslateModule.forRoot({
      loader: {
        provide: TranslateLoader,
        useFactory: HttpLoaderFactory,
        deps: [HttpClient]
      }
    }),
    SocialLoginModule,
    LayoutModule,
    MatToolbarModule,
    MatButtonModule,
    MatSidenavModule,
    MatIconModule,
    MatListModule,
    ServiceWorkerModule.register('ngsw-worker.js', { enabled: environment.production }),
    MomentModule,
    ZXingScannerModule,
    MaterialFileInputModule,
    NgxPayPalModule,
    NgxQRCodeModule,
    NgxMaskModule.forRoot()
  ],
  providers: [
    {
      provide: AuthServiceConfig,
      useFactory: provideConfig,
    },
    AuthService,
    LoginService,
    EditAccountService,
    AuthGuard,
    LogService,
    MenuFestasService,
    CadastrarFestaService,
    DatePipe,
    GoogleSheetsDbService,
    {
      provide: MatPaginatorIntl,
      useClass: MatPaginatorPtBr
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
