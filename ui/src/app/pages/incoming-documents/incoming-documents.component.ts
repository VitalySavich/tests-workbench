import { Component, OnInit } from '@angular/core';
import { DialogService } from 'primeng/dynamicdialog';
import { IncomingDocumentService } from '../service/incoming-document.service';
import { IncomingDocument } from 'src/interfaces/incoming-document';
import { TableLazyLoadEvent, TableModule } from 'primeng/table';
import { Button } from "primeng/button";
import { FormsModule } from '@angular/forms';
import { InputText } from "primeng/inputtext";
import { AutoCompleteCompleteEvent, AutoCompleteModule } from 'primeng/autocomplete';
import { SelectListItemDto } from 'src/interfaces/select-list-item-dto';
import { ContractorService } from '../service/contractor.service';
import { IncomingDocumentEditorComponent } from '@/components/incoming-document-editor/incoming-document-editor.component';
import {Router, ActivatedRoute, Params} from '@angular/router';

@Component({
    selector: 'app-incoming-documents',
    templateUrl: './incoming-documents.component.html',
    styleUrls: ['./incoming-documents.component.css'],
    providers: [DialogService],
    imports: [Button, TableModule, FormsModule, InputText, AutoCompleteModule]
})
export class IncomingDocumentsComponent {
    data!: IncomingDocument[];
    totalRows!: number;
    query = '';
    first = 0;
    pageSize = 10;
    contractor: SelectListItemDto|undefined;
    contractors!: SelectListItemDto[];
    constructor(
        private router: Router,
        private service: IncomingDocumentService,
        private dialogService: DialogService,
        private contractorService: ContractorService
    ) {}

    loadData(event: TableLazyLoadEvent) {
        if (event.first !== undefined) {
            this.first = event.first;
        }
        if (event.rows != null && event.rows !== 0) {
            this.pageSize = event.rows;
        }
        const pageNumber = this.first / this.pageSize;
        this.service.find(this.query, this.contractor?.id, pageNumber, this.pageSize).subscribe((res) => {
            this.data = res.content;
            this.totalRows = res.page.totalElements;
            if (event.forceUpdate) event.forceUpdate();
        });
    }

    edit(incomingDocumentId?: number) {
        this.dialogService
            .open(IncomingDocumentEditorComponent, {
                width: '50vw',
                modal: true,
                breakpoints: {
                    '960px': '75vw',
                    '640px': '90vw'
                },
                data: {
                    incomingDocumentId
                },
            })
            .onClose.subscribe((res) => {
                if (res) {
                    this.loadData({});
                }
            });
    }

    openTable(incomingDocumentId?: number) {
        let url = '/pages/incoming-documents/' + incomingDocumentId + '/warehouse-items';
        this.router.navigate([url]);             
    }

    delete(id: number) {
        this.service.delete(id).subscribe(() => {
            this.loadData({});
        });
    }

    completeContractors(event: AutoCompleteCompleteEvent) {
        this.contractorService.findSimple(event.query).subscribe(res=>{
            this.contractors = res;
        });
    }

    clearSelection() {
        this.contractor = undefined;
        this.loadData({});
    }
}
