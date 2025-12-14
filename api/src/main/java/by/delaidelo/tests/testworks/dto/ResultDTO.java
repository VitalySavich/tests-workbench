package by.delaidelo.tests.testworks.dto;

import lombok.Data;

@Data
public class ResultDTO {
    private Long id;
    private String fileAnalyzeDateTime; // Дата-время анализа
    private String author; // Имя файла
    private String fileName; // Имя файла
    private String tempFilePath; // Путь к временному файлу
    private Double fileSizeInMB; // Размер файла в Mb
    private Double processingTime; // Время обработки в секундах
    private Long count; // Кол-во валидных записей
    private Double minValue; // Минимальное значение
    private Double maxValue; // Максимальное значение
    private Double average; // Среднее значение
    private Double standardDeviation; // Стандартное отклонение
    private Long invalidRowsCount; // Кол-во пропусков (строк без данных, с ошибкой)
    private Long uniqueValuesCount; // Кол-во уникальных значений
}
