package com.example.myapplication;


import java.util.LinkedList;


public class Phrase extends LinkedList<Bigram>{

    /**
     * @param b - Bigram to be added at the end of the Phrase
     */
    public void enqueue(Bigram b){
        super.addLast(b);
    }

    /**
     * @return - The Bigram at the front of the Phrase
     * Removes the Bigram from the Phrase
     */
    public Bigram dequeue(){
        return super.removeFirst();
    }

    /**
     * @return - The Bigram at the front of the Phrase
     * Does not remove the Bigram from the Phrase
     */
    public Bigram peek(){
        return super.getFirst();
    }

    /**
     * @param s - the string to represent as a Bigram queue.
     * @return - Thew new Phrase object which contains a queue of bigram objects representing s.
     */
    public static Phrase buildPhraseFromStringforEnc(String s){
        Phrase phrase = new Phrase();
        s = s.toUpperCase().replaceAll("[^A-Z]", "").replace("J", "I");

        for (int i = 0; i<s.length(); i+=2){
            char first = s.charAt(i);
            char second;
            if (i + 1 < s.length()){
                second = s.charAt(i+1);
            }else{
                second = 'X';
            }

            if (first == second) {
                phrase.enqueue(new Bigram(first, 'X'));
                i--;
            } else {
                phrase.enqueue(new Bigram(first, second));
            }

        }
        return phrase;
    }


    /**
     * @param key - the KeyTable to use the encrypt this Phrase
     * @return The new Phrase object which contains a queue of Bigram objects representing the encrypted version of this Phrase.
     * @throws IllegalArgumentException if the key is null
     */
    public Phrase encrypt(KeyTable key) throws IllegalArgumentException{
        if (key == null){
            throw new IllegalArgumentException("KeyTable can't be null");
        }

        /*  This method encrypts the Phrase by each Bigram.
         * Since this is a queue structure, and first in is first out, we don't have to worry about losing order. */

        Phrase encryptedPhrase = new Phrase();

        while (!this.isEmpty()){
            Bigram b = this.dequeue();
            encryptedPhrase.enqueue(encryptBigram(b, key));
        }
        return encryptedPhrase;
    }


    /**
     * Encrypts a Bigram using the given KeyTable, this is just a helper function to make the Encrypt method easier
     * @param b Bigram to encrypted
     * @param key - the KeyTable to use to decrypt this Phrase
     * @return encrypted bigram
     */
    public Bigram encryptBigram(Bigram b, KeyTable key){
        int r1 = key.findRow(b.getFirst());
        int c1 = key.findCol(b.getFirst());
        int r2 = key.findRow(b.getSecond());
        int c2 = key.findCol(b.getSecond());

        /* If they are the same row, they are shifted horizontally */
        /* This is the same as just shifting the column, as we can see, 1 is being added */
        /* The mod is just there to account the existing row being the last one, so the mod just wraps it around if necessary */
        if (r1 == r2){
            Bigram encryptedBigram = new Bigram(key.getKeyTable()[r1][(c1 + 1) % 5], key.getKeyTable()[r2][(c2 + 1) % 5]);
            return encryptedBigram;
        }
        /* If they are the same column, they are shifted vertically */
        /* This is the same as just shifting the row, as we can see, 1 is being added */
        /* The mod is just there to account the existing column being the last one, so the mod just wraps it around if necessary */
        else if (c1 == c2){
            Bigram encryptedBigram = new Bigram(key.getKeyTable()[(r1 + 1) % 5][c1], key.getKeyTable()[(r2 + 1) % 5][c2]);
            return encryptedBigram;
        }
        /* If they are not in the same row or column, they are in a rectangle */
        /* The first character is replaced with the character in the same row but in the column of the second character */
        /* The second character is replaced with the character in the same row but in the column of the first character */

        else{
            Bigram encryptedBigram = new Bigram(key.getKeyTable()[r1][c2], key.getKeyTable()[r2][c1]);
            return encryptedBigram;
        }

    }

    /**
     * @param key - the KeyTable to use to decrypt this Phrase
     * @return The new Phrase object which contains a queue of Bigram objects representing the decrypted version of this Phrase.
     * @throws IllegalArgumentException if the key is null
     */
    public Phrase decrypt(KeyTable key) throws IllegalArgumentException{
        if (key == null) {
            throw new IllegalArgumentException("Key cannot be null");
        }

        Phrase decryptedPhrase = new Phrase();

        /* This decrypts the Phrase by each Bigram.
         * Since this is a queue structure, and first in is first out, we don't have to worry about losing order.  */
        while (!this.isEmpty()) {
            Bigram b = this.dequeue();
            decryptedPhrase.enqueue(decryptBigram(b, key));
        }

        return decryptedPhrase;
    }

    /**
     * Decrypts a Bigram using the given KeyTable, this is just a helper function to make the Decrypt method easier
     * @param b Bigram to decrypted
     * @param key - the KeyTable to use to decrypt this Phrase
     * @return decrypted bigram
     */
    public Bigram decryptBigram(Bigram b, KeyTable key){
        int r1 = key.findRow(b.getFirst());
        int c1 = key.findCol(b.getFirst());
        int r2 = key.findRow(b.getSecond());
        int c2 = key.findCol(b.getSecond());

        /* This is basically the same logic as encyrptBigram method but we gotta remember that we're doing things in reverse */
        /* What's really happening with [(c1+4)%5] is this [(c1 -1 + 5) % 5] */
        /* This is just to account for the fact that we're going backwards and to ensure the column value is not negative*/
        if (r1 == r2){
            Bigram decryptedBigram = new Bigram(key.getKeyTable()[r1][(c1 + 4) % 5], key.getKeyTable()[r2][(c2 + 4) % 5]);
            return decryptedBigram;
        }
        /* If the rows are different, we gotta replace the first character with the character in the same */
        else if (c1 == c2){
            Bigram decryptedBigram =  new Bigram(key.getKeyTable()[(r1 + 4) % 5][c1], key.getKeyTable()[(r2 + 4) % 5][c2]);
            return decryptedBigram;
        }
        /* If they are not in the same row or column, they are in a rectangle */
        /* The first character is replaced with the character in the same row but in the column of the second character */
        /* The second character is replaced with the character in the same row but in the column of the first character */

        else{
            Bigram decryptedBigram =  new Bigram(key.getKeyTable()[r1][c2], key.getKeyTable()[r2][c1]);
            return decryptedBigram;
        }
    }


    /**
     * @return - The string representation of the Phrase
     * This basically turns each bigram to its string representation by using the toString of the Bigram class
     * Then joins them all together using String builder
     * And then returns the final representation of the string by using the toString of the StringBuilder class.
     */
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        for (Bigram b : this){
            sb.append(b.toString());
        }
        return sb.toString();
    }
}