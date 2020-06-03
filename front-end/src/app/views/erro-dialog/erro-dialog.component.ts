import { Component, OnInit, Inject } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';
import { MAT_DIALOG_DATA } from '@angular/material';

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
    switch (this.erro.toString()) {
      case '1': this.erro = this.translate.instant('ERRO.USEDMAIL'); break;
      case '2': this.erro = this.translate.instant('ERRO.UORPINCO'); break;
      case '3': this.erro = this.translate.instant('ERRO.USERNCAD'); break;
      case '4': this.erro = this.translate.instant('ERRO.PASSDIFF'); break;
      case '5': this.erro = this.translate.instant('ERRO.USERNFOU'); break;
      case '6': this.erro = this.translate.instant('ERRO.PASSINCO'); break;
      case '101': this.erro = this.translate.instant('ERRO.NOMOD'); break;
      default: this.erro = this.translate.instant('ERRO.STSTRANGE');
    }
  }

}
