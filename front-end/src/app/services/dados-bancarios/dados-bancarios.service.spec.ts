/* tslint:disable:no-unused-variable */

import { HttpClientModule } from '@angular/common/http';
import { TestBed, async, inject } from '@angular/core/testing';
import { MatDialog } from '@angular/material';
import { DadosBancariosService } from './dados-bancarios.service';

describe('Service: DadosBancarios', () => {
  let dialogSpy: MatDialog;

  beforeEach(() => {
    dialogSpy = jasmine.createSpyObj('MatDialog', ['open', 'closeAll']);
    TestBed.configureTestingModule({

      imports: [
        HttpClientModule
      ],
      providers: [
        DadosBancariosService,
        { provide: MatDialog, useValue: dialogSpy },
      ]
    });
  });

  it('should ...', inject([DadosBancariosService], (service: DadosBancariosService) => {
    expect(service).toBeTruthy();
  }));
});
