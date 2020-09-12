import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DeletarFestaComponent } from './deletar-festa.component';

import { TranslateModule, TranslateLoader } from '@ngx-translate/core';
import { TranslateHttpLoader } from '@ngx-translate/http-loader';
import { HttpClient } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { CustomMaterialModule } from '../../views/material/material.module';
import { MAT_DIALOG_DATA, MatDialog } from '@angular/material';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { SuccessDialogComponent } from '../success-dialog/success-dialog.component';

import { RouterModule } from '@angular/router';
import { BrowserDynamicTestingModule } from '@angular/platform-browser-dynamic/testing';
import { DeletarFestaService } from 'src/app/services/deletar-festa/deletar-festa.service';
import { of } from 'rxjs/internal/observable/of';

export function HttpLoaderFactory(http: HttpClient) {
  return new TranslateHttpLoader(http);
}

describe('DeletarFestaComponent', () => {
  let component: DeletarFestaComponent;
  let fixture: ComponentFixture<DeletarFestaComponent>;
  let dialogSpy: MatDialog;

  beforeEach(async(() => {
    dialogSpy = jasmine.createSpyObj('MatDialog', ['open', 'closeAll']);

    TestBed.configureTestingModule({
      declarations: [
        DeletarFestaComponent,
        SuccessDialogComponent
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
        { provide: MAT_DIALOG_DATA, useValue: {festa: {nomeFesta: 'teste', codFesta: '1'}} },
        { provide: MatDialog, useValue: dialogSpy },
        {provide: DeletarFestaService, useValue: {
          deleteFesta: () => of('Sucesso'),
          setFarol: () => false,
        }}
      ]
    })
    .overrideModule(BrowserDynamicTestingModule, { set: { entryComponents: [SuccessDialogComponent] } })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DeletarFestaComponent);
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

  it('should deletarFesta', () => {
    spyOn(component.deleteService, 'deleteFesta')
    .and
    .callThrough();

    spyOn(component.deleteService, 'setFarol')
    .and
    .callThrough();

    spyOn(component, 'openDialogSuccess')
    .and
    .callThrough();

    spyOn(component.router, 'navigate')
    .and
    .callThrough();

    component.festa = {codFesta: 'teste'};
    component.deletarFesta();
    expect(component.deleteService.deleteFesta).toHaveBeenCalledWith('teste');
    expect(component.deleteService.setFarol).toHaveBeenCalledWith(false);
    expect(dialogSpy.closeAll).toHaveBeenCalled();
    expect(component.router.navigate).toHaveBeenCalledWith(['minhas-festas']);
    expect(component.openDialogSuccess).toHaveBeenCalledWith('Sucesso');
  });
});
