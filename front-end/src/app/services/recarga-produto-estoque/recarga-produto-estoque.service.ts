import { Injectable } from '@angular/core';
import { environment } from '../../../environments/environment';
import { HttpClient, HttpErrorResponse, HttpParams } from '@angular/common/http';
import { ErroDialogComponent } from '../../views/erro-dialog/erro-dialog.component';
import { LogService } from '../logging/log.service';
import { take, catchError } from 'rxjs/operators';
import { throwError } from 'rxjs';
import { LoginService } from '../loginService/login.service';
import { MatDialog } from '@angular/material';

@Injectable({
  providedIn: 'root'
})
export class RecargaProdutoEstoqueService {

  farol = false;
  private readonly urlRecargaProdutoEstoque = `${environment.URL_BACK}produto/recargaProdutoEstoque`;

  constructor(private http: HttpClient, public logService: LogService, public dialog: MatDialog,
              public loginService: LoginService) { }

  recargaProdutoEstoque(quantidade, codProduto, codEstoque) {
    const httpParams = new HttpParams()
    .append('idUsuarioPermissao', this.loginService.usuarioInfo.codUsuario)
    .append('quantidade', quantidade)
    .append('codProduto', codProduto)
    .append('codEstoque', codEstoque);
    return this.http.put(this.urlRecargaProdutoEstoque, quantidade, {params: httpParams}).pipe(
      take(1),
      catchError(error => {
        return this.handleError(error, this.logService);
      })
    );
  }

  handleError = (error: HttpErrorResponse, logService: LogService) => {
    this.dialog.closeAll();
    this.openErrorDialog(error.error);
    logService.initialize();
    logService.logHttpInfo(JSON.stringify(error), 0, error.url);
    this.setFarol(false);
    return throwError(error);
  }

  openErrorDialog(error) {
    const dialogRef = this.dialog.open(ErroDialogComponent, {
      width: '250px',
      data: {erro: error}
    });
  }

  setFarol(flag: boolean) {
    this.farol = flag;
  }

  getFarol() {
    return this.farol;
  }

}
