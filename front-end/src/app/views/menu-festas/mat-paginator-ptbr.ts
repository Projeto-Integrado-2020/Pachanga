import {MatPaginatorIntl} from '@angular/material';
import { TranslateService } from '@ngx-translate/core';
import { Inject } from '@angular/core';


export class MatPaginatorPtBr extends MatPaginatorIntl {

  itemsPerPageLabel = 'Itens por página';
  nextPageLabel     = 'Próxima página';
  previousPageLabel = 'Página anterior';

  /*
  const ipp: string;
  const np: string;
  const pp: string;

  this.translate.get('PAGINATOR.ITEMSPPAGE').subscribe((translated: string) => {
    ipp = translated;
  });

  constructor(@Inject(TranslateService) private translate: TranslateService) { 
    super();
  }
*/
  getRangeLabel = function (page, pageSize, length) {
    if (length === 0 || pageSize === 0) {
      return '0 de ' + length;
    }
    length = Math.max(length, 0);
    const startIndex = page * pageSize;
    // If the start index exceeds the list length, do not try and fix the end index to the end.
    const endIndex = startIndex < length ?
      Math.min(startIndex + pageSize, length) :
      startIndex + pageSize;
    return startIndex + 1 + ' - ' + endIndex + ' de ' + length;
  };

}