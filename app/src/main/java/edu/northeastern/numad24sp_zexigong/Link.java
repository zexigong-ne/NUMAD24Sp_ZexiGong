package edu.northeastern.numad24sp_zexigong;
import android.os.Parcel;
import android.os.Parcelable;

public class Link implements Parcelable {
    private String name;
    private String url;

    public Link(String name, String url) {
        this.name = name;
        this.url = url;
    }

    protected Link(Parcel in) {
        name = in.readString();
        url = in.readString();
    }

    public static final Creator<Link> CREATOR = new Creator<Link>() {
        @Override
        public Link createFromParcel(Parcel in) {
            return new Link(in);
        }

        @Override
        public Link[] newArray(int size) {
            return new Link[size];
        }
    };

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(url);
    }
}
