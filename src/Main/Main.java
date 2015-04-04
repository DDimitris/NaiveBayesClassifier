/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import java.io.File;
import java.util.List;
import Utils.*;

/**
 *
 * @author Dimitris Dedousis <dimitris.dedousis@gmail.com>
 */
public class Main {

    public static void main(String[] args) throws Exception {
        List<File> listOfTrainingFiles = Utils.getListOfFiles(Utils.TRAINING_EMAIL_DIR);
        List<File> listOfTestingFiles = Utils.getListOfFiles(Utils.TESTING_EMAIL_DIR);
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
        Categories categories = new Categories(trainParser.getAllMails());
        categories.setTotalWordCountForEveryCategory();
        categories.setLegitOrSpamMails(trainParser.getLegitEmails());
        double legitMailProbability = categories.getPriorCategoryProbability();
        categories.setLegitOrSpamMails(trainParser.getSpamEmails());
        double spamMailProbability = categories.getPriorCategoryProbability();
        System.out.println("Vocabulary size: " + trainParser.getDictionary().size());
        System.out.println("Total legit mails: " + trainParser.getLegitEmails().size());
        System.out.println("Total spam mails: " + trainParser.getSpamEmails().size());
        System.out.println("Total mails: " + trainParser.getTotalEmails());
        System.out.println("Legit mail probability: " + legitMailProbability);
        System.out.println("Spam mail probability: " + spamMailProbability);
        System.out.println("Total (unique) words in legit mails from map: " + categories.getTotalWordFrequencyForLegitEmails().size());
        System.out.println("Total (unique) words in spam mails from map: " + categories.getTotalWordFrequencyForSpamEmails().size());
        System.out.println("Total words in legit mails from int value: " + categories.getTotalWordsForLegitEmail());
        System.out.println("Total words in spam mails ftom int value: " + categories.getTotalWordsForSpamMail());
    }
}
