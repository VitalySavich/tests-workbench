import { Component, OnInit } from '@angular/core';
import { DialogService } from 'primeng/dynamicdialog';
import { FileEditorComponent } from '@/components/file-editor/file-editor.component';
import { TableLazyLoadEvent, TableModule } from 'primeng/table';
import { Button } from "primeng/button";
import { FormsModule } from '@angular/forms';
import { InputText } from "primeng/inputtext";
import { AutoCompleteCompleteEvent, AutoCompleteModule } from 'primeng/autocomplete';
import { SelectListItemDto } from 'src/interfaces/select-list-item-dto';
import { StyleClass } from "primeng/styleclass";
import { FileService } from '../service/file.servise';
import { Result } from 'src/interfaces/result';

@Component({
    selector: 'app-files',
    templateUrl: './files.component.html',
    styleUrls: ['./files.component.css'],
    providers: [DialogService],
    imports: [Button, TableModule, FormsModule, InputText, AutoCompleteModule]
})
export class FilesComponent {
    data!: Result[];
    totalRows!: number;
    query = '';
    first = 0;
    pageSize = 10;
    constructor(
        private service: FileService,
        private dialogService: DialogService,
    ) {}

    loadData(event: TableLazyLoadEvent) {
        if (event.first !== undefined) {
            this.first = event.first;
        }
        if (event.rows != null && event.rows !== 0) {
            this.pageSize = event.rows;
        }
        const pageNumber = this.first / this.pageSize;
        this.service.find(this.query, pageNumber, this.pageSize).subscribe((res) => {
            this.data = res.content;
            this.totalRows = res.page.totalElements;
            if (event.forceUpdate) event.forceUpdate();
        });
    }

    viewDetails(resultId?: number) {
        this.dialogService
            .open(FileEditorComponent, {
                width: '50vw',
                modal: true,
                breakpoints: {
                    '960px': '75vw',
                    '640px': '90vw'
                },
                data: {
                    resultId
                },
            })
            .onClose.subscribe((res) => {
                if (res) {
                    this.loadData({});
                }
            });
    }

    delete(id: number) {
        this.service.delete(id).subscribe(() => {
            this.loadData({});
        });
    }
}
