import { Injectable } from '@angular/core';
import { environment } from '../../../environments/environment';
import { take, catchError } from 'rxjs/operators';
import { throwError } from 'rxjs';
import { HttpClient, HttpErrorResponse, HttpHeaders, HttpParams } from '@angular/common/http';
import { LogService } from '../logging/log.service';
import { MatDialog } from '@angular/material/dialog';
import { ErroDialogComponent } from '../../views/erro-dialog/erro-dialog.component';
import { LoginService } from '../loginService/login.service';

@Injectable({
  providedIn: 'root'
})
export class CepApiService {

  private readonly urlProxy = 'https://cors-anywhere.herokuapp.com/';
  private readonly urlAPI = 'https://viacep.com.br/ws/';

  constructor(private http: HttpClient, public logService: LogService, public dialog: MatDialog,
              public loginService: LoginService) { }

  resgatarEndereco(cep) {
    const url = this.urlProxy + this.urlAPI + cep + '/json/';

    return this.http.get(url).pipe(
      take(1),
      catchError(error => {
        error.error = 'ENDENFOU';
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
