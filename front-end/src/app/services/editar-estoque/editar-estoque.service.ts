import { Injectable } from '@angular/core';
import { environment } from '../../../environments/environment';
import { take, catchError } from 'rxjs/operators';
import { throwError } from 'rxjs';
import { HttpClient, HttpErrorResponse, HttpParams } from '@angular/common/http';
import { LogService } from '../logging/log.service';
import { MatDialog } from '@angular/material/dialog';
import { ErroDialogComponent } from '../../views/erro-dialog/erro-dialog.component';
import { LoginService } from '../loginService/login.service';

@Injectable({
  providedIn: 'root'
})
export class EditarEstoqueService {

  farol = false;
  private readonly urlEstoqueEdit = `${environment.URL_BACK}estoque/atualizar`;

  constructor(private http: HttpClient, public logService: LogService, public dialog: MatDialog,
              public loginService: LoginService) { }

  atualizarEstoque(idFesta, estoque) {
    if (!this.farol) {
      this.setFarol(true);
      const httpParams = new HttpParams()
      .append('codFesta', idFesta)
      .append('codUsuario', this.loginService.usuarioInfo.codUsuario);
      return this.http.put(this.urlEstoqueEdit, estoque, {params: httpParams}).pipe(
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
