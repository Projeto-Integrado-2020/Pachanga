import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, FormControl, Validators } from '@angular/forms';
import { EditarFestaService } from 'src/app/services/editar-festa/editar-festa.service';
import { GetFestaService } from 'src/app/services/get-festa/get-festa.service';
import { SuccessDialogComponent } from '../../views/success-dialog/success-dialog.component';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { GetCategoriasService } from 'src/app/services/get-categorias/get-categorias.service';
import { FileInput, FileValidator } from 'ngx-material-file-input';
import { ProcessingDialogComponent } from '../processing-dialog/processing-dialog.component';

@Component({
  selector: 'app-editar-festa',
  templateUrl: './editar-festa.component.html',
  styleUrls: ['./editar-festa.component.scss']
})
export class EditarFestaComponent implements OnInit {

  constructor(public formBuilder: FormBuilder, public festaService: EditarFestaService,
              public router: Router, public dialog: MatDialog, public getFestaService: GetFestaService,
              public getCategoria: GetCategoriasService) { }

  public form: FormGroup;
  minDate: Date;
  festa: any;
  categorias = [];
  urlNoImage = 'https://res.cloudinary.com/htctb0zmi/image/upload/v1611182093/pachanga-logo.png';
  imagem = this.urlNoImage;

  ngOnInit() {
    this.resgatarFesta();
    this.resgatarCategorias();
    this.gerarForm();
  }

  gerarForm() {
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

  resgatarFesta() {
    let idFesta = this.router.url;
    idFesta = idFesta.slice(idFesta.indexOf('&') + 1, idFesta.indexOf('/', idFesta.indexOf('&')));
    this.callServiceGet(idFesta);
  }

  atualizarFesta(nomeFesta, descricaoFesta, codEnderecoFesta, dataInicio, horaInicio, dataFim,
                 horaFim, organizador, descOrganizador) {
    this.openDialogProcessing();
    const dadosFesta = {
      codFesta: this.festa.codFesta,
      nomeFesta,
      statusFesta: null,
      organizador,
      horarioInicioFesta: dataInicio.slice(6, 10) + '-' + dataInicio.slice(3, 5) + '-' + dataInicio.slice(0, 2) + ' ' + horaInicio + ':00',
      horarioFimFesta: dataFim.slice(6, 10) + '-' + dataFim.slice(3, 5) + '-' + dataFim.slice(0, 2) + ' ' + horaFim + ':00',
      descricaoFesta,
      codEnderecoFesta,
      codPrimaria: this.f.categoriaPrincipal.value,
      codSecundaria: this.f.categoriaSecundaria.value == null ? 0 : this.f.categoriaSecundaria.value,
      descOrganizador
    };
    this.callServiceAtualizacao(dadosFesta, this.form.get('imagem').value);
  }

  callServiceAtualizacao(dadosFesta, imagem) {
    this.festaService.atualizarFesta(dadosFesta, imagem).subscribe((resp: any) => {
      this.festaService.setFarol(false);
      this.dialog.closeAll();
      this.openDialogSuccess('FESTAALT');
    });
  }

  callServiceGet(idFesta) {
    this.getFestaService.acessarFesta(idFesta).subscribe((resp: any) => {
      this.getFestaService.setFarol(false);
      this.festa = resp;
      this.setFormValues();
    });
  }

  openDialogSuccess(message: string) {
    this.dialog.open(SuccessDialogComponent, {
      width: '20rem',
      data: {message}
    });
  }

  getTimeFromDTF(date) {
    return date.split(' ')[1].slice(0, 5);
  }

  setFormValues() {
    this.f.nomeFesta.setValue(this.festa.nomeFesta);
    this.f.descFesta.setValue(this.festa.descricaoFesta);
    this.f.endereco.setValue(this.festa.codEnderecoFesta);
    this.f.organizador.setValue(this.festa.organizador);
    this.f.descOrganizador.setValue(this.festa.descOrganizador);
    this.f.categoriaPrincipal.setValue(this.festa.categoriaPrimaria.codCategoria.toString());
    this.f.categoriaSecundaria.setValue(this.festa.categoriaSecundaria === null ?
                                        undefined : this.festa.categoriaSecundaria.codCategoria.toString());
    this.f.inicioData.setValue(new Date(this.festa.horarioInicioFesta));
    this.f.fimData.setValue(new Date(this.festa.horarioFimFesta));
    this.f.inicioHora.setValue(this.getTimeFromDTF(this.festa.horarioInicioFesta));
    this.f.fimHora.setValue(this.getTimeFromDTF(this.festa.horarioFimFesta));
    if (new Date() > new Date(this.festa.horarioInicioFesta)) {
      this.minDate = new Date(this.festa.horarioInicioFesta);
    }
    if (this.festa.urlImagem) {
      const fileInput = new FileInput([new File([this.festa.urlImagem], 'upload.jpg', { type: 'image/jpeg' })]);
      this.f.imagem.patchValue(fileInput);
      this.carregarImagem();
    }
  }

  openDialogProcessing() {
    this.dialog.open(ProcessingDialogComponent, {
        width: '20rem',
        disableClose: true,
        data: {
            tipo: 'EDITFESTA'
        }
    });
  }

  carregarImagem() {
    if (this.form.get('imagem').value) {
      this.imagem = this.festa.urlImagem;
    } else {
      this.imagem = this.urlNoImage;
    }
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
    const fileInput = document.querySelector('ngx-mat-file-input[formcontrolname="imagem"] input[type="file"]') as HTMLInputElement;
    fileInput.value = null;
  }

}
