package com.util;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

class RandomVarNameGenerator {

    private static final String FILENAME = "test.txt";
    private final static Set<String> KEYWORDS = new HashSet<>(Arrays.asList(
                                                                    "abstract",
                                                                    "continue",
                                                                    "for",
                                                                    "new",
                                                                    "switch",
                                                                    "assert",
                                                                    "default",
                                                                    "goto",
                                                                    "package",
                                                                    "synchronized",
                                                                    "boolean",
                                                                    "do",
                                                                    "if",
                                                                    "private",
                                                                    "this",
                                                                    "break",
                                                                    "double",
                                                                    "implements",
                                                                    "protected",
                                                                    "throw",
                                                                    "byte",
                                                                    "else",
                                                                    "import",
                                                                    "public",
                                                                    "throws",
                                                                    "case",
                                                                    "enum",
                                                                    "instanceof",
                                                                    "return",
                                                                    "transient",
                                                                    "catch",
                                                                    "extends",
                                                                    "int",
                                                                    "short",
                                                                    "try",
                                                                    "char",
                                                                    "final",
                                                                    "interface",
                                                                    "static",
                                                                    "void",
                                                                    "class",
                                                                    "finally",
                                                                    "long",
                                                                    "strictfp",
                                                                    "volatile",
                                                                    "const",
                                                                    "float",
                                                                    "native",
                                                                    "super",
                                                                    "while"
                                                                    ));

    public static void main(String args[]) throws IOException {
        int characterLength = 200;
        int generateSize = 1000000;

        Map<String, Integer> testInputs = new HashMap<>();
        while(testInputs.size() <  generateSize) {
            String name = RandomStringUtils.randomAlphanumeric(RandomUtils.nextInt(0, characterLength - 1) + 1);
            if(!Character.isJavaIdentifierStart(name.charAt(0)))
                continue;
            if(!KEYWORDS.contains(name)) {
                testInputs.put(name, RandomUtils.nextInt(1, 101));
            }
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILENAME))) {
            for (String key : testInputs.keySet()) {
                bw.write(key+","+testInputs.get(key)+"\n");
            }
        }
    }
}
