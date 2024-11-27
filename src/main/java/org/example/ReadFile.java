package org.example;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

import java.io.BufferedWriter;
import java.util.HashMap;
import java.util.List;

public class ReadFile {
    public static void readFileIn() {
        String fileName = "src/main/resources/2023 summer ITEC-2150 PostTest (Midterm1) 4 - Anonymtized.csv"; // Updated path

        ArrayList<String> studentId = new ArrayList<>();
        ArrayList<String> questionType = new ArrayList<>();
        ArrayList<String> questionTitle = new ArrayList<>();
        ArrayList<String> questionAsked = new ArrayList<>();
        ArrayList<String> answerField = new ArrayList<>();
        ArrayList<String> studentAnswer = new ArrayList<>();
        ArrayList<String> score = new ArrayList<>();
        ArrayList<String> outOf = new ArrayList<>();

        ArrayList<ArrayList<String>> listOfFields = new ArrayList<>();

        listOfFields.add(studentId);
        listOfFields.add(questionType);
        listOfFields.add(questionTitle);
        listOfFields.add(questionAsked);
        listOfFields.add(answerField);
        listOfFields.add(studentAnswer);
        listOfFields.add(score);
        listOfFields.add(outOf);

        HashMap<String, List<String>> correctAnswersMap = new HashMap<>(); // Map to connect questions to correct answers
        HashMap<String, List<List<String>>> wrongAnswers = new HashMap<>(); // Map to store wrong answers

        try (CSVReader br = new CSVReader(new FileReader(fileName))) {
            List<String[]> csvData = br.readAll();

            if (csvData.isEmpty()) {
                System.out.println("The CSV file is empty.");
                return;
            }

            int studentIdIndex = 0;
            int qTypeIndex = 1;
            int qTitleIndex = 2;
            int questionAskedIndex = 3;
            int answerBlanksIndex = 4;
            int studentAnswerIndex = 5;
            int scoreIndex = 6;
            int outOfIndex = 7;

            String currentStudentId = "";
            String currentQuestion = "";
            String currentQuestionTitle = "";
            String currentOutOfScore = "";
            int blankCount = 1;

            List<String> currentWrongAnswers = new ArrayList<>();
            Question question;

            for (int i = 1; i < csvData.size(); i++) {
                String[] currentRow = csvData.get(i);
                if (!currentRow[qTypeIndex].equals("FIB")) {
                    continue;
                }

                question = new Question(
                        currentRow[studentIdIndex].trim(),
                        currentRow[qTitleIndex].trim(),
                        currentRow[questionAskedIndex].trim(),
                        currentRow[answerBlanksIndex].trim() + ": ",
                        currentRow[studentAnswerIndex].trim(),
                        currentRow[scoreIndex].trim(),
                        currentRow[outOfIndex].trim()
                );

                // Reset for a new question or new student
                if (!question.getStudentId().equals(currentStudentId) || !question.getQuestionAsked().equals(currentQuestion)) {
                    currentStudentId = question.getStudentId();
                    currentQuestion = question.getQuestionAsked();
                    currentQuestionTitle = question.getQuestionTitle();
                    currentOutOfScore = question.getOutOf();
                    blankCount = 1;

                    // Add the wrong answers for the previous student/question to the map
                    if (!currentWrongAnswers.isEmpty()) {
                        String questionKey = currentQuestion + "^" + currentQuestionTitle + "^" + currentOutOfScore;
                        wrongAnswers.computeIfAbsent(questionKey, k -> new ArrayList<>()).add(new ArrayList<>(currentWrongAnswers));
                        currentWrongAnswers.clear();
                    }
                }

                // Create a composite key with question text and title
                String questionKey = currentQuestion + "^" + currentQuestionTitle + "^" + currentOutOfScore;

                if (question.getScore().equals(question.getOutOf())) {
                    correctAnswersMap.computeIfAbsent(questionKey, k -> new ArrayList<>());

                    if (correctAnswersMap.get(questionKey).size() < blankCount) {
                        correctAnswersMap.get(questionKey).add(question.getStudentAnswer() + "^100");
                        blankCount++;
                    }
                } else {
                    currentWrongAnswers.add(question.getStudentAnswer() + "^" + question.getScore());
                }
            }

            // Add the last set of wrong answers to the map
            if (!currentWrongAnswers.isEmpty()) {
                String questionKey = currentQuestion + "^" + currentQuestionTitle + "^" + currentOutOfScore;
                wrongAnswers.computeIfAbsent(questionKey, k -> new ArrayList<>()).add(new ArrayList<>(currentWrongAnswers));
            }

            System.out.println("Wrong Answers Map:");
            for (Map.Entry<String, List<List<String>>> entry : wrongAnswers.entrySet()) {
                System.out.println("Question: " + entry.getKey());
                System.out.println("Wrong Answers: " + entry.getValue() + "\n");
            }

            // Write the output to a CSV file in the specified format
            writeOutputToCSV(correctAnswersMap, wrongAnswers);

        } catch (IOException | CsvException e) {
            e.printStackTrace();
        }
    }

