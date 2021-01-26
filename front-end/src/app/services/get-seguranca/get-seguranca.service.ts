import { Injectable } from '@angular/core';
import { environment } from '../../../environments/environment';
import { HttpClient, HttpErrorResponse, HttpHeaders, HttpParams } from '@angular/common/http';
import { LogService } from '../logging/log.service';
import { take, catchError } from 'rxjs/operators';
import { throwError } from 'rxjs';
import { Router } from '@angular/router';
import { LoginService } from '../loginService/login.service';
import { MatDialog } from '@angular/material';
import { ErroDialogComponent } from 'src/app/views/erro-dialog/erro-dialog.component';

@Injectable({
  providedIn: 'root'
})
export class GetSegurancaService {

  private readonly urlAreaSergurancaGet = `${environment.URL_BACK}areaSeguranca/lista`;

  constructor(private http: HttpClient, public logService: LogService, public router: Router,
              public dialog: MatDialog, public loginService: LoginService) { }

  getAreaSeguranca(idFesta) {
    const httpParams = new HttpParams()
    .append('codFesta', idFesta)
    .append('codUsuario', this.loginService.usuarioInfo.codUsuario);

    let headers = new HttpHeaders();
    headers = headers.append('Content-Type', 'application/json');
    headers = headers.append('Authorization', 'Bearer ' + JSON.parse(localStorage.getItem('token')).token);

    return this.http.get(this.urlAreaSergurancaGet, {params: httpParams, headers}).pipe(
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
    let painel = this.router.url;
    painel = painel.slice(0, -10);
    this.router.navigate([painel]);
    return throwError(error);
  }

  openErrorDialog(error) {
    const dialogRef = this.dialog.open(ErroDialogComponent, {
      width: '250px',
      data: {erro: error}
    });
  }

}
