import { Component, Inject, OnInit } from '@angular/core';
import { MatDialog, MAT_DIALOG_DATA } from '@angular/material';
import { LoginService } from 'src/app/services/loginService/login.service';
import { SegurancaProblemasService } from 'src/app/services/seguranca-problemas/seguranca-problemas.service';
import { DatePipe } from '@angular/common';

@Component({
  selector: 'app-relatar-problema-dialog',
  templateUrl: './relatar-problema-dialog.component.html',
  styleUrls: ['./relatar-problema-dialog.component.scss']
})
export class RelatarProblemaDialogComponent implements OnInit {
  public component: any;
  public codFesta: any;
  public area: any;
  public problemaTO: any;
  public date: any;

  listaProblemas: any;
  mockProblemas: any = [
    {cod_problema: 1, desc_problema: 'Tumulto'},
    {cod_problema: 2, desc_problema: 'Briga'},
    {cod_problema: 3, desc_problema: 'Emergência Médica'},
    {cod_problema: 4, desc_problema: 'Furto'},
    {cod_problema: 5, desc_problema: 'Assalto'},
    {cod_problema: 6, desc_problema: 'Porte de Objeto ou Substância Proibida'},
    {cod_problema: 7, desc_problema: 'Venda não Autorizada'},
    {cod_problema: 8, desc_problema: 'Cliente tentando sair sem pagar'},
    {cod_problema: 9, desc_problema: 'Vandalismo'},
    {cod_problema: 10, desc_problema: 'Assédio Sexual'},
    {cod_problema: 11, desc_problema: 'Atentado ao Pudor'},
    {cod_problema: 12, desc_problema: 'Entrada Não Autorizada'},
    {cod_problema: 13, desc_problema: 'Outros'},
  ];

  constructor(
    @Inject(MAT_DIALOG_DATA) data,
    public dialog: MatDialog,
    private segurancaProblemaService: SegurancaProblemasService,
    private loginService: LoginService,
    private datePipe: DatePipe
    ) {
    this.codFesta = data.codFesta;
    this.area = data.area;
    this.component = data.component;
  }

  ngOnInit() {
    console.log(this.area);
    this.segurancaProblemaService.listarProblemas().subscribe(
      (resp: any) => {
        // console.log(resp);
        this.listaProblemas = resp;
        console.log(this.listaProblemas);
        this.segurancaProblemaService.setFarol(false);
      }
    );
    // console.log(this.listaProblemas);
  }

  relatarProblema(problemaTO) {
    /*
      problemaTO:
        codFesta,
        descProblema,
        codFesta,
        codAreaSeguranca,
        codUsuarioEmissor,

     */

    this.date = new Date();

    Object.assign(
      problemaTO, // codFesta, descProblema
      {
        codFesta: this.codFesta,
        codAreaSeguranca: this.area.codArea,
        codUsuarioEmissor: this.loginService.getusuarioInfo().codUsuario,
        horarioInicio: this.datePipe.transform(this.date, 'yyyy-MM-ddThh:mm:ss')
      }
    );

    this.segurancaProblemaService.adicionarProblema(problemaTO).subscribe();
    console.log(problemaTO);
  }

}
