import { TestBed } from '@angular/core/testing';
import { EditarGrupoService } from './editar-grupo.service';
import { HttpClientModule } from '@angular/common/http';
import { CustomMaterialModule } from '../../views/material/material.module';
import { Router } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { MatDialog } from '@angular/material';

describe('EditarGrupoService', () => {
  let dialogSpy: MatDialog;
  const router = {
    navigate: jasmine.createSpy('navigate')
  };

  beforeEach(() => {
    dialogSpy = jasmine.createSpyObj('MatDialog', ['open']);

    TestBed.configureTestingModule({
      imports: [
        HttpClientModule,
        CustomMaterialModule,
        RouterTestingModule
      ],
      providers: [
        { provide: MatDialog, useValue: dialogSpy },
        { provide: Router, useValue: router }
      ]
    });
  });

  it('should be created', () => {
    const service: EditarGrupoService = TestBed.get(EditarGrupoService);
    expect(service).toBeTruthy();
  });

  it('should open a dialog through a method', () => {
    const service: EditarGrupoService = TestBed.get(EditarGrupoService);
    service.openErrorDialog('teste');
    expect(dialogSpy.open).toHaveBeenCalled();
  });

  it('should set farol', () => {
    const service: EditarGrupoService = TestBed.get(EditarGrupoService);
    service.setFarol(true);
    expect(service.getFarol()).toBeTruthy();
    service.setFarol(false);
    expect(service.getFarol()).toBeFalsy();
  });

  it('should get farol', () => {
    const service: EditarGrupoService = TestBed.get(EditarGrupoService);
    expect(service.getFarol()).toBeFalsy();
    service.setFarol(true);
    expect(service.getFarol()).toBeTruthy();
  });
});
