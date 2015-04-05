/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import Utils.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Dimitris Dedousis <dimitris.dedousis@gmail.com>
 */
public class Analysis {

    private int truePositive = 0;
    private int falsePositive = 0;
    private int falseNegative = 0;
    private int trueNegative = 0;
    private List<Email> testMails;
    private List<PrecisionRecallPair> pairList;

    {
        pairList = new ArrayList<>();
    }

    public Analysis(List<Email> testMails) {
        this.testMails = testMails;
    }

    public void calculatePrecisionRecall() {
        pairList.clear();
        for (Email e : testMails) {
            if (e.isCorrectClassification() && e.getCategory() == Utils.LEGIT) {
                truePositive++;
            } else if (e.getCategory() != e.getClassifiedCategory() && e.getCategory() == Utils.SPAM) {
                falseNegative++;
            } else if (e.getCategory() != e.getClassifiedCategory() && e.getCategory() == Utils.LEGIT) {
                falsePositive++;
            } else {
                trueNegative++;
            }
            PrecisionRecallPair<Double, Double> pair = new PrecisionRecallPair<>();
            double precision = (double) truePositive / (truePositive + falsePositive);
            double recall = (double) truePositive / (truePositive + falseNegative);
            pair.add(precision, recall);
            pairList.add(pair);
        }
    }

    public List<PrecisionRecallPair> getPercisionRecallList() {
        return pairList;
    }

    public double calculateAccuracy() {
        int correct = 0;
        for (Email email : testMails) {
            if (email.getCategory() == email.getClassifiedCategory()) {
                correct++;
            }
        }
        return (double) correct / testMails.size();
    }

    public double calculateError() {
        return 1 - calculateAccuracy();
    }

    public void printPrecisionRecall() {
        for (PrecisionRecallPair pair : getPercisionRecallList()) {
            System.out.println("Precision " + pair.getPrecision() + "\t" + " recall " + pair.getRecall());
        }
        System.out.println("Total calculations: " + getPercisionRecallList().size());
    }
}
