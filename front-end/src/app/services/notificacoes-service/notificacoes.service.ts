import { Injectable } from '@angular/core';
import { HttpClient, HttpParams, HttpErrorResponse } from '@angular/common/http';
import { LogService } from '../logging/log.service';
import { environment } from 'src/environments/environment';
import { LoginService } from '../loginService/login.service';
import { take, catchError } from 'rxjs/operators';
import { MatDialog } from '@angular/material';
import { throwError } from 'rxjs';
import { ErroDialogComponent } from 'src/app/views/erro-dialog/erro-dialog.component';

@Injectable({
  providedIn: 'root'
})
export class NotificacoesService {

  /*
{
    "notificacoesUsuario": [
        {
            "codUsuario": 1,
            "destaque": true,
            "status": "ok",
            "mensagem": "notificacao de usuario",
            "notificacao": 1
        }
    ],
    "notificacoesGrupo": [],
    "notificacaoConvidado": [
        {
            "codNotificacao": 1,
            "descNotificacao": "usuario"
        }
    ]
}
  */


  private readonly URL = `${environment.URL_BACK}notificacao`;

  constructor(
    private http: HttpClient,
    public logService: LogService,
    public loginService: LoginService,
    public dialog: MatDialog
    ) { }

  getNotificacoes() {
      const httpParams = new HttpParams()
      .append('idUser', this.loginService.usuarioInfo.codUsuario);

      return this.http.get(this.URL + '/lista', {params: httpParams})
        .pipe(
        take(1),
        catchError(error => {
          return this.handleError(error, this.logService);
        })
      );
  }

  atualizarNotificacoes(notifIds: number[] ) {
      const httpParams = new HttpParams();
      httpParams.append('idUser', this.loginService.usuarioInfo.codUsuario);
      return this.http.put(this.URL + '/mudarStatus', notifIds, {params: httpParams}).pipe(
        take(1),
        catchError(error => {
          return this.handleError(error, this.logService);
      }),
    );
  }

  destacarNotificacao(notifId: number) {
      const httpParams = new HttpParams();
      httpParams.append('idUser', this.loginService.usuarioInfo.codUsuario);
      httpParams.append('idNotificacao', notifId.toString());
      return this.http.put(this.URL + '/destaque', null, {params: httpParams}).pipe(
        take(1),
        catchError(error => {
          return this.handleError(error, this.logService);
      }),
    );
  }

  deletarNotificacao(notifId: number) {
      const httpParams = new HttpParams();
      httpParams.append('idUser', this.loginService.usuarioInfo.codUsuario);
      httpParams.append('idNotificacao', notifId.toString());
      return this.http.put(this.URL + '/delete', null, {params: httpParams}).pipe(
        take(1),
        catchError(error => {
          return this.handleError(error, this.logService);
      }),
    );
  }

  // METODO PARA DELETAR E MODIFICAR ESTADO!

  handleError(error: HttpErrorResponse, logService: LogService) {
    this.openErrorDialog(error.error);
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
