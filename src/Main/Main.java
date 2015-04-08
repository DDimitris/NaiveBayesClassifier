/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import java.io.File;
import java.util.List;
import Utils.*;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author Dimitris Dedousis <dimitris.dedousis@gmail.com>
 */
public class Main {

    private static List<File> listOfTrainingFiles = new ArrayList<>();
    private static List<File> listOfTestingFiles = new ArrayList<>();

    public static void main(String[] args) throws Exception {
        listOfTrainingFiles.addAll(Utils.getListOfFiles(Utils.TRAINING_EMAIL_DIR));
        Utils.clearFileList();
        listOfTestingFiles.addAll(Utils.getListOfFiles(Utils.TESTING_EMAIL_DIR));
        MailParser trainParser = new MailParser(listOfTrainingFiles);
        MailParser testParser = new MailParser(listOfTestingFiles);
        trainParser.parseAndCategorize();
        testParser.parseAndCategorize();
        /*
         * when you create a new object of type Categories you give as parameter 
         * the size of the list that contains all the mails (training set).
         * After that you can call the method setLegitOrSpamEmails to set the 
         * category of wich you want to calculate the probability and then you can call the
         * method getPriorCategoryProbability to get the probability of the category that mails belongs to.
         * The probability is calculated as follows P(category) = (mails that belongs to these category) / (total mails)
         * This probability is calles prior and show the probability of a document that belongs to a certain category.
         * Note that the result is returned as a logarithm with base 2.
         * A simple example is demonstrated below.
         */
        Categories categories = new Categories(trainParser.getTotalCategorizedEmails());
        categories.setTotalWordCountForEveryCategory();
        categories.setLegitOrSpamMails(trainParser.getLegitEmails());
        double legitMailProbability = categories.getPriorCategoryProbability();
        categories.setLegitOrSpamMails(trainParser.getSpamEmails());
        double spamMailProbability = categories.getPriorCategoryProbability();
        trainParser.printTotalNumberOfFilesRead();
        testParser.printTotalNumberOfFilesRead();
        System.out.println("Vocabulary size: " + trainParser.getDictionary().size());
        System.out.println("Total legit mails: " + trainParser.getLegitEmails().size());
        System.out.println("Total spam mails: " + trainParser.getSpamEmails().size());
        System.out.println("Total mails: " + trainParser.getTotalNumberOfEmails());
        System.out.println("Legit mail prior-probability: " + legitMailProbability);
        System.out.println("Spam mail prior-probability: " + spamMailProbability);
        System.out.println("Total (unique) words in legit mails from map: " + categories.getTotalWordFrequencyForLegitEmails().size());
        System.out.println("Total (unique) words in spam mails from map: " + categories.getTotalWordFrequencyForSpamEmails().size());
        System.out.println("Total words in legit mails from int value: " + categories.getTotalWordsForLegitEmail());
        System.out.println("Total words in spam mails from int value: " + categories.getTotalWordsForSpamMail());
        MultinomialClassification classification = new MultinomialClassification(trainParser, testParser, categories);
        classification.calculateProbability();
        Analysis analysis = new Analysis(classification.getClassifiedEmails());
        analysis.calculatePrecisionRecall();
        analysis.printPrecisionRecall();
        double calculateAccuracy = analysis.calculateAccuracy() * 100;
        double calculateError = analysis.calculateError() * 100;
        System.out.println("Accuracy: " + calculateAccuracy + "%");
        System.out.println("Error: " + calculateError + "%");
//        writeToFile(analysis);
    }

    private static void writeToFile(Analysis analysis) throws IOException {
        FileWriter writer = new FileWriter("C:\\Users\\Dimitris\\Desktop\\results.csv");
        for (PrecisionRecallPair pair : analysis.getPercisionRecallList()) {
            writer.append(pair.getRecall() + "," + pair.getPrecision() + "\n");
        }
        writer.flush();
        writer.close();
    }
}
