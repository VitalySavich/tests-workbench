package by.delaidelo.tests.testworks.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "results")
@Getter
@Setter
public class Result extends AbstractEntity {
    @Column
    private String fileName; // Имя файла
    @Column
    private String tempFilePath; // Путь к временному файлу
    @Column
    private Double fileSizeInMB; // Размер файла в Mb
    @Column
    private Double processingTime; // Время обработки в секундах
    @Column
    private Long count; // Кол-во валидных записей
    @Column
    private Double minValue; // Минимальное значение
    @Column
    private Double maxValue; // Максимальное значение
    @Column
    private Double average; // Среднее значение
    @Column
    private Double standardDeviation; // Стандартное отклонение
    @Column
    private Long invalidRowsCount; // Кол-во пропусков (строк без данных, с ошибкой)
    @Column
    private Long uniqueValuesCount; // Кол-во уникальных значений
}
