import { BaseDto } from "./base-dto";
import { SelectListItemDto } from "./select-list-item-dto";

export interface IncomingDocument extends BaseDto {
    incomingDocumentDate: Date;
    incomingDocumentNumber: number;
    description?: string;
    contractor: SelectListItemDto;
    warehouse: SelectListItemDto;
}