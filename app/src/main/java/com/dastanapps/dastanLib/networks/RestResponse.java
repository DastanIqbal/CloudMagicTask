package com.dastanapps.dastanLib.networks;

import com.dastanapps.cloudmagictask.bean.MailB;
import com.dastanapps.cloudmagictask.bean.MailDetailsB;
import com.dastanapps.cloudmagictask.bean.MailParticipantB;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by IQBAL-MEBELKART on 8/16/2016.
 */

public class RestResponse {

    public static ArrayList<MailB> getAllMails(String json) {
        ArrayList<MailB> mailBArrayList = new ArrayList<>();
        try {
            JSONArray mailsArray = new JSONArray(json);
            if (mailsArray.length() != 0) {
                for (int i = 0; i < mailsArray.length(); i++) {
                    JSONObject mailObj = mailsArray.getJSONObject(i);
                    String subject = mailObj.getString("subject");
                    String participants = mailObj.getString("participants");
                    String preview = mailObj.getString("preview");
                    String isRead = mailObj.getString("isRead");
                    String isStarred = mailObj.getString("isStarred");
                    String timestamp = mailObj.getString("ts");
                    String mailId = mailObj.getString("id");

                    MailB mailB = new MailB();
                    mailB.subject = subject;
                    mailB.participants = participants;
                    mailB.preview = preview;
                    mailB.isRead = isRead;
                    mailB.isStarred = isStarred;
                    mailB.timestamp = timestamp;
                    mailB.mailId = mailId;
                    mailBArrayList.add(mailB);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return mailBArrayList;
    }

    public static MailDetailsB getMail(String json) {
        JSONObject mailObj = null;
        try {
            mailObj = new JSONObject(json);
            String subject = mailObj.getString("subject");
            String participants = mailObj.getString("participants");
            String preview = mailObj.getString("preview");
            String isRead = mailObj.getString("isRead");
            String isStarred = mailObj.getString("isStarred");
            String timestamp = mailObj.getString("ts");
            String mailId = mailObj.getString("id");
            String body = mailObj.getString("body");

            MailDetailsB mailB = new MailDetailsB();
            mailB.subject = subject;
            mailB.participants = participants;
            mailB.preview = preview;
            mailB.isRead = isRead;
            mailB.isStarred = isStarred;
            mailB.timestamp = timestamp;
            mailB.mailId = mailId;
            mailB.body = body;
            return mailB;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ArrayList<String> getAllParticipants(String json) {
        ArrayList<String> arrayList = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                arrayList.add(jsonArray.getString(i));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return arrayList;
    }

    public static ArrayList<MailParticipantB> getAllParticipantsB(String json) {
        ArrayList<MailParticipantB> arrayList = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject mailObj = jsonArray.getJSONObject(i);
                String name = mailObj.getString("name");
                String email = mailObj.getString("email");

                MailParticipantB mailParticipantB = new MailParticipantB();
                mailParticipantB.name = name;
                mailParticipantB.email = email;

                arrayList.add(mailParticipantB);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return arrayList;
    }

    public static String getDeleteMsg(String resp) {
        try {
            JSONObject jsonObject = new JSONObject(resp);
            if (jsonObject.getString("status").equals("200")) {
                return "Deleted Successfully";
            } else {
                return "Not Deleted Successfully";
            }
        } catch (JSONException e) {
            return "Something went wrong";
        }

    }
}
