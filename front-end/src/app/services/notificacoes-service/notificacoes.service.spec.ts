/* tslint:disable:no-unused-variable */

import { TestBed, async, inject } from '@angular/core/testing';
import { NotificacoesService } from './notificacoes.service';
import { HttpClientModule } from '@angular/common/http';
import { MatDialog } from '@angular/material';

describe('Service: Notificacoes', () => {
  beforeEach(() => {
    let dialogSpy = jasmine.createSpyObj('MatDialog', ['open']);
    TestBed.configureTestingModule({

      imports: [
        HttpClientModule,
      ],
      providers: [
        NotificacoesService,
        {provide: MatDialog, useValue: dialogSpy}
      ]
    });
  });

  it('should ...', inject([NotificacoesService], (service: NotificacoesService) => {
    expect(service).toBeTruthy();
  }));
});
