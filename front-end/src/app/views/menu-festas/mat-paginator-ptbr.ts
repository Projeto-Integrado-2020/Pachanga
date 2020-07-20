import {MatPaginatorIntl} from '@angular/material';
import { TranslateService } from '@ngx-translate/core';
import { Inject } from '@angular/core';


export class MatPaginatorPtBr extends MatPaginatorIntl {

  itemsPerPageLabel = 'Itens por página';
  nextPageLabel     = 'Próxima página';
  previousPageLabel = 'Página anterior';
/*
  constructor(@Inject(TranslateService) private readonly translate: TranslateService) {
    super();
  }

  getPaginatorIntl(): MatPaginatorIntl {
    // tslint:disable-next-line: new-parens
    const paginatorIntl = new MatPaginatorIntl;
    paginatorIntl.itemsPerPageLabel = this.translate.instant('PAGINATOR.ITENSPPAGE');
    paginatorIntl.nextPageLabel = this.translate.instant('PAGINATOR.NEXTPAGE');
    paginatorIntl.previousPageLabel = this.translate.instant('PAGINATOR.PREVIOUSPAGE');
    paginatorIntl.getRangeLabel = this.getRangeLabel.bind(this);

    return paginatorIntl;

  }
  */

  getRangeLabel = (page, pageSize, length) => {
    if (length === 0 || pageSize === 0) {
      return '0 de ' /*this.translate.instant('PAGINATOR.OF')*/ + length;
    }
    length = Math.max(length, 0);
    const startIndex = page * pageSize;
    // If the start index exceeds the list length, do not try and fix the end index to the end.
    const endIndex = startIndex < length ?
      Math.min(startIndex + pageSize, length) :
      startIndex + pageSize;
    return startIndex + 1 + ' - ' + endIndex + ' de '/* this.translate.instant('PAGINATOR.OF') */ + length;
  }

}
