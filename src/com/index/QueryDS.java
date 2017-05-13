package com.index;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.*;

public class QueryDS {

    private static QueryDS instance;

    private Map<Integer, List<Data>> dataMap;
    Type dataMapType = new TypeToken<HashMap<Integer, List<Data>>>(){}.getType();


    private QueryDS(String jsonString) {
        Gson gson = new Gson();
        dataMap = gson.fromJson(jsonString, dataMapType);
    }

    public static QueryDS getInstance(String jsonString) {
        if(instance == null){
            instance = new QueryDS(jsonString);
        }
        return instance;
    }

    public List<String> getTop10Strings(String query) {

        PriorityQueue<Data> queue = new PriorityQueue<>();
        int queryLen = query.length();

        BoyerMoore bm = new BoyerMoore(query);

        for(Integer key: dataMap.keySet()) {
            if(key >= queryLen) {
                for(Data d: dataMap.get(key)) {
                    if(bm.search(d.getName()) != d.getName().length()) {
                        queue.offer(d);
                        if(queue.size() > 10) {
                            queue.poll();
                        }
                    }
                }
            }
        }

        List<String> result = new ArrayList<>();
        while(queue.size()>0){
            result.add(queue.poll().getName());
        }
        Collections.reverse(result);

        return result;
    }

}
