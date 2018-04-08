package com.example.scorpiowg.a2340project.model;

/**
 * Created by nancy on 2/25/18.
 */

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CSVFile {
    InputStream inputStream;

    public CSVFile(InputStream inputStream){
        this.inputStream = inputStream;
    }

    public Map<String, String[]> read(){
        Map shelterMap = new HashMap();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        try {
            String oneLine;
            reader.readLine();
            //noinspection NestedAssignment
            while ((oneLine = reader.readLine()) != null) {
                String[] row = oneLine.split(",");
                String address = row[6] + "," + row[7] + "," + row[8];
                String specialNotes = "";
                for (int i = 9; i < (row.length - 2); i++) {
                    specialNotes += row[i] + ",";
                }
                specialNotes += row[row.length - 2];
                String[] modifiedRow = new String[8];
                for (int i = 1; i < 6; i++) {
                    modifiedRow[i-1] = row[i];
                }
                modifiedRow[5] = address;
                modifiedRow[6] = specialNotes;
                modifiedRow[7] = row[row.length - 1];
                String uniqueid = row[0];

//                for (int i = 0; i < modifiedRow.length; i++) {
//                    Log.d("CVSread", modifiedRow[i] + " ");
//                }
                shelterMap.put(uniqueid,modifiedRow);
            }
        }
        catch (IOException ex) {
            throw new RuntimeException("Error in reading CSV file: " + ex);
        }
        finally {
            try {
                inputStream.close();
            }
            catch (IOException e) {
                throw new RuntimeException("Error while closing input stream: "+ e);
            }
        }
//        for (int i = 0; i < resultList.size(); i++) {
//            Log.d("CVSread", resultList.get(i) + " ");
//        }
        return shelterMap;
    }
}