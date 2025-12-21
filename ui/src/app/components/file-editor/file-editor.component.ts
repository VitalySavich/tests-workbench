import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { DynamicDialogConfig, DynamicDialogRef } from 'primeng/dynamicdialog';
import { FloatLabel } from "primeng/floatlabel";
import { Button } from "primeng/button";
import { EditorModule } from 'primeng/editor';
import { FileService } from '@/pages/service/file.servise';
import { Result } from 'src/interfaces/result';
import { HttpClient } from '@angular/common/http';
import { FileUploadModule } from 'primeng/fileupload';
@Component({
    selector: 'app-file-editor',
    templateUrl: './file-editor.component.html',
    styleUrls: ['./file-editor.component.css'],
    imports: [FloatLabel, Button, FormsModule, ReactiveFormsModule, EditorModule]
})
export class FileEditorComponent implements OnInit {
    resultId!: number;
    data!: Result;
    formGroup: FormGroup;
    // Свойство для хранения выбранного файла
    selectedFile: File | null = null;
    uploadStatus: string = '';
    deleteMode: boolean = false;
    
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
        this.deleteMode = this.config.data.deleteMode;
        if(this.resultId) {
            this.service.findById(this.resultId).subscribe(r=>{
                this.data = r;  
                console.log(this.data);              
            });
        }
    }

    save() {
    }
    
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
        //formData.append('file', this.selectedFile);

        
        // Отправляем POST-запрос на серверный API       
        this.service.upload(formData).subscribe(res => {
            this.uploadStatus = 'Загрузка успешно завершена!'
        }
            
        //     {complete: {
        //         console.log('Файл успешно загружен', res);
        //         this.uploadStatus = 'Загрузка успешно завершена!';
        //         this.selectedFile = null; // Очищаем выбранный файл после успешной загрузки
        //     }
        //     error: {
        //         console.error('Ошибка загрузки', res);
        //         this.uploadStatus = 'Ошибка загрузки файла.';
        //     }       
        // }         
        );
        } else {
            this.uploadStatus = 'Пожалуйста, выберите файл для загрузки.';
        }
    }

    delete(id: number) {
        this.service.delete(id).subscribe(() => {
            this.ref.close(false);
        });
    }
}
