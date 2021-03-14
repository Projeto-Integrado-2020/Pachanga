import { Injectable } from '@angular/core';
import { environment } from '../../../environments/environment';
import { take, catchError } from 'rxjs/operators';
import { throwError } from 'rxjs';
import { HttpClient, HttpErrorResponse, HttpHeaders, HttpParams } from '@angular/common/http';
import { LogService } from '../logging/log.service';
import { MatDialog } from '@angular/material/dialog';
import { ErroDialogComponent } from '../../views/erro-dialog/erro-dialog.component';
import { LoginService } from '../loginService/login.service';
import { DialogDadosBancariosComponent } from 'src/app/views/dialog-dados-bancarios/dialog-dados-bancarios.component';

@Injectable({
  providedIn: 'root'
})
export class CriarLoteService {

  farol = false;
  private readonly urlLoteAdd = `${environment.URL_BACK}lote/adicionar`;

  constructor(private http: HttpClient, public logService: LogService, public dialog: MatDialog,
              public loginService: LoginService) { }

    novoLote(lote) {
      if (!this.farol) {
        this.setFarol(true);
        const httpParams = new HttpParams()
        .append('codUsuario', this.loginService.usuarioInfo.codUsuario);

        let headers = new HttpHeaders();
        headers = headers.append('Content-Type', 'application/json');
        headers = headers.append('Authorization', 'Bearer ' + JSON.parse(localStorage.getItem('token')).token);

        return this.http.post(this.urlLoteAdd, lote, {params: httpParams, responseType: 'text', headers}).pipe(
          take(1),
          catchError(error => {
            return this.handleError(error, this.logService);
          })
        );
      }
    }

  handleError = (error: HttpErrorResponse, logService: LogService) => {
    this.dialog.closeAll();
    if (error.error === 'DADNLOTE') {
      this.openDialogDadosBancarios();
      this.openErrorDialog(error.error);
      logService.initialize();
      logService.logHttpInfo(JSON.stringify(error), 0, error.url);
      this.setFarol(false);
      return throwError(error);
    }
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

  openDialogDadosBancarios() {
    const dialogRef = this.dialog.open(DialogDadosBancariosComponent, {
      width: '500px'
    });
  }

  setFarol(flag: boolean) {
    this.farol = flag;
  }

  getFarol() {
    return this.farol;
  }
}
