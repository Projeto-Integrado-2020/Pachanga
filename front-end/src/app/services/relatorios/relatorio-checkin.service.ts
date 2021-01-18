import { HttpClient, HttpErrorResponse, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { throwError } from 'rxjs';
import { catchError, take } from 'rxjs/operators';
import { environment } from 'src/environments/environment';
import { LogService } from '../logging/log.service';
import { LoginService } from '../loginService/login.service';

@Injectable({
  providedIn: 'root'
})
export class RelatorioCheckinService {

  private readonly baseUrl = `${environment.URL_BACK}relatorioCheckIn`;
  public farol = false;

  constructor(
    public httpClient: HttpClient,
    public loginService: LoginService,
    public logService: LogService,
    public router: Router
    ) { }

  faixaEtaria(codFesta) {
    const httpParams = new HttpParams()
      .append('codFesta', codFesta)
      .append('codUsuario', this.loginService.getusuarioInfo().codUsuario);

    let headers = new HttpHeaders();
    headers = headers.append('Content-Type', 'application/json');
    headers = headers.append('Authorization', 'Bearer ' + JSON.parse(localStorage.getItem('token')).token);

    return this.httpClient.get(this.baseUrl + '/faixaEtaria', {params: httpParams, headers}).pipe(
      take(1),
      catchError(error => {
        return this.handleError(error, this.logService);
      })
    );
  }

  genero(codFesta) {
    const httpParams = new HttpParams()
      .append('codFesta', codFesta)
      .append('codUsuario', this.loginService.getusuarioInfo().codUsuario);

    let headers = new HttpHeaders();
    headers = headers.append('Content-Type', 'application/json');
    headers = headers.append('Authorization', 'Bearer ' + JSON.parse(localStorage.getItem('token')).token);

    return this.httpClient.get(this.baseUrl + '/generos', {params: httpParams, headers}).pipe(
      take(1),
      catchError(error => {
        return this.handleError(error, this.logService);
      })
    );
  }

  qtdEntradasHora(codFesta) {
    const httpParams = new HttpParams()
      .append('codFesta', codFesta)
      .append('codUsuario', this.loginService.getusuarioInfo().codUsuario);

    let headers = new HttpHeaders();
    headers = headers.append('Content-Type', 'application/json');
    headers = headers.append('Authorization', 'Bearer ' + JSON.parse(localStorage.getItem('token')).token);

    return this.httpClient.get(this.baseUrl + '/quantidadeEntradasHora', {params: httpParams, headers}).pipe(
      take(1),
      catchError(error => {
        return this.handleError(error, this.logService);
      })
    );
  }

  checkedUnchecked(codFesta) {
    const httpParams = new HttpParams()
      .append('codFesta', codFesta)
      .append('codUsuario', this.loginService.getusuarioInfo().codUsuario);

    let headers = new HttpHeaders();
    headers = headers.append('Content-Type', 'application/json');
    headers = headers.append('Authorization', 'Bearer ' + JSON.parse(localStorage.getItem('token')).token);

    return this.httpClient.get(this.baseUrl + '/ingressosFestaCheckedUnchecked', {params: httpParams, headers}).pipe(
      take(1),
      catchError(error => {
        return this.handleError(error, this.logService);
      })
    );
  }

  handleError = (error: HttpErrorResponse, logService: LogService) => {
    logService.initialize();
    logService.logHttpInfo(JSON.stringify(error), 0, error.url);
    this.router.navigate(['404']);
    return throwError(error);
  }

}
