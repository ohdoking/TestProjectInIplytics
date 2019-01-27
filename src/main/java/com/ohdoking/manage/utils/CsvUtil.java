package com.ohdoking.manage.utils;

import com.ohdoking.manage.dao.Task;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CsvUtil {

    List list;

    public CsvUtil(){
        list = new ArrayList();
    }

    /**
     * read Data from CSV
     * @param path
     * @return
     */
    public List<String[]> readData(String path) {
        List<String[]> content = new ArrayList<>();
        try{
            CSVReader reader = new CSVReader(new FileReader(path), ',','"',1);
            List<String[]> lines = reader.readAll();
            for(String[] row : lines){
                content.add(row);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }

    /**
     * delete specific item row in CSV
     * @param path
     * @param deleteItemName
     * @param header
     */
    public void deleteRowInCSV( String path, String deleteItemName, String[] header) {
        try {
            CSVReader reader = new CSVReader(new FileReader(path), ',','"',1);
            List<String[]> lines = reader.readAll();
            CSVWriter writer = new CSVWriter(new FileWriter(path,false), ',');
            writer.writeNext(header);
            for(String[] row : lines){
                //delete row
                if(deleteItemName.equals(row[0])) {
                    continue;
                }
                writer.writeNext(row);
            }
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
