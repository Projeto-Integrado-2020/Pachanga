/* tslint:disable:no-unused-variable */
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { DebugElement, NO_ERRORS_SCHEMA } from '@angular/core';

import { IndexCarrocelComponent } from './index-carrocel.component';
import { TranslateLoader, TranslateModule } from '@ngx-translate/core';
import { HttpLoaderFactory } from 'src/app/app.module';
import { HttpClient } from '@angular/common/http';
import { AppRoutingModule } from 'src/app/app-routing.module';
import { IndexComponent } from '../index/index.component';
import { PerfilComponent } from '../perfil/perfil.component';
import { MenuFestasComponent } from '../menu-festas/menu-festas.component';
import { LoginComponent } from '../login/login.component';
import { CadastroComponent } from '../cadastro/cadastro.component';
import { InfoCompleteComponent } from '../info-complete/info-complete.component';
import { FestaPainelControleComponent } from '../festa-painel-controle/festa-painel-controle.component';
import { CriarFestaComponent } from '../criar-festa/criar-festa.component';
import { EditarFestaComponent } from '../editar-festa/editar-festa.component';
import { NotFoundComponent } from '../not-found/not-found.component';
import { CriarGrupoComponent } from '../criar-grupo/criar-grupo.component';
import { FiltroFestaPipe } from '../menu-festas/filtroFesta.pipe';
import { GerenciadorMembrosComponent } from '../gerenciador-membros/gerenciador-membros.component';
import { EditarGrupoComponent } from '../editar-grupo/editar-grupo.component';
import { DistribuicaoPermissoesComponent } from '../distribuicao-permissoes/distribuicao-permissoes.component';
import { EstoquePainelComponent } from '../estoque-painel/estoque-painel.component';
import { GerenciadorProdutosComponent } from '../gerenciador-produtos/gerenciador-produtos.component';
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
import { MeusIngressosComponent } from '../meus-ingressos/meus-ingressos.component';
import { GerenciadorCuponsComponent } from '../gerenciador-cupons/gerenciador-cupons.component';
import { ControleSidenavComponent } from '../controle-sidenav/controle-sidenav.component';
import { RelatoriosExportComponent } from '../relatorios-export/relatorios-export.component';
import { MatAutocompleteModule, MatFormFieldModule } from '@angular/material';
import { CustomMaterialModule } from '../material/material.module';
import { NgxMaterialTimepickerModule } from 'ngx-material-timepicker';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { RouterModule } from '@angular/router';
import { MaterialFileInputModule } from 'ngx-material-file-input';

describe('IndexCarrocelComponent', () => {
  let component: IndexCarrocelComponent;
  let fixture: ComponentFixture<IndexCarrocelComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ 
        IndexCarrocelComponent,
       ],
      imports: [
    ],
      schemas: [NO_ERRORS_SCHEMA]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(IndexCarrocelComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
