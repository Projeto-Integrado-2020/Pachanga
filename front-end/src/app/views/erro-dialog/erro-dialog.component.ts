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
    if (this.translate.instant('ERRO.' + this.erro).includes(this.erro)) {
      this.erro = 'SERVERRO';
    }
  }

}
