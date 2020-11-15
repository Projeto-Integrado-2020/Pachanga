import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material';
import { Router } from '@angular/router';
import { throwError } from 'rxjs';
import { catchError, take } from 'rxjs/operators';
import { GetFestaService } from 'src/app/services/get-festa/get-festa.service';
import { GetIntegracaoService } from 'src/app/services/get-integracao/get-integracao.service';
import { LogService } from 'src/app/services/logging/log.service';
import { SymplaApiService } from 'src/app/services/sympla-api/sympla-api.service';
import { ErroDialogComponent } from '../erro-dialog/erro-dialog.component';
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

  form: any;
  constructor(public getFestaService: GetFestaService, public router: Router,
              public formBuilder: FormBuilder, public getIntegracoes: GetIntegracaoService,
              public symplaApi: SymplaApiService, public dialog: MatDialog,
              public logService: LogService) { }

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
      this.getFestaService.setFarol(false);
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
              return this.handleErrorScanner(error, this.logService);
            })
          )
          .subscribe(resp => {
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
        // checkIn Pachanga
      }
    }
  }

  executarCheckInForm(codigoIngresso) {
    if (this.tipoIngressoForm === 'S') {
      if (this.integracaoSympla) {
        this.symplaApi.checkInIngresso(this.integracaoSympla.codEvent, codigoIngresso, this.integracaoSympla.token)
        .pipe(
          take(1),
          catchError(error => {
            return this.handleErrorForm(error, this.logService);
          })
        )
        .subscribe(resp => {
          this.openSuccessDialog('SYMCHECK');
        });
      } else {
        this.openErrorDialog('SYMPNFOU');
      }
    } else {
      // checkIn Pachanga
    }
  }

  limparScanner() {
    this.scannerSucesso = false;
    this.scannerErro = false;
    this.scannerLoading = false;
  }

  alterarTipoIngresso(scanner) {
    if (scanner) {
      this.tipoIngressoScanner = this.tipoIngressoScanner !== 'P' ? 'P' : 'S';
    } else {
      this.tipoIngressoForm = this.tipoIngressoForm !== 'P' ? 'P' : 'S';
    }
  }

  openErrorDialog(error) {
    this.dialog.open(ErroDialogComponent, {
      width: '250px',
      data: {erro: error}
    });
  }

  openSuccessDialog(error) {
    this.dialog.open(SuccessDialogComponent, {
      width: '250px',
      data: {erro: error}
    });
  }

  handleErrorScanner = (error: HttpErrorResponse, logService: LogService) => {
    this.scannerLoading = false;
    this.scannerErro = true;
    this.openErrorDialog(error.error);
    logService.initialize();
    logService.logHttpInfo(JSON.stringify(error), 0, error.url);
    return throwError(error);
  }

  handleErrorForm = (error: HttpErrorResponse, logService: LogService) => {
    this.openErrorDialog(error.error);
    logService.initialize();
    logService.logHttpInfo(JSON.stringify(error), 0, error.url);
    return throwError(error);
  }

}
