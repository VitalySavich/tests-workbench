import { environment } from 'src/environments/environment';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AbstractCrudService } from 'src/services/AbstractCrudService';
import { Warehouse } from 'src/interfaces/warehouse';
import { Observable } from 'rxjs';
import { PageModel } from 'src/interfaces/page-model';
import { SelectListItemDto } from 'src/interfaces/select-list-item-dto';
@Injectable({
    providedIn: 'root'
})
export class WarehouseService extends AbstractCrudService<Warehouse> {
    readonly _subUrl: string;

    constructor(protected override http: HttpClient) {
        super(http);
        this._subUrl = `${environment.apiBaseUrl}/warehouses`;
    }

    protected override entityUrl(): string {
        return this._subUrl;
    }

    find(query = '', page?: number, size?: number): Observable<PageModel<Warehouse>> {
        let params = new HttpParams();
        if (query) {
            params = params.set('query', query);
        }
        if (page!==undefined) {
            params = params.append('page', page);
        }
        if (size!==undefined) {
            params = params.append('size', size);
        } else {
            params = params.append('size', 1000);
        }


        return this.http.get<PageModel<Warehouse>>(this._subUrl, {
            params
        });
    }

    public findSimple(query = ''): Observable<SelectListItemDto[]> {
        let params = new HttpParams();
        if (query) {
            params = params.set('query', query);
        }
        return this.http.get<SelectListItemDto[]>(this._subUrl + '/simple', { params });
    }

    switchWarehouse(warehouseId: number) {
        return this.http.post(`${this._subUrl}/${warehouseId}/switch`, {});
    }
}
