package by.delaidelo.tests.testworks.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ResultDTO {
    private Long id;
    private String fileName; // Имя файла
    private Long count; // Кол-во валидных записей
    private Double minValue; // Минимальное значение
    private Double maxValue; // Максимальное значение
    private Double average; // Среднее значение
    private Double standardDeviation; // Стандартное отклонение
    private Long invalidRowsCount; // Кол-во пропусков (строк без данных, с ошибкой)
    private Long uniqueValuesCount; // Кол-во уникальных значений
}
