import { Injectable } from '@angular/core';
import { environment } from '../../../environments/environment';
import { HttpClient, HttpErrorResponse, HttpHeaders, HttpParams } from '@angular/common/http';
import { LogService } from '../logging/log.service';
import { take, catchError } from 'rxjs/operators';
import { throwError } from 'rxjs';
import { Router } from '@angular/router';
import { LoginService } from '../loginService/login.service';

@Injectable({
  providedIn: 'root'
})
export class GetSegurancaService {

  private readonly urlAreaSergurancaAdd = `${environment.URL_BACK}areaSeguranca/lista`;

  public farol = false;

  constructor(private http: HttpClient, public logService: LogService, public router: Router,
              public loginService: LoginService) { }

  getAreaSeguranca(idFesta) {
    if (!this.farol) {
      this.setFarol(true);
      const httpParams = new HttpParams()
      .append('codFesta', idFesta)
      .append('codUsuario', this.loginService.usuarioInfo.codUsuario);

      let headers = new HttpHeaders();
      headers = headers.append('Content-Type', 'application/json');
      headers = headers.append('Authorization', 'Bearer ' + JSON.parse(localStorage.getItem('token')).token);

      return this.http.get(this.urlAreaSergurancaAdd, {params: httpParams, headers}).pipe(
        take(1),
        catchError(error => {
          return this.handleError(error, this.logService);
        })
      );
    }
  }

  handleError = (error: HttpErrorResponse, logService: LogService) => {
    logService.initialize();
    logService.logHttpInfo(JSON.stringify(error), 0, error.url);
    this.setFarol(false);
    this.router.navigate(['404']);
    return throwError(error);
  }

  setFarol(flag: boolean) {
    this.farol = flag;
  }

  getFarol() {
    return this.farol;
  }
}
