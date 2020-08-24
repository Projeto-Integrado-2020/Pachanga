import { Injectable } from '@angular/core';
import { HttpClient, HttpParams, HttpErrorResponse } from '@angular/common/http';
import { LogService } from '../logging/log.service';
import { environment } from 'src/environments/environment';
import { take, catchError } from 'rxjs/operators';
import { throwError } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ConvidadoService {


private readonly URL = `${environment.URL_BACK}convidado`;
private readonly FESTA = `${environment.URL_BACK}festa`;

constructor(
  private http: HttpClient,
  public logService: LogService,
  ) { }

aceitarConvite(codConvidado, idGrupo) {
  const httpParams = new HttpParams()
    .append('codConvidado', codConvidado)
    .append('idGrupo', idGrupo);
  return this.http.post(this.URL + '/accConvite',
      null,
     {params: httpParams}).pipe(
      take(1),
      catchError(error => {
        return this.handleError(error, this.logService);
    }),
  );
}

recusarConvite(codConvidado, idGrupo) {
  const httpParams = new HttpParams()
    .append('codConvidado', codConvidado)
    .append('idGrupo', idGrupo);
  return this.http.post(this.URL + '/recuConvite',
      null,
     {params: httpParams}).pipe(
      take(1),
      catchError(error => {
        return this.handleError(error, this.logService);
    }),
  );
}

getDetalhesFesta(codConvidado, idGrupo) {
  const httpParams = new HttpParams()
  .append('codConvidado', codConvidado)
  .append('codGrupo', idGrupo);

  return this.http.get(this.FESTA + '/festaUnicaConvidado',
   {params: httpParams}).pipe(
    take(1),
    catchError(error => {
      return this.handleError(error, this.logService);
  }),
);
}

  handleError(error: HttpErrorResponse, logService: LogService) {
    logService.initialize();
    logService.logHttpInfo(JSON.stringify(error), 0, error.url);
    return throwError(error);
  }

}
