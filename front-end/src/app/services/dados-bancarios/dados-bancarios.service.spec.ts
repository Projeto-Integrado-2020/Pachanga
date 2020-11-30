/* tslint:disable:no-unused-variable */

import { HttpClientModule } from '@angular/common/http';
import { TestBed, inject } from '@angular/core/testing';
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

    const token = {
      timeToken: '2020-09-21T01:14:04.028+0000',
      token: 'teste'
    };
    localStorage.setItem('token', JSON.stringify(token));
  });

  it('should ...', inject([DadosBancariosService], (service: DadosBancariosService) => {
    expect(service).toBeTruthy();
  }));
});
