import { HttpClientModule } from '@angular/common/http';
import { TestBed } from '@angular/core/testing';
import { MatDialog, MatDialogRef } from '@angular/material';
import { RouterModule } from '@angular/router';

import { RelatorioEstoqueService } from './relatorio-estoque.service';

describe('RelatorioEstoqueService', () => {
  beforeEach(() => TestBed.configureTestingModule({
    imports: [
      HttpClientModule,
      RouterModule.forRoot([])
  ],
    providers: [
      { provide: MatDialog, useValue: MatDialog },
      { provide: MatDialogRef, useValue: {} }
    ]
}));

  it('should be created', () => {
    const service: RelatorioEstoqueService = TestBed.get(RelatorioEstoqueService);
    expect(service).toBeTruthy();
  });
});
