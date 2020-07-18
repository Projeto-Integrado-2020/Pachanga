import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { DeletarFestaService } from './deletar-festa.service';
import { CustomMaterialModule } from '../../views/material/material.module';

describe('DeletarFestaService', () => {
  beforeEach(() => TestBed.configureTestingModule({
    imports: [
      HttpClientTestingModule,
      CustomMaterialModule
    ]
  }));

  it('should be created', () => {
    const service: DeletarFestaService = TestBed.get(DeletarFestaService);
    expect(service).toBeTruthy();
  });
});
