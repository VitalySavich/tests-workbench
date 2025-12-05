package by.delaidelo.tests.testworks.mvc.controllers;

import by.delaidelo.tests.testworks.dto.ResultDTO;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.Stream;

@RestController
@RequestMapping("/files")
@CrossOrigin("*")
public class FileController {

    /**
     * Проверяет, является ли строка валидным DateTime в указанном формате.
     * @param dateTimeStr Строка для проверки
     * @param formatPattern Шаблон даты-времени (например, "dd.MM.yyyy HH:mm:ss")
     * @return true, если строка валидна, иначе false
     */
    public static boolean isValidDateTime(String dateTimeStr, String formatPattern) {
        try {
            // Создаем форматер с нужным шаблоном
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(formatPattern);

            // Пытаемся распарсить строку в объект LocalDateTime
            LocalDateTime.parse(dateTimeStr, formatter);

            // Если парсинг прошел успешно, дата-время валидны
            return true;

        } catch (DateTimeParseException e) {
            // Если произошло исключение, строка не является валидным DateTime
            return false;
        }
    }

    /**
     * Проверяет, является ли строка валидным числом типа double.
     * @param str Строка для проверки
     * @return true, если строка является валидным double, иначе false
     */
    public static boolean isValidDouble(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }
        try {
            // Попытка парсинга
            Double.parseDouble(str);
            // Если исключения не было, строка валидна
            return true;
        } catch (NumberFormatException e) {
            // Если было исключение, строка не является валидным double
            return false;
        }
    }

    @PostMapping("/analyze")
    public ResultDTO analyze() {

        Path filePath = Paths.get("D:/PROJECTS/InvLab/smalltest.txt"); // Укажите путь к вашему файлу

        ResultDTO resultDTO = new ResultDTO();

        // --- Создание Supplier<Stream<String>> ---
        // Эта лямбда-функция будет выполняться каждый раз при вызове .get()
        Supplier<Stream<String>> linesSupplier = () -> {
            try {
                // При каждом вызове get() открывается НОВЫЙ поток из файла
                return Files.lines(filePath);
            } catch (IOException e) {
                // В случае ошибки IO, возвращаем пустой поток или выбрасываем RuntimeException
                System.err.println("Ошибка чтения файла: " + e.getMessage());
                return Stream.empty();
            }
        };

        // Создаем поставщика потоков с отфильтрованными невалидными строками
        Supplier<Stream<String>> validLinesSupplier = () -> linesSupplier.get() // Пропускать некорректные строки, не подходящие для анализа
                // (не полные или ошибочные данные).
                .filter(line -> line.split(",").length == 2)
                // Приверка что первый элемент является валидным датой-временем
                // Стандарт ISO_LOCAL_DATE_TIME
                .filter(line -> isValidDateTime(line.split(",")[0], "yyyy-MM-dd'T'HH:mm:ss"))
                // Приверка что второй элемент является валидным числом типа double
                .filter(line -> isValidDouble(line.split(",")[1]));

        // Собираем основную статистику
        try (Stream<String> firstStream = validLinesSupplier.get()) {
            DoubleSummaryStatistics stats = firstStream
                    .map(line -> line.split(",")[1])
                    .mapToDouble(Double::parseDouble)
                    .summaryStatistics();

            resultDTO.setCount(stats.getCount());
            resultDTO.setMinValue(stats.getMin());
            resultDTO.setMaxValue(stats.getMax());
            resultDTO.setAverage(stats.getAverage());
        } // Поток firstStream закрывается здесь

        // Вычисляет стандартное отклонение для выборки (sample standard deviation).
        try (Stream<String> secondStream = validLinesSupplier.get()) {

            // Шаг 1: Вычисление среднего значения (Mean)
            double mean = resultDTO.getAverage();

            // Шаг 2: Вычисление суммы квадратов разностей (Sum of Squares of Differences)
            double sumOfSquares = secondStream
                    // Получаем только значение
                    .map(line -> line.split(",")[1])
                    .mapToDouble(Double::parseDouble)
                    .map(value -> value - mean) // Разница между значением и средним
                    .map(diff -> diff * diff)   // Квадрат разницы
                    .sum();                            // Сумма квадратов

            // Шаг 3: Деление на (n - 1) для выборки и извлечение квадратного корня
            double variance = sumOfSquares / (resultDTO.getCount() - 1); // Дисперсия (Variance)
            double standardDeviation = Math.sqrt(variance);
            resultDTO.setStandardDeviation(standardDeviation);

        } // Поток secondStream закрывается здесь

        // Вычисляем кол-во уникальных значений
        try (Stream<String> thirdStream = validLinesSupplier.get()) {
            Long uniqueValueCount = thirdStream
                    // Получаем только значение
                    .map(line -> line.split(",")[1])
                    .mapToDouble(Double::parseDouble)
                    // Удаляем дубликаты из потока
                    .distinct()
                    .count();

            resultDTO.setUniqueValuesCount(uniqueValueCount);
      } // Поток thirdStream закрывается здесь

        // Вычисляем кол-во пропусков (строк без данных, с ошибкой)
        try (Stream<String> fourthStream = linesSupplier.get()) {
            Long allRowsCount = fourthStream.count();
            Long invalidRowsCount = allRowsCount - resultDTO.getCount();
            resultDTO.setInvalidRowsCount(invalidRowsCount);
        } // Поток fourthStream закрывается здесь

        return resultDTO;
    }
}


