/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Dimitris Dedousis <dimitris.dedousis@gmail.com>
 */
public class Categories {

    private Map<String, Integer> unitedWordFrequencyForLegitEmails;
    private Map<String, Integer> unitedWordFrequencyForSpamEmails;
    private List<Email> emails;
    private int totalWordsForLegitEmail = 0;
    private int totalWordsForSpamMail = 0;
    private List<Email> legitOrSpamMails;

    {
        unitedWordFrequencyForLegitEmails = new HashMap<>();
        unitedWordFrequencyForSpamEmails = new HashMap<>();
    }

    public Categories(List<Email> emails) {
        this.emails = emails;

    }

    public void setTotalWordCountForEveryCategory() {
        for (Email email : emails) {
            if (email.getCategory() == 0) {
                for (Map.Entry<String, Integer> map : email.getData().entrySet()) {
                    totalWordsForLegitEmail += map.getValue();
                    Integer value = unitedWordFrequencyForLegitEmails.get(map.getKey());
                    if (value == null) {
                        unitedWordFrequencyForLegitEmails.put(map.getKey(), map.getValue());
                    } else {
                        Integer newValue = value + map.getValue();
                        unitedWordFrequencyForLegitEmails.put(map.getKey(), newValue);
                    }
                }
            } else {
                for (Map.Entry<String, Integer> map : email.getData().entrySet()) {
                    totalWordsForSpamMail += map.getValue();
                    Integer value = unitedWordFrequencyForSpamEmails.get(map.getKey());
                    if (value == null) {
                        unitedWordFrequencyForSpamEmails.put(map.getKey(), map.getValue());
                    } else {
                        Integer newValue = value + map.getValue();
                        unitedWordFrequencyForSpamEmails.put(map.getKey(), newValue);
                    }
                }
            }
        }
    }

    public Map<String, Integer> getUnitedWordFrequencyForLegitEmails() {
        return unitedWordFrequencyForLegitEmails;
    }

    public Map<String, Integer> getUnitedWordFrequencyForSpamEmails() {
        return unitedWordFrequencyForSpamEmails;
    }

    public int getTotalWordsForLegitEmail() {
        return totalWordsForLegitEmail;
    }

    public int getTotalWordsForSpamMail() {
        return totalWordsForSpamMail;
    }

    public void setLegitOrSpamMails(List<Email> legitOrSpamMails) {
        this.legitOrSpamMails = legitOrSpamMails;
    }

    public double getCategoryProbability() {
        double prob = (double) legitOrSpamMails.size() / emails.size();
        return Math.log(prob) / Math.log(2);
    }
}
