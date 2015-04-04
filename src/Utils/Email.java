/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Utils;

import java.util.List;
import java.util.Map;

/**
 *
 * @author Dimitris Dedousis <dimitris.dedousis@gmail.com>
 */
public class Email {
    private Map<String, Integer> data;
    private int category;
    private String fileName;
    private int classifiedCategory;
    private List<String> dataAsList;
    public Email(List<String> dataAsList, Map<String, Integer> data, int category, String fileName) {
        this.dataAsList = dataAsList;
        this.data = data;
        this.category = category;
        this.fileName = fileName;
    }

    public Map<String, Integer> getData() {
        return data;
    }

    public int getCategory() {
        return category;
    }

    public String getFileName() {
        return fileName;
    }

    public int getClassifiedCategory() {
        return classifiedCategory;
    }

    public void setClassifiedCategory(int classifiedCategory) {
        this.classifiedCategory = classifiedCategory;
    }
    
}
