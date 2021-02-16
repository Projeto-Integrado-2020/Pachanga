import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
    name: 'permissionFilter',
    pure: false
})
export class PermissionFilter implements PipeTransform {
    transform(items: any[], filter: any): any {
        if (!items || !filter) {
            return items;
        }
        // filter items array, items which match and return true will be
        // kept, false will be filtered out
        return items.filter(item => item.tipPermissao.indexOf(filter) !== -1);
    }
}
