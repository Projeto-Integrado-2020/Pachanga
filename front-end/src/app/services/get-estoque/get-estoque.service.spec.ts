import { TestBed } from '@angular/core/testing';

import { GetEstoqueService } from './get-estoque.service';
import { HttpClientModule } from '@angular/common/http';
import { CustomMaterialModule } from '../../views/material/material.module';
import { MatDialog, MatDialogRef } from '@angular/material';
import { RouterModule } from '@angular/router';

describe('GetEstoqueService', () => {
  let dialogSpy: MatDialog;
  let service: GetEstoqueService;

  beforeEach(() => {
    dialogSpy = jasmine.createSpyObj('MatDialog', ['open', 'closeAll']);
    TestBed.configureTestingModule({
      imports: [
        HttpClientModule,
        CustomMaterialModule,
        RouterModule.forRoot([])
      ],
      providers: [
        { provide: MatDialog, useValue: dialogSpy },
        { provide: MatDialogRef, useValue: {} }
      ]
    });

    service = TestBed.get(GetEstoqueService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should get Info at getEstoque', () => {
    service.loginService.usuarioInfo = {codusuario: 'teste'};
    expect(service.getEstoque('teste')).toBeTruthy();
  });
});
