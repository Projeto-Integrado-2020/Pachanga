import { Pipe, PipeTransform } from '@angular/core';

@Pipe({name: 'filtroFesta'})
export class FiltroFestaPipe implements PipeTransform {
    transform(festasMostradas: any, buscaPorNome: string, buscaPorAdmin: boolean): any {
        if (buscaPorNome) {
            festasMostradas = festasMostradas.filter(festa =>
                festa.nomeFesta.toLowerCase().indexOf(buscaPorNome.toLowerCase()) !== -1);
        }
        if (buscaPorAdmin) {
            festasMostradas = festasMostradas.filter(festa =>
                festa.isOrganizador);
        }
        return festasMostradas;
    }
}
