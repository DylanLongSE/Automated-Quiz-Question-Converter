Automated Conversion of Fill-in-the-Blank and Short-Answer Questions to Multiple-Choice Questions

Overview
This project automates the conversion of fill-in-the-blank (FIB) and short-answer (SA) questions into multiple-choice questions (MCQs) using real student responses.
By leveraging previously graded answers, the tool creates MCQs that reflect actual misconceptions, improving the accuracy and fairness of assessments while saving time for educators.

Project Details
  - Course: STEC 4500
  - Semester: Summer 2024
  - Supervisor: Dr. Wei Jin
  - Developed By: Danielle Mathieu and Dylan Long
    
Features
  - Converts FIB/SA questions into MCQs using graded student answers from CSV files.
  - Captures real student misconceptions for more effective distractors.
  - Ensures fairness by incorporating partial credit and shuffling answer choices.
  - Outputs MCQs in a CSV format compatible with D2L for seamless quiz creation.
    
Technology Stack
  - Programming Language: Java
  - Data Storage: Hash maps for efficient question and answer storage
  - Output: CSV file for direct upload to D2L course management system

How It Works
  - Input: A CSV file containing anonymized, graded student responses (hidden for privacy reasons).
Processing:
  - The program, ReadFile.java, processes the CSV to extract FIB/SA questions and answers.
  - Uses hash maps to store correct and incorrect answers, associating them with each question.
  - Generates a minimum of three distractors for each question, using "None of the above" or "All of the above" if necessary.
  - Randomizes the order of answer choices.
Output: A new CSV file containing the generated MCQs, ready to be uploaded to D2L.

Usage
  - Obtain the input CSV file with student responses (hidden for privacy reasons).
  - Run the ReadFile.java program in your preferred Java IDE or command line.
  - The program will generate an output CSV file containing MCQs.
  - Upload the generated CSV file directly to D2L as a question bank or quiz.
    
Example Workflow
  - Export student answers from D2L as a CSV file.
  - Combine multiple CSV files, if applicable, into a single input file.
  - Execute ReadFile.java to generate the output.
  - Import the output into D2L for automatic grading and quiz deployment.
    
Benefits
  - Efficiency: Automates the time-consuming task of grading FIB/SA questions.
  - Fairness: Ensures consistency by using real student responses as answer choices.
  - Improved Assessment: MCQs based on actual misconceptions challenge students and enhance learning outcomes.
    
Limitations
  - Requires a properly formatted input CSV file, which is not included in this repository for privacy reasons.
  - Customization for different grading systems or formats may require additional adjustments.
    
License
  - This project is for educational purposes under Georgia Gwinnett College's guidelines.

Acknowledgments
  - Advisor: Dr. Wei Jin for guidance and supervision.
  - Collaborator: Danielle Mathieu for contributing to the project development.

Example of Outcome

<img width="1170" alt="Screenshot 2024-11-27 at 6 29 45 PM" src="https://github.com/user-attachments/assets/43f8c03e-f0c4-496e-8438-4b0393a42038">
