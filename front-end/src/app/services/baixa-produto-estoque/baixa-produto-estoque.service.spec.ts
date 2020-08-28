import { TestBed } from '@angular/core/testing';
import { BaixaProdutoEstoqueService } from './baixa-produto-estoque.service';
import { MatDialog, MatDialogRef } from '@angular/material';
import { HttpClientModule } from '@angular/common/http';
import { CustomMaterialModule } from 'src/app/views/material/material.module';

describe('BaixaProdutoEstoqueService', () => {
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
    const service: BaixaProdutoEstoqueService = TestBed.get(BaixaProdutoEstoqueService);
    expect(service).toBeTruthy();
  });

  it('should be created', () => {
    const service: BaixaProdutoEstoqueService = TestBed.get(BaixaProdutoEstoqueService);
    expect(service).toBeTruthy();
  });

  it('should set farol', () => {
    const service: BaixaProdutoEstoqueService = TestBed.get(BaixaProdutoEstoqueService);
    service.setFarol(true);
    expect(service.getFarol()).toBeTruthy();
    service.setFarol(false);
    expect(service.getFarol()).toBeFalsy();
  });

  it('should get farol', () => {
    const service: BaixaProdutoEstoqueService = TestBed.get(BaixaProdutoEstoqueService);
    expect(service.getFarol()).toBeFalsy();
    service.setFarol(true);
    expect(service.getFarol()).toBeTruthy();
  });

  it('should open a dialog through a method', () => {
    const service: BaixaProdutoEstoqueService = TestBed.get(BaixaProdutoEstoqueService);
    service.openErrorDialog('teste');
    expect(dialogSpy.open).toHaveBeenCalled();
  });
});
