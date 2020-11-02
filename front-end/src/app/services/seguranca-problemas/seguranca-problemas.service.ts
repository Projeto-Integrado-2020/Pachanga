import { HttpClient, HttpErrorResponse, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { throwError } from 'rxjs';
import { catchError, take } from 'rxjs/operators';
import { environment } from '../../../environments/environment';
import { LogService } from '../logging/log.service';
import { LoginService } from '../loginService/login.service';

@Injectable({
  providedIn: 'root'
})
export class SegurancaProblemasService {

  baseUrl = `${environment.URL_BACK}areaSegurancaProblema`;
  listaProblemas = `${environment.URL_BACK}problema/lista`;
  farol = false;

  constructor(
    private httpClient: HttpClient,
    public loginService: LoginService,
    private logService: LogService,
    private router: Router
    ) { }

  listarProblemas() {
    if (!this.farol) {
      let headers = new HttpHeaders();
      headers = headers.append('Content-Type', 'application/json');
      headers = headers.append('Authorization', 'Bearer ' + JSON.parse(localStorage.getItem('token')).token);

      return this.httpClient.get(this.listaProblemas, {headers}).pipe(
        take(1),
        catchError(error => {
          return this.handleError(error, this.logService);
        })
      );
    }
  }

  adicionarProblema(problemaTO) {
    if (!this.farol) {
      let headers = new HttpHeaders();
      headers = headers.append('Content-Type', 'application/json');
      headers = headers.append('Authorization', 'Bearer ' + JSON.parse(localStorage.getItem('token')).token);

      const httpParams = new HttpParams()
        .append('idUsuario', this.loginService.getusuarioInfo().codUsuario);

      return this.httpClient.post(this.baseUrl + '/adicionar', problemaTO, {headers, params: httpParams}).pipe(
        take(1),
        catchError(error => {
          return this.handleError(error, this.logService);
        })
      );
    }
  }

  // acredito que não sirva para nada
  // atualizarProblema(problemaTO) {
  //   const httpParams = new HttpParams()
  //     .append('idUsuario', this.loginService.getusuarioInfo().codUsuario);

  //   return this.httpClient.put(this.baseUrl + '/atualizar', problemaTO, {params: httpParams});
  // }

  deletarProblema(codArea, codFesta, codProblema) {
    if (!this.farol) {
      const httpParams = new HttpParams()
        .append('codAreaSeguranca', codArea)
        .append('codProblema', codProblema)
        .append('codFesta', codFesta)
        .append('idUsuario', this.loginService.getusuarioInfo().codUsuario);

      return this.httpClient.delete(this.baseUrl + '/delete', {params: httpParams}).pipe(
        take(1),
        catchError(error => {
          return this.handleError(error, this.logService);
        })
      );
    }
  }


  // / procurar problema único. para que?
  // getProblema(codProblema, codArea, codFesta) {

  //   const httpParams = new HttpParams()
  //     .append('codFesta', codFesta)
  //     .append('codProblema', codProblema)
  //     .append('codAreaSeguranca', codArea)
  //     .append('idUsuario', this.loginService.getusuarioInfo().codUsuario);

  //   return this.httpClient.get(this.baseUrl + '/areaUnica', {params: httpParams});
  // }

  // /lista get
  getAllProblemasArea(codArea, codFesta) {
    if(!this.farol) {
      let headers = new HttpHeaders();
      headers = headers.append('Content-Type', 'application/json');
      headers = headers.append('Authorization', 'Bearer ' + JSON.parse(localStorage.getItem('token')).token);

      const httpParams = new HttpParams()
        .append('codAreaSeguranca', codArea)
        .append('codFesta', codFesta)
        .append('idUsuario', this.loginService.getusuarioInfo().codUsuario);

      return this.httpClient.get(this.baseUrl + '/findAllProblemasSegurancaArea', {headers, params: httpParams}).pipe(
        take(1),
        catchError(error => {
          return this.handleError(error, this.logService);
        })
      );
    }
  }

    // /lista get
    getAllProblemasFesta(codFesta) {
      if(!this.farol){
        this.setFarol(true);
        const httpParams = new HttpParams()
          .append('codFesta', codFesta)
          .append('idUsuario', this.loginService.getusuarioInfo().codUsuario);
  
        return this.httpClient.get(this.baseUrl + '/lista', {params: httpParams}).pipe(
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
      this.router.navigate(['404']);
      return throwError(error);
    }

    setFarol(farol: boolean) {
      this.farol = farol;
    }

    getFarol() {
      return this.farol;
    }

}
