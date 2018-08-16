package app.com.esenatenigeria.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by dev on 17/5/18.
 */

public class RecentModel implements Parcelable {

    String title;
    String description;
    String image;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.description);
        dest.writeString(this.image);
    }

    public RecentModel() {
    }

    protected RecentModel(Parcel in) {
        this.title = in.readString();
        this.description = in.readString();
        this.image = in.readString();
    }

    public static final Parcelable.Creator<RecentModel> CREATOR = new Parcelable.Creator<RecentModel>() {
        @Override
        public RecentModel createFromParcel(Parcel source) {
            return new RecentModel(source);
        }

        @Override
        public RecentModel[] newArray(int size) {
            return new RecentModel[size];
        }
    };
}
