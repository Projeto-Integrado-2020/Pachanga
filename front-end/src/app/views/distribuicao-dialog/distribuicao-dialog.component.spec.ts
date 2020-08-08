import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DistribuicaoDialogComponent } from './distribuicao-dialog.component';
import { TranslateModule, TranslateLoader } from '@ngx-translate/core';
import { TranslateHttpLoader } from '@ngx-translate/http-loader';
import { HttpClient } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { CustomMaterialModule } from '../../views/material/material.module';
import { MAT_DIALOG_DATA, MatDialog } from '@angular/material';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { SuccessDialogComponent } from '../success-dialog/success-dialog.component';
import { BrowserDynamicTestingModule } from '@angular/platform-browser-dynamic/testing';

export function HttpLoaderFactory(http: HttpClient) {
  return new TranslateHttpLoader(http);
}

describe('DistribuicaoDialogComponent', () => {
  let component: DistribuicaoDialogComponent;
  let fixture: ComponentFixture<DistribuicaoDialogComponent>;
  let dialogSpy: MatDialog;

  beforeEach(async(() => {
    dialogSpy = jasmine.createSpyObj('MatDialog', ['open', 'closeAll']);
    TestBed.configureTestingModule({
      declarations: [
        DistribuicaoDialogComponent,
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
        })
      ],
      providers: [
        { provide: MAT_DIALOG_DATA,
          useValue: {codFesta: 'teste', listaUser: [], grupo: {codGrupo: 'teste', nomeGrupo: 'teste'} }
        },
        { provide: MatDialog, useValue: dialogSpy },
      ]
    })
    .overrideModule(BrowserDynamicTestingModule, { set: { entryComponents: [SuccessDialogComponent] } })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DistribuicaoDialogComponent);
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
});
