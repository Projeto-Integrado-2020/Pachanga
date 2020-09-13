import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { StatusDialogComponent } from './status-dialog.component';
import { TranslateModule, TranslateLoader } from '@ngx-translate/core';
import { TranslateHttpLoader } from '@ngx-translate/http-loader';
import { HttpClient } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { CustomMaterialModule } from '../../views/material/material.module';
import { MAT_DIALOG_DATA, MatDialog } from '@angular/material';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { of } from 'rxjs';
import { StatusFestaService } from 'src/app/services/status-festa/status-festa.service';

export function HttpLoaderFactory(http: HttpClient) {
  return new TranslateHttpLoader(http);
}

describe('StatusDialogComponent', () => {
  let component: StatusDialogComponent;
  let fixture: ComponentFixture<StatusDialogComponent>;
  let dialogSpy: MatDialog;

  beforeEach(async(() => {
    dialogSpy = jasmine.createSpyObj('MatDialog', ['open', 'closeAll']);

    TestBed.configureTestingModule({
      declarations: [ StatusDialogComponent ],
      imports: [
        HttpClientTestingModule,
        CustomMaterialModule,
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
        { provide: MAT_DIALOG_DATA, useValue: {festa: {codFesta: '1', status: 'P', painel: null, codUsuario: '1'}} },
        { provide: MatDialog, useValue: dialogSpy },
        {provide: StatusFestaService, useValue: {
          mudarStatusFesta: () => of({}),
          setFarol: () => false,
        }},
      ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(StatusDialogComponent);
    component = fixture.componentInstance;
    component.painel = {
      setFesta: (resp) =>  true,
      ngOnInit: () => true,
    };
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should setStatusFesta', () => {
    spyOn(component.statusService, 'mudarStatusFesta')
    .and
    .callThrough();

    spyOn(component.statusService, 'setFarol')
    .and
    .callThrough();

    component.codFesta = 'teste';
    component.status = 'teste';
    component.setStatusFesta();
    expect(component.statusService.mudarStatusFesta).toHaveBeenCalled();
    expect(component.statusService.setFarol).toHaveBeenCalledWith(false);
  });

});
