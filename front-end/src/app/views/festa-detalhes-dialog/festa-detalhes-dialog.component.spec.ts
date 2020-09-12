/* tslint:disable:no-unused-variable */
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { FestaDetalhesDialogComponent } from './festa-detalhes-dialog.component';
import { LoginService } from 'src/app/services/loginService/login.service';
import { MatDialog, MAT_DIALOG_DATA } from '@angular/material';
import { CustomMaterialModule } from '../material/material.module';
import { RouterTestingModule } from '@angular/router/testing';
import { TranslateModule, TranslateLoader } from '@ngx-translate/core';
import { TranslateHttpLoader } from '@ngx-translate/http-loader';
import { HttpClient } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';
import { ConvidadoService } from 'src/app/services/convidado/convidado.service';

export function HttpLoaderFactory(http: HttpClient) {
  return new TranslateHttpLoader(http);
}

describe('FestaDetalhesDialogComponent', () => {
  let component: FestaDetalhesDialogComponent;
  let fixture: ComponentFixture<FestaDetalhesDialogComponent>;
  let dialogSpy: MatDialog;

  beforeEach(async(() => {
    dialogSpy = jasmine.createSpyObj('MatDialog', ['open', 'closeAll']);
    TestBed.configureTestingModule({
      declarations: [ FestaDetalhesDialogComponent ],
      imports: [
        CustomMaterialModule,
        HttpClientTestingModule,
        RouterTestingModule.withRoutes([]),
        TranslateModule.forRoot({
          loader: {
            provide: TranslateLoader,
            useFactory: HttpLoaderFactory,
            deps: [HttpClient]
          }
        })
      ],
      providers: [
        { provide: MAT_DIALOG_DATA, useValue: {
          alerta: { conviteFesta: {
          nomeFesta: 'teste', marca: 'teste', nomeEstoque: 'teste', qtdAtual: 'teste'},
          mensagem: 'TESTE?testeCod7&testeCod8'
        }}
      },
        { provide: MatDialog, useValue: dialogSpy },
        {provide: ConvidadoService, useValue: {
          aceitarConvite: () => of({}),
          recusarConvite: () => of({}),
        }}
      ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FestaDetalhesDialogComponent);
    component = fixture.componentInstance;
    const service: LoginService = TestBed.get(LoginService);
    service.usuarioInfo = {codUsuario: '1'};
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should formatDateTime', () => {
    const result = component.formatDateTime('2000-04-05T10:00');
    expect(result).toEqual('05/04/2000 - 10:00');
  });

  it('should aceitarConvite', () => {
    spyOn(component.convService, 'aceitarConvite')
    .and
    .callThrough();

    spyOn(component.router, 'navigate')
    .and
    .callThrough();

    component.alerta = {codConvidado: 'teste'};
    component.idGrupo = 'teste';
    component.aceitarConvite();
    expect(component.convService.aceitarConvite).toHaveBeenCalledWith('teste', 'teste');
    expect(dialogSpy.closeAll).toHaveBeenCalled();
    expect(component.router.navigate).toHaveBeenCalledWith(['/minhas-festas']);
  });

  it('should recusarConvite', () => {
    spyOn(component.convService, 'recusarConvite')
    .and
    .callThrough();

    spyOn(component.router, 'navigate')
    .and
    .callThrough();

    component.alerta = {codConvidado: 'teste'};
    component.idGrupo = 'teste';
    component.recusarConvite();
    expect(component.convService.recusarConvite).toHaveBeenCalledWith('teste', 'teste');
    expect(dialogSpy.closeAll).toHaveBeenCalled();
  });
});
