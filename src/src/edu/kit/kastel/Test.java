package edu.kit.kastel;


import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class Test {
    public static void main(String[] args) {
        Map<String, Integer> map = new LinkedHashMap<>();
        map.put("A", 2);
        map.put("C", 1);
        map.put("B", 3);
        System.out.println(map.values());
        System.out.println(map.keySet());
    }


}