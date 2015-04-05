/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import Utils.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Dimitris Dedousis <dimitris.dedousis@gmail.com>
 */
public class MultinomialClassification {

    private MailParser trainParser;
    private MailParser testParser;
    private Categories categories;
    private List<Email> classifiedEmails;

    {
        classifiedEmails = new ArrayList<>();
    }

    public MultinomialClassification(MailParser trainParser, MailParser testParser, Categories categories) {
        this.trainParser = trainParser;
        this.testParser = testParser;
        this.categories = categories;
    }

    /**
     * The probability of a document d being in class c.
     */
    public void calculateProbability() {
        classifiedEmails.clear();
        for (Email e : testParser.getTotalCategorizedEmails()) {//Iterate all over the documents in the test set.
            double probabilityOfLegitMail = 0.0;
            double probabilityOfSpamMail = 0.0;
            Map<String, Integer> data = e.getData();
            for (Map.Entry<String, Integer> map : data.entrySet()) {
                probabilityOfLegitMail = (double) probabilityOfLegitMail + probabilityToLogarithm(calculateConditionalProbability(map.getKey(), Utils.LEGIT)) * map.getValue();
                probabilityOfSpamMail = (double) probabilityOfSpamMail + probabilityToLogarithm(calculateConditionalProbability(map.getKey(), Utils.SPAM)) * map.getValue();
            }
            categories.setLegitOrSpamMails(trainParser.getLegitEmails());
            probabilityOfLegitMail += categories.getPriorCategoryProbability();
            categories.setLegitOrSpamMails(trainParser.getSpamEmails());
            probabilityOfSpamMail += categories.getPriorCategoryProbability();
            if (probabilityOfLegitMail > probabilityOfSpamMail) {
                classifiedEmails.add(e);
                e.setClassifiedCategory(Utils.LEGIT);
            } else {
                classifiedEmails.add(e);
                e.setClassifiedCategory(Utils.SPAM);
            }
        }
    }

    /**
     * This method calculates the probability of a term (t) being in a certain
     * category (c) P(term | category). Laplace smoothing is used to avoid zero
     * results.
     *
     * @param word
     * @return
     */
    private double calculateConditionalProbability(String word, int category) {
        double probabilityForBothCategories = 0.0;
        if (category == Utils.LEGIT) {
            probabilityForBothCategories =
                    (double) (categories.getWordFrequencyInLegitEmails(word) + 1)
                    / (categories.getTotalWordsForLegitEmail() + trainParser.getDictionary().size());
        } else {
            probabilityForBothCategories =
                    (double) (categories.getWordFrequencyInSpamEmails(word) + 1)
                    / (categories.getTotalWordsForSpamMail() + trainParser.getDictionary().size());
        }
        return probabilityForBothCategories;
    }

    private double probabilityToLogarithm(double prob) {
        return Math.log(prob) / Math.log(2);
    }

    public List<Email> getClassifiedEmails() {
        return classifiedEmails;
    }

    public void printDefaultCategoryAndAssign() {
        for (Email e : classifiedEmails) {
            System.out.println("Initial category: " + e.getCategory());
            System.out.println("Automative category: " + e.getClassifiedCategory());
        }
    }
}
