import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AutoCompleteCompleteEvent, AutoComplete } from 'primeng/autocomplete';
import { DynamicDialogConfig, DynamicDialogRef } from 'primeng/dynamicdialog';
import { FloatLabel } from "primeng/floatlabel";
import { InputText } from "primeng/inputtext";
import { Button } from "primeng/button";
import { DatePicker } from "primeng/datepicker";
import { EditorModule } from 'primeng/editor';
import { FileService } from '@/pages/service/file.servise';
import { Result } from 'src/interfaces/result';
import { HttpClient } from '@angular/common/http';
@Component({
    selector: 'app-file-editor',
    templateUrl: './file-editor.component.html',
    styleUrls: ['./file-editor.component.css'],
    imports: [FloatLabel, InputText, Button, FormsModule, ReactiveFormsModule, AutoComplete, DatePicker, EditorModule]
})
export class FileEditorComponent implements OnInit {
    resultId!: number;
    data!: Result;
    formGroup: FormGroup;
    // Свойство для хранения выбранного файла
    selectedFile: File | null = null;
    uploadStatus: string = '';
    
    constructor(
        private config: DynamicDialogConfig,
        private ref: DynamicDialogRef,
        private service: FileService,
        private fb: FormBuilder,
        private http: HttpClient
    ) {
        this.formGroup = fb.group({
            contractor: [undefined, Validators.required],
            contractNumber: [undefined, Validators.required],
            contractDate: [undefined, Validators.required],
            description: [undefined]
        });
    }

    ngOnInit() {
        this.resultId = this.config.data.resultId;
        if(this.resultId) {
            this.service.findById(this.resultId).subscribe(r=>{
                this.data = r;  
                console.log(this.data);              
            });
        }
    }

    save() {
    }


    // save() {
    //     const c = this.formGroup.value as Contract;
    //     if (this.contractId) {
    //         this.service.update(this.contractId, c).subscribe(()=>{
    //             this.ref.close(true);
    //         });
    //     } else {
    //         this.service.create(c).subscribe(()=>{
    //             this.ref.close(true);
    //         });
    //     }
    // }

    close() {
        this.ref.close(false);
    }

    // Метод вызывается при выборе файла в input[type="file"]
    onFileSelected(event: any): void {
        // Получаем первый выбранный файл
        this.selectedFile = event.target.files[0] as File;
        this.uploadStatus = this.selectedFile ? `Выбран файл: ${this.selectedFile.name}` : '';
    }

    // Метод вызывается при нажатии кнопки "Загрузить"
    onUpload(): void {
        if (this.selectedFile) {
        this.uploadStatus = 'Загрузка...';

        // FormData используется для отправки данных формы, включая файлы
        const formData = new FormData();
        formData.append('file', this.selectedFile, this.selectedFile.name);

        
        // Отправляем POST-запрос на серверный API       
        this.service.upload(formData).subscribe(
            res => {
                console.log('Файл успешно загружен', res);
                this.uploadStatus = 'Загрузка успешно завершена!';
                this.selectedFile = null; // Очищаем выбранный файл после успешной загрузки
            },
            error => {
                //console.error('Ошибка загрузки', error);
                this.uploadStatus = 'Ошибка загрузки файла.';
            }
        );
        } else {
            this.uploadStatus = 'Пожалуйста, выберите файл для загрузки.';
        }
    }
}
