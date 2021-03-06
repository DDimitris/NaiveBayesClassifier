/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Dimitris Dedousis <dimitris.dedousis@gmail.com>
 */
public class Utils {

    public static final String TRAINING_EMAIL_DIR = "C:\\Users\\Dimitris\\Dropbox\\2ο Εξάμηνο MSc\\Natural Language Processing\\2ο Σέτ Ασκήσεων\\training";
    public static final String TESTING_EMAIL_DIR = "C:\\Users\\Dimitris\\Dropbox\\2ο Εξάμηνο MSc\\Natural Language Processing\\2ο Σέτ Ασκήσεων\\testing";
    public static final int LEGIT = 0;
    public static final int SPAM = 1;
    private static List<File> listOfFiles = new ArrayList<>();

    public static void clearFileList() {
        listOfFiles.clear();
    }

    public static List<File> getListOfFiles(String path) {
        File directory = new File(path);
        File[] fList = directory.listFiles();
        for (File file : fList) {
            if (file.isFile()) {
                listOfFiles.add(file);
            } else if (file.isDirectory()) {
                getListOfFiles(file.getAbsolutePath());
            }
        }
        return listOfFiles;
    }
}
