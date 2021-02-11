import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse, HttpHeaders, HttpParams } from '@angular/common/http';
import { MatDialog } from '@angular/material/dialog';
import { LogService } from '../logging/log.service';
import { take, catchError } from 'rxjs/operators';
import { Observable, throwError } from 'rxjs';
import { LoginService } from '../loginService/login.service';
import { ErroDialogComponent } from 'src/app/views/erro-dialog/erro-dialog.component';

@Injectable({
  providedIn: 'root'
})
export class SymplaApiService {

  farol = false;
  private readonly urlBaseAPI = 'https://api.sympla.com.br/public/v3/events/';
  private readonly urlParticipants = '/participants';
  private readonly urlTicketNumber = '/ticketNumber/';
  private readonly urlCheckIn = '/checkIn';

  constructor(private http: HttpClient, public logService: LogService, public dialog: MatDialog,
              public loginService: LoginService) { }

  testSymplaConnection(eventId, S_TOKEN) {
    let headers = new HttpHeaders();
    headers = headers.append('Content-Type', 'application/json');
    headers = headers.append('S_TOKEN', S_TOKEN);

    const url = this.urlBaseAPI + eventId;

    return this.http.get(url, {headers}).pipe(
      take(1),
      catchError(error => {
        error.error = 'SYMERROR';
        return this.handleError(error, this.logService);
      })
    );
  }

  listarParticipantesEvento(eventId, S_TOKEN) {
    let headers = new HttpHeaders();
    headers = headers.append('Content-Type', 'application/json');
    headers = headers.append('S_TOKEN', S_TOKEN);

    const url = this.urlBaseAPI + eventId + this.urlParticipants;

    return this.http.get(url, {headers}).pipe(
      take(1),
      catchError(error => {
        return this.handleError(error, this.logService);
      })
    );
  }

  checkInIngresso(eventId, ticketNumber, S_TOKEN) {
    if (!this.farol) {
      this.setFarol(true);
      let headers = new HttpHeaders();
      headers = headers.append('Content-Type', 'application/json');
      headers = headers.append('S_TOKEN', S_TOKEN);

      const url = this.urlBaseAPI + eventId +
                  this.urlParticipants + this.urlTicketNumber + ticketNumber + this.urlCheckIn;

      return this.http.post(url, null, {headers});
    }
    return new Observable<never>();
  }

  handleError = (error: HttpErrorResponse, logService: LogService) => {
    this.openErrorDialog(error.error);
    logService.initialize();
    logService.logHttpInfo(JSON.stringify(error), 0, error.url);
    return throwError(error);
  }

  openErrorDialog(error) {
    const dialogRef = this.dialog.open(ErroDialogComponent, {
      width: '250px',
      data: {erro: error}
    });
  }

  setFarol(flag: boolean) {
    this.farol = flag;
  }

  getFarol() {
    return this.farol;
  }

}
