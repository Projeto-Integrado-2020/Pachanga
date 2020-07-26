import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { CustomMaterialModule } from '../../views/material/material.module';
import { GetGruposService } from './get-grupos.service';
import { MatDialog } from '@angular/material';

describe('GetGruposService', () => {
  let dialogSpy: MatDialog;

  beforeEach(() => {
    dialogSpy = jasmine.createSpyObj('MatDialog', ['open']);

    TestBed.configureTestingModule({
      imports: [
        HttpClientTestingModule,
        CustomMaterialModule
      ],
      providers: [
        { provide: MatDialog, useValue: dialogSpy }
      ]
    });
  });

  it('should be created', () => {
    const service: GetGruposService = TestBed.get(GetGruposService);
    expect(service).toBeTruthy();
  });

  it('should set farol', () => {
    const service: GetGruposService = TestBed.get(GetGruposService);
    service.setFarol(true);
    expect(service.getFarol()).toBeTruthy();
    service.setFarol(false);
    expect(service.getFarol()).toBeFalsy();
  });

  it('should get farol', () => {
    const service: GetGruposService = TestBed.get(GetGruposService);
    expect(service.getFarol()).toBeFalsy();
    service.setFarol(true);
    expect(service.getFarol()).toBeTruthy();
  });

  it('should open a dialog through a method', () => {
    const service: GetGruposService = TestBed.get(GetGruposService);
    service.openErrorDialog('teste');
    expect(dialogSpy.open).toHaveBeenCalled();
  });
});
