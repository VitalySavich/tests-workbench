import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { PageModel } from 'src/interfaces/page-model';
import { Result } from 'src/interfaces/result';
import { SelectListItemDto } from 'src/interfaces/select-list-item-dto';
import { AbstractCrudService } from 'src/services/AbstractCrudService';

@Injectable({
  providedIn: 'root'
})
export class FileService extends AbstractCrudService<Result> {
    private readonly _subUrl: string;
    private readonly _uploadUrl: string;
    constructor(protected override http: HttpClient) {
        super(http);
        this._subUrl = environment.apiBaseUrl + '/files/history';
        this._uploadUrl = environment.apiBaseUrl + '/files/analyze';
    }
    protected override entityUrl(): string {
        return this._subUrl;
    }

    public find(query = '', page?: number, size?: number): Observable<PageModel<Result>> {
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

        return this.http.get<PageModel<Result>>(this._subUrl, { params });
    }

    public findSimple(query = ''): Observable<SelectListItemDto[]> {
        let params = new HttpParams();
        if (query) {
            params = params.set('query', query);
        }
        return this.http.get<SelectListItemDto[]>(this._subUrl + '/simple', { params });
    }

    public upload(formData: FormData): Observable<Object> {
        return this.http.post<string>(this._uploadUrl, formData);
    }    

}
