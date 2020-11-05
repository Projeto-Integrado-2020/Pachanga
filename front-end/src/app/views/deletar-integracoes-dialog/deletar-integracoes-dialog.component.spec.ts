import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { MatDialog, MAT_DIALOG_DATA } from '@angular/material';
import { CustomMaterialModule } from '../material/material.module';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { TranslateModule, TranslateLoader } from '@ngx-translate/core';
import { HttpLoaderFactory } from 'src/app/app.module';
import { HttpClient } from '@angular/common/http';
import { RouterModule } from '@angular/router';

import { of } from 'rxjs';
import { DeletarIntegracoesDialogComponent } from './deletar-integracoes-dialog.component';
import { DeletarIntegracaoService } from 'src/app/services/deletar-integracao/deletar-integracao.service';

describe('DeletarIntegracoesDialogComponent', () => {
  let component: DeletarIntegracoesDialogComponent;
  let fixture: ComponentFixture<DeletarIntegracoesDialogComponent>;
  let dialogSpy: MatDialog;

  beforeEach(async(() => {
    dialogSpy = jasmine.createSpyObj('MatDialog', ['open', 'closeAll']);
    TestBed.configureTestingModule({
      declarations: [ DeletarIntegracoesDialogComponent ],
      imports: [
        CustomMaterialModule,
        HttpClientTestingModule,
        BrowserAnimationsModule,
        TranslateModule.forRoot({
          loader: {
            provide: TranslateLoader,
            useFactory: HttpLoaderFactory,
            deps: [HttpClient]
          }
        }),
        RouterModule.forRoot([])
      ],
      providers: [
        { provide: MAT_DIALOG_DATA, useValue: {integracao: {codInfo: 'teste'},
          codFesta: 'teste'}
        },
        { provide: MatDialog, useValue: dialogSpy },
        {provide: DeletarIntegracaoService, useValue: {
          deleteIntegracao: () => of({}),
          setFarol: () => false,
        }}
      ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DeletarIntegracoesDialogComponent);
    component = fixture.componentInstance;
    component.integracao = {codInfo: 'teste'};
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should open a dialog through a method', () => {
    component.openDialogSuccess('teste');
    expect(dialogSpy.open).toHaveBeenCalled();
  });

  it('should deletarIntegracao', () => {
    spyOn(component.deleteService, 'deleteIntegracao')
    .and
    .callThrough();

    spyOn(component, 'openDialogSuccess')
    .and
    .callThrough();

    component.component = {
      ngOnInit: () => true
    };
    spyOn(component.component, 'ngOnInit')
    .and
    .callThrough();

    component.integracao = {codInfo: 'teste'};
    component.deletarIntegracao();
    expect(component.deleteService.deleteIntegracao).toHaveBeenCalledWith('teste');
    expect(dialogSpy.closeAll).toHaveBeenCalled();
    expect(component.component.ngOnInit).toHaveBeenCalled();
    expect(component.openDialogSuccess).toHaveBeenCalledWith('INTDELE');
  });
});
