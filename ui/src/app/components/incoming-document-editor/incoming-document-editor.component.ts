import { IncomingDocumentService } from '@/pages/service/incoming-document.service';
import { ContractService } from '@/pages/service/contract.service';
import { ContractorService } from '@/pages/service/contractor.service';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AutoCompleteCompleteEvent, AutoComplete } from 'primeng/autocomplete';
import { DynamicDialogConfig, DynamicDialogRef } from 'primeng/dynamicdialog';
import { Contract } from 'src/interfaces/contract';
import { IncomingDocument } from 'src/interfaces/incoming-document';
import { SelectListItemDto } from 'src/interfaces/select-list-item-dto';
import { FloatLabel } from "primeng/floatlabel";
import { InputText } from "primeng/inputtext";
import { Button } from "primeng/button";
import { DatePicker } from "primeng/datepicker";
import { EditorModule } from 'primeng/editor';
@Component({
    selector: 'app-incoming-document-editor',
    templateUrl: './incoming-document-editor.component.html',
    styleUrls: ['./incoming-document-editor.component.css'],
    imports: [FloatLabel, InputText, Button, FormsModule, ReactiveFormsModule, AutoComplete, DatePicker, EditorModule]
})
export class IncomingDocumentEditorComponent implements OnInit {
    incomingDocumentId!: number;
    formGroup: FormGroup;
    contractors!: SelectListItemDto[];
    contracts!: SelectListItemDto[];

    constructor(
        private config: DynamicDialogConfig,
        private ref: DynamicDialogRef,
        private service: IncomingDocumentService,
        private contractorService: ContractorService,
        private contractService: ContractService,
        private fb: FormBuilder
    ) {
        this.formGroup = fb.group({
            contractor: [undefined, Validators.required],            
            incomingDocumentNumber: [undefined, Validators.required],
            incomingDocumentDate: [undefined, Validators.required],
            contractNumber: [undefined, Validators.required],
            description: [undefined]
        });
    }

    ngOnInit() {
        this.incomingDocumentId = this.config.data.incomingDocumentId;
        if(this.incomingDocumentId) {
            this.service.findById(this.incomingDocumentId).subscribe(c=>{
                this.formGroup.patchValue(c);
            });
        }
    }

    save() {
        const idoc = this.formGroup.value as IncomingDocument;
        if (this.incomingDocumentId) {
            this.service.update(this.incomingDocumentId, idoc).subscribe(()=>{
                this.ref.close(true);
            });
        } else {
            this.service.create(idoc).subscribe(()=>{
                this.ref.close(true);
            });
        }
    }

    cancel() {
        this.ref.close(false);
    }

    completeContractors(event: AutoCompleteCompleteEvent) {
        this.contractorService.findSimple(event.query).subscribe(res=>{
            this.contractors = res;
        });
    }

    clearSelection() {
        this.formGroup.controls["contractor"].setValue(undefined);
    }

    /*completeContracts(event: AutoCompleteCompleteEvent) {
        this.contractService.findSimple(event.query).subscribe(res=>{
            this.contracts = res;
        });
    }

    clearContractsSelection() {
        this.formGroup.controls["contract"].setValue(undefined);
    }*/
}
