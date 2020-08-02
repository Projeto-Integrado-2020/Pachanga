import { TestBed } from '@angular/core/testing';
import { HttpErrorResponse, HttpClientModule } from '@angular/common/http';
import { CustomMaterialModule } from '../../views/material/material.module';
import { MatDialog, MatDialogRef } from '@angular/material';
import { EditarMembroGrupoService } from './editar-membro-grupo.service';

describe('EditarMembroGrupoService', () => {
  let dialogSpy: MatDialog;

  beforeEach(() => {
    dialogSpy = jasmine.createSpyObj('MatDialog', ['open', 'closeAll']);
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
    const service: EditarMembroGrupoService = TestBed.get(EditarMembroGrupoService);
    expect(service).toBeTruthy();
  });
});
