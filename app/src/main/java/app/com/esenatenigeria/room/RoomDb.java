package app.com.esenatenigeria.room;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import app.com.esenatenigeria.model.ActsModel;
import app.com.esenatenigeria.model.BillsModel;
import app.com.esenatenigeria.model.CommitteeModel;
import app.com.esenatenigeria.model.InformationModel;
import app.com.esenatenigeria.model.SenatorDetailModel;

/**
 * Created by Shubham verma on 25-04-2018.
 */
@Database(entities = {ActsModel.DataBean.class,
        BillsModel.DataBean.class,
        CommitteeModel.DataBean.class,
        SenatorDetailModel.DataBean.class,
        InformationModel.DataBean.class}, version = 2)
@TypeConverters({MemberTypeConverters.class})
public abstract class RoomDb extends RoomDatabase {
    public abstract DaoAccess daoAccess();
//
//    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
//        @Override
//        public void migrate(SupportSQLiteDatabase database) {
//            database.execSQL("ALTER TABLE Book "
//                    + " ADD COLUMN pub_year INTEGER");
//        }
//    };
}

