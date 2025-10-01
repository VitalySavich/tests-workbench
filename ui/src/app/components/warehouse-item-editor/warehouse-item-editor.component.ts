import { WarehouseItemService } from '@/pages/service/warehouse-item.service';
import { WarehouseItemTypeService } from '@/pages/service/warehouse-item-type.service';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AutoCompleteCompleteEvent, AutoComplete } from 'primeng/autocomplete';
import { DynamicDialogConfig, DynamicDialogRef } from 'primeng/dynamicdialog';
import { WarehouseItem } from 'src/interfaces/warehouse-item';
import { WarehouseItemType } from 'src/interfaces/warehouse-item-type';
import { FloatLabel } from "primeng/floatlabel";
import { InputText } from "primeng/inputtext";
import { Button } from "primeng/button";
import { EditorModule } from 'primeng/editor';
@Component({
    selector: 'app-warehouse-item-editor',
    templateUrl: './warehouse-item-editor.component.html',
    styleUrls: ['./warehouse-item-editor.component.css'],
    imports: [FloatLabel, InputText, Button, FormsModule, ReactiveFormsModule, AutoComplete, EditorModule]
})
export class WarehouseItemEditorComponent implements OnInit {
    incomingDocumentId!: number;
    warehouseItemId!: number;
    formGroup: FormGroup;
    warehouseItemTypes!: WarehouseItemType[];

    constructor(
        private config: DynamicDialogConfig,
        private ref: DynamicDialogRef,
        private service: WarehouseItemService,
        private warehouseItemTypeService: WarehouseItemTypeService,
        private fb: FormBuilder
    ) {
        this.formGroup = fb.group({
            warehouseItemType: [undefined, Validators.required],
            quantity: [undefined, Validators.required],
            price: [undefined, Validators.required],
            amount: [undefined, Validators.required]
        });
    }

    ngOnInit() {
        this.incomingDocumentId = this.config.data.incomingDocumentId;
        this.warehouseItemId = this.config.data.warehouseItemId;
        if(this.warehouseItemId) {
            this.service.findById(this.warehouseItemId).subscribe(c=>{
                this.formGroup.patchValue(c);
            });
        }
    }

    save() {
        const wi = this.formGroup.value as WarehouseItem;
        if (this.warehouseItemId) {
            this.service.update(this.warehouseItemId, wi).subscribe(()=>{
                this.ref.close(true);
            });
        } else {
            this.service.createForIncomingDocument(wi, this.incomingDocumentId).subscribe(()=>{
                this.ref.close(true);
            });
        }
    }

    cancel() {
        this.ref.close(false);
    }

    completeWarehouseItemTypes(event: AutoCompleteCompleteEvent) {
        this.warehouseItemTypeService.findSimple(event.query).subscribe(res=>{
            this.warehouseItemTypes = res;
        });
    }

    clearSelection() {
        this.formGroup.controls["warehouseItemType"].setValue(undefined);
    }
}
