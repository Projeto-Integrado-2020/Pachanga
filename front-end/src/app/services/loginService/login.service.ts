import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { environment } from '../../../environments/environment';
import { take, catchError } from 'rxjs/operators';
import { throwError } from 'rxjs';
import { MatDialog } from '@angular/material/dialog';
import { ErroDialogComponent } from '../../views/erro-dialog/erro-dialog.component';

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  public usuarioAutenticado = false;

  public usuarioInfo;

  private readonly urlLogin = `${environment.URL_BACK}usuario/login`;
  private readonly urlCadastro = `${environment.URL_BACK}usuario/cadastro`;

  constructor(private http: HttpClient, public dialog: MatDialog) { }

  logar(usuario) {
    console.log(JSON.stringify(usuario));
    return this.http.post(this.urlLogin, usuario).pipe(
      take(1),
      catchError(this.handleError)
    );
  }

  cadastrar(usuario) {
    console.log(JSON.stringify(usuario));
    return this.http.post(this.urlCadastro, usuario).pipe(
      take(1),
      catchError(this.handleError)
    );
  }

  handleError = (error: HttpErrorResponse) => {
    const dialogRef = this.dialog.open(ErroDialogComponent, {
      width: '250px',
      data: {erro: error.error}
    });
    return throwError(error);
  }

  getUsuarioAutenticado() {
    return this.usuarioAutenticado;
  }

  setUsuarioAutenticado(flag: boolean) {
    this.usuarioAutenticado = flag;
  }

  getusuarioInfo() {
    return this.usuarioInfo;
  }

  setusuarioInfo(json) {
    this.usuarioInfo = json;
  }

}
