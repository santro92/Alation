package com.util;

import com.index.ConstructDS;
import com.index.QueryDS;
import org.apache.commons.lang3.RandomStringUtils;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

class ProjectTester {

    private Properties prop = new Properties();

    private ProjectTester() {
        getProperties();
    }

    private void getProperties() {
        String propFileName = "resources/config.properties";
        try (InputStream inputStream = new FileInputStream(propFileName)) {
            prop.load(inputStream);
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        }
    }

    private QueryDS getSearchQueryInstance() {
        Map<String, Integer> input = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(prop.getProperty("InputFileName")))) {
            String sCurrentLine;
            while ((sCurrentLine = br.readLine()) != null) {
                try {
                    input.put(sCurrentLine.split(",")[0], Integer.parseInt(sCurrentLine.split(",")[1]));
                }
                catch (Exception ignored) {
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        ConstructDS.getInstance(prop).serializeData(input);
        return QueryDS.getInstance(prop);
    }

    public static void main(String args[]) {
        ProjectTester tester= new ProjectTester();
        QueryDS search = tester.getSearchQueryInstance();
        String query = RandomStringUtils.randomAlphanumeric(5);

        long start = System.currentTimeMillis();
        System.out.println("The Results for query - " + query + "\n" + search.getTop10Strings(query));
        System.out.println("Time taken:" + (System.currentTimeMillis() - start)/1000.0 +"secs");
    }
}
