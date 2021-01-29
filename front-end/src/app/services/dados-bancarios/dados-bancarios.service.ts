import { HttpHeaders, HttpParams } from '@angular/common/http';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { MatDialog } from '@angular/material';
import { throwError } from 'rxjs';
import { catchError, take } from 'rxjs/operators';
import { ErroDialogComponent } from 'src/app/views/erro-dialog/erro-dialog.component';
import { environment } from 'src/environments/environment';
import { LogService } from '../logging/log.service';
import { LoginService } from '../loginService/login.service';


@Injectable({
  providedIn: 'root'
})
export class DadosBancariosService {


private readonly baseURL = `${environment.URL_BACK}dadoBancario`;

  farol = false;
  constructor(
    public dialog: MatDialog,
    private logService: LogService,
    private loginService: LoginService,
    private http: HttpClient
    ) { }

  receberDado(codFesta) {
    if (!this.farol) {
      this.setFarol(true);

      const httpParams = new HttpParams()
      .append('codFesta', codFesta)
      .append('codUsuario', this.loginService.usuarioInfo.codUsuario);

      let headers = new HttpHeaders();
      headers = headers.append('Content-Type', 'application/json');
      headers = headers.append('Authorization', 'Bearer ' + JSON.parse(localStorage.getItem('token')).token);

      return this.http.get(this.baseURL + '/dadoUnico', {params: httpParams, headers}).pipe(
        take(1),
        catchError(error => {
          return this.handleError(error, this.logService, true);
        })
      );
    }
  }

  inserirDados(dadosTO) {
    if (!this.farol) {
    this.setFarol(true);
    const httpParams = new HttpParams()
      .append('codUsuario', this.loginService.usuarioInfo.codUsuario);

    let headers = new HttpHeaders();
    headers = headers.append('Content-Type', 'application/json');
    headers = headers.append('Authorization', 'Bearer ' + JSON.parse(localStorage.getItem('token')).token);

    return this.http.post(this.baseURL + '/modificarDadoBancario', dadosTO, { params: httpParams, headers}).pipe(
      take(1),
      catchError(error => {
        return this.handleError(error, this.logService, false);
      })
    );
    }
  }

  setFarol(flag: boolean) {
    this.farol = flag;
  }

  getFarol() {
    return this.farol;
  }

  handleError = (error: HttpErrorResponse, logService: LogService, get: boolean) => {
    this.setFarol(false);
    this.dialog.closeAll();
    if (!get) {
      this.openErrorDialog(error.error);
    }
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
