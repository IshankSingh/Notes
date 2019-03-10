package provider.movie.com.ishank.engineer.notes.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Utility {

  //  private static final String TAG = "Utility";
    public static String getCurrentTimeStamp(){
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM-yyyy", Locale.US); //MUST USE LOWERCASE 'y'. API 23- can't use uppercase
            return dateFormat.format(new Date()); // Find today date
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public static String getMonthFromNumber(String monthNumber){

        switch(monthNumber){
            case "01":{
                return "Jan";
            }
            case "02":{
                return "Feb";
            }
            case "03":{
                return "Mar";
            }
            case "04":{
                return "Apr";
            }
            case "05":{
                return "May";
            }
            case "06":{
                return "Jun";
            }
            case "07":{
                return "Jul";
            }
            case "08":{
                return "Aug";
            }
            case "09":{
                return "Sep";
            }
            case "10":{
                return "Oct";
            }
            case "11":{
                return "Nov";
            }
            case "12":{
                return "Dec";
            }
            default:{
                return "Error";
            }
        }
    }
}