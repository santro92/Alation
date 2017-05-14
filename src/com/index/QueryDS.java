package com.index;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import org.apache.commons.lang3.StringUtils;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

public class QueryDS {
    private static QueryDS instance;
    private Map<Integer, List<ConstructDS.Data>> dataMap;
    private static int TOP_K_NAMES;

    private QueryDS(Properties prop) {
        TOP_K_NAMES = Integer.parseInt(prop.getProperty("NoOfNamesToBeRetrieved"));
        Gson gson = new Gson();
        JsonReader jsonString = null;
        try {
            jsonString = new JsonReader(new FileReader(prop.getProperty("SerializedOutputFileName")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if (jsonString != null) {
            dataMap = gson.fromJson(jsonString, new TypeToken<HashMap<Integer, List<ConstructDS.Data>>>() {}.getType());
        }
    }

    public static QueryDS getInstance(Properties prop) {
        if (instance == null) {
            instance = new QueryDS(prop);
        }
        return instance;
    }

    public Stack<String> getTop10Strings(String query) {
        PriorityQueue<ConstructDS.Data> queue = new PriorityQueue<>();
        if (!query.equals(StringUtils.EMPTY)) {
            for (Integer key : dataMap.keySet()) {
                if (key >= query.length()) {
                    for (ConstructDS.Data d : dataMap.get(key)) {
                        if (d.getName().startsWith(query)) {
                            queue.offer(d);
                            if (queue.size() > TOP_K_NAMES) {
                                queue.poll();
                            }
                        } else {
                            if (d.isUnderscorePresent()) {
                                for (Integer pos : d.getUnderscore_pos()) {
                                    if (d.getName().substring(pos + 1).startsWith(query)) {
                                        queue.offer(d);
                                        if (queue.size() > TOP_K_NAMES) {
                                            queue.poll();
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        Stack<String> result = new Stack<>();
        while (queue.size() > 0) {
            result.push(queue.poll().getName());
        }
        return result;
    }
}
