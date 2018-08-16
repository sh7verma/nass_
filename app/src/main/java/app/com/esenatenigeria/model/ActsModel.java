package app.com.esenatenigeria.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.List;

/**
 * Created by dev on 26/4/18.
 */

public class ActsModel {

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

    public DataBeanX getDataX() {
        return data;
    }

    public void setDataX(DataBeanX data) {
        this.data = data;
    }

    public static class DataBeanX {

        private int count;
        private List<DataBean> data;

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public List<DataBean> getData() {
            return data;
        }

        public void setData(List<DataBean> data) {
            this.data = data;
        }

    }

    @Entity(tableName = "ActsDataBean", indices = {@Index(value = {"doc_id"},
            unique = true)})
    public static class DataBean {
        /**
         * doc_id : 5addad53f58204746f80c03b
         * category : SbA06tQMWcg8GR7Yh+RiTA==
         * chamber : 9ZjqcZ0HS8yvraRMyThHOg==
         * date : 2017-11-17T00:00:00.000Z
         * session : null
         * parliament : null
         * summary:"
         * docURL : FsGrVtqTOKTEYoXievB4tXOxnCLdz4FUZgiai04uKMw+85uiktr7MBH+qVqRleNW
         * name : FIaYb7vVjo7AYDjQ7oQOExH67DmTeWe5VLRj6gG1vQjYTBOiLdrwAlwwjNHudKM+xe9eEYnuJkWKlCAJru5T2Q==
         */

        @PrimaryKey
        @ColumnInfo(name = "doc_id")
        @NonNull
        private String doc_id;

        @ColumnInfo(name = "act_category")
        private String category;

        @ColumnInfo(name = "act_chamber")
        private String chamber;

        @ColumnInfo(name = "act_date")
        private String date;

        @ColumnInfo(name = "act_summary")
        private String summary;

        @ColumnInfo(name = "act_source")
        private String source;

        @ColumnInfo(name = "act_session")
        private String session;

        @ColumnInfo(name = "act_parliament")
        private String parliament;

        @ColumnInfo(name = "act_docURL")
        private String docURL;

        @ColumnInfo(name = "act_docLocalPath")
        private String docLocalPath;

        @ColumnInfo(name = "act_name")
        private String name;

        @ColumnInfo(name = "act_codeUpdatedAt")
        private String codeUpdatedAt;


        public String getCodeUpdatedAt() {
            return codeUpdatedAt;
        }

        public void setCodeUpdatedAt(String codeUpdatedAt) {
            this.codeUpdatedAt = codeUpdatedAt;
        }


        public String getSummary() {
            return summary;
        }

        public void setSummary(String summary) {
            this.summary =  summary;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source =  source;
        }

        public String getDocLocalPath() {
            return docLocalPath;
        }

        public void setDocLocalPath(String docLocalPath) {
            this.docLocalPath = docLocalPath;
        }

        public String getDoc_id() {
            return doc_id;
        }

        public void setDoc_id(String doc_id) {
            this.doc_id = doc_id;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category =  category;
        }

        public String getChamber() {
            return chamber;
        }

        public void setChamber(String chamber) {
            this.chamber =  chamber;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getSession() {
            return session;
        }

        public void setSession(String session) {
            this.session =  session;
        }

        public String getParliament() {
            return parliament;
        }

        public void setParliament(String parliament) {
            this.parliament =  parliament;
        }

        public String getDocURL() {
            return docURL;
        }

        public void setDocURL(String docURL) {
            this.docURL =  docURL;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name =  name;
        }
//
//        public int get__v() {
//            return __v;
//        }
//
//        public void set__v(int __v) {
//            this.__v = __v;
//        }
    }



}
