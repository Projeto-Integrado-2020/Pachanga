import { Component, OnInit, Inject } from '@angular/core';
import {TranslateService} from '@ngx-translate/core';
import {MAT_DIALOG_DATA} from '@angular/material';

@Component({
  selector: 'app-erro-dialog',
  templateUrl: './erro-dialog.component.html',
  styleUrls: ['./erro-dialog.component.scss']
})
export class ErroDialogComponent implements OnInit {

  public erro: string;

  constructor(
      private translate: TranslateService,
      @Inject(MAT_DIALOG_DATA) data) {
      this.erro = data.erro;
    }

  ngOnInit() {
    if (this.erro.toString() === '1') {
      this.erro = this.translate.instant('ERRO.ERRO1');
    } else if (this.erro.toString() === '2') {
      this.erro = this.translate.instant('ERRO.ERRO2');
    } else {
      this.erro = this.translate.instant('ERRO.ERRO3');
    }
  }

}
