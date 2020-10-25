import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DeleteAreaSegurancaDialogComponent } from './delete-area-seguranca-dialog.component';
import { MatDialog, MAT_DIALOG_DATA } from '@angular/material';
import { CustomMaterialModule } from '../material/material.module';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { TranslateModule, TranslateLoader } from '@ngx-translate/core';
import { HttpClient } from '@angular/common/http';
import { RouterModule } from '@angular/router';
import { TranslateHttpLoader } from '@ngx-translate/http-loader';
import { of } from 'rxjs';
import { DeletarAreaSegurancaService } from 'src/app/services/deletar-area-seguranca/deletar-area-seguranca.service';

export function HttpLoaderFactory(http: HttpClient) {
  return new TranslateHttpLoader(http);
}

describe('DeleteAreaSegurancaDialogComponent', () => {
  let component: DeleteAreaSegurancaDialogComponent;
  let fixture: ComponentFixture<DeleteAreaSegurancaDialogComponent>;
  let dialogSpy: MatDialog;

  beforeEach(async(() => {
    dialogSpy = jasmine.createSpyObj('MatDialog', ['open', 'closeAll']);

    TestBed.configureTestingModule({
      declarations: [
        DeleteAreaSegurancaDialogComponent,
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
        {provide: DeletarAreaSegurancaService, useValue: {
          deletarAreaSeguranca: () => of({}),
          setFarol: () => false,
        }}
      ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DeleteAreaSegurancaDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should deleteAreaSeguranca', () => {
    spyOn(component.deleteAreaSegurancaService, 'deletarAreaSeguranca')
    .and
    .callThrough();

    spyOn(component.deleteAreaSegurancaService, 'setFarol')
    .and
    .callThrough();

    component.component = {
      ngOnInit: () => true
    };
    spyOn(component.component, 'ngOnInit')
    .and
    .callThrough();

    component.codArea = 'teste';
    component.deleteAreaSeguranca();
    expect(component.deleteAreaSegurancaService.deletarAreaSeguranca).toHaveBeenCalledWith('teste');
    expect(component.deleteAreaSegurancaService.setFarol).toHaveBeenCalledWith(false);
    expect(dialogSpy.closeAll).toHaveBeenCalled();
    expect(component.component.ngOnInit).toHaveBeenCalled();
  });
});
