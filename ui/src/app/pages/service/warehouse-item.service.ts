import { AbstractCrudService } from 'src/services/AbstractCrudService';
import { Injectable } from '@angular/core';
import { WarehouseItem } from 'src/interfaces/warehouse-item';
import { HttpClient, HttpParams } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { PageModel } from 'src/interfaces/page-model';
import { Observable } from 'rxjs';

@Injectable({
    providedIn: 'root'
})
export class WarehouseItemService extends AbstractCrudService<WarehouseItem> {
    readonly _subUrl: string;
    constructor(protected override http: HttpClient) {
        super(http);
        this._subUrl = environment.apiBaseUrl + '/warehouse-items';
    }

    protected override entityUrl(): string {
        return this._subUrl;
    }

    public find(query = '', page?: number, size?:number) {
        
        let params = new HttpParams();
        if(query) {
            params = params.append('query', query);
        }
        if (page!==undefined) {
            params = params.append('page', page);
        }
        if (size!==undefined) {
            params = params.append('size', size);
        } else {
            params = params.append('size', 100);
        }

        return this.http.get<PageModel<WarehouseItem>>(this._subUrl, {
            params
        });
    }

    public findByIncomingDocumentId(incomingDocumentId: number): Observable<WarehouseItem[]> {
        let params = new HttpParams();
        let url = environment.apiBaseUrl + '/incoming-documents/' + incomingDocumentId + '/warehouse-items';
        return this.http.get<WarehouseItem[]>(url, { params });
    }

    public createForIncomingDocument(entity: WarehouseItem, incomingDocumentId: number): Observable<number> {
        let params = new HttpParams();
        params = params.append('incomingDocumentId', incomingDocumentId);

        return this.http.post<number>(`${this.entityUrl()}`, entity, { params });
    }

    public createWarehouseOperation(entity: WarehouseItem, incomingDocumentId: number): Observable<number> {
        let params = new HttpParams();
        let url = environment.apiBaseUrl + '/warehouse-operations/create-operation';
        params = params.append('incomingDocumentId', incomingDocumentId);

        return this.http.post<number>(url, entity, { params });
    }
}
