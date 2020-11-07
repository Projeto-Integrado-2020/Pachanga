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
import { ThirdPartyPainelComponent } from './third-party-painel.component';
import { GetFestaService } from 'src/app/services/get-festa/get-festa.service';
import { GetIntegracaoService } from 'src/app/services/get-integracao/get-integracao.service';

export function HttpLoaderFactory(http: HttpClient) {
  return new TranslateHttpLoader(http);
}

describe('ThirdPartyPainelComponent', () => {
  let component: ThirdPartyPainelComponent;
  let fixture: ComponentFixture<ThirdPartyPainelComponent>;
  let dialogSpy: MatDialog;

  beforeEach(async(() => {
    dialogSpy = jasmine.createSpyObj('MatDialog', ['open']);

    TestBed.configureTestingModule({
      declarations: [ ThirdPartyPainelComponent ],
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
        {provide: GetFestaService, useValue: {
          acessarFesta: () => of([{
            codFesta: 'testeCod',
            nomeFesta: 'testeNome'
          }]),
          setFarol: () => false,
        }},
        {provide: GetIntegracaoService, useValue: {
          getIntegracoes: () => of([{
            codInfo: 'testeCod'
          }]),
          setFarol: () => false,
        }}
      ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ThirdPartyPainelComponent);
    component = fixture.componentInstance;
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

  it('should resgatarFesta', () => {
    spyOn(component.getFestaService, 'acessarFesta')
    .and
    .callThrough();

    spyOn(component.getFestaService, 'setFarol')
    .and
    .callThrough();

    component.resgatarFesta();
    expect(component.getFestaService.acessarFesta).toHaveBeenCalled();
    expect(component.getFestaService.setFarol).toHaveBeenCalledWith(false);
  });

  it('should resgatarIntegracoes', () => {
    spyOn(component.getIntegracoes, 'getIntegracoes')
    .and
    .callThrough();

    spyOn(component.getIntegracoes, 'setFarol')
    .and
    .callThrough();

    component.resgatarIntegracoes();
    expect(component.getIntegracoes.getIntegracoes).toHaveBeenCalled();
    expect(component.getIntegracoes.setFarol).toHaveBeenCalledWith(false);
  });
});
