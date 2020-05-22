import { Component, OnInit } from '@angular/core';
import {TranslateService} from '@ngx-translate/core';

@Component({
  selector: 'app-erro-dialog',
  templateUrl: './erro-dialog.component.html',
  styleUrls: ['./erro-dialog.component.scss']
})
export class ErroDialogComponent implements OnInit {

  constructor(private translate: TranslateService) { }

  public erro: string;

  ngOnInit() {
    if (this.erro === '1') {
      this.erro = this.translate.instant('ERRO.ERRO1');
    } else if (this.erro === '2') {
      this.erro = this.translate.instant('ERRO.ERRO2');
    } else {
      this.erro = this.translate.instant('ERRO.ERRO3');
    }
  }

}
