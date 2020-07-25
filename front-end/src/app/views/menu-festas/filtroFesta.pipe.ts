import { Pipe, PipeTransform } from '@angular/core';

@Pipe({name: 'filtroFesta'})
export class FiltroFestaPipe implements PipeTransform{
    transform(festasMostradas: any, buscaPorNome: string): any {
        if (!festasMostradas || !buscaPorNome){
            return festasMostradas;
        }

        return festasMostradas.filter(festa =>
            festa.nomeFesta.toLowerCase().indexOf(buscaPorNome.toLowerCase()) !== -1);
    }
}
