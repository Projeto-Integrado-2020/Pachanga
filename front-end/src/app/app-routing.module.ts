import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './views/login/login.component';
import { CadastroComponent } from './views/cadastro/cadastro.component';
import { IndexComponent } from './views/index/index.component';
import { PerfilComponent } from './views/perfil/perfil.component';
import { AuthGuard } from './guard/auth.guard';
import { MenuFestasComponent } from './views/menu-festas/menu-festas.component';
import { FestaPainelControleComponent } from './views/festa-painel-controle/festa-painel-controle.component';
import { CriarFestaComponent } from './views/criar-festa/criar-festa.component';

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
      path: 'festa-painel-controle',
      component: FestaPainelControleComponent,
      canActivate: [AuthGuard]
    },

    {
      path: 'criar-festa',
      component: CriarFestaComponent,
      canActivate: [AuthGuard]
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
