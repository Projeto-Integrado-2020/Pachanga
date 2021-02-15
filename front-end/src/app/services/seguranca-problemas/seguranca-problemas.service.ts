import { HttpClient, HttpErrorResponse, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable, EventEmitter } from '@angular/core';
import { MatDialog } from '@angular/material';
import { Router } from '@angular/router';
import { throwError } from 'rxjs';
import { catchError, take } from 'rxjs/operators';
import { ErroDialogComponent } from 'src/app/views/erro-dialog/erro-dialog.component';
import { environment } from '../../../environments/environment';
import { LogService } from '../logging/log.service';
import { LoginService } from '../loginService/login.service';

@Injectable({
  providedIn: 'root'
})
export class SegurancaProblemasService {

  baseUrl = `${environment.URL_BACK}areaSegurancaProblema`;
  listaProblemas = `${environment.URL_BACK}problema/lista`;
  public farol = false;
  updateProblemasEmitter = new EventEmitter();

  constructor(
    private httpClient: HttpClient,
    public loginService: LoginService,
    private logService: LogService,
    private router: Router,
    public dialog: MatDialog
    ) { }

  listarProblemas() {
    this.setFarol(true);
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

  adicionarProblema(problemaTO, imagem) {
    if (!this.farol) {
      this.setFarol(true);

      const httpParams = new HttpParams()
        .append('idUsuario', this.loginService.getusuarioInfo().codUsuario);

      let headers = new HttpHeaders();
      headers = headers.append('Authorization', 'Bearer ' + JSON.parse(localStorage.getItem('token')).token);

      const formData = new FormData();
      formData.append('problemaSegurancaTO', JSON.stringify(problemaTO));
      if (imagem) {
        formData.append('imagem', imagem._files[0]);
      }

      console.log(formData);

      return this.httpClient.post(this.baseUrl + '/adicionar', formData, {params: httpParams, headers}).pipe(
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

  alterarStatus(finaliza, problemaTO) {
    if (!this.farol) {
      this.setFarol(true);

      let headers = new HttpHeaders();
      headers = headers.append('Content-Type', 'application/json');
      headers = headers.append('Authorization', 'Bearer ' + JSON.parse(localStorage.getItem('token')).token);

      const httpParams = new HttpParams()
        .append('finaliza', finaliza)
        .append('codUsuario', this.loginService.getusuarioInfo().codUsuario);

      return this.httpClient.put(this.baseUrl + '/alterarStatus', problemaTO, {headers, params: httpParams}).pipe(
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
    this.setFarol(true);
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

    // /lista get
    getAllProblemasFesta(codFesta) {
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

    getProblemaSeguranca(codAreaSegurancaProblema, codFesta) {
      this.setFarol(true);

      let headers = new HttpHeaders();
      headers = headers.append('Content-Type', 'application/json');
      headers = headers.append('Authorization', 'Bearer ' + JSON.parse(localStorage.getItem('token')).token);

      const httpParams = new HttpParams()
        .append('codAreaSegurancaProblema', codAreaSegurancaProblema)
        .append('idUsuario', this.loginService.getusuarioInfo().codUsuario)
        .append('codFesta', codFesta)
        .append('retornoImagem', 'true');

      return this.httpClient.get(this.baseUrl + '/findProblemaSeguranca', {headers, params: httpParams}).pipe(
        take(1),
        catchError(error => {
          return this.handleError(error, this.logService);
        })
      );
    }

    // updateProblemas() {
    //   if (!this.farol) {
    //   this.updateProblemasEmitter.emit();
    //   }
    // }
    /*
    handleError = (error: HttpErrorResponse, logService: LogService) => {
      this.dialog.closeAll();
      logService.initialize();
      logService.logHttpInfo(JSON.stringify(error), 0, error.url);
      this.setFarol(false);
      this.router.navigate(['404']);
      return throwError(error);
    } */

    handleError = (error: HttpErrorResponse, logService: LogService) => {
      this.dialog.closeAll();
      this.openErrorDialog(error.error);
      let painel = this.router.url;
      painel = painel.slice(0, -10);
      // this.router.navigate([painel]);
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

    setFarol(farol: boolean) {
      this.farol = farol;
    }

    getFarol() {
      return this.farol;
    }

}
