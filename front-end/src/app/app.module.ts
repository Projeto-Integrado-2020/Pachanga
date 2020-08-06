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
import { ClicarForaDirective } from './views/navbar/clicar-fora-fecha.directive';


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
    ClicarForaDirective
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
    DeletarGrupoComponent
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
    ServiceWorkerModule.register('ngsw-worker.js', { enabled: environment.production })
  ],
  providers: [
    {
      provide: AuthServiceConfig,
      useFactory: provideConfig
    },
    AuthService,
    LoginService,
    EditAccountService,
    AuthGuard,
    LogService,
    MenuFestasService,
    CadastrarFestaService,
    {
      provide: MatPaginatorIntl,
      useClass: MatPaginatorPtBr
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
