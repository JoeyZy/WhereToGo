package com.luxoft.wheretogo.mailer;

import com.luxoft.wheretogo.models.Group;
import com.luxoft.wheretogo.models.User;

/**
 * Created by bobbi on 10.08.16.
 */
public class SimpleNotification {
    public static final int ADDED_TO_A_GROUP = 0;

    public static void notifyUser(Group group, User user, int type){
        switch (type){
            case 0:
                addedNotification(group,user);
            break;
        }
    }

    private static void addedNotification(Group group, User user) {
        String userEmail = user.getEmail();
        String userName = user.getFirstName()+" "+user.getLastName();
        String newGroupName = group.getName();
        String subject = "WhereToGo notification!";
        String email = "<p>You were added to a group: "+newGroupName+"</p>";
        Mailer.sendMail(userEmail,subject,email);
    }
}
