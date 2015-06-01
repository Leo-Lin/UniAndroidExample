package me.leolin.sample.parcelable.app;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Leolin
 */
public class ToBeTransferred implements Parcelable {

    public static Parcelable.Creator<ToBeTransferred> CREATOR = new Creator<ToBeTransferred>() {
        @Override
        public ToBeTransferred createFromParcel(Parcel source) {

            ToBeTransferred toBeTransferred = new ToBeTransferred();
            source.readMap(toBeTransferred.map, ClassLoader.getSystemClassLoader());
            return toBeTransferred;
        }

        @Override
        public ToBeTransferred[] newArray(int size) {
            return new ToBeTransferred[size];
        }
    };

    private Map<String, Integer> map = new HashMap<String, Integer>();

    public ToBeTransferred() {
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeMap(map);
    }

    public Map<String, Integer> getMap() {
        return map;
    }


}
