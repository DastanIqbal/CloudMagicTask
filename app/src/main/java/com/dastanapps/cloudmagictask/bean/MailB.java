package com.dastanapps.cloudmagictask.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by IQBAL-MEBELKART on 8/16/2016.
 */

public class MailB implements Parcelable {
    public String subject;
    public String participants;
    public String preview;
    public String isRead;
    public String isStarred;
    public String timestamp;
    public String mailId;

    public MailB(Parcel in) {
        subject = in.readString();
        participants = in.readString();
        preview = in.readString();
        isRead = in.readString();
        isStarred = in.readString();
        timestamp = in.readString();
        mailId = in.readString();
    }

    public MailB() {

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(subject);
        parcel.writeString(participants);
        parcel.writeString(preview);
        parcel.writeString(isRead);
        parcel.writeString(isStarred);
        parcel.writeString(timestamp);
        parcel.writeString(mailId);
    }

    public static final Creator<MailB> CREATOR = new Creator<MailB>() {
        @Override
        public MailB createFromParcel(Parcel in) {
            return new MailB(in);
        }

        @Override
        public MailB[] newArray(int size) {
            return new MailB[size];
        }
    };

}
