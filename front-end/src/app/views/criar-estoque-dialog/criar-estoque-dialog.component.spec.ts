import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CriarEstoqueDialogComponent } from './criar-estoque-dialog.component';
import { CustomMaterialModule } from '../material/material.module';
import { NgxMaterialTimepickerModule } from 'ngx-material-timepicker';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { TranslateModule, TranslateLoader } from '@ngx-translate/core';
import { HttpClient } from '@angular/common/http';
import { LoginService } from 'src/app/services/loginService/login.service';
import { MAT_DIALOG_DATA, MatDialog } from '@angular/material';
import { TranslateHttpLoader } from '@ngx-translate/http-loader';
import { CriarEstoqueService } from 'src/app/services/criar-estoque/criar-estoque.service';
import { of } from 'rxjs';

export function HttpLoaderFactory(http: HttpClient) {
  return new TranslateHttpLoader(http);
}

describe('CriarEstoqueDialogComponent', () => {
  let component: CriarEstoqueDialogComponent;
  let fixture: ComponentFixture<CriarEstoqueDialogComponent>;
  let dialogSpy: MatDialog;

  beforeEach(async(() => {
    dialogSpy = jasmine.createSpyObj('MatDialog', ['open', 'closeAll']);
    TestBed.configureTestingModule({
      declarations: [ CriarEstoqueDialogComponent ],
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
          { provide: MAT_DIALOG_DATA, useValue: { codFesta: '1'} },
          { provide: MatDialog, useValue: dialogSpy },
          {provide: CriarEstoqueService, useValue: {
            novoEstoque: () => of({}),
            setFarol: () => false,
          }}
      ]
      })
      .compileComponents();
    }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CriarEstoqueDialogComponent);
    component = fixture.componentInstance;
    const service: LoginService = TestBed.get(LoginService);
    service.usuarioInfo = {codUsuario: '1'};
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should gerarForm', () => {
    component.gerarForm();
    expect(component.form).toBeTruthy();
    expect(component.form.get('nomeEstoque')).toBeTruthy();
  });

  it('should addEstoque', () => {
    const criarEstoqueService: CriarEstoqueService = TestBed.get(CriarEstoqueService);

    spyOn(criarEstoqueService, 'novoEstoque')
    .and
    .callThrough();

    spyOn(criarEstoqueService, 'setFarol')
    .and
    .callThrough();

    component.codFesta = 'teste';
    component.component = {
      ngOnInit: () => {
        return true;
      }
    };
    component.addEstoque('Teste');

    expect(criarEstoqueService.novoEstoque).toHaveBeenCalledWith('teste', {nomeEstoque: 'Teste'});
    expect(criarEstoqueService.setFarol).toHaveBeenCalledWith(false);
    expect(dialogSpy.closeAll).toHaveBeenCalled();
  });
});
