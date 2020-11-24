import { HttpClient, HttpErrorResponse, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { throwError } from 'rxjs';
import { catchError, take } from 'rxjs/operators';
import { environment } from 'src/environments/environment';
import { LogService } from '../logging/log.service';
import { LoginService } from '../loginService/login.service';

@Injectable({
  providedIn: 'root'
})
export class IngressosService {

  private readonly baseUrl = `${environment.URL_BACK}ingresso`;
  public farol = false;

  constructor(
    private httpClient: HttpClient,
    private loginService: LoginService,
    private logService: LogService,
    private router: Router
    ) { }

  listaIngressos() {
    if (!this.farol) {
    const httpParams = new HttpParams()
      .append('idUser', this.loginService.getusuarioInfo().codUsuario);

    let headers = new HttpHeaders();
    headers = headers.append('Content-Type', 'application/json');
    headers = headers.append('Authorization', 'Bearer ' + JSON.parse(localStorage.getItem('token')).token);

    return this.httpClient.get(this.baseUrl + '/listaUser', {params: httpParams, headers}).pipe(
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
    //this.router.navigate(['404']);
    return throwError(error);
  }

  setFarol(flag: boolean) {
    this.farol = flag;
  }
}
