import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { environment } from '../../../environments/environment';
import { take, catchError } from 'rxjs/operators';
import { throwError } from 'rxjs';


@Injectable({
  providedIn: 'root'
})
export class LoginService {

  private readonly urlLogin = `${environment.URL_BACK}usuario/login`;
  private readonly urlCadastro = `${environment.URL_BACK}usuario/cadastro`;

  constructor(private http: HttpClient) { }

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

  handleError(error: HttpErrorResponse) {
    alert('Error: ' + error.error);
    return throwError(error);
  }

}
