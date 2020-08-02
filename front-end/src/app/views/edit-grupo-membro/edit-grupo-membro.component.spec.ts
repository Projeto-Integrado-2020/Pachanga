import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EditGrupoMembroComponent } from './edit-grupo-membro.component';
import { MatDialog, MAT_DIALOG_DATA } from '@angular/material';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { CustomMaterialModule } from '../material/material.module';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { TranslateModule, TranslateLoader } from '@ngx-translate/core';
import { TranslateHttpLoader } from '@ngx-translate/http-loader';
import { HttpClient } from '@angular/common/http';

export function HttpLoaderFactory(http: HttpClient) {
  return new TranslateHttpLoader(http);
}

describe('EditGrupoMembroComponent', () => {
  let component: EditGrupoMembroComponent;
  let fixture: ComponentFixture<EditGrupoMembroComponent>;
  let dialogSpy: MatDialog;

  beforeEach(async(() => {
    dialogSpy = jasmine.createSpyObj('MatDialog', ['open']);
    TestBed.configureTestingModule({
      declarations: [ EditGrupoMembroComponent ],
      imports: [
      HttpClientTestingModule,
      CustomMaterialModule,
      BrowserAnimationsModule,
      FormsModule,
      ReactiveFormsModule,
      TranslateModule.forRoot({
        loader: {
          provide: TranslateLoader,
          useFactory: HttpLoaderFactory,
          deps: [HttpClient]
        }
      })],
      providers: [
        { provide: MAT_DIALOG_DATA, useValue: {grupos: [
                                                {codGrupo: '1', nomeGrupo: 'Salve'},
                                                {codGrupo: '2', nomeGrupo: 'Salve2'},
                                                {codGrupo: '3', nomeGrupo: 'Salve3'},
                                                {codGrupo: '4', nomeGrupo: 'Salve4'},
                                                {codGrupo: '5', nomeGrupo: 'Salve5'}
                                              ]}},
        { provide: MatDialog, useValue: dialogSpy }
    ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EditGrupoMembroComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
