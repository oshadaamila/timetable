/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package timetable;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author pavilion 15
 */
public class Timetable {

    /**
     * @param args the command line arguments
     */
    static ArrayList<String[]> input= new ArrayList<String[]>();
    public static void main(String[] args) {
        // TODO code application logic here
        System.out.println("Enter input file path: ");
        Scanner scanner = new Scanner(System.in);
        String inputFilePath = scanner.nextLine();
        System.out.println("Enter output file path: ");
        String outputFilePath = scanner.nextLine();
        String text="";
        try (BufferedReader br = new BufferedReader(new FileReader(inputFilePath))) {
            while ((text = br.readLine()) != null) {
                     // use comma as separator
                input.add(text.split(","));
                        
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        for(String[] x:input){
            for(String y:x)
            System.out.println(y);
        }
        

    }
    
}
