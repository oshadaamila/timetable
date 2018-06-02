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
import java.util.Arrays;
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
    static ArrayList<Subject> subjectList= new ArrayList<Subject>();
    static ArrayList<String> rooms= new ArrayList<String>();
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
        for(int i=0;i<input.size()-1;i++){
            Subject sbj = getSubject(input.get(i));
            subjectList.add(sbj);
        }
        for(String x:input.get(input.size()-1)){
            rooms.add(x);
        }
        
        System.out.println("finished");
        
        

    }
    
    static Subject getSubject(String[] array){
        String name = array[0];
        boolean cmplsr;
        System.out.println(array[1]);
        if(array[1].toString().contains("c")){
            cmplsr=true;
        }else{
            cmplsr=false;
        }
        String [] timeslts = Arrays.copyOfRange(array, 2, array.length);
        Subject sbj =  new Subject(cmplsr,name,timeslts);
        return sbj;
    }
    
}
