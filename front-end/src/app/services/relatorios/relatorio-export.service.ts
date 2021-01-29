import { HttpClient, HttpErrorResponse, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { MatDialog } from '@angular/material';
import { Observable, throwError } from 'rxjs';
import { catchError, take } from 'rxjs/operators';
import { ErroDialogComponent } from 'src/app/views/erro-dialog/erro-dialog.component';
import { environment } from 'src/environments/environment';
import { LogService } from '../logging/log.service';
import { LoginService } from '../loginService/login.service';

@Injectable({
  providedIn: 'root'
})
export class RelatorioExportService {

  farol = false;
  private readonly urlExport = `${environment.URL_BACK}envioDePDFPorEmail/enviarRelatorio`;

  constructor(private http: HttpClient, public logService: LogService, public dialog: MatDialog,
              public loginService: LoginService) { }

  exportarRelatorio(codFesta, pdf, emails) {
    if (!this.farol) {
      this.setFarol(true);
      const httpParams = new HttpParams()
        .append('codFesta', codFesta)
        .append('codUsuario', this.loginService.usuarioInfo.codUsuario);

      let headers = new HttpHeaders();
      headers = headers.append('Authorization', 'Bearer ' + JSON.parse(localStorage.getItem('token')).token);

      const body = {
        listaDeEmails: emails,
        base64Pdf: pdf
      };

      return this.http.post(this.urlExport, body, {params: httpParams, headers}).pipe(
        take(1),
        catchError(error => {
          return this.handleError(error, this.logService);
        })
      );
    }
    return new Observable<never>();
  }

  handleError = (error: HttpErrorResponse, logService: LogService) => {
    this.dialog.closeAll();
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
