/* tslint:disable:no-unused-variable */

import { TestBed, async, inject } from '@angular/core/testing';
import { ConvidadoService } from './convidado.service';
import { HttpClientModule } from '@angular/common/http';

describe('Service: Convidado', () => {
  let service: ConvidadoService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        HttpClientModule,
      ],
      providers: [ConvidadoService]
    });

    service = TestBed.get(ConvidadoService);
  });

  it('should ...', () => {
    expect(service).toBeTruthy();
  });

  it('should post Info at aceitarConvite', () => {
    expect(service.aceitarConvite('teste', 'teste')).toBeTruthy();
  });

  it('should post Info at recusarConvite', () => {
    expect(service.recusarConvite('teste', 'teste')).toBeTruthy();
  });

  it('should get Info at getDetalhesFesta', () => {
    expect(service.getDetalhesFesta('teste', 'teste')).toBeTruthy();
  });
});
