import { Injectable } from '@angular/core';
import { environment } from '../../../environments/environment';
import { HttpClient, HttpErrorResponse, HttpParams } from '@angular/common/http';
import { LogService } from '../logging/log.service';
import { take, catchError } from 'rxjs/operators';
import { throwError } from 'rxjs';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class PainelControleService {

  private readonly urlFesta = `${environment.URL_BACK}festa/festaUnica`;

  public farol = false;

  constructor(private http: HttpClient, public logService: LogService) { }

  acessarFesta(idFesta){
    if (!this.farol) {
      this.setFarol(true);
      const httpParams = new HttpParams()
      .append('idFesta', idFesta);
      return this.http.get(this.urlFesta, {params: httpParams}).pipe(
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
