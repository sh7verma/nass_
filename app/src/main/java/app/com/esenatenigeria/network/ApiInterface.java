package app.com.esenatenigeria.network;

import app.com.esenatenigeria.model.ActsModel;
import app.com.esenatenigeria.model.BillsModel;
import app.com.esenatenigeria.model.CommitteeModel;
import app.com.esenatenigeria.model.DeleteDataModel;
import app.com.esenatenigeria.model.InformationModel;
import app.com.esenatenigeria.model.SenatorDetailModel;
import app.com.esenatenigeria.youtube.YoutubeModel;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface ApiInterface {

    //API Method to get Questions From API
    @GET("api/user/getUserCommittees")
    Call<CommitteeModel> getUserCommittees();

    @FormUrlEncoded
    @PUT("/api/user/getUserDocuments")
    Call<ActsModel> getActsUserDocuments(@Field("category") String category);

    @FormUrlEncoded
    @PUT("/api/user/getUserDocuments")
    Call<BillsModel> getBillsUserDocuments(@Field("category") String category);

    @GET("/api/user/getUserDirectory")
    Call<SenatorDetailModel> getUserDirectory();

    @GET("/api/user/getSenator")
    Call<SenatorDetailModel> getSenator(@Query("userId") int user_id);

    @FormUrlEncoded
    @PUT("/api/user/sendEmail")
    Call<SenatorDetailModel> sendEmail(@Field("senator") String senator,
                                       @Field("message") String message,
                                       @Field("name") String name,
                                       @Field("email") String email);

    @GET
    Call<YoutubeModel> getYoutubeVideos(@Url String url);

    @GET("/api/user/getInformation")
    Call<InformationModel> getInformation();


    @GET("/api/user/getDeletedDocuments")
    Call<DeleteDataModel> getDeletedDocuments();

}