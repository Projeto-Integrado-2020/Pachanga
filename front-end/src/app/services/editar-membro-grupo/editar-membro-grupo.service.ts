import { Injectable } from '@angular/core';
import { environment } from '../../../environments/environment';
import { HttpClient, HttpErrorResponse, HttpParams } from '@angular/common/http';
import { MatDialog } from '@angular/material/dialog';
import { LogService } from '../logging/log.service';
import { take, catchError } from 'rxjs/operators';
import { throwError } from 'rxjs';
import { LoginService } from '../loginService/login.service';
import { ErroDialogComponent } from 'src/app/views/erro-dialog/erro-dialog.component';

@Injectable({
  providedIn: 'root'
})
export class EditarMembroGrupoService {

  public farol = false;

  private readonly urlDeletarMembro = `${environment.URL_BACK}grupo/updateUser`;

  constructor(private http: HttpClient, public logService: LogService, public dialog: MatDialog,
              public loginService: LoginService) { }

  editarMembroColaborador(id, grupo, novosGrupos) {
    if (!this.farol) {
      this.setFarol(true);
      const httpParams = new HttpParams()
      .append('idUsuarioEdit', id)
      .append('codGrupo', grupo)
      .append('idUsuario', this.loginService.usuarioInfo.codUsuario);
      return this.http.put(this.urlDeletarMembro, novosGrupos, {params: httpParams}).pipe(
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

  setFarol(flag: boolean) {
    this.farol = flag;
  }

  getFarol() {
    return this.farol;
  }

  openErrorDialog(error) {
    const dialogRef = this.dialog.open(ErroDialogComponent, {
      width: '250px',
      data: {erro: error}
    });
  }

}
