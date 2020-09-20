import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { environment } from '../../../environments/environment';
import { take, catchError } from 'rxjs/operators';
import { throwError } from 'rxjs';
import { LogService } from '../logging/log.service';

@Injectable({
  providedIn: 'root'
})
export class AuthTokenService {

  public token = JSON.parse(localStorage.getItem('token'));

  private readonly urlOAuth = `${environment.URL_BACK}authenticate`;

  constructor(private http: HttpClient, public logService: LogService) {  }

  getOAuth() {
    const body = {
      username: 'pachanga',
      password: 'abc123XYZ'
    };
    return this.http.post(this.urlOAuth, body).pipe(
      take(1),
      catchError(error => {
        return this.handleError(error, this.logService);
      })
    );
  }

  handleError = (error: HttpErrorResponse, logService: LogService) => {
    logService.initialize();
    logService.logHttpInfo(JSON.stringify(error), 0, error.url);
    return throwError(error);
  }

  setToken(token) {
    localStorage.setItem('token', JSON.stringify(token));
    this.token = token;
  }

}
