package app.com.esenatenigeria.activities;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnErrorListener;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;

import java.io.File;

import app.com.esenatenigeria.R;
import app.com.esenatenigeria.async.PdfDownloadTask;
import app.com.esenatenigeria.interfaces.Interfaces;
import app.com.esenatenigeria.interfaces.PdfDownloadInterface;
import app.com.esenatenigeria.model.ActsModel;
import app.com.esenatenigeria.model.BillsModel;
import app.com.esenatenigeria.model.DeleteDataModel;
import app.com.esenatenigeria.network.RetrofitClient;
import app.com.esenatenigeria.utils.Consts;
import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewPdfActivity extends BaseActivity implements PdfDownloadInterface {

    private static Interfaces.DocumentDeleted docDeleted;
    @BindView(R.id.pdfView)
    PDFView pdfDocument;
    @BindView(R.id.ll_progress)
    LinearLayout progressBar;
    ActsModel.DataBean mActModel;
    BillsModel.DataBean mBillModel;
    PdfDownloadTask task;

    public static void DeletedInterface(Interfaces.DocumentDeleted delete) {
        docDeleted = delete;
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_view_pdf;
    }

    @Override
    protected void onCreateStuff() {

    }

    @Override
    protected void initUI() {
        PdfDownloadTask.initDownloadInterface(this);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected Context getContext() {
        return ViewPdfActivity.this;
    }

    @Override
    public void onClick(View view) {

    }

    @OnClick(R.id.img_back)
    void backPress() {
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        backPress();
    }

    @Override
    public void onDownloaded(final File file, boolean b) {
        try {
            pdfDocument.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
            if (b) {
                pdfDocument.fromFile(file).onLoad(new OnLoadCompleteListener() {
                    @Override
                    public void loadComplete(int nbPages) {
                        if (getIntent().hasExtra(Consts.ACT_ID)) {
                            mRoomDb.daoAccess().updateActsPathRecord(file.getPath(), getIntent().getStringExtra(Consts.ACT_ID));
                        } else if (getIntent().hasExtra(Consts.BILL_ID)) {
                            mRoomDb.daoAccess().updateBillsPathRecord(file.getPath(), getIntent().getStringExtra(Consts.BILL_ID));
                        }
                    }
                }).onError(new OnErrorListener() {
                    @Override
                    public void onError(Throwable t) {
                        toast(mContext.getResources().getString(R.string.file_format));
                        finish();
                    }
                }).load();
            } else {

                if (getIntent().hasExtra(Consts.BILL_ID)) {
                    Toast.makeText(ViewPdfActivity.this, R.string.deleted_file, Toast.LENGTH_SHORT).show();
                    if (mRoomDb.daoAccess().containBillsSingleRecord(getIntent().getStringExtra(Consts.BILL_ID))) {
                        mRoomDb.daoAccess().deleteBillsRecord(getIntent().getStringExtra(Consts.BILL_ID));
                        docDeleted.onDocumentDeleted(Consts.BILLS);
                    }
                } else if (getIntent().hasExtra(Consts.ACT_ID)) {
                    if (mRoomDb.daoAccess().containActsSingleRecord(getIntent().getStringExtra(Consts.ACT_ID))) {
                        mRoomDb.daoAccess().deleteActsRecord(getIntent().getStringExtra(Consts.ACT_ID));
                        Toast.makeText(ViewPdfActivity.this, R.string.deleted_file, Toast.LENGTH_SHORT).show();
                        docDeleted.onDocumentDeleted(Consts.ACTS);
                    }
                }
                finish();
                hitDeleteApi();

            }
        } catch (Exception e) {
            toast(mContext.getResources().getString(R.string.file_format));
            finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        pdfDocument.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);

        if (getIntent().hasExtra(Consts.ACT_ID)) {
            mActModel = new ActsModel.DataBean();
            mActModel = mRoomDb.daoAccess().getActsSingleRecord(getIntent().getStringExtra(Consts.ACT_ID));
        } else if (getIntent().hasExtra(Consts.BILL_ID)) {
            mBillModel = new BillsModel.DataBean();
            mBillModel = mRoomDb.daoAccess().getBillsSingleRecord(getIntent().getStringExtra(Consts.BILL_ID));
        }

        if (mActModel != null && !TextUtils.isEmpty(mActModel.getDocLocalPath())) {
            pdfDocument.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
            pdfDocument.fromFile(new File(mActModel.getDocLocalPath())).onError(new OnErrorListener() {
                @Override
                public void onError(Throwable t) {
                    pdfDocument.setVisibility(View.GONE);
                    progressBar.setVisibility(View.VISIBLE);
                    task = new PdfDownloadTask(getContext(), getIntent().getStringExtra(Consts.FILE_NAME));
                }
            }).load();
        } else if (mBillModel != null && !TextUtils.isEmpty(mBillModel.getDocLocalPath())) {
            pdfDocument.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
            pdfDocument.fromFile(new File(mBillModel.getDocLocalPath())).onError(new OnErrorListener() {
                @Override
                public void onError(Throwable t) {
                    pdfDocument.setVisibility(View.GONE);
                    progressBar.setVisibility(View.VISIBLE);
                    task = new PdfDownloadTask(getContext(), getIntent().getStringExtra(Consts.FILE_NAME));
                }
            }).load();
        } else {
            if (connectedToInternet()) {
                task = new PdfDownloadTask(getContext(), getIntent().getStringExtra(Consts.FILE_NAME));
            } else {
                toast(mContext.getResources().getString(R.string.internet));
                finish();
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (task != null && task.downloadingTask != null)
            task.downloadingTask.cancel(true);
    }

    void hitDeleteApi() {
        if (connectedToInternet()) {
            Call<DeleteDataModel> call = RetrofitClient.getInstance().getDeletedDocuments();
            call.enqueue(new Callback<DeleteDataModel>() {
                @Override
                public void onResponse(Call<DeleteDataModel> call, Response<DeleteDataModel> response) {
                    try {
                        if (response.body().getData() != null) {
                            for (int i = 0; i < response.body().getData().size(); i++) {
                                if (response.body().getData().get(i).getCategory() != null ||
                                        !TextUtils.isEmpty(response.body().getData().get(i).getCategory())) {
                                    String category = encode.decrypt(response.body().getData().get(i).getCategory());

                                    if (category.equals(Consts.BILLS)) {
                                        if (mRoomDb.daoAccess().containBillsSingleRecord(String.valueOf(response.body().getData().get(i).getDoc_id()))) {
                                            mRoomDb.daoAccess().deleteBillsRecord(String.valueOf(response.body().getData().get(i).getDoc_id()));
                                            if (getIntent().hasExtra(Consts.BILL_ID)) {
                                                if (getIntent().getStringExtra(Consts.BILL_ID).equals(String.valueOf(response.body().getData().get(i).getDoc_id()))) {
                                                    Toast.makeText(ViewPdfActivity.this, R.string.deleted_file, Toast.LENGTH_SHORT).show();
                                                    docDeleted.onDocumentDeleted(Consts.BILLS);
                                                }
                                            }
                                        }

                                    } else if (category.equals(Consts.ACTS)) {
                                        if (mRoomDb.daoAccess().containActsSingleRecord(String.valueOf(response.body().getData().get(i).getDoc_id()))) {
                                            mRoomDb.daoAccess().deleteActsRecord(String.valueOf(response.body().getData().get(i).getDoc_id()));
                                            if (getIntent().hasExtra(Consts.ACT_ID)) {
                                                if (getIntent().getStringExtra(Consts.ACT_ID).equals(String.valueOf(response.body().getData().get(i).getDoc_id()))) {
                                                    Toast.makeText(ViewPdfActivity.this, R.string.deleted_file, Toast.LENGTH_SHORT).show();
                                                    docDeleted.onDocumentDeleted(Consts.ACTS);
                                                }
                                            }
                                        }
                                    }
                                }
                            }

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        toast(mContext.getResources().getString(R.string.file_format));
                    }
                }

                @Override
                public void onFailure(Call<DeleteDataModel> call, Throwable t) {
                    toast(mContext.getResources().getString(R.string.file_format));
                    finish();
                }
            });
        }
    }

}
