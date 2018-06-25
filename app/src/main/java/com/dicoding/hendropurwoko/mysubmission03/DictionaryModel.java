package com.dicoding.hendropurwoko.mysubmission03;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class DictionaryModel extends ArrayList<DictionaryModel> implements Parcelable {
    private int id;
    private String word;
    private String description;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.word);
        dest.writeString(this.description);
    }

    public DictionaryModel() {
    }

    public DictionaryModel(String word, String description) {
        this.word = word;
        this.description = description;
    }

    public DictionaryModel(int id, String word, String description, String is_english) {
        this.id = id;
        this.word = word;
        this.description = description;
    }

    protected DictionaryModel(Parcel in) {
        this.id = in.readInt();
        this.word = in.readString();
        this.description = in.readString();
    }

    public static final Creator<DictionaryModel> CREATOR = new Creator<DictionaryModel>() {
        @Override
        public DictionaryModel createFromParcel(Parcel source) {
            return new DictionaryModel(source);
        }

        @Override
        public DictionaryModel[] newArray(int size) {
            return new DictionaryModel[size];
        }
    };
}