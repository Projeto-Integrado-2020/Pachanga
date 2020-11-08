import { Component, Inject, OnInit } from '@angular/core';
import { MatDialog, MAT_DIALOG_DATA } from '@angular/material';
import { TranslateService } from '@ngx-translate/core';
import { LoginService } from 'src/app/services/loginService/login.service';
import { SegurancaProblemasService } from 'src/app/services/seguranca-problemas/seguranca-problemas.service';

@Component({
  selector: 'app-detalhes-problema-dialog',
  templateUrl: './detalhes-problema-dialog.component.html',
  styleUrls: ['./detalhes-problema-dialog.component.scss']
})
export class DetalhesProblemaDialogComponent implements OnInit {

  public detalhes: any;
  public dia: string;
  public hora: string;

  constructor(
    @Inject(MAT_DIALOG_DATA) public data,
    public modal: MatDialog,
    public translate: TranslateService,
    public segProblemaService: SegurancaProblemasService,
    public loginService: LoginService
    ) {
      this.detalhes = data.problema;
     }

  ngOnInit() {
    this.detalhes = this.data;
    console.log(this.detalhes);
    console.log(this.dataHorarioProblema());
  }

  dataHorarioProblema() {

    let str = this.detalhes.problema.horarioInicio;
    str = str.split('T');
    const dia = str[0].split('-').reverse().join('/');
    const hora = str[1].slice(0, 8);
    this.dia = dia;
    this.hora = hora;
  }

  /*
    private int codAreaProblema;
	  private int codAreaSeguranca;
	  private int codFesta;
	  private int codProblema;
  	private int codUsuarioResolv;
    private String statusProblema ;
  */

  alterarStatus(finaliza: boolean, status: string) {
    this.detalhes.problema.statusProblema = status;
    this.detalhes.problema.codUsuarioResolv = this.loginService.getusuarioInfo().codUsuario;
    console.log(this.detalhes.problema);
    this.segProblemaService.alterarStatus(finaliza, this.detalhes.problema).subscribe(
      () => {
        this.segProblemaService.setFarol(false);
      });
  }

}
