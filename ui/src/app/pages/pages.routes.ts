import { Routes } from '@angular/router';
import { Empty } from './empty/empty';
import { Warehouses } from './warehouses/warehouses';
import { ItemTypesComponent } from './item-types/item-types.component';
import { ContractorsComponent } from './contractors/contractors.component';
import { ContractsComponent } from './contracts/contracts.component';
import { IncomingDocumentsComponent } from './incoming-documents/incoming-documents.component';
import { WarehouseItemsComponent } from './warehouse-items/warehouse-items.component';

export default [
    { path: 'empty', component: Empty },
    { path: 'warehouses', component: Warehouses },
    { path: 'item-types', component: ItemTypesComponent },
    { path: 'contractors', component: ContractorsComponent},
    { path: 'contracts', component: ContractsComponent},
    { path: 'incoming-documents', component: IncomingDocumentsComponent},
    { path: "incoming-documents/:id/warehouse-items", component: WarehouseItemsComponent},
    { path: '**', redirectTo: '/notfound' }
] as Routes;
