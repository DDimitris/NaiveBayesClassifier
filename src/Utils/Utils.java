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

    private static final String USER_DIR = System.getProperty("user.dir");
    private static final String FILE_SEPERATOR = System.getProperty("file.separator");
    public static final String TRAINING_EMAIL_DIR = "C:\\Users\\Dimitris\\Dropbox\\2ο Εξάμηνο MSc\\Natural Language Processing\\2ο Σέτ Ασκήσεων\\training";
    public static final String TESTING_EMAIL_DIR = "C:\\Users\\Dimitris\\Dropbox\\2ο Εξάμηνο MSc\\Natural Language Processing\\2ο Σέτ Ασκήσεων\\testing";
    private static List<File> listOfFiles = new ArrayList<>();

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
