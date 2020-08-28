import { TestBed } from '@angular/core/testing';

import { RecargaProdutoEstoqueService } from './recarga-produto-estoque.service';
import { HttpClientModule } from '@angular/common/http';
import { CustomMaterialModule } from 'src/app/views/material/material.module';
import { MatDialog, MatDialogRef } from '@angular/material';

describe('RecargaProdutoEstoqueService', () => {
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
    const service: RecargaProdutoEstoqueService = TestBed.get(RecargaProdutoEstoqueService);
    expect(service).toBeTruthy();
  });

  it('should be created', () => {
    const service: RecargaProdutoEstoqueService = TestBed.get(RecargaProdutoEstoqueService);
    expect(service).toBeTruthy();
  });

  it('should set farol', () => {
    const service: RecargaProdutoEstoqueService = TestBed.get(RecargaProdutoEstoqueService);
    service.setFarol(true);
    expect(service.getFarol()).toBeTruthy();
    service.setFarol(false);
    expect(service.getFarol()).toBeFalsy();
  });

  it('should get farol', () => {
    const service: RecargaProdutoEstoqueService = TestBed.get(RecargaProdutoEstoqueService);
    expect(service.getFarol()).toBeFalsy();
    service.setFarol(true);
    expect(service.getFarol()).toBeTruthy();
  });

  it('should open a dialog through a method', () => {
    const service: RecargaProdutoEstoqueService = TestBed.get(RecargaProdutoEstoqueService);
    service.openErrorDialog('teste');
    expect(dialogSpy.open).toHaveBeenCalled();
  });
});
