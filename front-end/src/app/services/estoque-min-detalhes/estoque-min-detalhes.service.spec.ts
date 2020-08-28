import { TestBed } from '@angular/core/testing';
import { HttpClientModule } from '@angular/common/http';
import { CustomMaterialModule } from '../../views/material/material.module';
import { MatDialog, MatDialogRef } from '@angular/material';
import { EstoqueMinDetalhesService } from './estoque-min-detalhes.service';

describe('EstoqueMinDetalhesService', () => {
  let dialogSpy: MatDialog;

  beforeEach(() => {
    dialogSpy = jasmine.createSpyObj('MatDialog', ['open']);
    TestBed.configureTestingModule({
      imports: [
        HttpClientModule,
        CustomMaterialModule
      ],
      providers: [
        { provide: MatDialog, useValue: dialogSpy },
        { provide: MatDialogRef, useValue: {} }
      ]
    });
  });

  it('should be created', () => {
    const service: EstoqueMinDetalhesService = TestBed.get(EstoqueMinDetalhesService);
    expect(service).toBeTruthy();
  });

  it('should set farol', () => {
    const service: EstoqueMinDetalhesService = TestBed.get(EstoqueMinDetalhesService);
    service.setFarol(true);
    expect(service.getFarol()).toBeTruthy();
    service.setFarol(false);
    expect(service.getFarol()).toBeFalsy();
  });

  it('should get farol', () => {
    const service: EstoqueMinDetalhesService = TestBed.get(EstoqueMinDetalhesService);
    expect(service.getFarol()).toBeFalsy();
    service.setFarol(true);
    expect(service.getFarol()).toBeTruthy();
  });

  it('should open a dialog through a method', () => {
    const service: EstoqueMinDetalhesService = TestBed.get(EstoqueMinDetalhesService);
    service.openErrorDialog('teste');
    expect(dialogSpy.open).toHaveBeenCalled();
  });
});
