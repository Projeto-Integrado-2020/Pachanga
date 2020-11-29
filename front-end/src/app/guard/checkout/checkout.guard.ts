import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, UrlTree, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { DadosCompraIngressoService } from 'src/app/services/dados-compra-ingresso/dados-compra-ingresso.service';

@Injectable({
  providedIn: 'root'
})
export class CheckoutGuard implements CanActivate {

  constructor( public  dadosCheckout: DadosCompraIngressoService, public router: Router ) { }

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): Observable<boolean> | boolean {

    if (this.dadosCheckout.getIngressos()) {
      return true;
    }
    this.router.navigate(['/']);

    return false;

  }

}
