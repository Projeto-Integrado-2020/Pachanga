import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DeletarGrupoComponent } from './deletar-grupo.component';
import { TranslateModule, TranslateLoader } from '@ngx-translate/core';
import { TranslateHttpLoader } from '@ngx-translate/http-loader';
import { HttpClient } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { CustomMaterialModule } from '../../views/material/material.module';
import { MAT_DIALOG_DATA, MatDialog } from '@angular/material';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { SuccessDialogComponent } from '../success-dialog/success-dialog.component';
import { BrowserDynamicTestingModule } from '@angular/platform-browser-dynamic/testing';
import { RouterModule } from '@angular/router';
import { of } from 'rxjs';
import { DeleterGrupoService } from 'src/app/services/deletar-grupo/deleter-grupo.service';

export function HttpLoaderFactory(http: HttpClient) {
  return new TranslateHttpLoader(http);
}

describe('DeletarGrupoComponent', () => {
  let component: DeletarGrupoComponent;
  let fixture: ComponentFixture<DeletarGrupoComponent>;
  let dialogSpy: MatDialog;

  beforeEach(async(() => {
    dialogSpy = jasmine.createSpyObj('MatDialog', ['open', 'closeAll']);
    TestBed.configureTestingModule({
      declarations: [
        DeletarGrupoComponent, SuccessDialogComponent
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
        { provide: MAT_DIALOG_DATA, useValue: {codFesta: '1', grupo: {nomeGrupo: 'Teste', codGrupo: 'Teste'}}},
        { provide: MatDialog, useValue: dialogSpy },
        {provide: DeleterGrupoService, useValue: {
          deleteGrupo: () => of({}),
          setFarol: () => false,
        }}
      ]
    })
    .overrideModule(BrowserDynamicTestingModule, { set: { entryComponents: [SuccessDialogComponent] } })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DeletarGrupoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should open a dialog through a method', () => {
    component.openDialogSuccess('teste');
    expect(dialogSpy.open).toHaveBeenCalled();
  });

  it('should deletarGrupo', () => {
    spyOn(component.deleteService, 'deleteGrupo')
    .and
    .callThrough();

    spyOn(component.deleteService, 'setFarol')
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

    component.grupo = {codGrupo: 'teste'};
    component.deletarGrupo();
    expect(component.deleteService.deleteGrupo).toHaveBeenCalledWith('teste');
    expect(component.deleteService.setFarol).toHaveBeenCalledWith(false);
    expect(dialogSpy.closeAll).toHaveBeenCalled();
    expect(component.component.ngOnInit).toHaveBeenCalled();
    expect(component.openDialogSuccess).toHaveBeenCalledWith('GRUPDELE');
  });
});
