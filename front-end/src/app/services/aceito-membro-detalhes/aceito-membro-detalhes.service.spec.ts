import { TestBed } from '@angular/core/testing';
import { HttpClientModule } from '@angular/common/http';
import { CustomMaterialModule } from '../../views/material/material.module';
import { MatDialog, MatDialogRef } from '@angular/material';
import { AceitoMembroDetalhesService } from './aceito-membro-detalhes.service';

describe('AceitoMembroDetalhesService', () => {
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
    })
  });

  it('should be created', () => {
    const service: AceitoMembroDetalhesService = TestBed.get(AceitoMembroDetalhesService);
    expect(service).toBeTruthy();
  });

  it('should set farol', () => {
    const service: AceitoMembroDetalhesService = TestBed.get(AceitoMembroDetalhesService);
    service.setFarol(true);
    expect(service.getFarol()).toBeTruthy();
    service.setFarol(false);
    expect(service.getFarol()).toBeFalsy();
  });

  it('should get farol', () => {
    const service: AceitoMembroDetalhesService = TestBed.get(AceitoMembroDetalhesService);
    expect(service.getFarol()).toBeFalsy();
    service.setFarol(true);
    expect(service.getFarol()).toBeTruthy();
  });

  it('should open a dialog through a method', () => {
    const service: AceitoMembroDetalhesService = TestBed.get(AceitoMembroDetalhesService);
    service.openErrorDialog('teste');
    expect(dialogSpy.open).toHaveBeenCalled();
  });
});