    private static void writeOutputToCSV(HashMap<String, List<String>> correctAnswersMap, HashMap<String, List<List<String>>> wrongAnswers) {
        String outputFileName = "src/main/resources/output.csv";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFileName))) {
            writer.write(",,,,");
            writer.newLine();
            // Write data with numbered questions
            for (String question : correctAnswersMap.keySet()) {
                List<String> correctAnswers = correctAnswersMap.get(question);
                List<List<String>> wrongAnswersForQuestion = wrongAnswers.get(question);

                writer.write("NewQuestion,MC,,,");
                writer.newLine();
                writer.write("ID,CHEM110-237,,,");
                writer.newLine();
                String[] title = question.split("\\^");
                writer.write("Title," + title[1] + ",,,");
                writer.newLine();
                writer.write("QuestionText,\"" + title[0].replace("\"", "\"\"") + "\",,,");
                writer.newLine();
                writer.write("Points," + title[2] + ",,,");
                writer.newLine();
                writer.write("Difficulty,1,,,");
                writer.newLine();
                writer.write("Image,images/MC1.jpg,,,");
                writer.newLine();

                List<String> options = new ArrayList<>(); // list to keep track of all the options created
                Set<String> usedOptions = new HashSet<>(); // Track used options

                if (!correctAnswers.isEmpty()) {
                    StringBuilder correctAnswerText = new StringBuilder("[");
                    for (int i = 0; i < correctAnswers.size(); i++) {
                        String[] answerParts = correctAnswers.get(i).split("\\^");
                        correctAnswerText.append(answerParts[0]);
                        if (i < correctAnswers.size() - 1) {
                            correctAnswerText.append(", ");
                        }
                    }
                    correctAnswerText.append("]");
                    String newtext = correctAnswerText.toString();
                    options.add("Option,100,\"" + newtext.replace("\"", "\"\"") + "\",,"); // adding correct option to the list
                    usedOptions.add(newtext); // Mark correct answer as used
                }

                if (wrongAnswersForQuestion != null) {
                    Collections.shuffle(wrongAnswersForQuestion); // Shuffle the list to randomize the selection

                    // Write up to three wrong answers
                    int wrongAnswersCount = 0;
                    for (int i = 0; wrongAnswersCount < 3 && i < wrongAnswersForQuestion.size(); i++) {
                        List<String> getWrongPoints = wrongAnswersForQuestion.get(i);
                        StringBuilder newArray = new StringBuilder("[");
                        String points = "0"; // Default to 0 points if not set

                        for (int j = 0; j < getWrongPoints.size(); j++) {
                            String[] holdpoints = getWrongPoints.get(j).split("\\^");
                            if (holdpoints.length > 1) {
                                double score = Double.parseDouble(holdpoints[1]);
                                double outOf = Double.parseDouble(title[2]);
                                points = String.valueOf((score / outOf) * 100); // Calculate the score
                            }
                            newArray.append(holdpoints[0]); // Extract answer part
                            if (j < getWrongPoints.size() - 1) {
                                newArray.append(", "); // Append comma between answers
                            }
                        }
                        newArray.append("]");

                        String wrongAnswer = newArray.toString();
                        if (wrongAnswer.matches(".*[a-zA-Z0-9].*") && !usedOptions.contains(wrongAnswer)) {
                            options.add("Option," + points + ",\"" + wrongAnswer.replace("\"", "\"\"") + "\",,"); // adding wrong option to the list
                            usedOptions.add(wrongAnswer); // Mark wrong answer as used
                            wrongAnswersCount++;
                        }
                    }

                    // Ensure there are at least 3 options by adding "[All of the above]" if needed
                    while (options.size() < 3) {
                        if (!usedOptions.contains("[All of the above]")) {
                            options.add("Option,0,\"[All of the above]\",,");
                            usedOptions.add("[All of the above]");
                        }
                    }

                    // Shuffle the all the options added to the list
                    Collections.shuffle(options);

                    // Write all the shuffled options to my csv
                    for (String option : options) {
                        writer.write(option);
                        writer.newLine();
                    }
                }

                writer.write("Hint,This is the hint text,,,");
                writer.newLine();
                writer.write("Feedback,This is the feedback text,,,");
                writer.newLine();
                writer.write(",,,,");
                writer.newLine();
            }
            System.out.println("Output written to " + outputFileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        readFileIn();
    }
}
