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

public class EducationTypeConverter {

    @TypeConverter
    public List<SenatorDetailModel.DataBean.EducationBean> stringToList(String json) {
        if (json.length() > 0) {
            Gson gson = new Gson();
            Type type = new TypeToken<List<SenatorDetailModel.DataBean.EducationBean>>() {
            }.getType();
            List<SenatorDetailModel.DataBean.EducationBean> measurements = gson.fromJson(json, type);
            return measurements;
        }
        return null;
    }

    @TypeConverter
    public String listToString(List<SenatorDetailModel.DataBean.EducationBean> list) {
        if (list != null) {
            Gson gson = new Gson();
            Type type = new TypeToken<List<SenatorDetailModel.DataBean.EducationBean>>() {
            }.getType();
            String json = gson.toJson(list, type);
            return json;
        } else {
            return "";
        }
    }
}
