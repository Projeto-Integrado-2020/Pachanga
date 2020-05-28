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
    switch (this.message.toString()) {
      case '1': this.message = this.translate.instant('SUCCESS.SUCCESS1'); break;
    }
  }

}
