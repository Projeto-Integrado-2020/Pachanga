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
      case 'FESTNFOU': this.erro = this.translate.instant('ERRO.FESTNFOU'); break;
      case 'USERSPER': this.erro = this.translate.instant('ERRO.USERSPER'); break;
      case 'DATEINFE': this.erro = this.translate.instant('ERRO.DATEINFE'); break;
      case 'FESTNEND': this.erro = this.translate.instant('ERRO.FESTNEND'); break;
      case 'FESTNOME': this.erro = this.translate.instant('ERRO.FESTNOME'); break;
      case 'STATERRA': this.erro = this.translate.instant('ERRO.STATERRA'); break;
      case 'STANMUDA': this.erro = this.translate.instant('ERRO.STANMUDA'); break;
      case 'FSTANINI': this.erro = this.translate.instant('ERRO.FSTANINI'); break;
      case 'FESTINIC': this.erro = this.translate.instant('ERRO.FESTINIC'); break;
      case 'FESTSCAT': this.erro = this.translate.instant('ERRO.FESTSCAT'); break;
      case 'CATNFOUN': this.erro = this.translate.instant('ERRO.CATNFOUN'); break;
      case 'FESTMCAT': this.erro = this.translate.instant('ERRO.FESTMCAT'); break;
      case 'GRPOORGN': this.erro = this.translate.instant('ERRO.GRPOORGN'); break;
      case 'PERMINVA': this.erro = this.translate.instant('ERRO.PERMINVA'); break;
      case 'GRUPNFOU': this.erro = this.translate.instant('ERRO.GRUPNFOU'); break;
      case 'USESPERM': this.erro = this.translate.instant('ERRO.USESPERM'); break;
      case 'PERMNFOU': this.erro = this.translate.instant('ERRO.PERMNFOU'); break;
      case 'USERORGN': this.erro = this.translate.instant('ERRO.USERORGN'); break;
      case 'GRPONVAZ': this.erro = this.translate.instant('ERRO.GRPONVAZ'); break;
      case 'PERMDUPL': this.erro = this.translate.instant('ERRO.PERMDUPL'); break;
      case 'GRPEXIST': this.erro = this.translate.instant('ERRO.GRPEXIST'); break;
      case 'EDITORGN': this.erro = this.translate.instant('ERRO.EDITORGN'); break;
      case 'PRODUUSO': this.erro = this.translate.instant('ERRO.PRODUUSO'); break;
      case 'PRODNFOU': this.erro = this.translate.instant('ERRO.PRODNFOU'); break;
      case 'PRODCADA': this.erro = this.translate.instant('ERRO.PRODCADA'); break;
      case 'PRODNEST': this.erro = this.translate.instant('ERRO.PRODNEST'); break;
      case 'QATMESTO': this.erro = this.translate.instant('ERRO.QATMESTO'); break;
      case 'QATMMAXI': this.erro = this.translate.instant('ERRO.QATMMAXI'); break;
      case 'PROMIGUA': this.erro = this.translate.instant('ERRO.PROMIGUA'); break;
      case 'QATUAINV': this.erro = this.translate.instant('ERRO.QATUAINV'); break;
      case 'OPERAINV': this.erro = this.translate.instant('ERRO.OPERAINV'); break;
      case 'ESTONFOU': this.erro = this.translate.instant('ERRO.ESTONFOU'); break;
      case 'ESTONOME': this.erro = this.translate.instant('ERRO.ESTONOME'); break;
      case 'PCMININV': this.erro = this.translate.instant('ERRO.PCMININV'); break;
      case 'ESTNPROD': this.erro = this.translate.instant('ERRO.ESTNPROD'); break;
      case 'PRODEUSO': this.erro = this.translate.instant('ERRO.PRODEUSO'); break;
      case 'CONVNGRU': this.erro = this.translate.instant('ERRO.CONVNGRU'); break;
      case 'FESTNPRE': this.erro = this.translate.instant('ERRO.FESTNPRE'); break;
      case 'ESTOMNOM': this.erro = this.translate.instant('ERRO.ESTOMNOM'); break;
      case 'QMAXINV': this.erro = this.translate.instant('ERRO.QMAXINV'); break;
      default: this.erro = this.translate.instant('ERRO.SERVERRO');
    }
  }

}
