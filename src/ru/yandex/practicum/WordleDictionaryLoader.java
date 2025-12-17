package ru.yandex.practicum;

import wordleexception.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/*
этот класс содержит в себе всю рутину по работе с файлами словарей и с кодировками
    ему нужны методы по загрузке списка слов из файла по имени файла
    на выходе должен быть класс WordleDictionary
 */
public class WordleDictionaryLoader {
    private final PrintWriter log;

    public WordleDictionaryLoader(PrintWriter log) {
        this.log = log;
    }


    public WordleDictionary load(String filePath) throws DictionaryFileNotFoundException,
            EmptyDictionaryException {
        log.println("Загрузка словаря: " + filePath);

        List<String> words = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = WordleDictionary.normalizeWord(line);

                if (line.length() == 5) {
                    words.add(line);
                }

            }
        } catch (FileNotFoundException e) {
            throw new DictionaryFileNotFoundException("Файл словаря не найден: " + filePath);
        } catch (IOException e) {
            System.out.println("Ошибка чтения файла");
        }

        if (words.isEmpty()) {
            throw new EmptyDictionaryException("Словарь пуст: " + filePath);
        }
        return new WordleDictionary(words);
    }
}
