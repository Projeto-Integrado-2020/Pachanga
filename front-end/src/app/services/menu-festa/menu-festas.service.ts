import { Injectable } from '@angular/core';
import { environment } from '../../../environments/environment';
import { take, catchError } from 'rxjs/operators';
import { throwError } from 'rxjs';
import { HttpClient, HttpErrorResponse, HttpHeaders, HttpParams } from '@angular/common/http';
import { LogService } from '../logging/log.service';

@Injectable({
  providedIn: 'root'
})
export class MenuFestasService {

  farol = false;
  private readonly urlGetFesta = `${environment.URL_BACK}festa/lista`;

  constructor(private http: HttpClient, public logService: LogService) { }

  getFestas(codUsuario) {
    if (!this.farol) {
      this.setFarol(true);
      const httpParams = new HttpParams()
        .append('idUser', codUsuario);

      let headers = new HttpHeaders();
      headers = headers.append('Content-Type', 'application/json');
      headers = headers.append('Authorization', 'Bearer ' + JSON.parse(localStorage.getItem('token')).token);

      return this.http.get(this.urlGetFesta, {params: httpParams, headers}).pipe(
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
    return throwError(error);
  }

  setFarol(flag: boolean) {
    this.farol = flag;
  }

  getFarol() {
    return this.farol;
  }
}
