import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormControl, Validators } from '@angular/forms';
import { MatDialog, MAT_DIALOG_DATA } from '@angular/material';
import { LoginService } from 'src/app/services/loginService/login.service';
import { SegurancaProblemasService } from 'src/app/services/seguranca-problemas/seguranca-problemas.service';

@Component({
  selector: 'app-alerta-seguranca',
  templateUrl: './alerta-seguranca.component.html',
  styleUrls: ['./alerta-seguranca.component.scss']
})
export class AlertaSegurancaComponent implements OnInit {

  notificacao: any;
  form: any;

  constructor(@Inject(MAT_DIALOG_DATA) data, public formBuilder: FormBuilder,
              public loginService: LoginService, public segProblemaService: SegurancaProblemasService,
              public modal: MatDialog) {
    this.notificacao = data.alerta;
  }

  ngOnInit() {
    this.form = this.formBuilder.group({
      observacaoSolucao: new FormControl('', Validators.required),
    });
    console.log(this.notificacao);
  }

  get f() { return this.form.controls; }

  getUrlFesta() {
    const codFesta = this.notificacao.notificacaoArea.codFesta;
    const nomeFesta = this.notificacao.notificacaoArea.nomeFesta.toLowerCase().replace('-', '').replace('–', '')
                        .replace(/\s+/g, '-').replace('ç', 'c')
                        .replace('º', '').replace('ª', '')
                        .normalize('NFD').replace(/[\u0300-\u036f]/g, '')
                        .replace(/[`~!@#$%^&*()_|+\=?;:'",.<>\{\}\[\]\\\/]/gi, '');
    const url = '../festas/' + nomeFesta + '&' + codFesta + '/painel-seguranca';
    return url;
  }

  alterarStatus(status: string) {
    const problema = {
      codAreaProblema: this.notificacao.notificacaoArea.codAreaProblema,
      codAreaSeguranca: this.notificacao.notificacaoArea.codArea,
      codFesta: this.notificacao.notificacaoArea.codFesta,
      codProblema: this.notificacao.notificacaoArea.codProblema,
      observacaoSolucao: this.form.get('observacaoSolucao').value,
      statusProblema: status,
      codUsuarioResolv: this.loginService.getusuarioInfo().codUsuario
    };
    this.segProblemaService.alterarStatus(true, problema).subscribe(() => {
        this.segProblemaService.setFarol(false);
        this.modal.closeAll();
      });
  }

}
