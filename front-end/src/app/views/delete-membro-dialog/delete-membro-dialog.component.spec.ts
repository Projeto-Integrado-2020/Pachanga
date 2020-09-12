import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DeleteMembroDialogComponent } from './delete-membro-dialog.component';
import { MatDialog, MAT_DIALOG_DATA } from '@angular/material';
import { CustomMaterialModule } from '../material/material.module';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { TranslateModule, TranslateLoader } from '@ngx-translate/core';
import { HttpLoaderFactory } from 'src/app/app.module';
import { HttpClient } from '@angular/common/http';
import { RouterModule } from '@angular/router';
import { DeletarMembroGrupo } from 'src/app/services/deletar-membro-grupo/deletar-membro-grupo.service';
import { of } from 'rxjs';

describe('DeleteMembroDialogComponent', () => {
  let component: DeleteMembroDialogComponent;
  let fixture: ComponentFixture<DeleteMembroDialogComponent>;
  let dialogSpy: MatDialog;

  beforeEach(async(() => {
    dialogSpy = jasmine.createSpyObj('MatDialog', ['open', 'closeAll']);

    TestBed.configureTestingModule({
      declarations: [
        DeleteMembroDialogComponent,
      ],
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
        { provide: MAT_DIALOG_DATA, useValue: {grupo: {codFesta: '1', codUsuario: '1', codGrupo: '1'}} },
        { provide: MatDialog, useValue: dialogSpy },
        {provide: DeletarMembroGrupo, useValue: {
          deletarMembroColaborador: () => of({}),
          setFarol: () => false,
        }}
      ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DeleteMembroDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should deletarProduto', () => {
    spyOn(component.membroExcluirService, 'deletarMembroColaborador')
    .and
    .callThrough();

    spyOn(component.membroExcluirService, 'setFarol')
    .and
    .callThrough();

    component.component = {
      ngOnInit: () => true
    };
    spyOn(component.component, 'ngOnInit')
    .and
    .callThrough();

    component.codUsuario = 'teste';
    component.codGrupo = 'teste';
    component.deleteMembroGrupo();
    expect(component.membroExcluirService.deletarMembroColaborador).toHaveBeenCalledWith('teste', 'teste');
    expect(component.membroExcluirService.setFarol).toHaveBeenCalledWith(false);
    expect(dialogSpy.closeAll).toHaveBeenCalled();
    expect(component.component.ngOnInit).toHaveBeenCalled();
  });
});
