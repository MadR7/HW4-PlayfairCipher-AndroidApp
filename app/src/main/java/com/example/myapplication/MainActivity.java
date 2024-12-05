package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private KeyTable keyTable;
    private EditText keyPhraseInput;
    private EditText messageInput;
    private TextView keyTableDisplay;
    private TextView resultText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize UI elements
        keyPhraseInput = findViewById(R.id.keyPhraseInput);
        messageInput = findViewById(R.id.messageInput);
        keyTableDisplay = findViewById(R.id.keyTableDisplay);
        resultText = findViewById(R.id.resultText);
        Button generateKeyButton = findViewById(R.id.generateKeyButton);
        Button clearKeyButton = findViewById(R.id.clearKeyButton);
        Button encryptButton = findViewById(R.id.encryptButton);
        Button decryptButton = findViewById(R.id.decryptButton);
        Button clearButton = findViewById(R.id.clearButton);
        // Set click listeners
        generateKeyButton.setOnClickListener(v -> generateKey());
        clearKeyButton.setOnClickListener(v -> clearKey());
        encryptButton.setOnClickListener(v -> encrypt());
        decryptButton.setOnClickListener(v -> decrypt());
        clearButton.setOnClickListener(v -> clearText());
    }

    private void generateKey() {
        try {
            String keyphrase = keyPhraseInput.getText().toString().trim();
            keyTable = KeyTable.buildFromString(keyphrase);
            displayKeyTable();
            resultText.setText("Key generated successfully!");
        } catch (IllegalArgumentException e) {
            resultText.setText("Error: " + e.getMessage());
        }
    }

    private void clearKey(){
        keyTable = null;
        keyPhraseInput.setText("");
        keyTableDisplay.setText("");
    }

    private void clearText(){
        messageInput.setText("");
    }

    private void displayKeyTable() {
        StringBuilder display = new StringBuilder();
        char[][] table = keyTable.getKeyTable();
        for (char[] row : table) {
            for (char c : row) {
                display.append(c).append(" ");
            }
            display.append("\n");
        }
        keyTableDisplay.setText(display.toString());
    }

    private void encrypt() {
        if (keyTable == null) {
            resultText.setText("Please generate a key first");
            return;
        }
        String message = messageInput.getText().toString().trim();
        Phrase phrase = Phrase.buildPhraseFromStringforEnc(message);
        String encrypted = phrase.encrypt(keyTable).toString();
        resultText.setText("Encrypted: " + encrypted);
    }

    private void decrypt() {
        if (keyTable == null) {
            resultText.setText("Please generate a key first");
            return;
        }
        String message = messageInput.getText().toString().trim();
        Phrase phrase = Phrase.buildPhraseFromStringforEnc(message);
        String decrypted = phrase.decrypt(keyTable).toString();
        resultText.setText("Decrypted: " + decrypted);
    }
}