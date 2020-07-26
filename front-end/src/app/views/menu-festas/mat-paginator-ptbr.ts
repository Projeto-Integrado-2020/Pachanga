import { MatPaginatorIntl } from '@angular/material';
import { TranslateService } from '@ngx-translate/core';
import { Inject } from '@angular/core';

export class MatPaginatorPtBr extends MatPaginatorIntl {

  constructor(@Inject(TranslateService) private readonly translate: TranslateService) {
    super();
    this.getPaginatorIntl();
  }

  getPaginatorIntl() {
    this.translate.get(['PAGINATOR.ITEMSPPAGE', 'PAGINATOR.NEXTPAGE', 'PAGINATOR.PREVIOUSPAGE'])
    .subscribe(traducao => {
      this.itemsPerPageLabel = traducao['PAGINATOR.ITEMSPPAGE'];
      this.nextPageLabel = traducao['PAGINATOR.NEXTPAGE'];
      this.previousPageLabel = traducao['PAGINATOR.PREVIOUSPAGE'];
      this.changes.next();
    });
  }

  getRangeLabel = (page, pageSize, length) => {
    this.getPaginatorIntl();
    if (length === 0 || pageSize === 0) {
      return '0 ' + this.translate.instant('PAGINATOR.OF') + length;
    }
    length = Math.max(length, 0);
    const startIndex = page * pageSize;
    // If the start index exceeds the list length, do not try and fix the end index to the end.
    const endIndex = startIndex < length ?
      Math.min(startIndex + pageSize, length) :
      startIndex + pageSize;
    return startIndex + 1 + ' - ' + endIndex + ' ' + this.translate.instant('PAGINATOR.OF') + ' ' + length;
  }
}
