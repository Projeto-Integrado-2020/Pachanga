import { animate, state, style, transition, trigger } from '@angular/animations';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-controle-sidenav',
  templateUrl: './controle-sidenav.component.html',
  styleUrls: ['./controle-sidenav.component.scss'],
  animations: [
    // sidenav
    trigger('openClose', [
      state('aberto', style({
        left: '0'
      })),

      state('fechado', style({
        left: '0',
        transform: 'translateX(-100%)'
      })),

      transition('aberto => fechado', [
        animate('.3s')
      ]),

      transition('fechado => aberto', [
        animate('.3s')
      ] )
    ]),
    // botao desktop
    trigger('btn-desktop', [
      state('aberto', style({
        // right: '-40rem',
        // transform: 'translateX(-2.8rem)'
        left: '16rem'
      })),

      state('fechado', style({
        // right: '-2rem',
        transform: 'rotate(180deg)'// transform: 'translateX(3rem)'
      })),

      transition('aberto => fechado', [
        animate('.3s')
      ] ),

      transition('fechado => aberto', [
        animate('.3s')
      ] )

    ]),

    // botao mobile
    trigger('btn-mobile', [
      state('aberto', style({
        left: 'calc(100% - 3rem)',
        transform: 'rotate(180deg)'
      })),

      state('fechado', style({
        right: '0',
        transform: 'translateX(3rem)'
      })),

      transition('aberto => fechado', [
        animate('.3s')
      ] ),

      transition('fechado => aberto', [
        animate('.3s')
      ] )

    ])
  ]
})
export class ControleSidenavComponent implements OnInit {

  isOpen = window.innerWidth > 748;

  constructor() { }

  ngOnInit() {
  }

  toggle() {
    this.isOpen = !this.isOpen;
  }

}
