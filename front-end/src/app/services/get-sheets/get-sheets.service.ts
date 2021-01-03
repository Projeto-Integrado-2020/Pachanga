import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { MatDialog } from '@angular/material';
import { throwError } from 'rxjs';
import { catchError, take } from 'rxjs/operators';
import { ErroDialogComponent } from 'src/app/views/erro-dialog/erro-dialog.component';
import { LogService } from '../logging/log.service';

@Injectable({
  providedIn: 'root'
})
export class GetSheetsService {

  constructor(private http: HttpClient, public logService: LogService, public dialog: MatDialog) { }

  getSheets(urlQuestionario) {
    return this.http.get('https://spreadsheets.google.com/feeds/list/' + urlQuestionario + '/default/public/values?alt=json').pipe(
      take(1), catchError(error => {
            return this.handleError(error, this.logService);
          })
    );
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
}
