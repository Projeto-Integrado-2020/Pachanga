import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material';
import { Router } from '@angular/router';
import { throwError } from 'rxjs';
import { catchError, take } from 'rxjs/operators';
import { CheckInService } from 'src/app/services/check-in/check-in.service';
import { GetFestaService } from 'src/app/services/get-festa/get-festa.service';
import { GetIntegracaoService } from 'src/app/services/get-integracao/get-integracao.service';
import { LogService } from 'src/app/services/logging/log.service';
import { SymplaApiService } from 'src/app/services/sympla-api/sympla-api.service';
import { ErroDialogComponent } from '../erro-dialog/erro-dialog.component';
import { ProcessingDialogComponent } from '../processing-dialog/processing-dialog.component';
import { SuccessDialogComponent } from '../success-dialog/success-dialog.component';

@Component({
  selector: 'app-leitor-qr',
  templateUrl: './leitor-qr.component.html',
  styleUrls: ['./leitor-qr.component.scss']
})
export class LeitorQrComponent implements OnInit {

  festa: any;
  festaNome: string;
  scannerLoading = false;
  scannerSucesso = false;
  scannerErro = false;
  tipoIngressoScanner = 'P';
  tipoIngressoForm = 'P';
  integracaoSympla: any;
  hasDevices = true;
  hasPermission: boolean;

  form: any;
  constructor(public getFestaService: GetFestaService, public router: Router,
              public formBuilder: FormBuilder, public getIntegracoes: GetIntegracaoService,
              public symplaApi: SymplaApiService, public dialog: MatDialog,
              public logService: LogService, public pachangaCheckIn: CheckInService) { }

  get f() { return this.form.controls; }

  ngOnInit() {
    this.resgatarFesta();
    this.resgatarIntegracaoSympla();

    this.form = this.formBuilder.group({
      codIngresso: new FormControl('', Validators.required)
    });
  }

  resgatarIntegracaoSympla() {
    let idFesta = this.router.url;
    idFesta = idFesta.substring(idFesta.indexOf('&') + 1, idFesta.indexOf('/', idFesta.indexOf('&')));
    this.getIntegracoes.getIntegracoes(idFesta).subscribe((resp: any) => {
      this.getIntegracoes.setFarol(false);
      for (const integracao of resp) {
        if (integracao.terceiroInt === 'S') {
          this.integracaoSympla = integracao;
        }
      }
    });
  }

  resgatarFesta() {
    let idFesta = this.router.url;
    idFesta = idFesta.substring(idFesta.indexOf('&') + 1, idFesta.indexOf('/', idFesta.indexOf('&')));
    this.getFestaService.acessarFesta(idFesta).subscribe((resp: any) => {
      this.festa = resp;
      this.festaNome = resp.nomeFesta;
    });
  }

  executarCheckInScanner(event: any) {
    if (!this.scannerErro && !this.scannerSucesso && !this.scannerLoading) {
      this.scannerLoading = true;
      if (this.tipoIngressoScanner === 'S') {
        if (this.integracaoSympla) {
          this.symplaApi.checkInIngresso(this.integracaoSympla.codEvent, event, this.integracaoSympla.token)
          .pipe(
            take(1),
            catchError(error => {
              if (error.error.code === 112) {
                error.error = 'INGRNFOU';
              } else if (error.error.code === 113) {
                error.error = 'CHECKERR';
              }
              return this.handleErrorScanner(error, this.logService);
            })
          )
          .subscribe(resp => {
            this.symplaApi.setFarol(false);
            this.dialog.closeAll();
            this.scannerLoading = false;
            this.scannerSucesso = true;
            this.openSuccessDialog('SYMCHECK');
          });
        } else {
          this.scannerLoading = false;
          this.scannerErro = true;
          this.openErrorDialog('SYMPNFOU');
        }
      } else {
        this.pachangaCheckIn.checkInIngresso(event, this.festa.codFesta)
        .pipe(
          take(1),
          catchError(error => {
            return this.handleErrorScanner(error, this.logService);
          })
        )
        .subscribe(resp => {
          this.pachangaCheckIn.setFarol(false);
          this.dialog.closeAll();
          this.scannerLoading = false;
          this.scannerSucesso = true;
          this.openSuccessDialog('PACCHECK');
        });
      }
    }
  }

  executarCheckInForm(codigoIngresso) {
    this.openDialogProcessing();
    if (this.tipoIngressoForm === 'S') {
      if (this.integracaoSympla) {
        this.symplaApi.checkInIngresso(this.integracaoSympla.codEvent, codigoIngresso, this.integracaoSympla.token)
        .pipe(
          take(1),
          catchError(error => {
            if (error.error.code === 112) {
              error.error = 'INGRNFOU';
            } else if (error.error.code === 113) {
              error.error = 'CHECKERR';
            }
            return this.handleErrorForm(error, this.logService);
          })
        )
        .subscribe(resp => {
          this.symplaApi.setFarol(false);
          this.dialog.closeAll();
          this.openSuccessDialog('SYMCHECK');
        });
      } else {
        this.openErrorDialog('SYMPNFOU');
      }
    } else {
      this.pachangaCheckIn.checkInIngresso(codigoIngresso, this.festa.codFesta)
      .pipe(
        take(1),
        catchError(error => {
          return this.handleErrorForm(error, this.logService);
        })
      )
      .subscribe(resp => {
        this.pachangaCheckIn.setFarol(false);
        this.dialog.closeAll();
        this.openSuccessDialog('PACCHECK');
      });
    }
  }

  limparScanner() {
    this.scannerSucesso = false;
    this.scannerErro = false;
    this.scannerLoading = false;
  }

  alterarTipoIngresso(scanner, tipo) {
    if (scanner) {
      this.tipoIngressoScanner = tipo;
    } else {
      this.tipoIngressoForm = tipo;
    }
  }

  openErrorDialog(error) {
    this.dialog.open(ErroDialogComponent, {
      width: '250px',
      data: {erro: error}
    });
  }

  openSuccessDialog(message) {
    this.dialog.open(SuccessDialogComponent, {
      width: '250px',
      data: {message}
    });
  }

  openDialogProcessing() {
    this.dialog.open(ProcessingDialogComponent, {
        width: '20rem',
        disableClose: true,
        data: {
          tipo: 'CHECKIN'
        }
    });
  }

  handleErrorScanner = (error: HttpErrorResponse, logService: LogService) => {
    this.scannerLoading = false;
    this.scannerErro = true;
    this.symplaApi.setFarol(false);
    this.pachangaCheckIn.setFarol(false);
    this.dialog.closeAll();
    this.openErrorDialog(error.error);
    logService.initialize();
    logService.logHttpInfo(JSON.stringify(error), 0, error.url);
    return throwError(error);
  }

  handleErrorForm = (error: HttpErrorResponse, logService: LogService) => {
    this.symplaApi.setFarol(false);
    this.pachangaCheckIn.setFarol(false);
    this.dialog.closeAll();
    this.openErrorDialog(error.error);
    logService.initialize();
    logService.logHttpInfo(JSON.stringify(error), 0, error.url);
    return throwError(error);
  }

  onHasPermission(has: boolean) {
    this.hasPermission = has;
  }

  onCamerasNotFound(notFound: boolean): void {
    this.hasDevices = !notFound;
  }

}
