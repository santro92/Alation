package com.util;

import com.index.ConstructDS;
import com.index.QueryDS;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

class ProjectTester {
    public static void main(String args[]) {

        Map<String, Integer> input = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(RandomVarNameGenerator.FILENAME))) {
            String sCurrentLine;
            while ((sCurrentLine = br.readLine()) != null) {
                try {
                    input.put(sCurrentLine.split(",")[0], Integer.parseInt(sCurrentLine.split(",")[1]));
                } catch (Exception e) {
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        ConstructDS.getInstance().serializeData(input);
        QueryDS search = QueryDS.getInstance();
        long start = System.currentTimeMillis();
        System.out.println(search.getTop10Strings("rev"));
        System.out.println((System.currentTimeMillis() - start) / 1000.0);
    }
}
