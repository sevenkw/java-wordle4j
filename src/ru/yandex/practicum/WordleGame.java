package ru.yandex.practicum;

import wordleexception.GameAlreadyFinishedException;
import wordleexception.InvalidWordLengthException;
import wordleexception.WordNotFoundInDictionaryException;

import java.io.PrintWriter;
import java.util.*;

/*
в этом классе хранится словарь и состояние игры
    текущий шаг
    всё что пользователь вводил
    правильный ответ

в этом классе нужны методы, которые
    проанализируют совпадение слова с ответом
    предложат слово-подсказку с учётом всего, что вводил пользователь ранее

не забудьте про специальные типы исключений для игровых и неигровых ошибок
 */
public class WordleGame {

    private String answer;
    private int steps; // количество шагов
    private WordleDictionary dictionary;
    private List<String> guessedWords; // список введенных слов пользователя
    private List<String> hints; // подсказки
    private PrintWriter log;
    private Set<Character> correctLetters;      // буквы, которые есть в слове
    private Set<Character> incorrectLetters;    // буквы, которых нет в слове
    private Map<Integer, Character> correctPositions; // буквы на верных местах
    private Map<String, Integer> suggestedWord;

    public WordleGame(String answer, WordleDictionary dictionary, PrintWriter log) {
        this.answer = answer;
        this.steps = 6;
        this.dictionary = dictionary;
        this.guessedWords = new ArrayList<>();
        this.hints = new ArrayList<>();
        this.log = log;
        this.correctLetters = new HashSet<>();
        this.incorrectLetters = new HashSet<>();
        this.correctPositions = new HashMap<>();
        this.suggestedWord = new LinkedHashMap<>();
    }

    public int getSteps() {
        return steps;
    }

    public boolean isWordValid(String word) {
        return dictionary.isCorrectLength(word) && dictionary.contains(word);
    }

    public String generateHint(String word) {
        word = WordleDictionary.normalizeWord(word);
        char[] ansChars = answer.toCharArray();
        char[] wordChars = word.toCharArray();
        char[] hint = new char[wordChars.length];
        boolean[] used = new boolean[wordChars.length];

        for (int i = 0; i < wordChars.length; i++) {
            if (wordChars[i] == ansChars[i]) {
                hint[i] = '+';
                used[i] = true;
            }
        }

        for (int i = 0; i < wordChars.length; i++) {
            if (hint[i] == '+') continue;
            boolean found = false;
            for (int j = 0; j < ansChars.length; j++) {
                if (!used[j] && wordChars[i] == ansChars[j]) {
                    hint[i] = '^';
                    used[j] = true;
                    found = true;
                    break;
                }
            }
            if (!found) {
                hint[i] = '-';
            }
        }

        for (int i = 0; i < hint.length; i++) {
            if (hint[i] == '+') {
                correctLetters.add(wordChars[i]);
                correctPositions.put(i, wordChars[i]);
            } else if (hint[i] == '^') {
                correctLetters.add(wordChars[i]);
            } else {
                incorrectLetters.add(wordChars[i]);
            }
        }


        String result = new String(hint);
        return result;
    }

    public boolean isWin() {
        return guessedWords.contains(answer);
    }

    public boolean isLose() {
        return steps <= 0 && !isWin();
    }

    public String makeMove(String word) throws GameAlreadyFinishedException, InvalidWordLengthException,
            WordNotFoundInDictionaryException {
        word = WordleDictionary.normalizeWord(word);
        if (isWin() || isLose()) {
            throw new GameAlreadyFinishedException("Игра завершена");
        }

        if (!dictionary.isCorrectLength(word)) {
            throw new InvalidWordLengthException("Неверная длина слова");
        }


        if (!dictionary.contains(word)) {
            throw new WordNotFoundInDictionaryException("Слово не найдено в словаре");
        }

        String hint = generateHint(word);
        guessedWords.add(word);
        hints.add(hint);
        steps--;

        return hint;
    }

    public String suggestWord() {

        List<String> candidates = new ArrayList<>();

        for (String word : dictionary.getWords()) {

            if (guessedWords.contains(word)) {
                continue;
            }

            boolean hasIncorrectLetter = false;
            for (int i = 0; i < word.length(); i++) {
                char c = word.charAt(i);
                if (incorrectLetters.contains(c)) {
                    hasIncorrectLetter = true;
                    break;
                }
            }
            if (hasIncorrectLetter) {
                continue;
            }

            boolean allCorrectPresent = true;
            for (char c : correctLetters) {
                if (word.indexOf(c) == -1) {
                    allCorrectPresent = false;
                    break;
                }
            }
            if (!allCorrectPresent) {
                continue;
            }

            boolean iscorrectPosition = false;
            for (Map.Entry<Integer, Character> entry : correctPositions.entrySet()) {
                int pos = entry.getKey();
                char expected = entry.getValue();

                if (word.charAt(pos) != expected) {
                    iscorrectPosition = true;
                    break;
                }
            }
            if (iscorrectPosition) continue;
            candidates.add(word);
        }
        if (candidates.isEmpty()) {
            return "Нет подходящих слов для подсказки";
        }
        String bestPromt = candidates.get(0);
        int minCount = getSuggestionCount(bestPromt);

        for (String word : candidates) {
            int count = getSuggestionCount(word);
            if (count < minCount) {
                minCount = count;
                bestPromt = word;
            }
        }

        suggestedWord.put(bestPromt, minCount + 1);
        return bestPromt;
    }

    private int getSuggestionCount(String word) {
        Integer count = suggestedWord.get(word);
        return count == null ? 0 : count;
    }

}