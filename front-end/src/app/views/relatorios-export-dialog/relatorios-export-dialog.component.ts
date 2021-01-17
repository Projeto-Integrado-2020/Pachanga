import { Component, Inject, OnInit } from '@angular/core';
import { COMMA, ENTER } from '@angular/cdk/keycodes';
import { MatChipInputEvent } from '@angular/material/chips';
import { MAT_DIALOG_DATA, MatDialog } from '@angular/material';
import { SuccessDialogComponent } from '../success-dialog/success-dialog.component';
import { RelatorioExportService } from 'src/app/services/relatorios/relatorio-export.service';
import { ProcessingDialogComponent } from '../processing-dialog/processing-dialog.component';
import { Router } from '@angular/router';

export interface Email {
  address: string;
  valid: boolean;
}

@Component({
  selector: 'app-relatorios-export-dialog',
  templateUrl: './relatorios-export-dialog.component.html',
  styleUrls: ['./relatorios-export-dialog.component.scss']
})
export class RelatoriosExportDialogComponent implements OnInit {

  visible = true;
  selectable = true;
  removable = true;
  addOnBlur = true;
  codFesta: string;
  pdf: string;

  readonly separatorKeysCodes: number[] = [ENTER, COMMA];
  maillist: Email[] = [];
  errors = 0;

  constructor(public exportService: RelatorioExportService, @Inject(MAT_DIALOG_DATA) data,
              public modal: MatDialog) {
    this.codFesta = data.codFesta;
    this.pdf = data.pdf;
  }

  ngOnInit() {
  }

  add(event: MatChipInputEvent): void {
    const input = event.input;
    const value = event.value;

    // adicionar email
    if ((value || '').trim()) {
      const mails = value.split(/[,;]+/);
      for (const v of mails) {
        const validation = this.validate(v);
        this.maillist.push({address: v.trim(), valid: validation});
        if (!validation) {
          this.errors += 1;
          console.log(this.errors);
        }
      }
    }

    // resetar o valir do input
    if (input) {
      input.value = '';
    }
  }

  remove(email: Email): void {
    const index = this.maillist.indexOf(email);

    if (index >= 0) {
      this.maillist.splice(index, 1);
    }
    if (!email.valid) {
      this.errors -= 1;
    }
  }

  validate(email) {
    /* tslint:disable */
    const expression = /^(([^<>()\[\]\\.,;:\s@+"]+(\.[^<>()\[\]\\.,;:\s@+"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    /* tslint:enable */
    return expression.test(String(email).toLowerCase());
  }

  exportarPDF() {
    this.openDialogProcessing();
    const emails = [];
    for (const email of this.maillist) {
      emails.push(email.address);
    }
    this.exportService.exportarRelatorio(this.codFesta, this.pdf, emails).subscribe((resp: any) => {
      this.exportService.setFarol(false);
      this.modal.closeAll();
      this.openDialogSuccess('EXPORELA');
    });
  }

  openDialogProcessing() {
    this.modal.open(ProcessingDialogComponent, {
        width: '20rem',
        disableClose: true,
        data: {
            tipo: 'EXPORTEMAIL'
        }
    });
  }

  openDialogSuccess(message: string) {
    this.modal.open(SuccessDialogComponent, {
      width: '20rem',
      data: {message}
    });
  }
}
