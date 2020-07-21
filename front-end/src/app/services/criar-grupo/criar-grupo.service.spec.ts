import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { CriarGrupoService } from './criar-grupo.service';
import { CustomMaterialModule } from '../../views/material/material.module';
import { MatDialog, MatDialogRef } from '@angular/material';

describe('CriarGrupoService', () => {
  let dialogSpy: MatDialog;

  beforeEach(() => {
    dialogSpy = jasmine.createSpyObj('MatDialog', ['open']);

    TestBed.configureTestingModule({
      imports: [
        HttpClientTestingModule,
        CustomMaterialModule
      ],
      providers: [
        { provide: MatDialog, useValue: dialogSpy },
        { provide: MatDialogRef, useValue: {} }
      ]
    });
  });

  it('should be created', () => {
    const service: CriarGrupoService = TestBed.get(CriarGrupoService);
    expect(service).toBeTruthy();
  });

  it('should set farol', () => {
    const service: CriarGrupoService = TestBed.get(CriarGrupoService);
    service.setFarol(true);
    expect(service.getFarol()).toBeTruthy();
    service.setFarol(false);
    expect(service.getFarol()).toBeFalsy();
  });

  it('should get farol', () => {
    const service: CriarGrupoService = TestBed.get(CriarGrupoService);
    expect(service.getFarol()).toBeFalsy();
    service.setFarol(true);
    expect(service.getFarol()).toBeTruthy();
  });

  it('should open a dialog through a method', () => {
    const service: CriarGrupoService = TestBed.get(CriarGrupoService);
    service.openErrorDialog('teste');
    expect(dialogSpy.open).toHaveBeenCalled();
  });
});
