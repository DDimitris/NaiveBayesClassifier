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
        List<File> listOfFiles = Utils.getListOfFiles(Utils.TRAINING_EMAIL_DIR);
        MailParser parser = new MailParser(listOfFiles);
        parser.parseAndCategorize();
        /*
         * when you create a new object of type Categories you give as parameter 
         * the size of the list that contains all the mails (training set).
         * After that you can call the method setLegitOrSpamEmails to set the 
         * category of wich you want to calculate the probability and then you can call the
         * method getCategoryProbability to get the probability of the category that mails belongs to.
         * The probability is calculated as follows P(category) = (mails that belongs to these category) / (total mails)
         * Note that the result is returned as a logarithm with base 2.
         * A simple example is demonstrated below.
         */
        Categories categories = new Categories(parser.getAllMails());
        categories.setLegitOrSpamMails(parser.getLegitEmails());
        double legitMailProbability = categories.getCategoryProbability();
        categories.setLegitOrSpamMails(parser.getSpamEmails());
        double spamMailProbability = categories.getCategoryProbability();
        System.out.println("Total legit mails: " + parser.getLegitEmails().size());
        System.out.println("Total spam mails: " + parser.getSpamEmails().size());
        System.out.println("Total mails: " + parser.getTotalEmails());
        System.out.println("Legit mail probability: " + legitMailProbability);
        System.out.println("Spam mail probability: " + spamMailProbability);
    }
}
