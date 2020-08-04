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
      path: 'festas/:festa&:id/painel',
      component: FestaPainelControleComponent,
      canActivate: [AuthGuard]
    },

    {
      path: 'criar-festa',
      component: CriarFestaComponent,
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
      path: 'festas/:festa&:id/membros/atribuirPermissoes',
      component: DistribuicaoPermissoesComponent,
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
