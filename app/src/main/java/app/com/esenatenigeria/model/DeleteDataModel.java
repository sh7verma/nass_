package app.com.esenatenigeria.model;

import java.util.List;

/**
 * Created by dev on 27/6/18.
 */

public class DeleteDataModel {


    /**
     * statusCode : 200
     * message : Success
     * data : [{"id":1,"doc_id":49,"category":"KAgsHOybNvjWN7xtJJAuIQ==","isLawOfFed":0}]
     */

    private int statusCode;
    private String message;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 1
         * doc_id : 49
         * category : KAgsHOybNvjWN7xtJJAuIQ==
         * isLawOfFed : 0
         */

        private int id;
        private int doc_id;
        private String category;
        private int isLawOfFed;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getDoc_id() {
            return doc_id;
        }

        public void setDoc_id(int doc_id) {
            this.doc_id = doc_id;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public int getIsLawOfFed() {
            return isLawOfFed;
        }

        public void setIsLawOfFed(int isLawOfFed) {
            this.isLawOfFed = isLawOfFed;
        }
    }
}
