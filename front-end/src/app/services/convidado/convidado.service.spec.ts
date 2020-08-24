/* tslint:disable:no-unused-variable */

import { TestBed, async, inject } from '@angular/core/testing';
import { ConvidadoService } from './convidado.service';
import { HttpClientModule } from '@angular/common/http';

describe('Service: Convidado', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        HttpClientModule,
      ],
      providers: [ConvidadoService]
    });
  });

  it('should ...', inject([ConvidadoService], (service: ConvidadoService) => {
    expect(service).toBeTruthy();
  }));
});
