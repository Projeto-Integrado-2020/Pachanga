import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RemoverUnidadeDoseDialogComponent } from './remover-unidade-dose-dialog.component';
import { MAT_DIALOG_DATA, MatDialog } from '@angular/material';
import { HttpClient } from '@angular/common/http';
import { TranslateHttpLoader } from '@ngx-translate/http-loader';
import { CustomMaterialModule } from '../material/material.module';
import { NgxMaterialTimepickerModule } from 'ngx-material-timepicker';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { TranslateModule, TranslateLoader } from '@ngx-translate/core';

export function HttpLoaderFactory(http: HttpClient) {
  return new TranslateHttpLoader(http);
}


describe('RemoverUnidadeDoseDialogComponent', () => {
  let component: RemoverUnidadeDoseDialogComponent;
  let fixture: ComponentFixture<RemoverUnidadeDoseDialogComponent>;
  let dialogSpy: MatDialog;

  beforeEach(async(() => {
    dialogSpy = jasmine.createSpyObj('MatDialog', ['open', 'closeAll']);
    TestBed.configureTestingModule({
      declarations: [ RemoverUnidadeDoseDialogComponent ],
      imports: [
        CustomMaterialModule,
        NgxMaterialTimepickerModule,
        FormsModule,
        ReactiveFormsModule,
        HttpClientTestingModule,
        BrowserAnimationsModule,
        TranslateModule.forRoot({
          loader: {
            provide: TranslateLoader,
            useFactory: HttpLoaderFactory,
            deps: [HttpClient]
          }
        })],
        providers: [
          { provide: MAT_DIALOG_DATA, useValue: { codFesta: '1', estoque: { nomeEstoque: 'Estoque' } } },
          { provide: MatDialog, useValue: dialogSpy }
      ]
      })
      .compileComponents();
    }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RemoverUnidadeDoseDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
