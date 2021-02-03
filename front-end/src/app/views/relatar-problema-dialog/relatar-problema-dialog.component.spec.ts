/* tslint:disable:no-unused-variable */
import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RelatarProblemaDialogComponent } from './relatar-problema-dialog.component';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { CustomMaterialModule } from '../material/material.module';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { TranslateLoader, TranslateModule } from '@ngx-translate/core';
import { HttpClient } from '@angular/common/http';
import { HttpLoaderFactory } from 'src/app/app.module';
import { of } from 'rxjs';
import { CommonModule, DatePipe } from '@angular/common';
import { RouterModule } from '@angular/router';
import { FormsModule, ReactiveFormsModule  } from '@angular/forms';
import { LoginService } from 'src/app/services/loginService/login.service';
import { FileInput, MaterialFileInputModule } from 'ngx-material-file-input';
import { SegurancaProblemasService } from 'src/app/services/seguranca-problemas/seguranca-problemas.service';
import { PainelSegurancaComponent } from '../painel-seguranca/painel-seguranca.component';

describe('RelatarProblemaDialogComponent', () => {
  let component: RelatarProblemaDialogComponent;
  let fixture: ComponentFixture<RelatarProblemaDialogComponent>;
  let dialogSpy: MatDialog;
  let dialogRefSpy: MatDialogRef<RelatarProblemaDialogComponent>;
  let datePipe: DatePipe;

  beforeEach(async(() => {
    dialogSpy = jasmine.createSpyObj('MatDialog', ['open', 'closeAll']);
    dialogRefSpy = jasmine.createSpyObj('MatDialogRef<RelatarProblemaDialogComponent>', ['close']);
    datePipe = jasmine.createSpyObj('DatePipe', ['transform']);
    TestBed.configureTestingModule({
      declarations: [ RelatarProblemaDialogComponent ],
      imports: [
        MaterialFileInputModule,
        CustomMaterialModule,
        FormsModule,
        ReactiveFormsModule,
        HttpClientTestingModule,
        BrowserAnimationsModule,
        RouterModule.forRoot([]),
        TranslateModule.forRoot({
          loader: {
            provide: TranslateLoader,
            useFactory: HttpLoaderFactory,
            deps: [HttpClient]
          }
        })],
      providers: [
        { provide: MAT_DIALOG_DATA, useValue: { codFesta: '1', area: { codArea: '1', codFesta: '1', nomeArea: 'Nome'} } },
        { provide: MatDialog, useValue: dialogSpy },
        {
          provide: MatDialogRef,
          useValue: {dialogRefSpy}
        },
        {provide: SegurancaProblemasService, useValue: {
          adicionarProblema: () => of({}),
          listarProblemas: () => of([{}]),
          setFarol: () => false,
        }},
        {provide: DatePipe, useValue: datePipe}
      ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RelatarProblemaDialogComponent);
    component = fixture.componentInstance;
    const service: LoginService = TestBed.get(LoginService);
    service.usuarioInfo = {codUsuario: '1'};
    const token = {
      timeToken: '2020-09-21T01:14:04.028+0000',
      token: 'teste'
    };
    component.date = new Date();
    component.dialogRef = dialogRefSpy;
    component.component = {
      ngOnInit: () => true
    };
    localStorage.setItem('token', JSON.stringify(token));
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should excluirInputImagem', () => {
    component.form.get('imagemProblema').setValue(new FileInput([]));
    component.imagem = 'teste';
    expect(component.form.get('imagemProblema').value).toBeTruthy();
    expect(component.imagem).toEqual('teste');

    component.excluirInputImagem();
    expect(component.form.get('imagemProblema').value).toBeFalsy();
  });

  it('should buildForm', () => {
    expect(component.form.get('imagemProblema')).toBeTruthy();
    expect(component.form.get('codProblema')).toBeTruthy();
    expect(component.form.get('descProblema')).toBeTruthy();
  });

  it('should get f to get form controls', () => {
    expect(component.f).toBe(component.form.controls);
  });

  it('should alterarPreview', () => {
    component.alterarPreview();

    expect(component.urlNoImage).toBe('https://xtremebike.com.br/website/images/product/1.jpg');
  });

  it('should open a dialog through a method', () => {
    component.openDialogProcessing();
    expect(dialogSpy.open).toHaveBeenCalled();
  });

  it('should relatarProblm', () => {
    spyOn(component.segurancaProblemaService, 'adicionarProblema')
    .and
    .callThrough();

    spyOn(component.segurancaProblemaService, 'setFarol')
    .and
    .callThrough();


    spyOn(component, 'openDialogProcessing')
    .and
    .callThrough();

    const codProblema = 1;
    const descProblema = 'teste';
    const imagem = null;
    const problemaTO = {
      codProblema: 1,
      descProblema: 'teste',
      horarioInicio: undefined,
      observacaoSolucao: '',
      codFesta: '1',
      codAreaSeguranca: '1',
      codUsuarioEmissor: '1',
    };

    component.relatarProblm(codProblema, descProblema);

    expect(component.segurancaProblemaService.adicionarProblema).toHaveBeenCalledWith(problemaTO, imagem);
  });

});
