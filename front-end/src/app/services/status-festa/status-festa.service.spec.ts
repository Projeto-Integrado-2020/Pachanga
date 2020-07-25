import { TestBed } from '@angular/core/testing';
import { CustomMaterialModule } from '../../views/material/material.module';
import { HttpClientModule } from '@angular/common/http';
import { StatusFestaService } from './status-festa.service';

describe('StatusFestaService', () => {
  beforeEach(() => TestBed.configureTestingModule({
    imports: [
      HttpClientModule,
      CustomMaterialModule
    ]
  }));

  it('should be created', () => {
    const service: StatusFestaService = TestBed.get(StatusFestaService);
    expect(service).toBeTruthy();
  });
});
