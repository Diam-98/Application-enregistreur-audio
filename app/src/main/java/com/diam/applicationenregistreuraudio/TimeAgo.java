package com.diam.applicationenregistreuraudio;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class TimeAgo {

    public String getTimeAgo(long duration){
        Date now = new Date();

        long seconds = TimeUnit.MILLISECONDS.toSeconds(now.getTime() - duration);
        long minutes = TimeUnit.MINUTES.toMinutes(now.getTime() - duration);
        long hours = TimeUnit.HOURS.toHours(now.getTime() - duration);
        long days = TimeUnit.MILLISECONDS.toDays(now.getTime() - duration);

        if (seconds < 60){
            return "Maintenant";
        }else if(minutes == 1){
            return "il y'a une minute";
        }else if (minutes > 1 && minutes < 60){
            return "il y'a " + minutes + " minutes";
        }else if (hours == 1){
            return "il y'a une heure";
        }else if (hours > 1 && hours <24){
            return "il y'a " + hours + " heures";
        }else if (days == 1){
            return "il y'a un jour";
        }else {
            return "il y'a " + days + " jours";
        }

    }

}
