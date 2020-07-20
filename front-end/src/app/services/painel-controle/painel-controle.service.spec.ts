import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { PainelControleService } from './painel-controle.service';
import { RouterModule } from '@angular/router';

describe('PainelControleService', () => {
  beforeEach(() => TestBed.configureTestingModule({
    imports: [
      HttpClientTestingModule,
      RouterModule.forRoot([])
    ]
  }));

  it('should be created', () => {
    const service: PainelControleService = TestBed.get(PainelControleService);
    expect(service).toBeTruthy();
  });

  it('should set farol', () => {
    const service: PainelControleService = TestBed.get(PainelControleService);
    service.setFarol(true);
    expect(service.getFarol()).toBeTruthy();
    service.setFarol(false);
    expect(service.getFarol()).toBeFalsy();
  });

  it('should get farol', () => {
    const service: PainelControleService = TestBed.get(PainelControleService);
    expect(service.getFarol()).toBeFalsy();
    service.setFarol(true);
    expect(service.getFarol()).toBeTruthy();
  });
});
