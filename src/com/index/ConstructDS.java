package com.index;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConstructDS {

    private static ConstructDS instance;
    private Map<Integer, List<Data>> dataMap;

    private ConstructDS() {
    }

    public static ConstructDS getInstance() {
        if(instance == null){
            instance = new ConstructDS();
        }
        return instance;
    }

    public String serializeData(Map<String, Integer> input) {
        dataMap = new HashMap<>();
        for(String name: input.keySet()) {
            int len = name.length();

            Data data = new Data();
            data.setName(name);
            data.setScore(input.get(name));

            if(dataMap.containsKey(len)) {
                dataMap.get(len).add(data);
            }
            else {
                List<Data> lst = new ArrayList<>();
                lst.add(data);
                dataMap.put(len, lst);
            }
        }

        Gson gson = new Gson();
        return gson.toJson(dataMap);
    }
}
