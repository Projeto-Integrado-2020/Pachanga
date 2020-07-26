import { Injectable } from '@angular/core';
import { environment } from '../../../environments/environment';
import { HttpClient, HttpErrorResponse, HttpParams } from '@angular/common/http';
import { MatDialog } from '@angular/material/dialog';
import { ErroDialogComponent } from '../../views/erro-dialog/erro-dialog.component';
import { LogService } from '../logging/log.service';
import { take, catchError } from 'rxjs/operators';
import { throwError } from 'rxjs';
import { LoginService } from '../loginService/login.service';
import { Router } from '@angular/router';


@Injectable({
  providedIn: 'root'
})
export class EditarFestaService {

  private readonly urlAtualizarFesta = `${environment.URL_BACK}festa/atualizar`;

  constructor(private http: HttpClient, public logService: LogService, public dialog: MatDialog,
              public loginService: LoginService) { }

  atualizarFesta(dadosFesta) {
    const httpParams = new HttpParams()
    .append('idUser', this.loginService.usuarioInfo.codUsuario);
    return this.http.put(this.urlAtualizarFesta, dadosFesta, {params: httpParams}).pipe(
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
