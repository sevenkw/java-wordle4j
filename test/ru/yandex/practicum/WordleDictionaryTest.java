package ru.yandex.practicum;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WordleDictionaryTest {

    private static WordleDictionary dictionary;

    @BeforeAll
    static void init() {
        dictionary = new WordleDictionary(
                List.of("арбуз", "банан", "груша")
        );
    }

    @Test
    void containsWord() {
        assertTrue(dictionary.contains("арбуз"));
    }

    @Test
    void doesNotContainWord() {
        assertFalse(dictionary.contains("слива"));
    }

    @Test
    void correctLength() {
        assertTrue(dictionary.isCorrectLength("арбуз"));
    }

    @Test
    void incorrectLength() {
        assertFalse(dictionary.isCorrectLength("кот"));
    }

    @Test
    void normalizeWord() {
        assertEquals("елка", WordleDictionary.normalizeWord(" Ёлка "));
    }
}
