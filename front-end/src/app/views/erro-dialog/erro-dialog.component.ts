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
    switch (this.erro) {
      case 'USERMAIL': this.erro = this.translate.instant('ERRO.USERMAIL'); break;
      case 'PASSINC1': this.erro = this.translate.instant('ERRO.UORPINCO'); break;
      case 'PASSINC2': this.erro = this.translate.instant('ERRO.PASSINCO'); break;
      case 'EMALINCO': this.erro = this.translate.instant('ERRO.UORPINCO'); break;
      case 'PASSDIFF': this.erro = this.translate.instant('ERRO.PASSDIFF'); break;
      case 'USERNFOU': this.erro = this.translate.instant('ERRO.USERNFOU'); break;
      case 'NOTMODIF': this.erro = this.translate.instant('ERRO.NOTMODIF'); break;
      default: this.erro = this.translate.instant('ERRO.SERVERRO');
    }
  }

}
