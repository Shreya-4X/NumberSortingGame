package com.example.puzzlesolver;

import java.awt.*;


import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.Border;

public class SelectionSortPuzzle extends JFrame {
    private JButton[] buttons;
    private int[] numbers;
    private int currentIndex;
    private JButton resetButton;
    private long startTime;

    public SelectionSortPuzzle() {
        setTitle("Selection Sort Puzzle");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(true);
        setLocationRelativeTo(null);

        initializeUI();

        setSize(500, 500);
        setVisible(true);
        setLocationRelativeTo(null);

        startTimer(); // Start the timer
    }

    private void initializeUI() {
        JPanel panel = new JPanel(new GridLayout(3, 3, 5, 5));
        buttons = new JButton[9];
        numbers = new int[9];

        for (int i = 0; i < 9; i++) {
            buttons[i] = new JButton();
            buttons[i].setFont(new Font("Arial", Font.BOLD, 32));
            buttons[i].addActionListener(new ButtonClickListener());
            panel.add(buttons[i]);
        }

        generateNumbers();
        updateButtons();

        getContentPane().setBackground(Color.GRAY);

        Border border = BorderFactory.createLineBorder(Color.BLACK, 5);
        panel.setBorder(border);

        getContentPane().add(panel, BorderLayout.CENTER);

        resetButton = new JButton("Reset");
        resetButton.addActionListener(new ResetButtonClickListener());
        resetButton.setBackground(Color.BLUE);
        resetButton.setForeground(Color.WHITE);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(resetButton);

        getContentPane().add(buttonPanel, BorderLayout.SOUTH);
    }

    private void startTimer() {
        startTime = System.currentTimeMillis();
        Timer timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                long elapsedTime = System.currentTimeMillis() - startTime;
                long minutes = (elapsedTime / 1000) / 60;
                long seconds = (elapsedTime / 1000) % 60;
                setTitle("Selection Sort Puzzle - Time: " + minutes + " minutes " + seconds + " seconds");
            }
        });
        timer.start();
    }

    private void generateNumbers() {
        for (int i = 0; i < 9; i++) {
            numbers[i] = (int) (Math.random() * 100);
        }
    }

    private void updateButtons() {
        for (int i = 0; i < 9; i++) {
            buttons[i].setText(String.valueOf(numbers[i]));
        }
    }

    private boolean isSorted() {
        for (int i = 0; i < 8; i++) {
            if (numbers[i] > numbers[i + 1]) {
                return false;
            }
        }
        return true;
    }

    private void showCongratulations(long timeTaken) {
        long minutes = timeTaken / (1000 * 60);
        long seconds = (timeTaken / 1000) % 60;

        String message = "Congratulations! Numbers are sorted.\n";
        message += "Time taken: " + minutes + " minutes " + seconds + " seconds.";

        JOptionPane.showMessageDialog(this, message, "Congratulations", JOptionPane.INFORMATION_MESSAGE);
    }

    private long calculateTimeTaken() {
        return System.currentTimeMillis() - startTime;
    }

    private class ButtonClickListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JButton source = (JButton) e.getSource();

            if (currentIndex == 0) {
                startTime = System.currentTimeMillis();
            }

            for (int i = 0; i < 9; i++) {
                if (source == buttons[i]) {
                    currentIndex = i;
                    break;
                }
            }

            int minIndex = currentIndex;
            for (int i = currentIndex + 1; i < 9; i++) {
                if (numbers[i] < numbers[minIndex]) {
                    minIndex = i;
                }
            }

            int temp = numbers[currentIndex];
            numbers[currentIndex] = numbers[minIndex];
            numbers[minIndex] = temp;

            updateButtons();

            if (isSorted()) {
                long timeTaken = calculateTimeTaken();
                showCongratulations(timeTaken);
            }
        }
    }

    private class ResetButtonClickListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            currentIndex = 0;
            generateNumbers();
            updateButtons();
            startTimer();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                showStartScreen();
            }
        });
    }

    private static void showStartScreen() {
        JFrame startFrame = new JFrame();
        startFrame.setTitle("Sorting Algorithm Game");
        startFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        startFrame.setResizable(false);
        startFrame.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setBackground(Color.BLUE);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel titleLabel = new JLabel("Sorting Algorithm Game");
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 42));
        titleLabel.setForeground(Color.BLACK);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(40, 0, 40, 0));
        panel.add(titleLabel);

        JButton enterButton = new JButton("Enter");
        enterButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        enterButton.setFont(new Font("Arial", Font.BOLD, 20));
        enterButton.setForeground(Color.WHITE);
        enterButton.setBackground(Color.BLACK);
        enterButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                startFrame.dispose();
                showGameWindow();
            }
        });
        panel.add(enterButton);

        startFrame.getContentPane().add(panel);
        startFrame.pack();
        startFrame.setVisible(true);
        startFrame.setLocationRelativeTo(null);
    }

    private static void showGameWindow() {
        SelectionSortPuzzle game = new SelectionSortPuzzle();
        game.setLocationRelativeTo(null);
    }
}
