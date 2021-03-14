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
export class GerarIngressoService {

  private readonly urlIngressosAdd = `${environment.URL_BACK}ingresso/addIngressoLista`;

  constructor(private http: HttpClient, public logService: LogService, public dialog: MatDialog,
              public loginService: LoginService) { }

  adicionarIngressos(body) {
    let headers = new HttpHeaders();
    headers = headers.append('Content-Type', 'application/json');
    headers = headers.append('Authorization', 'Bearer ' + JSON.parse(localStorage.getItem('token')).token);

    return this.http.post(this.urlIngressosAdd, body, {headers});
  }

  handleError = (error: HttpErrorResponse) => {
    let mensagemError;
    if (error.error) {
      if (error.error.error_messages) {
        mensagemError = error.error.error_messages[0];
        if (mensagemError.code === '40002') {
          mensagemError = mensagemError.parameter_name;
        }
      } else {
        mensagemError = error.error;
      }
    } else {
      mensagemError = 'BOLEREGI';
    }
    this.openErrorDialog(mensagemError);
    this.logService.initialize();
    this.logService.logHttpInfo(JSON.stringify(error), 0, error.url);
    return throwError(error);
  }

  openErrorDialog(error) {
    const dialogRef = this.dialog.open(ErroDialogComponent, {
      width: '250px',
      data: {erro: error}
    });
  }
}
