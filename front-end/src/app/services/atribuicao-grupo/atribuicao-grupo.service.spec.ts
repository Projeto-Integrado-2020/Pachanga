import { TestBed } from '@angular/core/testing';
import { AtribuicaoGrupoService } from './atribuicao-grupo.service';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { CustomMaterialModule } from '../../views/material/material.module';
import { MatDialog, MatDialogRef } from '@angular/material';


describe('AtribuicaoGrupoService', () => {
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
    const service: AtribuicaoGrupoService = TestBed.get(AtribuicaoGrupoService);
    expect(service).toBeTruthy();
  });

  it('should set farol', () => {
    const service: AtribuicaoGrupoService = TestBed.get(AtribuicaoGrupoService);
    service.setFarol(true);
    expect(service.getFarol()).toBeTruthy();
    service.setFarol(false);
    expect(service.getFarol()).toBeFalsy();
  });

  it('should get farol', () => {
    const service: AtribuicaoGrupoService = TestBed.get(AtribuicaoGrupoService);
    expect(service.getFarol()).toBeFalsy();
    service.setFarol(true);
    expect(service.getFarol()).toBeTruthy();
  });

  it('should open a dialog through a method', () => {
    const service: AtribuicaoGrupoService = TestBed.get(AtribuicaoGrupoService);
    service.openErrorDialog('teste');
    expect(dialogSpy.open).toHaveBeenCalled();
  });
});
