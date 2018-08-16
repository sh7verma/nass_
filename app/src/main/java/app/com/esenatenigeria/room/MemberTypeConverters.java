package app.com.esenatenigeria.room;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import app.com.esenatenigeria.model.CommitteeModel;

/**
 * Created by dev on 30/4/18.
 */


public class MemberTypeConverters {
    @TypeConverter
    public List<CommitteeModel.DataBean.MemberBean> stringToList(String json) {
        if (json.length() > 0) {
            Gson gson = new Gson();
            Type type = new TypeToken<List<CommitteeModel.DataBean.MemberBean>>() {
            }.getType();
            List<CommitteeModel.DataBean.MemberBean> measurements = gson.fromJson(json, type);
            return measurements;
        }
    return null;}

    @TypeConverter
    public String listToString(List<CommitteeModel.DataBean.MemberBean> list) {
        if (list!=null && list.size() > 0) {
            Gson gson = new Gson();
            Type type = new TypeToken<List<CommitteeModel.DataBean.MemberBean>>() {
            }.getType();
            String json = gson.toJson(list, type);
            return json;
        } else {
            return "";
        }
    }
}
