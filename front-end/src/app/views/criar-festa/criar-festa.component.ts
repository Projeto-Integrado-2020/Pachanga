import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, FormControl, Validators } from '@angular/forms';
import { CadastrarFestaService } from 'src/app/services/cadastro-festa/cadastrar-festa.service';
import { Router } from '@angular/router';
import { GetCategoriasService } from 'src/app/services/get-categorias/get-categorias.service';
import { LoginService } from 'src/app/services/loginService/login.service';
import { FileValidator } from 'ngx-material-file-input';

@Component({
  selector: 'app-criar-festa',
  templateUrl: './criar-festa.component.html',
  styleUrls: ['./criar-festa.component.scss']
})
export class CriarFestaComponent implements OnInit {

  public form: FormGroup;
  minDate = new Date();
  categorias = [];
  urlNoImage = 'https://xtremebike.com.br/website/images/product/1.jpg';
  imagem = this.urlNoImage;

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
      organizador: new FormControl(this.loginService.usuarioInfo.nomeUser, Validators.required),
      descOrganizador: new FormControl('', Validators.required),
      imagem: new FormControl(null, FileValidator.maxContentSize(2097152))
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
      horarioInicioFesta: dataInicio.slice(6, 10) + '-' + dataInicio.slice(3, 5) + '-' + dataInicio.slice(0, 2) + ' ' + horaInicio + ':00',
      horarioFimFesta: dataFim.slice(6, 10) + '-' + dataFim.slice(3, 5) + '-' + dataFim.slice(0, 2) + ' ' + horaFim + ':00',
      descricaoFesta,
      codEnderecoFesta,
      codPrimaria: categoriaPrincipal,
      codSecundaria: categoriaSecundaria == null ? 0 : categoriaSecundaria,
      descOrganizador
    };
    this.callService(dadosFesta, this.form.get('imagem').value);
  }

  callService(dadosFesta, imagem) {
    this.festaService.cadastrarFesta(dadosFesta, imagem).subscribe((resp: any) => {
      this.festaService.setFarol(false);
      const nomeFesta = resp.nomeFesta.toLowerCase().replace('-', '').replace('–', '')
                                    .replace(/\s+/g, '-').replace('ç', 'c')
                                    .replace('º', '').replace('ª', '')
                                    .normalize('NFD').replace(/[\u0300-\u036f]/g, '')
                                    .replace(/[`~!@#$%^&*()_|+\=?;:'",.<>\{\}\[\]\\\/]/gi, '');
      this.router.navigate(['festas/' + nomeFesta + '&' + resp.codFesta + '/painel/']);
    });
  }

  alterarPreview() {
    if (this.form.get('imagem').value) {
      const arquivo = this.form.get('imagem').value._files[0];
      const reader = new FileReader();
      reader.readAsDataURL(arquivo);

      reader.onload = (event: any) => { // called once readAsDataURL is completed
        this.imagem = event.target.result;
      };
    } else {
      this.imagem = this.urlNoImage;
    }
  }

  excluirInputImagem() {
    this.form.get('imagem').setValue(null);
    this.imagem = this.urlNoImage;
    const fileInput = document.querySelector('ngx-mat-file-input[formcontrolname="imagem"] input[type="file"]')  as HTMLInputElement;
    fileInput.value = null;
  }

}
