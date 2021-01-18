import { HttpClient, HttpErrorResponse, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { throwError } from 'rxjs';
import { catchError, take } from 'rxjs/operators';
import { environment } from 'src/environments/environment';
import { LogService } from '../logging/log.service';
import { LoginService } from '../loginService/login.service';

@Injectable({
  providedIn: 'root'
})
export class RelatorioVendaService {

  private readonly baseUrl = `${environment.URL_BACK}relatorioVenda`;

  constructor(
    private httpClient: HttpClient,
    public loginService: LoginService,
    private logService: LogService
  ) { }

  ingressosFesta(codFesta) {
    const httpParams = new HttpParams()
      .append('codFesta', codFesta)
      .append('codUsuario', this.loginService.getusuarioInfo().codUsuario);

    let headers = new HttpHeaders();
    headers = headers.append('Content-Type', 'application/json');
    headers = headers.append('Authorization', 'Bearer ' + JSON.parse(localStorage.getItem('token')).token);

    return this.httpClient.get(this.baseUrl + '/ingressosFesta', {params: httpParams, headers}).pipe(
      take(1),
      catchError(error => {
        return this.handleError(error, this.logService);
      })
    );
  }

  ingressosFestaCompradosPagos(codFesta) {
    const httpParams = new HttpParams()
      .append('codFesta', codFesta)
      .append('codUsuario', this.loginService.getusuarioInfo().codUsuario);

    let headers = new HttpHeaders();
    headers = headers.append('Content-Type', 'application/json');
    headers = headers.append('Authorization', 'Bearer ' + JSON.parse(localStorage.getItem('token')).token);

    return this.httpClient.get(this.baseUrl + '/ingressosFestaCompradosPagos', {params: httpParams, headers}).pipe(
      take(1),
      catchError(error => {
        return this.handleError(error, this.logService);
      })
    );
  }

  lucroFesta(codFesta) {
    const httpParams = new HttpParams()
      .append('codFesta', codFesta)
      .append('codUsuario', this.loginService.getusuarioInfo().codUsuario);

    let headers = new HttpHeaders();
    headers = headers.append('Content-Type', 'application/json');
    headers = headers.append('Authorization', 'Bearer ' + JSON.parse(localStorage.getItem('token')).token);

    return this.httpClient.get(this.baseUrl + '/lucroFesta', {params: httpParams, headers}).pipe(
      take(1),
      catchError(error => {
        return this.handleError(error, this.logService);
      })
    );
  }

  handleError = (error: HttpErrorResponse, logService: LogService) => {
    logService.initialize();
    logService.logHttpInfo(JSON.stringify(error), 0, error.url);
    return throwError(error);
  }


}
