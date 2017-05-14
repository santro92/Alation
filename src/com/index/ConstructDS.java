package com.index;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.*;

public class ConstructDS {

    private static ConstructDS instance;
    private static final String PREFIX = "_";

    private ConstructDS() {
    }

    public static ConstructDS getInstance() {
        if (instance == null) {
            instance = new ConstructDS();
        }
        return instance;
    }

    public void serializeData(Map<String, Integer> input) {
        Map<Integer, List<Data>> dataMap = new HashMap<>();
        if (input != null) {
            for (String name : input.keySet()) {
                Data data = new Data();
                data.setName(name);
                data.setScore(input.get(name));

                List<Integer> us_lst = new LinkedList<>();
                int index = name.indexOf(PREFIX);
                while (index >= 0) {
                    us_lst.add(index);
                    index = name.indexOf(PREFIX, index + 1);
                }
                data.setUnderscore_pos(us_lst);

                if (us_lst.size() > 0) {
                    data.setUnderscorePresent();
                }

                if (dataMap.containsKey(name.length())) {
                    dataMap.get(name.length()).add(data);
                } else {
                    List<Data> lst = new ArrayList<>();
                    lst.add(data);
                    dataMap.put(name.length(), lst);
                }
            }
        }

        try (Writer writer = new FileWriter("Output.json")) {
            Gson gson = new GsonBuilder().create();
            gson.toJson(dataMap, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    class Data implements Comparable<Data> {
        private String name;
        private int score;
        private boolean underscorePresent = false;
        private List<Integer> underscore_pos;

        Data() {

        }

        String getName() {
            return name;
        }

        void setName(String name) {
            this.name = name;
        }

        int getScore() {
            return score;
        }

        void setScore(int score) {
            this.score = score;
        }

        boolean isUnderscorePresent() {
            return underscorePresent;
        }

        void setUnderscorePresent() {
            this.underscorePresent = true;
        }

        List<Integer> getUnderscore_pos() {
            return underscore_pos;
        }

        void setUnderscore_pos(List<Integer> underscore_pos) {
            this.underscore_pos = underscore_pos;
        }

        @Override
        public int compareTo(Data obj) {
            int score = obj.getScore();
            String obj_name = obj.getName();

            if (this.getScore() < score) {
                return -1;
            }

            if (this.getScore() > score) {
                return 1;
            }

            if (this.getScore() == score) {
                return this.getName().compareTo(obj_name);
            }
            return 0;
        }
    }
}
