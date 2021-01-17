import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { IndexComponent } from './views/index/index.component';
import { PerfilComponent } from './views/perfil/perfil.component';
import { AuthGuard } from './guard/auth.guard';
import { MenuFestasComponent } from './views/menu-festas/menu-festas.component';
import { FestaPainelControleComponent } from './views/festa-painel-controle/festa-painel-controle.component';
import { CriarFestaComponent } from './views/criar-festa/criar-festa.component';
import { EditarFestaComponent } from './views/editar-festa/editar-festa.component';
import { NotFoundComponent } from './views/not-found/not-found.component';
import { CriarGrupoComponent } from './views/criar-grupo/criar-grupo.component';
import { GerenciadorMembrosComponent } from './views/gerenciador-membros/gerenciador-membros.component';
import { EditarGrupoComponent } from './views/editar-grupo/editar-grupo.component';
import { DistribuicaoPermissoesComponent } from './views/distribuicao-permissoes/distribuicao-permissoes.component';
import { EstoquePainelComponent } from './views/estoque-painel/estoque-painel.component';
import { GerenciadorProdutosComponent } from './views/gerenciador-produtos/gerenciador-produtos.component';
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
import { MeusIngressosComponent } from './views/meus-ingressos/meus-ingressos.component';
import { GerenciadorCuponsComponent } from './views/gerenciador-cupons/gerenciador-cupons.component';
import { CheckoutGuard } from './guard/checkout/checkout.guard';
import { RelatoriosExportComponent } from './views/relatorios-export/relatorios-export.component';

const routes: Routes = [

    {
      path: '',
      component: IndexComponent
    },

    {
      path: 'perfil',
      component: PerfilComponent,
      canActivate: [AuthGuard]
    },

    {
      path: 'minhas-festas',
      component: MenuFestasComponent,
      canActivate: [AuthGuard]
    },

    {
      path: 'meus-ingressos',
      component: MeusIngressosComponent,
      canActivate: [AuthGuard]
    },

    {
      path: 'festas/:festa&:id/painel',
      component: FestaPainelControleComponent,
      canActivate: [AuthGuard]
    },

    {
      path: 'festas/:festa&:id/painel-seguranca',
      component: PainelSegurancaComponent,
      canActivate: [AuthGuard]
    },

    {
      path: ':festa&:id/venda-ingressos',
      component: VendaIngressosComponent
    },

    {
      path: ':festa&:id/venda-ingressos/venda-checkout',
      component: CheckoutComponent,
      canActivate: [AuthGuard, CheckoutGuard]
    },

    {
      path: 'festas/:festa&:id/ingressos',
      component: PainelIngressoComponent,
      canActivate: [AuthGuard]
    },

    {
      path: 'festas/:festa&:id/ingressos/cupons',
      component: GerenciadorCuponsComponent,
      canActivate: [AuthGuard]
    },

    {
      path: 'festas/:festa&:id/ingressos/integracoes',
      component: ThirdPartyPainelComponent,
      canActivate: [AuthGuard]
    },

    {
      path: 'festas/:festa&:id/ingressos/check-in',
      component: LeitorQrComponent,
      canActivate: [AuthGuard]
    },

    {
      path: 'festas/:festa&:id/estoque',
      component: EstoquePainelComponent,
      canActivate: [AuthGuard]
    },

    {
      path: 'festas/:festa&:id/estoque/produtos',
      component: GerenciadorProdutosComponent,
      canActivate: [AuthGuard]
    },

    {
      path: 'festas/:festa&:id/estoque/produtos',
      component: GerenciadorCuponsComponent,
      canActivate: [AuthGuard]
    },

    {
      path: 'criar-festa',
      component: CriarFestaComponent,
      canActivate: [AuthGuard]
    },

    {
      path: 'festas/:festa&:id/ingressos/criar-lote',
      component: CriarLoteComponent,
      canActivate: [AuthGuard]
    },

    {
      path: 'festas/:festa&:id/ingressos/editar-lote/:idLote',
      component: EditarLoteComponent,
      canActivate: [AuthGuard]
    },

    {
      path: 'festas/:festa&:id/editar',
      component: EditarFestaComponent,
      canActivate: [AuthGuard]
    },

    {
      path: 'festas/:festa&:id/membros',
      component: GerenciadorMembrosComponent,
      canActivate: [AuthGuard]
    },

    {
      path: 'festas/:festa&:id/membros/criar-grupo',
      component: CriarGrupoComponent,
      canActivate: [AuthGuard]
    },

    {
      path: 'festas/:festa&:id/membros/:idGrupo/editar',
      component: EditarGrupoComponent,
      canActivate: [AuthGuard]
    },

    {
      path: 'festas/:festa&:id/membros/atribuir-grupos',
      component: DistribuicaoPermissoesComponent,
      canActivate: [AuthGuard]
    },

    {
      path: 'festas/:festa&:id/relatorios',
      component: RelatoriosPainelComponent,
      canActivate: [AuthGuard]
    },

    {
      path: 'festas/:festa&:id/relatorios/exportar',
      component: RelatoriosExportComponent,
      canActivate: [AuthGuard]
    },

    {
      path: 'festas/:festa&:id/relatorios/forms',
      component: FormsPainelComponent,
      canActivate: [AuthGuard]
    },

    { // Tanto esta path quanto a '*' devem ser as últimas.
      path: '404',
      component: NotFoundComponent
    },

    {
      path: '**',
      redirectTo: '404'
    }
];

@NgModule({
    imports: [
        RouterModule.forRoot(routes)
    ],
    exports: [
        RouterModule
    ],
    declarations: []
})
export class AppRoutingModule {
}
