import { Injectable } from '@angular/core';
import { MatDialog } from '@angular/material';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, UrlTree, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { DadosCompraIngressoService } from 'src/app/services/dados-compra-ingresso/dados-compra-ingresso.service';
import { LoginService } from 'src/app/services/loginService/login.service';
import { LoginComponent } from 'src/app/views/login/login.component';

@Injectable({
  providedIn: 'root'
})
export class CheckoutGuard implements CanActivate {

  constructor(public  dadosCheckout: DadosCompraIngressoService, public router: Router,
              public loginService: LoginService, public dialog: MatDialog ) { }

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): Observable<boolean> | boolean {

    if (this.loginService.getUsuarioAutenticado()) {
      if (this.dadosCheckout.getIngressos()) {
        return true;
      }
      this.router.navigate(['/']);

      return false;
    }
    this.dialog.open(LoginComponent, {
      width: '20rem',
    });

    return false;
  }

}
