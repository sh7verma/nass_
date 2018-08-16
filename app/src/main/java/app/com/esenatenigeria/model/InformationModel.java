package app.com.esenatenigeria.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.List;

/**
 * Created by dev on 16/5/18.
 */

public class InformationModel {


    /**
     * statusCode : 200
     * message : Success
     * data : {"data":[{"id":2,"title":"CONSTITUTIONAL ROLES","detail":"The Constitution has vested in the National Assembly the power to make laws for the peace, order and good governance of the Federation. The Assembly also has broad oversight functions and is therefore empowered to establish committees of its members to scrutinize bills and the conduct of government institutions and officials.The Constitution confers exclusive powers to the Senate among them the power to scrutinize and confirm major appointments of the executive. It is, however, specific about the appointments to be confirmed. They are those of the Ministers, Special Advisers, Ambassadors, top Judicial Officers heading specified levels of courts, the Auditor-General of the Federation, and the Chairmen and Members of the vital National Commissions."}]}
     */

    private int statusCode;
    private String message;
    private DataBeanX data;

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataBeanX getData() {
        return data;
    }

    public void setData(DataBeanX data) {
        this.data = data;
    }

    public static class DataBeanX {
        private List<DataBean> data;

        public List<DataBean> getData() {
            return data;
        }

        public void setData(List<DataBean> data) {
            this.data = data;
        }

    }

    @Entity(tableName = "InfoDataBean", indices = {@Index(value = {"id"},
            unique = true)})
    public static class DataBean {
        /**
         * id : 2
         * title : CONSTITUTIONAL ROLES
         * detail : The Constitution has vested in the National Assembly the power to make laws for the peace, order and good governance of the Federation. The Assembly also has broad oversight functions and is therefore empowered to establish committees of its members to scrutinize bills and the conduct of government institutions and officials.The Constitution confers exclusive powers to the Senate among them the power to scrutinize and confirm major appointments of the executive. It is, however, specific about the appointments to be confirmed. They are those of the Ministers, Special Advisers, Ambassadors, top Judicial Officers heading specified levels of courts, the Auditor-General of the Federation, and the Chairmen and Members of the vital National Commissions.
         */

        @PrimaryKey
        @ColumnInfo(name = "id")
        @NonNull
        private int id;
        private String title;
        private String detail;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDetail() {
            return detail;
        }

        public void setDetail(String detail) {
            this.detail = detail;
        }
    }
}
