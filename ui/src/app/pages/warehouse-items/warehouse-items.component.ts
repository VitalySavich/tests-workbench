import { WarehouseItemService } from './../service/warehouse-item.service';
import { Component } from '@angular/core';
import { DialogService } from 'primeng/dynamicdialog';
import { TableLazyLoadEvent, TableModule, TablePageEvent } from 'primeng/table';
import { WarehouseItem } from 'src/interfaces/warehouse-item';
import { Button } from 'primeng/button';
import { FormsModule } from '@angular/forms';
import { InputText } from 'primeng/inputtext';
import { WarehouseItemEditorComponent } from '@/components/warehouse-item-editor/warehouse-item-editor.component';
import { AutoCompleteCompleteEvent, AutoCompleteModule } from 'primeng/autocomplete';
import {Router, ActivatedRoute, Params} from '@angular/router';
import { IncomingDocumentService } from '../service/incoming-document.service';

@Component({
    selector: 'app-warehouse-items',
    templateUrl: './warehouse-items.component.html',
    styleUrls: ['./warehouse-items.component.css'],
    providers: [DialogService],
    imports: [TableModule, Button, FormsModule, InputText, AutoCompleteModule]
})
export class WarehouseItemsComponent {
    data!: WarehouseItem[];
    incomingDocumentId!: number;
    query = '';
    pageSize = 10;
    pageNumber = 0;
    first = 0;
    warehouseItems!: WarehouseItem[];
    totalRows!: number;
    constructor(
        private router: Router,
        private activatedRoute: ActivatedRoute,
        private service: WarehouseItemService,
        private dialogService: DialogService
    ) {     
    }

    loadWarehouseItems(event: TableLazyLoadEvent) {
        this.activatedRoute.paramMap
        .subscribe( params => { this.incomingDocumentId = Number(params.get('id')) });
  
        if (event.first !== undefined) {
            this.first = event.first;
        }
        if (event.rows != null && event.rows !== 0) {
            this.pageSize = event.rows;
        }
        const pageNumber = this.first / this.pageSize;
        /*this.service.find(this.query, pageNumber, this.pageSize).subscribe((res) => {
            this.warehouseItems = res.content;
            this.totalRows = res.page.totalElements;
            if (event.forceUpdate) event.forceUpdate();
        });*/

        this.service.findByIncomingDocumentId(this.incomingDocumentId).subscribe((res) => {
            this.warehouseItems = res;
            if (event.forceUpdate) event.forceUpdate();
        });
    }   

    edit(incomingDocumentId?: number, warehouseItemId?: number) {
        this.dialogService
            .open(WarehouseItemEditorComponent, {
                width: '50vw',
                modal: true,
                breakpoints: {
                    '960px': '75vw',
                    '640px': '90vw'
                },
                data: {
                    incomingDocumentId,
                    warehouseItemId                    
                },
            })
            .onClose.subscribe((res) => {
                if (res) {
                    this.loadWarehouseItems({});
                }
            });
    }

    delete(id: number) {
        this.service.delete(id).subscribe(() => {
            let event = {};
            this.loadWarehouseItems(event);
        });
    }

    accept(item: WarehouseItem) {
        this.service.createWarehouseOperation(item, this.incomingDocumentId).subscribe(()=>{                
        });
    }

    cancel(item: WarehouseItem) {
    }
}
