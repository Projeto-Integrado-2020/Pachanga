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
    this.atualizarProblemas();
  }

  atualizarProblemas() {
    this.segurancaProblemaService.updateProblemas();
  }

}
