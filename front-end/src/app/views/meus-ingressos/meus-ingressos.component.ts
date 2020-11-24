import { Component, OnInit } from '@angular/core';
import { IngressosService } from 'src/app/services/ingressos/ingressos.service';

@Component({
  selector: 'app-meus-ingressos',
  templateUrl: './meus-ingressos.component.html',
  styleUrls: ['./meus-ingressos.component.scss']
})
export class MeusIngressosComponent implements OnInit {

  infoIngresso: any = [
    {
    endereco: 'Rua das Caneleiras, 27',
    data: '21:00, 28 de Janeiro de 2019'
    },
    {
      endereco: 'Rua Asasd, 237',
      data: '8:00, 16 de Janeiro de 2019'
    }
  ];
  constructor(private ingressosService: IngressosService) { }

  ngOnInit() {
    this.printarMetodo1();
  }

  printarMetodo1() {
    this.ingressosService.metodo1().subscribe(
      (res) => {
        console.log(res);
      }
    );
  }

}
