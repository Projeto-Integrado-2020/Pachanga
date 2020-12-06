import { Injectable } from '@angular/core';
import {Location} from '@angular/common';
import { environment } from '../../../environments/environment';
import { HttpClient, HttpErrorResponse, HttpHeaders, HttpParams } from '@angular/common/http';
import { LogService } from '../logging/log.service';
import { take, catchError } from 'rxjs/operators';
import { throwError } from 'rxjs';
import { Router } from '@angular/router';
import { LoginService } from '../loginService/login.service';
import { MatDialog } from '@angular/material/dialog';
import { ErroDialogComponent } from '../../views/erro-dialog/erro-dialog.component';

@Injectable({
  providedIn: 'root'
})
export class GetCuponsService {

  private readonly urlGetAllCupons = `${environment.URL_BACK}cupom/cuponsFesta`;
  private readonly urlGetCupomUnico = `${environment.URL_BACK}cupom/cupomUnico`;

  public farol = false;

  constructor(private http: HttpClient, public logService: LogService, public router: Router,
              public loginService: LoginService, public location: Location, public dialog: MatDialog) { }

  getCupons(idFesta) {
    if (!this.farol) {
      this.setFarol(true);
      const httpParams = new HttpParams()
      .append('idUser', this.loginService.usuarioInfo.codUsuario)
      .append('codFesta', idFesta);

      let headers = new HttpHeaders();
      headers = headers.append('Content-Type', 'application/json');
      headers = headers.append('Authorization', 'Bearer ' + JSON.parse(localStorage.getItem('token')).token);

      return this.http.get(this.urlGetAllCupons, {params: httpParams, headers}).pipe(
        take(1),
        catchError(error => {
          return this.handleError(error, this.logService);
        })
      );
    }
  }

  getCupomUnico(codFesta, nomeCupom) {
    if (!this.farol) {
      this.setFarol(true);
      const httpParams = new HttpParams()
      .append('codFesta', codFesta)
      .append('nomeCupom', nomeCupom);

      let headers = new HttpHeaders();
      headers = headers.append('Content-Type', 'application/json');
      headers = headers.append('Authorization', 'Bearer ' + JSON.parse(localStorage.getItem('token')).token);

      return this.http.get(this.urlGetCupomUnico, {params: httpParams, headers}).pipe(
        take(1),
        catchError(error => {
          return this.handleError(error, this.logService);
        })
      );
    }
  }

  handleError = (error: HttpErrorResponse, logService: LogService) => {
    this.openErrorDialog(error.error);
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

  setFarol(flag: boolean) {
    this.farol = flag;
  }

  getFarol() {
    return this.farol;
  }
}
