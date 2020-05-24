import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './views/login/login.component';
import { CadastroComponent } from './views/cadastro/cadastro.component';
import { IndexComponent } from './views/index/index.component';
import { PerfilComponent } from './views/perfil/perfil.component';
import { AuthGuard } from './guard/auth.guard';

const routes: Routes = [

    {
      path: 'login',
      component: LoginComponent
    },

    {
      path: 'cadastro',
      component: CadastroComponent
    },

    {
      path: '',
      component: IndexComponent
    },

    {
      path: 'perfil',
      component: PerfilComponent
      //canActivate: [AuthGuard]
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
