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
export class RelatorioEstoqueService {

  private readonly baseUrl = `${environment.URL_BACK}relatorioEstoque`;
  public farol = false;

  constructor(
    private httpClient: HttpClient,
    public loginService: LoginService,
    private logService: LogService
    ) { }

  // consumoItemEstoque: get
  //    param: codFesta (int), codUsuario (int)
  // perdaItemEstoque: get
  //    param: codFesta (int), codUsuario (int)
  // quantidadeItemEstoque: get
  //    param: codFesta (int), codUsuario (int)

  consumoItemEstoque(codFesta) {
    if (!this.farol) {
    const httpParams = new HttpParams()
      .append('codFesta', codFesta)
      .append('codUsuario', this.loginService.getusuarioInfo().codUsuario);

    let headers = new HttpHeaders();
    headers = headers.append('Content-Type', 'application/json');
    headers = headers.append('Authorization', 'Bearer ' + JSON.parse(localStorage.getItem('token')).token);

    return this.httpClient.get(this.baseUrl + '/consumoItemEstoque', {params: httpParams, headers}).pipe(
      take(1),
      catchError(error => {
        return this.handleError(error, this.logService);
      })
    );
    }
  }

  perdaItemEstoque(codFesta) {
    if (!this.farol) {
    const httpParams = new HttpParams()
      .append('codFesta', codFesta)
      .append('codUsuario', this.loginService.getusuarioInfo().codUsuario);

    let headers = new HttpHeaders();
    headers = headers.append('Content-Type', 'application/json');
    headers = headers.append('Authorization', 'Bearer ' + JSON.parse(localStorage.getItem('token')).token);

    return this.httpClient.get(this.baseUrl + '/perdaItemEstoque', {params: httpParams, headers}).pipe(
      take(1),
      catchError(error => {
        return this.handleError(error, this.logService);
      })
    );
    }
  }

  quantidadeItemEstoque(codFesta) {
    if (!this.farol) {
    const httpParams = new HttpParams()
      .append('codFesta', codFesta)
      .append('codUsuario', this.loginService.getusuarioInfo().codUsuario);

    let headers = new HttpHeaders();
    headers = headers.append('Content-Type', 'application/json');
    headers = headers.append('Authorization', 'Bearer ' + JSON.parse(localStorage.getItem('token')).token);

    return this.httpClient.get(this.baseUrl + '/quantidadeItemEstoque', {params: httpParams, headers}).pipe(
      take(1),
      catchError(error => {
        return this.handleError(error, this.logService);
      })
    );
    }
  }

  consumoProduto(codFesta) {
    if (!this.farol) {
    const httpParams = new HttpParams()
      .append('codFesta', codFesta)
      .append('codUsuario', this.loginService.getusuarioInfo().codUsuario);

    let headers = new HttpHeaders();
    headers = headers.append('Content-Type', 'application/json');
    headers = headers.append('Authorization', 'Bearer ' + JSON.parse(localStorage.getItem('token')).token);

    return this.httpClient.get(this.baseUrl + '/quantidadeConsumoProduto', {params: httpParams, headers}).pipe(
      take(1),
      catchError(error => {
        return this.handleError(error, this.logService);
      })
    );
    }
  }

  handleError = (error: HttpErrorResponse, logService: LogService) => {
    logService.initialize();
    logService.logHttpInfo(JSON.stringify(error), 0, error.url);
    this.setFarol(false);
    return throwError(error);
  }

  setFarol(flag: boolean) {
    this.farol = flag;
  }

  getFarol() {
    return this.farol;
  }
}
