import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { environment } from '../../../environments/environment';
import { take, catchError } from 'rxjs/operators';
import { throwError, Observable } from 'rxjs';
import { MatDialog } from '@angular/material/dialog';
import { ErroDialogComponent } from '../../views/erro-dialog/erro-dialog.component';
import { LogService } from '../logging/log.service';
import { AuthTokenService } from '../auth-token/auth-token.service';


@Injectable({
  providedIn: 'root'
})
export class LoginService {

  public usuarioAutenticado = (localStorage.getItem('usuarioAutenticado') === 'true');

  public usuarioInfo = JSON.parse(localStorage.getItem('usuarioInfo'));

  public farol = false;

  private readonly urlLogin = `${environment.URL_BACK}usuario/login`;
  private readonly urlCadastro = `${environment.URL_BACK}usuario/cadastro`;

  constructor(private http: HttpClient, public logService: LogService, public dialog: MatDialog,
              public authToken: AuthTokenService) {  }

  logar(usuario) {
    if (!this.farol) {
      this.setFarol(true);

      let headers = new HttpHeaders();
      headers = headers.append('Content-Type', 'application/json');
      headers = headers.append('Authorization', 'Bearer ' + JSON.parse(localStorage.getItem('token')).token);

      return this.http.post(this.urlLogin, usuario, {headers}).pipe(
        take(1),
        catchError(error => {
          return this.handleError(error, this.logService);
        })
      );
    }
    return new Observable<never>();
  }

  cadastrar(usuario) {
    if (!this.farol) {
      this.setFarol(true);

      let headers = new HttpHeaders();
      headers = headers.append('Content-Type', 'application/json');
      headers = headers.append('Authorization', 'Bearer ' + JSON.parse(localStorage.getItem('token')).token);

      return this.http.post(this.urlCadastro, usuario, {headers}).pipe(
        take(1),
        catchError(error => {
          return this.handleError(error, this.logService);
        })
      );
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

  getUsuarioAutenticado() {
    return this.usuarioAutenticado;
  }

  setFarol(flag: boolean) {
    this.farol = flag;
  }

  getFarol() {
    return this.farol;
  }

  setUsuarioAutenticado(flag: boolean) {
    this.usuarioAutenticado = flag;
    localStorage.setItem('usuarioAutenticado', this.usuarioAutenticado.toString());
  }

  getusuarioInfo() {
    return this.usuarioInfo;
  }

  setusuarioInfo(json) {
    if (json.dtNasc != null) {
      json.dtNasc = json.dtNasc.slice(0, 10);
    }
    this.usuarioInfo = json;
    localStorage.setItem('usuarioInfo', JSON.stringify(this.usuarioInfo));
  }

  finalizarSessao() {
    localStorage.removeItem('usuarioInfo');
    localStorage.removeItem('usuarioAutenticado');
  }

}
