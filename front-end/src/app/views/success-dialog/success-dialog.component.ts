import { Component, OnInit, Inject } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';
import { MAT_DIALOG_DATA } from '@angular/material';

@Component({
  selector: 'app-success-dialog',
  templateUrl: './success-dialog.component.html',
  styleUrls: ['./success-dialog.component.scss']
})
export class SuccessDialogComponent implements OnInit {

  public message: string;

  constructor(@Inject(MAT_DIALOG_DATA) data, private translate: TranslateService) {
    this.message = data.message;
  }

  ngOnInit() {
    switch (this.message) {
      case 'ALTERSUC': this.message = this.translate.instant('SUCCESS.ALTERSUC'); break;
      case 'FESTDELE': this.message = this.translate.instant('SUCCESS.FESTDELE'); break;
      case 'FESTAALT': this.message = this.translate.instant('SUCCESS.FESTAALT'); break;
      case 'MEMBROAD': this.message = this.translate.instant('SUCCESS.MEMBROAD'); break;
      case 'GRUPDELE': this.message = this.translate.instant('SUCCESS.GRUPDELE'); break;
      case 'GRUPOALT': this.message = this.translate.instant('SUCCESS.GRUPOALT'); break;
    }
  }

}
