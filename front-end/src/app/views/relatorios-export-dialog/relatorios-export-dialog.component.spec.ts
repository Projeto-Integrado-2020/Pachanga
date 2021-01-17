import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { TranslateModule, TranslateLoader } from '@ngx-translate/core';
import { TranslateHttpLoader } from '@ngx-translate/http-loader';
import { HttpClient } from '@angular/common/http';
import { CustomMaterialModule } from '../material/material.module';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MAT_DIALOG_DATA } from '@angular/material';
import { MatDialog } from '@angular/material';
import { of } from 'rxjs';
import { RelatoriosExportDialogComponent } from './relatorios-export-dialog.component';
import { RelatorioExportService } from 'src/app/services/relatorios/relatorio-export.service';
import { LoginService } from 'src/app/services/loginService/login.service';

export function HttpLoaderFactory(http: HttpClient) {
  return new TranslateHttpLoader(http);
}

describe('RelatoriosExportDialogComponent', () => {
  let component: RelatoriosExportDialogComponent;
  let fixture: ComponentFixture<RelatoriosExportDialogComponent>;
  let dialogSpy: MatDialog;

  beforeEach(async(() => {
    dialogSpy = jasmine.createSpyObj('MatDialog', ['open', 'closeAll']);
    TestBed.configureTestingModule({
      declarations: [ RelatoriosExportDialogComponent ],
      imports: [
        CustomMaterialModule,
        BrowserAnimationsModule,
        HttpClientTestingModule,
        TranslateModule.forRoot({
          loader: {
            provide: TranslateLoader,
            useFactory: HttpLoaderFactory,
            deps: [HttpClient]
          }
        })
      ],
      providers: [
        { provide: MAT_DIALOG_DATA, useValue: {codFesta: '0', pdf: 'teste'} },
        { provide: MatDialog, useValue: dialogSpy },
        {provide: RelatorioExportService, useValue: {
          exportarRelatorio: () => of({}),
          setFarol: () => false,
        }}
      ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RelatoriosExportDialogComponent);
    component = fixture.componentInstance;
    const service: LoginService = TestBed.get(LoginService);
    service.usuarioInfo = {codUsuario: '1'};
    const token = {
      timeToken: '2020-09-21T01:14:04.028+0000',
      token: 'teste'
    };
    localStorage.setItem('token', JSON.stringify(token));
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should add e-mail', () => {
    component.add({input: null, value: 'teste@teste.com'});
    expect(component.maillist).toEqual([{address: 'teste@teste.com', valid: true}]);
  });

  it('should remove e-mail', () => {
    component.add({input: null, value: 'teste@teste.com'});
    component.remove(component.maillist[0]);
    expect(component.maillist).toEqual([]);
  });

  it('should open a dialog through a method', () => {
    component.openDialogSuccess('teste');
    expect(dialogSpy.open).toHaveBeenCalled();
  });

  it('should validate e-mail', () => {
    let result = component.validate('teste@teste.com');
    expect(result).toBeTruthy();
    result = component.validate('testeteste');
    expect(result).toBeFalsy();
  });

  it('should openDialogProcessing', () => {
    component.openDialogProcessing();
    expect(dialogSpy.open).toHaveBeenCalled();
  });

  it('should exportarPDF', () => {
    spyOn(component.exportService, 'exportarRelatorio')
    .and
    .callThrough();

    spyOn(component.exportService, 'setFarol')
    .and
    .callThrough();

    spyOn(component, 'openDialogSuccess')
    .and
    .callThrough();

    spyOn(component, 'openDialogProcessing')
    .and
    .callThrough();

    component.codFesta = 'codFesta Teste';
    component.pdf = 'pdf Teste';
    component.maillist = [
      {address: 'email@email.com', valid: true},
      {address: 'teste@teste.com', valid: true}
    ];
    component.exportarPDF();
    expect(component.exportService.exportarRelatorio)
      .toHaveBeenCalledWith('codFesta Teste', 'pdf Teste', ['email@email.com', 'teste@teste.com']);
    expect(component.exportService.setFarol).toHaveBeenCalledWith(false);
    expect(component.openDialogSuccess).toHaveBeenCalledWith('EXPORELA');
    expect(component.openDialogProcessing).toHaveBeenCalled();
    expect(dialogSpy.closeAll).toHaveBeenCalled();
  });
});
