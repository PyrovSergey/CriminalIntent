package com.test.criminalintent;


import java.util.Date;
import java.util.UUID;

// класс модели Crime
public class Crime {
    private UUID mId;
    private String mTitle;
    private Date mDate;
    private boolean mSolved;
    private String mSuspect;

    private boolean mRequiresPolice;

    public Crime() {
        this(UUID.randomUUID());
    }

    public Crime(UUID id) {
        mId = id;
        mDate = new Date();
    }

    public String getPhotoFileName(){
        return "IMG_" + getId().toString() + ".jpg";
    }

    public UUID getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public Date getDate() {
        return mDate;
    }

    public boolean isSolved() {
        return mSolved;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public void setSolved(boolean solved) {
        mSolved = solved;
    }

    public boolean ismRequiresPolice() {
        return mRequiresPolice;
    }

    public void setmRequiresPolice(boolean mRequiresPolice) {
        this.mRequiresPolice = mRequiresPolice;
    }
    public String getSuspect() {
        return mSuspect;
    }

    public void setSuspect(String suspect) {
        mSuspect = suspect;
    }
}
