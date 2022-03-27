package com.example.mini_rpg_lite_3000_withinterface.utils;

import java.util.Scanner;

public class InputParser {

    public static String questionString(String question){
        Scanner scanner = new Scanner(System.in);
        System.out.println(question);
        return scanner.nextLine();
    }

    public static int questionInt(String question){
        Scanner scanner = new Scanner(System.in);
        System.out.println(question);
        return scanner.nextInt();
    }
}
