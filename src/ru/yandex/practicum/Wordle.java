package ru.yandex.practicum;

import wordleexception.GameAlreadyFinishedException;
import wordleexception.InvalidWordLengthException;
import wordleexception.WordNotFoundInDictionaryException;

import java.io.PrintWriter;
import java.util.List;
import java.util.Random;
import java.util.Scanner;


/*
в главном классе нам нужно:
    создать лог-файл (он должен передаваться во все классы)
    создать загрузчик словарей WordleDictionaryLoader
    загрузить словарь WordleDictionary с помощью класса WordleDictionaryLoader
    затем создать игру WordleGame и передать ей словарь
    вызвать игровой метод в котором в цикле опрашивать пользователя и передавать информацию в игру
    вывести состояние игры и конечный результат
 */
public class Wordle {

    public static void main(String[] args) {

        try (
                PrintWriter log = new PrintWriter("wordle.log");
                Scanner scanner = new Scanner(System.in)
        ) {
            log.println("Игра Wordle запущена");

            WordleDictionaryLoader loader = new WordleDictionaryLoader(log);
            WordleDictionary dictionary = loader.load("words_ru.txt");

            List<String> words = dictionary.getWords();
            String answer = words.get(new Random().nextInt(words.size()));
            log.println("Загадано слово: " + answer);

            WordleGame game = new WordleGame(answer, dictionary, log);

            while (!game.isWin() && !game.isLose()) {

                System.out.println("Оставшиеся попытки - " + game.getSteps());
                System.out.print("Введите слово или (Enter) для подсказки : ");

                String input = scanner.nextLine().trim();

                if (input.isEmpty()) {
                    String suggestion = game.suggestWord();
                    System.out.println("Подсказка: " + suggestion);
                    log.println("Подсказка выдана: " + suggestion);
                    continue;
                }

                try {
                    String hint = game.makeMove(input);
                    System.out.println("Подсказка: " + hint);
                    log.println("Ход: " + input + " -> " + hint);

                } catch (GameAlreadyFinishedException |
                         InvalidWordLengthException |
                         WordNotFoundInDictionaryException e) {

                    System.out.println(e.getMessage());
                    log.println("Игровая ошибка: " + e.getMessage());
                }
            }

            if (game.isWin()) {
                System.out.println("Поздравляем! Вы отгадали слово!");
                log.println("Игра завершена победой игрока");
            } else {
                System.out.println("Попытки закончились. Вы проиграли.");
                log.println("Игра завершена поражением игрока");
            }

        } catch (Exception e) {
            System.out.println("Критическая ошибка в работе программы");
            e.printStackTrace();
        }
    }

}
