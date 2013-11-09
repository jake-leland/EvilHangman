EvilHangman
===========

Hangman with a twist.

This game is an "evil" version of hangman. It functions very similarly to the hangman game that everyone is used to, but it is much more difficult.
Instead of picking a word at the beginning of the game, the computer never decides on an actual word until the very end.
Instead, it changes the "word" that the user is guessing so that it is as difficult as possible to actually guess.
As the user guesses letters, the code goes through a dictionary and determines if there are more words with or without the guessed letter.
If there are more words in the dictionary without the guessed letter, then the computer will say that the word does not contain the letter, because this leaves more words as the possible answer.
If there are more words in the dictionary with the letter, the computer will split up the words based on the pattern that the guessed letter forms in the word. (this is explained better in the code below)
The pattern that has the most matching words is chosen, because this leaves more words as the possible answer.
In summary, the computer eventually chooses the last word that you would have ever guessed, making it very difficult.
It is possible to win! Have fun and good luck!
