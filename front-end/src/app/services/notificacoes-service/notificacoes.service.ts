import { Injectable } from '@angular/core';
import { HttpClient, HttpParams, HttpErrorResponse } from '@angular/common/http';
import { LogService } from '../logging/log.service';
import { environment } from 'src/environments/environment';
import { LoginService } from '../loginService/login.service';
import { take, catchError } from 'rxjs/operators';
import { throwError } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class NotificacoesService {

  private readonly URL = `${environment.URL_BACK}notificacao`;

  constructor(
    private http: HttpClient,
    public logService: LogService,
    public loginService: LoginService,
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

  // METODO PARA DELETAR E MODIFICAR ESTADO!

  atualizarNotificacoes(notifIds: number[] ) {
      const httpParams = new HttpParams()
        .append('idUser', this.loginService.usuarioInfo.codUsuario);
      return this.http.put(this.URL + '/mudarStatus', notifIds, {params: httpParams}).pipe(
        take(1),
        catchError(error => {
          return this.handleError(error, this.logService);
      }),
    );
  }

  destacarNotificacao(notifId: number) {
      const httpParams = new HttpParams()
      .append('idUser', this.loginService.usuarioInfo.codUsuario)
      .append('idNotificacao', notifId.toString());
      return this.http.put(this.URL + '/destaque', null, {params: httpParams}).pipe(
        take(1),
        catchError(error => {
          return this.handleError(error, this.logService);
      }),
    );
  }

  deletarNotificacao(notificacao: any) {
      const httpParams = new HttpParams()
      .append('idUser', this.loginService.usuarioInfo.codUsuario)
      .append('mensagem', notificacao.mensagem);
      return this.http.delete(this.URL + '/delete', {params: httpParams}).pipe(
        take(1),
        catchError(error => {
          return this.handleError(error, this.logService);
      }),
    );
  }

  handleError(error: HttpErrorResponse, logService: LogService) {
    logService.initialize();
    logService.logHttpInfo(JSON.stringify(error), 0, error.url);
    return throwError(error);
}

}
