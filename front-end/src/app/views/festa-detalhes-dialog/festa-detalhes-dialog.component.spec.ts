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

export function HttpLoaderFactory(http: HttpClient) {
  return new TranslateHttpLoader(http);
}

describe('FestaDetalhesDialogComponent', () => {
  let component: FestaDetalhesDialogComponent;
  let fixture: ComponentFixture<FestaDetalhesDialogComponent>;
  let dialogSpy: MatDialog;

  beforeEach(async(() => {
    dialogSpy = jasmine.createSpyObj('MatDialog', ['open']);
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
        { provide: MAT_DIALOG_DATA, useValue: {mensagem: 'CONVFEST?1&1'}},
        { provide: MatDialog, useValue: dialogSpy },
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
});
