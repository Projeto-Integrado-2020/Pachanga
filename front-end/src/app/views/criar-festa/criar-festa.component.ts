import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, FormControl, Validators } from '@angular/forms';
import { CadastrarFestaService } from 'src/app/services/cadastro-festa/cadastrar-festa.service';
import { Router } from '@angular/router';
import { GetCategoriasService } from 'src/app/services/get-categorias/get-categorias.service';
import { LoginService } from 'src/app/services/loginService/login.service';

@Component({
  selector: 'app-criar-festa',
  templateUrl: './criar-festa.component.html',
  styleUrls: ['./criar-festa.component.scss']
})
export class CriarFestaComponent implements OnInit {

  public form: FormGroup;
  minDate = new Date();
  categorias = [];

  constructor(
    public formBuilder: FormBuilder,
    public festaService: CadastrarFestaService,
    public router: Router,
    public getCategoria: GetCategoriasService,
    public loginService: LoginService
    ) { }

  ngOnInit() {
    this.buildForm();
    this.resgatarCategorias();
  }

  buildForm() {
    this.form = this.formBuilder.group({
      nomeFesta: new FormControl('', Validators.required),
      descFesta: new FormControl('', Validators.required),
      endereco: new FormControl('', Validators.required),
      inicioData: new FormControl('', Validators.required),
      fimData: new FormControl('', Validators.required),
      inicioHora: new FormControl('', Validators.required),
      fimHora: new FormControl('', Validators.required),
      categoriaPrincipal: new FormControl('', Validators.required),
      categoriaSecundaria: new FormControl(''),
      organizador: new FormControl('', Validators.required),
      descOrganizador: new FormControl('', Validators.required),
    });
  }

  resgatarCategorias() {
    this.getCategoria.getCategorias().subscribe((resp: any) => {
      this.getCategoria.setFarol(false);
      this.categorias = resp;
    });
  }

  get f() { return this.form.controls; }

  adicionarFesta(nomeFesta, descricaoFesta, codEnderecoFesta, dataInicio, horaInicio, dataFim, horaFim,
                 categoriaPrincipal, categoriaSecundaria, organizador, descOrganizador) {
    const dadosFesta = {
      nomeFesta,
      statusFesta: null,
      organizador,
      horarioInicioFesta: dataInicio.slice(6, 10) + '-' + dataInicio.slice(3, 5) + '-' + dataInicio.slice(0, 2) + 'T' + horaInicio,
      horarioFimFesta: dataFim.slice(6, 10) + '-' + dataFim.slice(3, 5) + '-' + dataFim.slice(0, 2) + 'T' + horaFim,
      descricaoFesta,
      codEnderecoFesta,
      codPrimaria: categoriaPrincipal,
      codSecundaria: categoriaSecundaria == null ? 0 : categoriaSecundaria,
      descOrganizador
    };
    this.callService(dadosFesta);
  }

  callService(dadosFesta) {
    this.festaService.cadastrarFesta(dadosFesta).subscribe((resp: any) => {
      this.festaService.setFarol(false);
      const nomeFesta = resp.nomeFesta.toLowerCase().replace('-', '').replace('–', '')
                                    .replace(/\s+/g, '-').replace('ç', 'c')
                                    .replace('º', '').replace('ª', '')
                                    .normalize('NFD').replace(/[\u0300-\u036f]/g, '')
                                    .replace(/[`~!@#$%^&*()_|+\=?;:'",.<>\{\}\[\]\\\/]/gi, '');
      this.router.navigate(['festas/' + nomeFesta + '&' + resp.codFesta + '/painel/']);
    });
  }

}
