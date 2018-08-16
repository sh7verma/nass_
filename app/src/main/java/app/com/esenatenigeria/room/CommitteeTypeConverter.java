package app.com.esenatenigeria.room;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import app.com.esenatenigeria.model.SenatorDetailModel;

/**
 * Created by dev on 10/5/18.
 */

public class CommitteeTypeConverter {

    @TypeConverter
    public List<SenatorDetailModel.DataBean.Committee> stringToList(String json) {

        Gson gson = new Gson();
        Type type = new TypeToken<List<SenatorDetailModel.DataBean.Committee>>() {
        }.getType();
        List<SenatorDetailModel.DataBean.Committee> measurements = gson.fromJson(json, type);
        return measurements;
    }

    @TypeConverter
    public String listToString(List<SenatorDetailModel.DataBean.Committee> list) {
        if (list != null) {
            Gson gson = new Gson();
            Type type = new TypeToken<List<SenatorDetailModel.DataBean.Committee>>() {
            }.getType();
            String json = gson.toJson(list, type);
            return json;
        } else {
            return "";
        }
    }
}
