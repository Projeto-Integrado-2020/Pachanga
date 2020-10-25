import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CriarAreaSegurancaDialogComponent } from './criar-area-seguranca-dialog.component';
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
import { of } from 'rxjs';
import { CriarAreaSegurancaService } from 'src/app/services/criar-area-seguranca/criar-area-seguranca.service';

export function HttpLoaderFactory(http: HttpClient) {
  return new TranslateHttpLoader(http);
}

describe('CriarAreaSegurancaDialogComponent', () => {
  let component: CriarAreaSegurancaDialogComponent;
  let fixture: ComponentFixture<CriarAreaSegurancaDialogComponent>;
  let dialogSpy: MatDialog;

  beforeEach(async(() => {
    dialogSpy = jasmine.createSpyObj('MatDialog', ['open', 'closeAll']);
    TestBed.configureTestingModule({
      declarations: [ CriarAreaSegurancaDialogComponent ],
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
          {provide: CriarAreaSegurancaService, useValue: {
            novaAreaSeguranca: () => of({}),
            setFarol: () => false,
          }}
      ]
      })
      .compileComponents();
    }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CriarAreaSegurancaDialogComponent);
    component = fixture.componentInstance;
    const service: LoginService = TestBed.get(LoginService);
    service.usuarioInfo = {codUsuario: '1'};
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should addAreaSeguranca', () => {
    const criarAreaSeguranca: CriarAreaSegurancaService = TestBed.get(CriarAreaSegurancaService);

    spyOn(criarAreaSeguranca, 'novaAreaSeguranca')
    .and
    .callThrough();

    spyOn(criarAreaSeguranca, 'setFarol')
    .and
    .callThrough();

    component.component = {
      ngOnInit: () => {
        return true;
      }
    };
    component.addAreaSeguranca('Teste');

    expect(criarAreaSeguranca.novaAreaSeguranca).toHaveBeenCalledWith({nomeArea: 'Teste', codFesta: '1'});
    expect(criarAreaSeguranca.setFarol).toHaveBeenCalledWith(false);
    expect(dialogSpy.closeAll).toHaveBeenCalled();
  });
});
