package by.delaidelo.tests.testworks.mvc.controllers;

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
import java.util.stream.Collectors;
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
    public Boolean analyze() {

        Path filePath = Paths.get("D:/PROJECTS/InvLab/smalltest.txt"); // Укажите путь к вашему файлу

        // Использование try-with-resources для автоматического закрытия потока
        try (Stream<String> lines = Files.lines(filePath)) {
            // Применение операций Stream API
//            lines
//                    .filter(line -> !line.trim().isEmpty()) // Пример: пропустить пустые строки
//                    .map(String::toUpperCase) // Пример: преобразовать все строки в верхний регистр
//                    .forEach(System.out::println); // Пример: вывести каждую строку

            //System.out.println("Валидные строки:");

            //List<String> uniqueValuesList = lines

            DoubleSummaryStatistics stats = lines
                    // Пропускать некорректные строки, не подходящие для анализа
                    // (не полные или ошибочные данные).
                    .filter(line -> line.split(",").length == 2)
                    // Приверка что первый элемент является валидным датой-временем
                    // Стандарт ISO_LOCAL_DATE_TIME
                    .filter(line -> isValidDateTime(line.split(",")[0], "yyyy-MM-dd'T'HH:mm:ss"))
                    // Приверка что второй элемент является валидным числом типа double
                    .filter(line -> isValidDouble(line.split(",")[1]))
                    // Получаем только значение
                    .map(line -> line.split(",")[1])
                    .mapToDouble(Double::parseDouble).summaryStatistics();
                    // Удаляем дубликаты из потока
                    //.distinct()
                    // Собираем в List
                    //.collect(Collectors.toList());
                    //.forEach(System.out::println); // Пример: вывести каждую строку

            //System.out.println("Уникальные значения (List): " + uniqueValuesList);

            System.out.println("\nСтатистика:");
            System.out.println("Кол-во валидных записей: " + stats.getCount());
            System.out.println("Минимальное значение: " + stats.getMin());
            System.out.println("Максимальное значение: " + stats.getMax());
            System.out.println("Среднее значение: " + stats.getAverage());

            System.out.println("Сумма: " + stats.getSum());








        } catch (IOException e) {
            System.err.println("Произошла ошибка при чтении файла: " + e.getMessage());
            e.printStackTrace();
        }

        return true;
    }
}


