import { BaseDto } from "./base-dto";
import { SelectListItemDto } from "./select-list-item-dto";

export interface IncomingDocument extends BaseDto {
    contractDate: Date;
    contractNumber: number;
    description?: string;
    contractor: SelectListItemDto;
    contract: SelectListItemDto;
}