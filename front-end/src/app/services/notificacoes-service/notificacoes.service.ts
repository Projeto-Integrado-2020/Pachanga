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
  FORMATO DA RESPONSE:
  - cod_notificacao (int)
  - desc_notificacao (string)
  - notificacao_grupo (set) {
  }
  - notificacao_usuario (set) {
  }
  */


  private readonly URL = `${environment.URL_BACK}notificacao/lista`;
  public farol = false;

  constructor(
    private http: HttpClient,
    public logService: LogService,
    public loginService: LoginService,
    public dialog: MatDialog
    ) { }

  getNotificacoes() {
    if (!this.farol) {
      this.farol = true;
      const httpParams = new HttpParams()
      .append('idUser', this.loginService.usuarioInfo.codUsuario);
      return this.http.get(this.URL, {params: httpParams}).pipe(
        take(1),
        catchError(error => {
          return this.handleError(error, this.logService);
        })
      );
    }
  }

  atualizarNotificacoes(notifIds: string[] ) {
    if (!this.farol) {
      this.farol = true;
      const httpParams = new HttpParams();
      httpParams.append('idUser', this.loginService.usuarioInfo.codUsuario);
      return this.http.put(this.URL, notifIds).pipe(
        take(1),
        catchError(error => {
          return this.handleError(error, this.logService);
      }),
    );
    }
  }

  deletarNotificacao(notifId: string) {
    if (!this.farol) {
      this.farol = true;
      const httpParams = new HttpParams();
      httpParams.append('idUser', this.loginService.usuarioInfo.codUsuario);
      httpParams.append('idNotificacao', notifId);
      return this.http.put(this.URL, {params: httpParams}).pipe(
        take(1),
        catchError(error => {
          return this.handleError(error, this.logService);
      }),
    );
    }
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