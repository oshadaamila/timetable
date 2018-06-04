/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package timetable;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author pavilion 15
 */
public class Timetable {

    /**
     * @param args the command line arguments
     */
    static ArrayList<String[]> input= new ArrayList<String[]>();
    static ArrayList<Subject> subjectList= new ArrayList<Subject>();            //to hold the subjects list
    static ArrayList<String> timeSlots = new ArrayList<>();                     //to hold the time slots
    static ArrayList<String> rooms= new ArrayList<String>();                    
    static ArrayList<String> generatedAllTimeSlots = new ArrayList<>();         //
    static ArrayList<String> compulsoryTimeSlots = new ArrayList<>();
    static ArrayList<String> assignedTimeSlots = new ArrayList<>();
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
        
        for(String x:input.get(input.size()-1)){
            rooms.add(x);
        }
        
        
        for(int i=0;i<input.size()-1;i++){      //adding subjects to subjects array
            Subject sbj = getSubject(input.get(i));
            subjectList.add(sbj);
        }
        generateAllTimeSlots();
        assignTime(0);
        writeToFile(outputFilePath);
        System.out.println("finished");
        
        

    }
    
    static Subject getSubject(String[] array){
        String name = array[0];
        boolean cmplsr;
        if(array[1].toString().contains("c")){
            cmplsr=true;
        }else{
            cmplsr=false;
        }
        String [] timeslts = Arrays.copyOfRange(array, 2, array.length);
        for (String x: timeslts){
            if(!timeSlots.contains(x)){
                timeSlots.add(x);
            }
        }
        Subject sbj =  new Subject(cmplsr,name,timeslts);
        return sbj;
    }
    
    
    //check this
     static boolean AllAssigned(){
         boolean output=true;
         for(Subject sbj : subjectList){
             if(sbj.assigned==false){
                 output=false;
             }
         }
         return output;
     }
     
     static void assignTime(int index){
         for(String timeSlot:generatedAllTimeSlots){
             if(timeSlotIsAvailable(index,timeSlot)){
                 if(subjectList.get(index).compulsory){
                     if(subjectList.get(index).assigned){
                         assignedTimeSlots.remove(subjectList.get(index).getAssignedTimeSlot()+","+subjectList.get(index).getAssignedRoom());
                         removeFromCompulsory(subjectList.get(index).getAssignedTimeSlot());
                         subjectList.get(index).assignTimeSlot(timeSlot);
                         assignedTimeSlots.add(timeSlot);
                         addTimeSlotToCompulsory(timeSlot);
                     }else{
                         subjectList.get(index).assignTimeSlot(timeSlot);
                         assignedTimeSlots.add(timeSlot);
                         addTimeSlotToCompulsory(timeSlot);
                     }
                 }else{
                     if(subjectList.get(index).assigned){
                         assignedTimeSlots.remove(subjectList.get(index).getAssignedTimeSlot()+","+subjectList.get(index).getAssignedRoom());
                         subjectList.get(index).assignTimeSlot(timeSlot);
                         assignedTimeSlots.add(timeSlot);
                     }else{
                         subjectList.get(index).assignTimeSlot(timeSlot);
                         assignedTimeSlots.add(timeSlot);
                     }
                 }
                 if(index+1<subjectList.size()){
                     assignTime(index+1);
                 }else{
                     return;
                 }
                 
             }
         }
         
     }
     
     static void generateAllTimeSlots(){
        for(String timeSlot : timeSlots){
            for (String room : Timetable.rooms){
                generatedAllTimeSlots.add(timeSlot+","+room);
            }
        }
    }
     
    static boolean timeSlotIsAvailable(int index, String timeSlot){
        boolean a = checkInCompulsory(timeSlot);
        boolean b = checkTimeSlotIsAssigned(timeSlot);
       boolean c =  subjectList.get(index).isAvailableOnTimeSlot(timeSlot);
        
        if(checkInCompulsory(timeSlot) && checkTimeSlotIsAssigned(timeSlot) && 
                subjectList.get(index).isAvailableOnTimeSlot(timeSlot)){
            return true;
        }else{
            return false;
        }
    }
    
    static boolean checkInCompulsory( String timeSlot){ //return true if a givenTime is not in Compulsory
        String [] timeArray = timeSlot.split(",");
        String onlyTime =  timeArray[0];
        if(compulsoryTimeSlots.contains(onlyTime)){
            return false;
        }else{
            return true;
        }  
    }
    
    static boolean checkTimeSlotIsAssigned(String timeslot){        //return true if a givenTime is not in assigned time slots
        if(assignedTimeSlots.contains(timeslot)){
            return false;
        }else{
            return true;
        }
    }
    
    static void addTimeSlotToCompulsory(String timeSlot){   //add time slot to compulsory time slot array
        String [] timeArray = timeSlot.split(",");
        String onlyTime =  timeArray[0];
        compulsoryTimeSlots.add(onlyTime);
    }
    static void removeFromCompulsory(String timeSlot){
        String [] timeArray = timeSlot.split(",");
        String onlyTime =  timeArray[0];
        compulsoryTimeSlots.remove(onlyTime);
    }
    
    static void writeToFile(String path){
        if(AllAssigned()){
            Path file = Paths.get(path);
            ArrayList<String> lines = new ArrayList<String>();
            for(Subject sbj: subjectList){
                lines.add(sbj.getName()+","+sbj.getAssignedTimeSlot()+","+sbj.getAssignedRoom());
            }
            try {
                Files.write(file,lines, Charset.forName("UTF-8"));
            } catch (IOException ex) {
                Logger.getLogger(Timetable.class.getName()).log(Level.SEVERE, null, ex);
            }

        }else{
            System.out.println("Cannot Assign TimeSlots");
        }
    }
    
    
        
        
        
         
    
}
