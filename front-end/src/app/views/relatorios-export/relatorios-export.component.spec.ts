import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CustomMaterialModule } from '../material/material.module';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { TranslateModule, TranslateLoader } from '@ngx-translate/core';
import { HttpLoaderFactory } from '../edit-dialog/edit-dialog.component.spec';
import { HttpClient } from '@angular/common/http';
import { LoginService } from 'src/app/services/loginService/login.service';
import { RelatorioEstoqueService } from 'src/app/services/relatorios/relatorio-estoque.service';
import { of } from 'rxjs';
import { NgxChartsModule } from '@swimlane/ngx-charts';

import { RelatoriosExportComponent } from './relatorios-export.component';
import { RelatorioVendaService } from 'src/app/services/relatorios/relatorio-venda.service';
import { RelatorioAreaSegService } from 'src/app/services/relatorios/relatorio-area-seg.service';
import { GetFestaService } from 'src/app/services/get-festa/get-festa.service';
import { MatDialog } from '@angular/material';

describe('RelatoriosExportComponent', () => {
  let component: RelatoriosExportComponent;
  let fixture: ComponentFixture<RelatoriosExportComponent>;
  let dialogSpy: MatDialog;

  beforeEach(async(() => {
    dialogSpy = jasmine.createSpyObj('MatDialog', ['open', 'closeAll']);

    TestBed.configureTestingModule({
      declarations: [ RelatoriosExportComponent ],
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
        NgxChartsModule
      ],
      providers: [
        { provide: MatDialog, useValue: dialogSpy },
        {provide: RelatorioEstoqueService, useValue: {
          consumoItemEstoque: () => of([]),
          perdaItemEstoque: () => of([]),
          quantidadeItemEstoque: () => of([]),
          consumoProduto: () => of([])
        }},
        {provide: RelatorioVendaService, useValue: {
          ingressosFesta: () => of({ingressos: {test: 1}}),
          ingressosFestaCompradosPagos: () => of({ingressosCompradosPagos: {loteteste: {1 : 2}}})
        }},
        {provide: RelatorioAreaSegService, useValue: {
          problemasArea: () => of({problemasArea: {}, chamadasEmitidasFuncionario: [], solucionadorAlertasSeguranca: []}),
          chamadasUsuario: () => of({problemasArea: {}, chamadasEmitidasFuncionario: [], solucionadorAlertasSeguranca: []}),
          usuarioSolucionador: () => of({problemasArea: {}, chamadasEmitidasFuncionario: [], solucionadorAlertasSeguranca: []})
        }},
        {provide: GetFestaService, useValue: {
          acessarFesta: () => of({
            nomeFesta: 'Teste',
            descricaoFesta: 'Teste',
            codEnderecoFesta: 'Teste',
            organizador: 'Teste',
            descOrganizador: 'Teste',
            horarioInicioFesta: '2020-02-01 12:00:00',
            horarioFimFesta: '2020-02-06 18:00:00',
            categoriaPrimaria: {
              codCategoria: 2,
              nomeCategoria: 'RAVEAFIM'
            },
            categoriaSecundaria: null
          }),
          setFarol: () => false
        }},
      ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RelatoriosExportComponent);
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

  it('should get consumoItemEstoque', () => {
    spyOn(component.relEstoqueService, 'consumoItemEstoque')
    .and
    .callThrough();

    component.consumoItemEstoque();
    expect(component.relEstoqueService.consumoItemEstoque).toHaveBeenCalled();
  });

  it('should get perdaItemEstoque', () => {
    spyOn(component.relEstoqueService, 'perdaItemEstoque')
    .and
    .callThrough();

    component.perdaItemEstoque();
    expect(component.relEstoqueService.perdaItemEstoque).toHaveBeenCalled();
  });

  it('should get quantidadeItemEstoque', () => {
    spyOn(component.relEstoqueService, 'quantidadeItemEstoque')
    .and
    .callThrough();

    component.quantidadeItemEstoque();
    expect(component.relEstoqueService.quantidadeItemEstoque).toHaveBeenCalled();
  });

  it('should get consumoProduto', () => {
    spyOn(component.relEstoqueService, 'consumoProduto')
    .and
    .callThrough();

    component.consumoProduto();
    expect(component.relEstoqueService.consumoProduto).toHaveBeenCalled();
  });

  it('should formatDate', () => {
    const result = component.formatDate(new Date('2021-01-09T14:19:49.656018'));
    expect(result).toEqual('49');
  });

  it('should toolTipDate', () => {
    const result = component.toolTipDate(new Date('2021-01-09T14:19:49.656018'));
    expect(result).toEqual('09/01 14:19:49');
  });

  it('should get getRelatorioIngressos', () => {
    spyOn(component.relatorioVendaService, 'ingressosFesta')
    .and
    .callThrough();

    component.getRelatorioIngressos();
    expect(component.relatorioVendaService.ingressosFesta).toHaveBeenCalled();
  });

  it('should get getRelatorioIngressosPagos', () => {
    spyOn(component.relatorioVendaService, 'ingressosFestaCompradosPagos')
    .and
    .callThrough();

    component.getRelatorioIngressosPagos();
    expect(component.relatorioVendaService.ingressosFestaCompradosPagos).toHaveBeenCalled();
  });

  it('should resgatarFesta', () => {
    spyOn(component.getFestaService, 'acessarFesta')
    .and
    .callThrough();

    component.resgatarFesta('teste');
    expect(component.getFestaService.acessarFesta).toHaveBeenCalled();
  });

  it('should processarDateTime', () => {
    const result = component.processarDateTime('2020-11-10 12:00:00');
    expect(result).toEqual('10/11/2020, 12:00');
  });

  it('should get problemasArea', () => {
    spyOn(component.relAreaSegService, 'problemasArea')
    .and
    .callThrough();

    component.problemasArea('teste');
    expect(component.relAreaSegService.problemasArea).toHaveBeenCalled();
  });

  it('should get chamadasUsuario', () => {
    spyOn(component.relAreaSegService, 'chamadasUsuario')
    .and
    .callThrough();

    component.chamadasUsuario('teste');
    expect(component.relAreaSegService.chamadasUsuario).toHaveBeenCalled();
  });

  it('should get usuariosPorEmissao', () => {
    spyOn(component.relAreaSegService, 'usuarioSolucionador')
    .and
    .callThrough();

    component.usuariosPorEmissao('teste');
    expect(component.relAreaSegService.usuarioSolucionador).toHaveBeenCalled();
  });

  it('should call criarPaginaPDF at gerarPDFRelatorios', () => {
    spyOn(component, 'criarPaginaPDF')
    .and
    .callThrough();

    component.gerarPDFRelatorios();
    expect(component.criarPaginaPDF).toHaveBeenCalled();
  });

  it('should openDialogProcessing', () => {
    component.openDialogProcessing();
    expect(dialogSpy.open).toHaveBeenCalled();
  });

  it('should openDialogExport', () => {
    component.openDialogExport('teste');
    expect(dialogSpy.open).toHaveBeenCalled();
  });
});
