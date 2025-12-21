import { Routes } from '@angular/router';
import { Empty } from './empty/empty';
import { FilesComponent } from './files/files.component';

export default [
    { path: 'empty', component: Empty },
    { path: 'history', component: FilesComponent},
    { path: '**', redirectTo: '/notfound' }
] as Routes;
