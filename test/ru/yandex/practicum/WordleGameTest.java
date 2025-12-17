package ru.yandex.practicum;

import wordleexception.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.PrintWriter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WordleGameTest {

    private WordleGame game;

    @BeforeEach
    void setup() {
        WordleDictionary dictionary = new WordleDictionary(
                List.of("арбуз", "банан", "балет", "буква")
        );

        game = new WordleGame(
                "арбуз",
                dictionary,
                new PrintWriter(System.out)
        );
    }

    @Test
    void generateHintCorrect() throws Exception {
        String hint = game.makeMove("балет");
        assertEquals("^^---", hint);
    }

    @Test
    void generateHintWin() throws Exception {
        String hint = game.makeMove("арбуз");
        assertEquals("+++++", hint);
    }

    @Test
    void winGame() throws Exception {
        game.makeMove("арбуз");
        assertTrue(game.isWin());
        assertFalse(game.isLose());
    }

    @Test
    void loseGame() throws Exception {
        for (int i = 0; i < 6; i++) {
            game.makeMove("балет");
        }
        assertTrue(game.isLose());
        assertFalse(game.isWin());
    }

    @Test
    void invalidLengthThrowsException() {
        assertThrows(InvalidWordLengthException.class,
                () -> game.makeMove("кот"));
    }

    @Test
    void wordNotInDictionaryThrowsException() {
        assertThrows(WordNotFoundInDictionaryException.class,
                () -> game.makeMove("слива"));
    }

    @Test
    void gameAlreadyFinishedThrowsException() throws Exception {
        game.makeMove("арбуз");

        assertThrows(GameAlreadyFinishedException.class,
                () -> game.makeMove("банан"));
    }

    @Test
    void suggestWordReturnsValidWord() {
        String suggestion = game.suggestWord();
        assertNotNull(suggestion);
        assertEquals(5, suggestion.length());
    }
}
