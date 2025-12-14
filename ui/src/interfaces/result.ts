import { BaseDto } from "./base-dto";
import { SelectListItemDto } from "./select-list-item-dto";

export interface Result extends BaseDto {
    fileAnalyzeDateTime: string; // Дата-время анализа
    fileName: string;  // Имя файла
    tempFilePath: string; // Путь к временному файлу
    fileSizeInMB: number; // Размер файла в Mb
    processingTime: number; // Время обработки в секундах
    count: number; // Кол-во валидных записей
    minValue: number; // Минимальное значение
    maxValue: number; // Максимальное значение
    average: number; // Среднее значение
    standardDeviation: number; // Стандартное отклонение
    invalidRowsCount: number; // Кол-во пропусков (строк без данных, с ошибкой)
    uniqueValuesCount: number; // Кол-во уникальных значений
}