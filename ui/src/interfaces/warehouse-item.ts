import { BaseDto } from './base-dto';
import { SelectListItemDto } from './select-list-item-dto';

export interface WarehouseItem extends BaseDto {
    quantity: number;
    price: number;
    sum: number;
    warehouseItemType: SelectListItemDto;
}