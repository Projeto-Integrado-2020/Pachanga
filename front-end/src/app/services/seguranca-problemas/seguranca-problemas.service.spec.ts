/* tslint:disable:no-unused-variable */

import { TestBed, async, inject } from '@angular/core/testing';
import { SegurancaProblemasService } from './seguranca-problemas.service';

describe('Service: SegurancaProblemas', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [SegurancaProblemasService]
    });
  });

  it('should ...', inject([SegurancaProblemasService], (service: SegurancaProblemasService) => {
    expect(service).toBeTruthy();
  }));
});
