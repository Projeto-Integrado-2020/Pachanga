import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { PainelControleService } from './painel-controle.service';

describe('PainelControleService', () => {
  beforeEach(() => TestBed.configureTestingModule({
    imports: [
      HttpClientTestingModule
    ]
  }));

  it('should be created', () => {
    const service: PainelControleService = TestBed.get(PainelControleService);
    expect(service).toBeTruthy();
  });
});
