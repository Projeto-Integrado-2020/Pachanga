import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { IndexComponent } from './index.component';

import { CustomMaterialModule } from '../material/material.module';
import { AppRoutingModule } from '../../app-routing.module';
import { LoginComponent } from '../login/login.component';
import { CadastroComponent } from '../cadastro/cadastro.component';

import { TranslateModule, TranslateLoader } from '@ngx-translate/core';
import { TranslateHttpLoader } from '@ngx-translate/http-loader';
import { HttpClient } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';

import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { PerfilComponent } from '../perfil/perfil.component';
import { InfoCompleteComponent } from '../info-complete/info-complete.component';
import { MenuFestasComponent } from '../menu-festas/menu-festas.component';
import { FestaPainelControleComponent } from '../festa-painel-controle/festa-painel-controle.component';
import { CriarFestaComponent } from '../criar-festa/criar-festa.component';
import { EditarFestaComponent } from '../editar-festa/editar-festa.component';
import { NotFoundComponent } from '../not-found/not-found.component';

import { NgxMaterialTimepickerModule } from 'ngx-material-timepicker';
import { LoginService } from 'src/app/services/loginService/login.service';
import { CriarGrupoComponent } from '../criar-grupo/criar-grupo.component';
import { FiltroFestaPipe } from '../menu-festas/filtroFesta.pipe';
import { GerenciadorMembrosComponent } from '../gerenciador-membros/gerenciador-membros.component';
import { EditarGrupoComponent } from '../editar-grupo/editar-grupo.component';
import { DistribuicaoPermissoesComponent } from '../distribuicao-permissoes/distribuicao-permissoes.component';
import { EstoquePainelComponent } from '../estoque-painel/estoque-painel.component';
import { GerenciadorProdutosComponent } from '../gerenciador-produtos/gerenciador-produtos.component';
import { MomentModule } from 'ngx-moment';
import { PainelSegurancaComponent } from '../painel-seguranca/painel-seguranca.component';
import { RelatoriosPainelComponent } from '../relatorios-painel/relatorios-painel.component';
import { FormsPainelComponent } from '../forms-painel/forms-painel.component';
import { CriarLoteComponent } from '../criar-lote/criar-lote.component';
import { PainelIngressoComponent } from '../painel-ingresso/painel-ingresso.component';
import { EditarLoteComponent } from '../editar-lote/editar-lote.component';
import { VendaIngressosComponent } from '../venda-ingressos/venda-ingressos.component';
import { ThirdPartyPainelComponent } from '../third-party-painel/third-party-painel.component';
import { of } from 'rxjs';
import { GetFestaIndexService } from 'src/app/services/get-festa-index/get-festa-index.service';
import { LeitorQrComponent } from '../leitor-qr/leitor-qr.component';
import { CheckInComponent } from '../check-in/check-in.component';
import { ZXingScannerModule } from '@zxing/ngx-scanner';

export function HttpLoaderFactory(http: HttpClient) {
  return new TranslateHttpLoader(http);
}

describe('IndexComponent', () => {
  let component: IndexComponent;
  let fixture: ComponentFixture<IndexComponent>;

  beforeEach(async(() => {
    jasmine.DEFAULT_TIMEOUT_INTERVAL = 100000;
    TestBed.configureTestingModule({
      declarations: [
        IndexComponent,
        LoginComponent,
        CadastroComponent,
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
        GerenciadorProdutosComponent,
        PainelSegurancaComponent,
        RelatoriosPainelComponent,
        FormsPainelComponent,
        PainelIngressoComponent,
        CriarLoteComponent,
        EditarLoteComponent,
        VendaIngressosComponent,
        ThirdPartyPainelComponent,
        LeitorQrComponent,
        CheckInComponent
      ],
      imports: [
        MomentModule,
        CustomMaterialModule,
        NgxMaterialTimepickerModule,
        AppRoutingModule,
        HttpClientTestingModule,
        ReactiveFormsModule,
        FormsModule,
        TranslateModule.forRoot({
          loader: {
            provide: TranslateLoader,
            useFactory: HttpLoaderFactory,
            deps: [HttpClient]
          }
        }),
        ZXingScannerModule
      ],
      providers: [
        { provide: GetFestaIndexService, useValue: {
          getFestasLista: () => of([{
            nomeFesta: '1',
            codFesta: '1',
            horarioInicioFesta: '2020-09-23T19:10:25',
            horarioFimFesta: '2020-09-23T19:10:25'
          }]),
          setFarol: () => false,
        }},
      ],
    })
    .compileComponents();
  }));

  beforeEach(() => {
    jasmine.DEFAULT_TIMEOUT_INTERVAL = 100000;
    fixture = TestBed.createComponent(IndexComponent);
    component = fixture.componentInstance;
    const service: LoginService = TestBed.get(LoginService);
    service.usuarioInfo = {codUsuario: 'teste', sexo: null, dtNasc: null};
    component.festas = {
      codFesta: '1',
      nomeFesta: 'festa',
      horarioInicioFesta: '2020-09-23T19:10:25',
      horarioFimFesta: '2020-09-23T19:10:25'
    };
    const token = {
      timeToken: '2020-09-21T01:14:04.028+0000',
      token: 'teste'
    };
    localStorage.setItem('token', JSON.stringify(token));
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should format date from datetime', () => {
    const result = component.getDateFromDTF('2020-09-23T19:10:25');
    expect(result).toBe('23/09/2020');
  });

  it('should format time from datetime', () => {
    const result = component.getTimeFromDTF('2020-09-23T19:10:25');
    expect(result).toBe('19:10:25');
  });

  it('should create ticket sales url', () => {
    const result = component.createUrl('festa', '123');
    expect(result).toBe('../festa&123/venda-ingressos');
  });
});
