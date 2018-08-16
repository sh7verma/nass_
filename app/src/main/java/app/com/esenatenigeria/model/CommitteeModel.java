package app.com.esenatenigeria.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;

import java.util.List;

import app.com.esenatenigeria.room.MemberTypeConverters;

/**
 * Created by dev on 25/4/18.
 */

public class CommitteeModel {

    /**
     * statusCode : 200
     * message : Success
     * data : {"count":1,"data":[{"committee_id":"5ad82f9c064312b3659aa8cc","chamber":"9ZjqcZ0HS8yvraRMyThHOg==","members":[],"viceChairman":{},"chairman":{"committee_id":"5ad9c7e5d1791a4f619e2cb7","name":"ytxvav5zyQ8sLinpT7L80A=="},"name":"z8Hf8egWgfJNLI/K4kRnpSLF08ySA/PpmcXmC5iGV/I=","__v":0}]}
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

    public DataBeanX getDataX() {
        return data;
    }

    public void setDataX(DataBeanX data) {
        this.data = data;
    }

    public static class DataBeanX {
        /**
         * count : 1
         * data : [{"committee_id":"5ad82f9c064312b3659aa8cc","chamber":"9ZjqcZ0HS8yvraRMyThHOg==","members":[],"viceChairman":{},"chairman":{"committee_id":"5ad9c7e5d1791a4f619e2cb7","name":"ytxvav5zyQ8sLinpT7L80A=="},"name":"z8Hf8egWgfJNLI/K4kRnpSLF08ySA/PpmcXmC5iGV/I=","__v":0}]
         */

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

    @Entity(tableName = "CommitteeDataBean", indices = {@Index(value = {"committee_id"},
            unique = true)})
    @TypeConverters(MemberTypeConverters.class)
    public static class DataBean {
        /**
         * committee_id : 5ad82f9c064312b3659aa8cc
         * chamber : 9ZjqcZ0HS8yvraRMyThHOg==
         * members : []
         * viceChairman : {}
         * chairman : {"committee_id":"5ad9c7e5d1791a4f619e2cb7","name":"ytxvav5zyQ8sLinpT7L80A=="}
         * name : z8Hf8egWgfJNLI/K4kRnpSLF08ySA/PpmcXmC5iGV/I=
         * __v : 0
         */
        @PrimaryKey
        @ColumnInfo(name = "committee_id")
        @NonNull
        private String committee_id;

        @ColumnInfo(name = "chamber")
        private String chamber;

        @Embedded(prefix = "viceChairman")
        private ViceChairmanBean viceChairman;

        @Embedded(prefix = "chairman")
        private ChairmanBean chairman;

        @ColumnInfo(name = "name")
        private String name;

        @ColumnInfo(name = "v")
        private int __v;

        @ColumnInfo(name = "members")
        private List<MemberBean> members;

        public String getCommittee_id() {
            return committee_id;
        }

        public void setCommittee_id(String committee_id) {
            this.committee_id = committee_id;
        }

        public String getChamber() {
            return chamber;
        }

        public void setChamber(String chamber) {
            this.chamber = chamber;
        }

        public ViceChairmanBean getViceChairman() {
            return viceChairman;
        }

        public void setViceChairman(ViceChairmanBean viceChairman) {
            this.viceChairman = viceChairman;
        }

        public ChairmanBean getChairman() {
            return chairman;
        }

        public void setChairman(ChairmanBean chairman) {
            this.chairman = chairman;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int get__v() {
            return __v;
        }

        public void set__v(int __v) {
            this.__v = __v;
        }

        public List<MemberBean> getMembers() {
            return members;
        }

        public void setMembers(List<MemberBean> members) {
            this.members = members;
        }

        public static class ViceChairmanBean {
            private String user_id;
            private String name;

            public String getUser_id() {
                return user_id;
            }

            public void setUser_id(String user_id) {
                this.user_id = user_id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }

        public static class ChairmanBean {
            /**
             * committee_id : 5ad9c7e5d1791a4f619e2cb7
             * name : ytxvav5zyQ8sLinpT7L80A==
             */

            private String user_id;
            private String name;

            public String getUser_id() {
                return user_id;
            }

            public void setUser_id(String user_id) {
                this.user_id = user_id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }

        public static class MemberBean {
            /**
             * committee_id : 5ad9c7e5d1791a4f619e2cb7
             * name : ytxvav5zyQ8sLinpT7L80A==
             */

            private String user_id;

            private String name;

            public String getUser_id() {
                return user_id;
            }

            public void setUser_id(String user_id) {
                this.user_id = user_id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }

    }

}