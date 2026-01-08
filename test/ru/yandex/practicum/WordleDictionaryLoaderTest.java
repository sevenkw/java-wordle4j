package ru.yandex.practicum;

import wordleexception.DictionaryFileNotFoundException;
import org.junit.jupiter.api.Test;

import java.io.PrintWriter;

import static org.junit.jupiter.api.Assertions.*;

class WordleDictionaryLoaderTest {

    @Test
    void loadDictionarySuccessfully() throws Exception {
        WordleDictionaryLoader loader =
                new WordleDictionaryLoader(new PrintWriter(System.out));

        WordleDictionary dictionary = loader.load("words_ru.txt");

        assertNotNull(dictionary);
        assertFalse(dictionary.getWords().isEmpty());
    }

    @Test
    void dictionaryFileNotFound() {
        WordleDictionaryLoader loader =
                new WordleDictionaryLoader(new PrintWriter(System.out));

        assertThrows(DictionaryFileNotFoundException.class,
                () -> loader.load("no_file.txt"));
    }

}
