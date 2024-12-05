package com.example.myapplication;

public class KeyTable{

    /* a 5x5 matrix of characters to hold the key */
    private char[][] key;

    /**
     * Constructor
     */
    public KeyTable(char[][] key){
        this.key = key;
    }

    /**
     * @param keyphare- The String to use as the key
     * @return - The new keytable object
     * @throws IllegalArgumentException if the keyphrase is null
     */
    public static KeyTable buildFromString(String keyphrase) throws IllegalArgumentException{
        if (keyphrase == null || keyphrase.equals("")) throw new IllegalArgumentException("keyphrase can't be empty");

        keyphrase = prepareKey(keyphrase);

        char[][] keytable = new char[5][5];

        int keyIndex = 0;
        char fillChar = 'A'; /* apparently if you just add 1 to the character, it goes to the next unicode character */

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (keyIndex < keyphrase.length()) {
                    keytable[i][j] = keyphrase.charAt(keyIndex++);
                } else {
                    while (keyphrase.indexOf(fillChar) != -1 || fillChar == 'J') {
                        fillChar++;
                    }
                    keytable[i][j] = fillChar++;
                }
            }
        }
        return new KeyTable(keytable);

    }

    /**
     * @param keyphrase - The string to use as the key
     * @return - The keyphrase with all non-alphabetic characters removed and all Js replaced with Is
     * This method is basically preparing the keyphrase to remove any spaces and stuff like that and replacing all Js with Is before making the keytable
     */
    public static String prepareKey(String keyphrase){
        StringBuilder key = new StringBuilder();
        /* using regex to remove any characters in the string that is not an uppercase Alphabet A-Z */
        keyphrase = keyphrase.toUpperCase().replaceAll("[^A-Z]", "").replace("J", "I");
        for(int i = 0; i < keyphrase.length(); i++){
            char c = keyphrase.charAt(i);
            /* Append only to the string builder object if and only if the value of the character is not already in the string builder object */
            if(key.indexOf(String.valueOf(c)) == -1){
                key.append(c);
            }
        }
        return key.toString();
    }



    /**
     *
     * @param c - The character to locate within the key matrix
     * @return The index of the row in which c occurs.
     * @throws IllegalArgumentException if c is not in the keytable
     */
    public int findRow(char c) throws IllegalArgumentException{
        for (int i=0; i<this.getKeyTable().length; i++){
            for (int j=0; j<this.getKeyTable()[0].length; j++){
                if (this.getKeyTable()[i][j] == c) {
                    return i;
                }
            }
        }
        throw new IllegalArgumentException("C is not in the keytable");
    }

    /**
     * @param c - The character to locate within the key matrix
     * @return the index of the column in which c occurs
     * @throws IllegalArgumentException if c is not in the keytable
     */
    public int findCol(char c) throws IllegalArgumentException{
        for (int i=0; i<this.getKeyTable().length; i++){
            for (int j=0; j<this.getKeyTable()[0].length; j++){
                if (this.getKeyTable()[i][j] == c) {
                    return j;
                }
            }
        }
        throw new IllegalArgumentException("C is not in the keytable");
    }

    /**
     * @return the key object
     */
    public char[][] getKeyTable(){
        return key;
    }

    /**
     * Prints the key table
     */
    public void printKeyTable(){
        for (char[] row: key){
            for (char c: row){
                System.out.print(c + " ");
            }
            System.out.println();
        }
    }



}