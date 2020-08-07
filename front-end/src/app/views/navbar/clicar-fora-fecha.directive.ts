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
/* tslint:disable */
@Directive({
  selector: '[clicarFora]'
})
/* tslint:enable */
export class ClicarForaDirective implements OnInit {

  @Output()
  public clicarFora = new EventEmitter();

  captured = false;

  constructor(private elementRef: ElementRef) { }

  @HostListener('document:click', ['$event.target'])
  onClick(target) {
    if (!this.captured) {
      return;
  }

    if (!this.elementRef.nativeElement.contains(target)) {
      this.clicarFora.emit();
    }
  }

  ngOnInit() {
    fromEvent(document, 'click', { capture: true}).pipe(take(1)).subscribe(() => this.captured = true);
  }

}
