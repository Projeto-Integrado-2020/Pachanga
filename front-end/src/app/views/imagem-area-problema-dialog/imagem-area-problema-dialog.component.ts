import { Component, Inject, OnInit } from '@angular/core';
import { MatDialog, MAT_DIALOG_DATA } from '@angular/material';
import { FileInput } from 'ngx-material-file-input';
import { LoginService } from 'src/app/services/loginService/login.service';
import { SegurancaProblemasService } from 'src/app/services/seguranca-problemas/seguranca-problemas.service';
import { ProcessingDialogComponent } from '../processing-dialog/processing-dialog.component';

@Component({
  selector: 'app-imagem-area-problema-dialog',
  templateUrl: './imagem-area-problema-dialog.component.html',
  styleUrls: ['./imagem-area-problema-dialog.component.scss']
})
export class ImagemAreaProblemaDialogComponent implements OnInit {

  public detalhes: any;
  public problemaSeguranca: any = null;
  urlNoImage = 'https://xtremebike.com.br/website/images/product/1.jpg';
  imagem = this.urlNoImage;

  constructor(@Inject(MAT_DIALOG_DATA) public data, public modal: MatDialog, public segProblemaService: SegurancaProblemasService,
              public loginService: LoginService) {
                this.detalhes = data.detalhes;
              }

  ngOnInit() {
    console.log(this.detalhes.problema);
    this.getProblemaSeguranca();
  }

  openDialogProcessing() {
    this.modal.open(ProcessingDialogComponent, {
        width: '20rem',
        disableClose: true,
        data: {
            tipo: 'CARREGIMAGEM'
        }
    });
  }

  getProblemaSeguranca() {
    this.segProblemaService.getProblemaSeguranca(this.detalhes.problema.codAreaProblema, this.detalhes.problema.codFesta).
    subscribe((resp: any) => {
        this.segProblemaService.setFarol(false);
        this.problemaSeguranca = resp;
        const base64Data = this.problemaSeguranca.imagemProblema;
        const imagem = this.dataURItoBlob(base64Data);
        const fileInput = new FileInput([new File([imagem], 'upload.jpg', { type: 'image/jpeg' })]);
        this.problemaSeguranca.imagemProblema = fileInput;
        this.alterarPreview();
      }
    );
  }

  dataURItoBlob(dataURI) {
    const byteString = window.atob(dataURI.replace(/^[^,]+,/, ''));
    const arrayBuffer = new ArrayBuffer(byteString.length);
    const int8Array = new Uint8Array(arrayBuffer);
    for (let i = 0; i < byteString.length; i++) {
      int8Array[i] = byteString.charCodeAt(i);
    }
    const blob = new Blob([int8Array], { type: 'image/jpeg' });
    return blob;
  }

  alterarPreview() {
    if (this.problemaSeguranca.imagemProblema) {
      const arquivo = this.problemaSeguranca.imagemProblema._files[0];
      const reader = new FileReader();
      reader.readAsDataURL(arquivo);

      reader.onload = (event: any) => { // called once readAsDataURL is completed
        this.imagem = event.target.result;
      };
    } else {
      this.imagem = this.urlNoImage;
    }
  }

}
