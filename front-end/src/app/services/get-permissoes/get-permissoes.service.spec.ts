import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { GetPermissoesService } from './get-permissoes.service';
import { CustomMaterialModule } from 'src/app/views/material/material.module';
import { MatDialog, MatDialogRef } from '@angular/material';

describe('GetPermissoesService', () => {
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
    const service: GetPermissoesService = TestBed.get(GetPermissoesService);
    expect(service).toBeTruthy();
  });

  it('should set farol', () => {
    const service: GetPermissoesService = TestBed.get(GetPermissoesService);
    service.setFarol(true);
    expect(service.getFarol()).toBeTruthy();
    service.setFarol(false);
    expect(service.getFarol()).toBeFalsy();
  });

  it('should get farol', () => {
    const service: GetPermissoesService = TestBed.get(GetPermissoesService);
    expect(service.getFarol()).toBeFalsy();
    service.setFarol(true);
    expect(service.getFarol()).toBeTruthy();
  });

  it('should open a dialog through a method', () => {
    const service: GetPermissoesService = TestBed.get(GetPermissoesService);
    service.openErrorDialog('teste');
    expect(dialogSpy.open).toHaveBeenCalled();
  });
});
