import {
  Directive, 
  ElementRef,
  Output,
  HostListener,
  EventEmitter,
  OnInit 
} from '@angular/core';
import { fromEvent, from } from 'rxjs';
import { take } from 'rxjs/operators';

@Directive({
  selector: '[clicarFora]'
})
export class ClicarForaDirective implements OnInit {

  @Output()
  public clicarFora = new EventEmitter();

  captured = false;

  constructor(private _elementRef: ElementRef) { }

  @HostListener('document:click', ['$event.target'])
  onClick(target) {
    if (!this.captured) {
      return;
  }

    if(!this._elementRef.nativeElement.contains(target)) {
      this.clicarFora.emit();
    }
  }

  ngOnInit() {
    fromEvent(document, 'click', { capture: true}).pipe(take(1)).subscribe(() => this.captured = true)
  }

}
