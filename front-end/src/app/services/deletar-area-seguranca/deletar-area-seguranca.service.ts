import { Injectable } from '@angular/core';
import { environment } from '../../../environments/environment';
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
export class DeletarAreaSegurancaService {

  public farol = false;

  private readonly urlDeletarAreaSeguranca = `${environment.URL_BACK}areaSeguranca/delete`;

  constructor(private http: HttpClient, public logService: LogService, public dialog: MatDialog,
              public loginService: LoginService) { }

  deletarAreaSeguranca(codArea) {
    if (!this.farol) {
      this.setFarol(true);
      const httpParams = new HttpParams()
      .append('codArea', codArea)
      .append('codUsuario', this.loginService.usuarioInfo.codUsuario);

      let headers = new HttpHeaders();
      headers = headers.append('Content-Type', 'application/json');
      headers = headers.append('Authorization', 'Bearer ' + JSON.parse(localStorage.getItem('token')).token);

      return this.http.delete(this.urlDeletarAreaSeguranca, {params: httpParams, headers}).pipe(
        take(1),
        catchError(error => {
          return this.handleError(error, this.logService);
        })
      );
    }
  }

  handleError = (error: HttpErrorResponse, logService: LogService) => {
    this.setFarol(false);
    this.dialog.closeAll();
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

  setFarol(flag: boolean) {
    this.farol = flag;
  }

  getFarol() {
    return this.farol;
  }
}
