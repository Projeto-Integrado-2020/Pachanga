import { Injectable } from '@angular/core';
import { environment } from '../../../environments/environment';
import { HttpClient, HttpErrorResponse, HttpParams } from '@angular/common/http';
import { LogService } from '../logging/log.service';
import { take, catchError } from 'rxjs/operators';
import { throwError } from 'rxjs';
import { MatDialog } from '@angular/material';
import { ErroDialogComponent } from 'src/app/views/erro-dialog/erro-dialog.component';
import { LoginService } from '../loginService/login.service';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class GetEstoqueService {

  private readonly urlGetEstoque = `${environment.URL_BACK}estoque/lista`;

  constructor(private http: HttpClient, public logService: LogService, public dialog: MatDialog,
              public loginService: LoginService, public router: Router) { }

  getEstoque(idFesta) {
    const httpParams = new HttpParams()
    .append('codFesta', idFesta)
    .append('codUsuario', this.loginService.usuarioInfo.codUsuario);
    return this.http.get(this.urlGetEstoque, {params: httpParams}).pipe(
      take(1),
      catchError(error => {
        return this.handleError(error, this.logService);
      })
    );
  }

  handleError = (error: HttpErrorResponse, logService: LogService) => {
    this.openErrorDialog(error.error);
    let painel = this.router.url;
    painel = painel.slice(0, -7) + 'painel';
    this.router.navigate([painel]);
    logService.initialize();
    logService.logHttpInfo(JSON.stringify(error), 0, error.url);
    return throwError(error);
  }

  openErrorDialog(error) {
    const dialogRef = this.dialog.open(ErroDialogComponent, {
      width: '250px',
      data: {erro: error}
    });
  }

}
