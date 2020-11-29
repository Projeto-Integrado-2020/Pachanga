/* tslint:disable:no-unused-variable */

import { TestBed, async, inject } from '@angular/core/testing';
import { DadosBancariosService } from './dados-bancarios.service';

describe('Service: DadosBancarios', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [DadosBancariosService]
    });
  });

  it('should ...', inject([DadosBancariosService], (service: DadosBancariosService) => {
    expect(service).toBeTruthy();
  }));
});
