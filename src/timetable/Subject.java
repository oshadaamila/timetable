/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package timetable;

import java.util.ArrayList;
import java.util.Arrays;

public class Subject {
    public boolean compulsory;
    private String name;
    ArrayList <String> possibleTimeSlots = new ArrayList<>();
    ArrayList<String> generatedTimeSlots = new ArrayList<>();
    private String assignedTimeSlot;
    private String assignedRoom;
    public boolean assigned =false;

    public Subject(boolean compulsory, String name,String[] timeSlots) {
        this.compulsory = compulsory;
        this.name = name;
        for(String x:timeSlots){
            //TimeSlot ts =  new TimeSlot(x);
            possibleTimeSlots.add(x);
        }
        generateAllTimeSlots();
    
}
    public void assignTimeSlot(String timeSlot){
       this.assignedTimeSlot=timeSlot.split(",")[0];
       this.assignedRoom = timeSlot.split(",")[1];
       
       assigned = true;
    }


    public String getName() {
        return name;
    }
    
    public String getPossibleTimeSlot(int index){
        return possibleTimeSlots.get(index);
    }
    
    public void generateAllTimeSlots(){
        for(String timeSlot : possibleTimeSlots){
            for (String room : Timetable.rooms){
                generatedTimeSlots.add(timeSlot+","+room);
            }
        }
    }
    
    public boolean isAvailableOnTimeSlot(String TimeSlot){
        if(generatedTimeSlots.contains(TimeSlot)){
            return true;
        }else{
            return false;
        }
    }

    public String getAssignedTimeSlot() {
        return assignedTimeSlot;
    }

    public String getAssignedRoom() {
        return assignedRoom;
    }
    
}
