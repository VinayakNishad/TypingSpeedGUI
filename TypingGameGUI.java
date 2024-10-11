import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class TypingGameGUI {
    private static final String[] sentences = {
            "The Bill class represents a legislative bill that can be voted on.",
            "The member of parliament class represents a member of the Indian parliament.",
            "In the city of 2024, temperature rise high; vibrant lights & sounds fill the air, creating Global warming!",
            "By the lake, where nature's beauty meets serenity, @100% pure relaxation is always within un reach!",
            "The Seven Wonders of the Ancient World, selected by Hellenic travelers and noted in poetry and other arts."
    };

    private static final String[] paragraphs = {
            "The Bill class represents a legislative bill that can be voted on. The MemberOfParliament class represents a member of the Indian Parliament.\nIn the Panaji city of 2024, Temperature rise high; vibrant lights & sounds fill the air, creating Global warming!\n By the Godavari lake, where nature's beauty meets serenity, @100% pure relaxation is always within unreach! Among ancient ruins,\nexplorers not find hidden treasures & clues: 7 wonders await, blending History & mystery.",
    };

    public JFrame frame;
    public JTextArea textArea;
    public JTextField inputField;
    public JLabel statusLabel;
    public JButton startButton;
    public JButton retryButton;
    public long startTime;

    public TypingGameGUI() {
        frame = new JFrame("Typing Speed Test");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setSize(600, 400);
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 1));
        statusLabel = new JLabel("Welcome to the Typing Speed Test! Choose an option to start.");
        statusLabel.setBorder(new EmptyBorder(10, 100, 10, 10));
        panel.add(statusLabel);

        textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setBackground(Color.CYAN);
        textArea.setSize(400, 300);
        textArea.setBorder(new EmptyBorder(10, 10, 10, 10));
        textArea.setFont(new Font("Times New Roman", Font.PLAIN, 24));
        frame.add(textArea);

       

        inputField = new JTextField();
        inputField.setBackground(Color.green);
        inputField.setFont(new Font("Times New Roman", Font.PLAIN, 24));
        inputField.setEnabled(false);
        panel.add(inputField);

        

        startButton = new JButton("Start Test");
        retryButton = new JButton("Retry");
        retryButton.setEnabled(false);
        panel.add(startButton);
        panel.add(retryButton);

        frame.add(panel, BorderLayout.SOUTH);

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startTest();
            }
        });

        retryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                retryTest();
            }
        });

        frame.setVisible(true);
    }

    private void startTest() {
        Random random = new Random();
        String originalText;
        int textTypeChoice = JOptionPane.showOptionDialog(frame,
                "What type of text do you want to type?",
                "Choose Text Type",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                new Object[]{"Sentence", "Paragraph"},"Sentence");

        switch (textTypeChoice) {
            case 0:
                originalText = sentences[random.nextInt(sentences.length)];
                break;
            case 1:
                originalText = paragraphs[random.nextInt(paragraphs.length)];
                break;
            default:
                return;
        }

        textArea.setText(originalText);
        inputField.setText("");
        
        inputField.setEnabled(true);
        startButton.setEnabled(false);
        retryButton.setEnabled(true);

        startTime = System.currentTimeMillis();

        inputField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                endTest(originalText);
            }
        });
    }

    private void endTest(String originalText) {
        long endTime = System.currentTimeMillis();
        long elapsedTime = endTime - startTime;
        double seconds = elapsedTime / 1000.0;   //milli second to second
        double minutes = seconds / 60.0;    //second to minute

        String userInput = inputField.getText();

        int originalTextLength = originalText.length();
        int userInputLength = userInput.length();

        int correctChars = 0;

        int minLength = Math.min(originalTextLength, userInputLength);
        // System.out.println(originalTextLength);

        for (int i = 0; i < minLength; i++) {
            if (originalText.charAt(i) == userInput.charAt(i)) {
                correctChars++;
            }
        }

        double grossWordsPerMinute = (userInputLength / 5.0) / minutes;
        double netWordsPerMinute = (correctChars / 5.0) / minutes;
        int accuracy = (int) ((correctChars /(double) originalTextLength) * 100);

        String resultMessage = String.format(
                "Test Result:\n" +
                "--------------\n" +
                "Time elapsed: %.2f seconds\n" +
                "Accuracy: %d%%\n" +
                "Gross Words per Minute: %.2f\n" +
                "Net Words per Minute: %.2f\n",
                seconds, accuracy, grossWordsPerMinute, netWordsPerMinute);



        //To calculate accuracy 
        if (accuracy >= 90) {
            resultMessage += "Gold Medal";
        } else if (accuracy >= 85) {
            resultMessage += "Silver Medal";
        } else if (accuracy >= 80) {
            resultMessage += "Platinum Medal";
        } else {
            resultMessage += "Bad Accuracy";
        }



        //To check if extra or less character are type.
        if (userInputLength > originalTextLength) {
            int extraCharacters = userInputLength - originalTextLength;
            resultMessage += "\nExtra characters typed: " + extraCharacters;
        } else if (userInputLength < originalTextLength) {
            int missingCharacters = originalTextLength - userInputLength;
            resultMessage += "\nMissing characters: " + missingCharacters;
        }


        JOptionPane.showMessageDialog(frame, resultMessage);

        inputField.setEnabled(false);
        startButton.setEnabled(true);
        retryButton.setEnabled(true);
    }

    private void retryTest() {
        textArea.setText("");
        inputField.setText("");
        // statusLabel.setText("Press:Start Test");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TypingGameGUI());
    }
}
