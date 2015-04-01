/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import Utils.Email;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Dimitris Dedousis <dimitris.dedousis@gmail.com>
 */
public class MailParser {

    private List<File> fileList;
    private Map<String, Integer> wordFrequency;
    private List<Email> legitEmails;
    private List<Email> spamEmails;
    private Map<String, Integer> dictionary;
    private List<Email> allMails;

    {
        allMails = new ArrayList<>();
        dictionary = new HashMap<>();
        legitEmails = new ArrayList<>();
        spamEmails = new ArrayList<>();
    }

    public MailParser(List<File> fileList) {
        this.fileList = fileList;
    }

    public void parseAndCategorize() throws IOException {
        for (File file : fileList) {
            wordFrequency = new HashMap<>();
            if (file.getName().endsWith(".txt")) {
                String fileName = file.getName().replaceAll("[0-9]+", "");
                String data = new String(Files.readAllBytes(file.toPath()));
                String dataOfNumbers = data.replaceAll("[a-zA-Z]+.", "");
                String[] dataFormated = dataOfNumbers.split("\\s+");
                for (String token : dataFormated) {
                    Integer value = wordFrequency.get(token);
                    if (value == null) {
                        value = 0;
                        wordFrequency.put(token, ++value);
                    } else {
                        wordFrequency.put(token, ++value);
                    }
                    Integer dictionaryValue = dictionary.get(token);
                    if (dictionaryValue == null) {
                        dictionaryValue = 0;
                        dictionary.put(token, ++dictionaryValue);
                    } else {
                        dictionary.put(token, ++dictionaryValue);
                    }
                }
                setCategory(fileName);
            }
        }
    }

    private void setCategory(String fileName) {
        int category;
        if (fileName.equalsIgnoreCase("legit.txt")) {
            category = 0;
            Email mail = new Email(wordFrequency, category, fileName);
            legitEmails.add(mail);
            allMails.add(mail);
        } else {
            category = 1;
            Email mail = new Email(wordFrequency, category, fileName);
            spamEmails.add(mail);
            allMails.add(mail);
        }
    }

    public List<Email> getAllMails() {
        return allMails;
    }

    public void resetMails() {
        allMails.clear();
    }

    public List<Email> getLegitEmails() {
        return legitEmails;
    }

    public List<Email> getSpamEmails() {
        return spamEmails;
    }

    public void printLegitMailList() {
        for (Email e : getLegitEmails()) {
            System.out.println("File name: " + e.getFileName() + " in category: " + e.getCategory());
        }
    }

    public void printSpamMailList() {
        for (Email e : getSpamEmails()) {
            System.out.println("File name: " + e.getFileName() + " in category: " + e.getCategory());
        }
    }

    public Map<String, Integer> getDictionary() {
        return dictionary;
    }

    public int getTotalEmails() {
        return legitEmails.size() + spamEmails.size();
    }
}
