import { HttpClientModule } from '@angular/common/http';
import { TestBed } from '@angular/core/testing';
import { MatDialog, MatDialogRef } from '@angular/material';
import { RouterModule } from '@angular/router';

import { RelatorioAreaSegService } from './relatorio-area-seg.service';

describe('RelatorioAreaSegService', () => {
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
    const service: RelatorioAreaSegService = TestBed.get(RelatorioAreaSegService);
    expect(service).toBeTruthy();
  });
});
