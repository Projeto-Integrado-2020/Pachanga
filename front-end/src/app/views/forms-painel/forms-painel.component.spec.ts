import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { MatDialog } from '@angular/material';
import { CustomMaterialModule } from '../material/material.module';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { TranslateModule, TranslateLoader } from '@ngx-translate/core';
import { HttpClient } from '@angular/common/http';
import { TranslateHttpLoader } from '@ngx-translate/http-loader';
import { LoginService } from 'src/app/services/loginService/login.service';
import { of } from 'rxjs';
import { FormsPainelComponent } from './forms-painel.component';
import { GetFormsService } from 'src/app/services/get-forms/get-forms.service';

export function HttpLoaderFactory(http: HttpClient) {
  return new TranslateHttpLoader(http);
}

describe('FormsPainelComponent', () => {
  let component: FormsPainelComponent;
  let fixture: ComponentFixture<FormsPainelComponent>;
  let dialogSpy: MatDialog;

  beforeEach(async(() => {
    dialogSpy = jasmine.createSpyObj('MatDialog', ['open']);

    TestBed.configureTestingModule({
      declarations: [ FormsPainelComponent ],
      imports: [
        CustomMaterialModule,
        HttpClientTestingModule,
        BrowserAnimationsModule,
        ReactiveFormsModule,
        RouterModule.forRoot([]),
        TranslateModule.forRoot({
          loader: {
            provide: TranslateLoader,
            useFactory: HttpLoaderFactory,
            deps: [HttpClient]
          }
        }),
      ],
      providers: [
        { provide: MatDialog, useValue: dialogSpy },
        {provide: GetFormsService, useValue: {
          getQuestionarios: () => of([{
            codQuestionario: 'Teste',
            nome: 'Teste',
            url: 'testeURL'
          }]),
          setFarol: () => false,
        }}
      ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FormsPainelComponent);
    component = fixture.componentInstance;
    component.codFesta = 'teste';
    const service: LoginService = TestBed.get(LoginService);
    service.usuarioInfo = {codUsuario: '1'};
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should open a add-dialog through a method', () => {
    component.openDialogAdd();
    expect(dialogSpy.open).toHaveBeenCalled();
  });

  it('should open a delete-dialog through a method', () => {
    component.openDialogDelete('teste');
    expect(dialogSpy.open).toHaveBeenCalled();
  });

  it('should open a edit-dialog through a method', () => {
    component.openDialogEdit({marca: 'teste', preco: 'teste'});
    expect(dialogSpy.open).toHaveBeenCalled();
  });

  it('should resgatarQuestionarios', () => {
    spyOn(component.getQuestionarios, 'getQuestionarios')
    .and
    .callThrough();

    spyOn(component.getQuestionarios, 'setFarol')
    .and
    .callThrough();

    component.resgatarQuestionarios();
    expect(component.getQuestionarios.getQuestionarios).toHaveBeenCalled();
    expect(component.getQuestionarios.setFarol).toHaveBeenCalledWith(false);
  });

  it('nomeQuestionarioSort', () => {
    expect(component.nomeQuestionarioSort({nome: 'A'}, {nome: 'B'})).toBe(-1);
    expect(component.nomeQuestionarioSort({nome: 'B'}, {nome: 'A'})).toBe(1);
  });
});
