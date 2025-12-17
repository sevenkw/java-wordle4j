package ru.yandex.practicum;

import java.util.List;

/*
этот класс содержит в себе список слов List<String>
    его методы похожи на методы списка, но учитывают особенности игры
    также этот класс может содержать рутинные функции по сравнению слов, букв и т.д.
 */
public class WordleDictionary {
    private List<String> words;
    private static final int fixLengthWord = 5;

    public WordleDictionary(List<String> words) {
        this.words = words;
    }

    public List<String> getWords() {
        return words;

    }

    public boolean contains(String word) {
        word = normalizeWord(word);
        return words.contains(word);
    }

    public static String normalizeWord (String word) {
        return word.trim().toLowerCase().replace("ё", "е");
    }

    public boolean isCorrectLength (String word) {
        word = normalizeWord(word);
        if (word.length() == fixLengthWord) {
            return true;
        } else {
            return false;
        }
    }
}
