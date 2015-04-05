/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import Utils.*;
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
    private List<String> dataAsList;

    {
        dictionary = new HashMap<>();
        legitEmails = new ArrayList<>();
        spamEmails = new ArrayList<>();


    }

    public MailParser(List<File> fileList) {
        this.fileList = fileList;
    }

    public void parseAndCategorize() throws IOException {
        for (File file : fileList) {
            dataAsList = new ArrayList<>();
            wordFrequency = new HashMap<>();
            if (file.getName().endsWith(".txt")) {
                String fileName = file.getName().replaceAll("[0-9]+", "");
                String data = new String(Files.readAllBytes(file.toPath()));
                String dataOfNumbers = data.replaceAll("[a-zA-Z]+.", "");
                String[] dataFormated = dataOfNumbers.split("\\s+");
                for (String token : dataFormated) {
                    try {
                        Integer.parseInt(token);
                    } catch (Exception e) {
                        continue;
                    }
                    dataAsList.add(token);
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
        if (fileName.equalsIgnoreCase("legit.txt")) {
            Email mail = new Email(dataAsList, wordFrequency, Utils.LEGIT, fileName);
            legitEmails.add(mail);
        } else {
            Email mail = new Email(dataAsList, wordFrequency, Utils.SPAM, fileName);
            spamEmails.add(mail);
        }
    }

    public List<Email> getLegitEmails() {
        return legitEmails;
    }

    public List<Email> getSpamEmails() {
        return spamEmails;
    }

    public void printTotalNumberOfFilesRead() {
        System.out.println("Total number of files read: " + getTotalNumberOfEmails());
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

    public List<Email> getTotalCategorizedEmails() {
        List<Email> total = new ArrayList<>();
        total.addAll(legitEmails);
        total.addAll(spamEmails);
        return total;
    }

    public int getTotalNumberOfEmails() {
        return legitEmails.size() + spamEmails.size();
    }
}
