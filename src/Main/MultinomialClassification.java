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
        for (Email e : testParser.getTotalCategorizedEmails()) {
            double probabilityOfLegitMail = 0.0;
            double probabilityOfSpamMail = 0.0;
            Map<String, Integer> data = e.getData();
            for (Map.Entry<String, Integer> map : data.entrySet()) {
                double[] calculateConditionalProbability = calculateConditionalProbability(map.getKey());
                probabilityOfLegitMail += probabilityToLogarithm(calculateConditionalProbability[0]) * map.getValue();
                probabilityOfSpamMail += probabilityToLogarithm(calculateConditionalProbability[1]) * map.getValue();
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
     * Laplace smoothing is used to avoid zero results.
     *
     * @param word
     * @return
     */
    private double[] calculateConditionalProbability(String word) {
        double[] probabilityForBothCategories = new double[2];
        probabilityForBothCategories[0] =
                (double) (categories.getWordFrequencyInLegitEmails(word) + 1)
                / (categories.getTotalWordsForLegitEmail() + trainParser.getDictionary().size());
        probabilityForBothCategories[1] =
                (double) (categories.getWordFrequencyInSpamEmails(word) + 1)
                / (categories.getTotalWordsForSpamMail() + trainParser.getDictionary().size());
        return probabilityForBothCategories;

    }

    private double probabilityToLogarithm(double prob) {
        return Math.log(prob) / Math.log(2);
    }
}
