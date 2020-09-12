import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MatDialog, MAT_DIALOG_DATA } from '@angular/material';
import { CustomMaterialModule } from '../material/material.module';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { TranslateModule, TranslateLoader } from '@ngx-translate/core';
import { HttpLoaderFactory } from 'src/app/app.module';
import { HttpClient } from '@angular/common/http';
import { RouterModule } from '@angular/router';
import { DeletarConvidadoDialogComponent } from './deletar-convidado-dialog.component';
import { DeletarConvidadoService } from 'src/app/services/deletar-convidado/deletar-convidado.service';
import { of } from 'rxjs';

describe('DeletarConvidadoDialogComponent', () => {
  let component: DeletarConvidadoDialogComponent;
  let fixture: ComponentFixture<DeletarConvidadoDialogComponent>;
  let dialogSpy: MatDialog;

  beforeEach(async(() => {
    dialogSpy = jasmine.createSpyObj('MatDialog', ['open', 'closeAll']);
    TestBed.configureTestingModule({
      declarations: [ DeletarConvidadoDialogComponent ],
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
        {provide: DeletarConvidadoService, useValue: {
          deletarConvidado: () => of({}),
          setFarol: () => false,
        }}
      ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DeletarConvidadoDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should deleteConvidadoGrupo', () => {
    spyOn(component.delConvidadoService, 'deletarConvidado')
    .and
    .callThrough();

    spyOn(component.delConvidadoService, 'setFarol')
    .and
    .callThrough();

    component.component = {
      ngOnInit: () => true
    };
    spyOn(component.component, 'ngOnInit')
    .and
    .callThrough();

    component.codConvidado = 'teste';
    component.codGrupo = 'teste';
    component.deleteConvidadoGrupo();
    expect(component.delConvidadoService.deletarConvidado).toHaveBeenCalledWith('teste', 'teste');
    expect(component.delConvidadoService.setFarol).toHaveBeenCalledWith(false);
    expect(dialogSpy.closeAll).toHaveBeenCalled();
    expect(component.component.ngOnInit).toHaveBeenCalled();
  });
});
