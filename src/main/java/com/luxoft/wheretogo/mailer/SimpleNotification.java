package com.luxoft.wheretogo.mailer;

import com.luxoft.wheretogo.models.Group;
import com.luxoft.wheretogo.models.User;
import com.luxoft.wheretogo.utils.PropertiesUtils;

/**
 * Created by bobbi on 10.08.16.
 */
public class SimpleNotification {
    public static final int ADDED_TO_A_GROUP = 0;

    public static void notifyUser(Group group, User user, int type){
        switch (type){
            case ADDED_TO_A_GROUP:
                addedNotification(group,user);
            break;
        }
    }

    private static void addedNotification(Group group, User user) {
        String userEmail = user.getEmail();
        String userName = user.getFirstName()+" "+user.getLastName();
        String newGroupName = group.getName();
        String server = PropertiesUtils.getProp("server.address");
        String groupLink = server+"#group/"+group.getId();
        String subject = "You were added to a group!";
        String picture = "http://2.bp.blogspot.com/-wh1GDiNwZh4/VOw5DRh6yCI/AAAAAAAAZ-M/HAGF0MWQsEQ/s640/Congratulations_zps35c2563e.jpg";
        String email = "<p>Dear "+userName+"</p><p>You were added to a group: "+groupLink+"<h1>"+newGroupName+"</h1></p><img src='"+ picture +"'><p>Best regards,</p><p>WhereToGo administration</p>";
        Mailer.sendMail(userEmail,subject,email);
    }
}
