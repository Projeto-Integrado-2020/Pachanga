import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse, HttpHeaders, HttpParams } from '@angular/common/http';
import { MatDialog } from '@angular/material/dialog';
import { LogService } from '../logging/log.service';
import { take, catchError } from 'rxjs/operators';
import { throwError } from 'rxjs';
import { LoginService } from '../loginService/login.service';
import { ErroDialogComponent } from 'src/app/views/erro-dialog/erro-dialog.component';

@Injectable({
  providedIn: 'root'
})
export class PagSeguroService {

  private readonly urlProxy = 'https://cors-anywhere.herokuapp.com/';
  private readonly urlPagSeguro = 'https://sandbox.api.pagseguro.com/charges';

  constructor(private http: HttpClient, public logService: LogService, public dialog: MatDialog,
              public loginService: LoginService) { }

  gerarBoleto(infoPagador) {
    let headers = new HttpHeaders();
    headers = headers.append('Content-Type', 'application/json');
    headers = headers.append('Authorization', '3B95AB63B9A94FEFAD7E95349B62FFAA');
    headers = headers.append('x-api-version', '4.0');

    const url = this.urlProxy + this.urlPagSeguro;
    return this.http.post(url, infoPagador, {headers}).pipe(
      take(1),
      catchError(error => {
        return this.handleError(error, this.logService);
      })
    );
  }

  resgatarBoleto(idBoleto) {
    let headers = new HttpHeaders();
    headers = headers.append('Content-Type', 'application/json');
    headers = headers.append('Authorization', '3B95AB63B9A94FEFAD7E95349B62FFAA');
    headers = headers.append('x-api-version', '4.0');

    const url = this.urlProxy + this.urlPagSeguro + '/' + idBoleto;

    return this.http.get(url, {headers}).pipe(
      take(1),
      catchError(error => {
        return this.handleError(error, this.logService);
      })
    );
  }

  handleError = (error: HttpErrorResponse, logService: LogService) => {
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
