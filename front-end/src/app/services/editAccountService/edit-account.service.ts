import { Injectable } from '@angular/core';
import { environment } from '../../../environments/environment';
import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { MatDialog } from '@angular/material/dialog';
import { ErroDialogComponent } from '../../views/erro-dialog/erro-dialog.component';
import { LogService } from '../logging/log.service';
import { take, catchError } from 'rxjs/operators';
import { throwError, Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class EditAccountService {

  private readonly urlEdit = `${environment.URL_BACK}usuario/atualizacao`;

  public farol = false;

  constructor(private http: HttpClient, public logService: LogService, public dialog: MatDialog) { }

  atualizar(usuarioAtualizado, userInfo) {
    if (!this.farol) {
      if (usuarioAtualizado.nomeUser === userInfo.nomeUser && usuarioAtualizado.genero === userInfo.genero &&
          usuarioAtualizado.dtNasc === userInfo.dtNasc && usuarioAtualizado.pronome === userInfo.pronome &&
          (usuarioAtualizado.emailNovo === null || usuarioAtualizado.emailNovo === userInfo.email) &&
          usuarioAtualizado.senhaNova === null) {
          this.openErrorDialog('NOTMODIF');
      } else {
        let headers = new HttpHeaders();
        headers = headers.append('Content-Type', 'application/json');
        headers = headers.append('Authorization', 'Bearer ' + JSON.parse(localStorage.getItem('token')).token);

        return this.http.put(this.urlEdit, usuarioAtualizado, {headers}).pipe(
          take(1),
          catchError(error => {
            return this.handleError(error, this.logService);
          }),
        );
      }
    }
    return new Observable<never>();
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
