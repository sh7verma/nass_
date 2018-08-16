package app.com.esenatenigeria.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import app.com.esenatenigeria.R;
import app.com.esenatenigeria.interfaces.Interfaces;
import app.com.esenatenigeria.utils.Consts;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by dev on 14/5/18.
 */

public class ContactUsDialog {

    static private Dialog mDialog;
    private static Interfaces.DialogClick mClick;
    @BindView(R.id.ed_name)
    EditText edName;
    @BindView(R.id.ed_text)
    EditText edText;
    @BindView(R.id.ed_email)
    EditText edEmail;
    private Context mContext;


    public ContactUsDialog(Context context) {
        mContext = context;
    }

    public static void DialogClickInterface(Interfaces.DialogClick open) {
        mClick = open;
    }

    public void dismiss() {
        if (mDialog != null) {
            mDialog.dismiss();
            mDialog = null;
        }
    }

    public void showDialog() {
        View view;
        if (mDialog == null) {
            mDialog = new Dialog(mContext);
            mDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            view = View.inflate(mContext, R.layout.dialog_contact_us, null);
            mDialog.setContentView(view);
            mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            mDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            mDialog.getWindow().getAttributes().windowAnimations = R.style.DialogPopAnimation;
            mDialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            mDialog.show();

            ButterKnife.bind(this, view);
            initUI();
            initListeners();

            mDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialogInterface) {
                    mDialog = null;
                }
            });
        }
    }

    @OnClick(R.id.txt_cancel)
    void cancel() {
        hideKeyBoard();
        if (mDialog != null) {
            mDialog.dismiss();
            mDialog = null;
        }
    }

    @OnClick(R.id.txt_send)
    void send() {
        if (TextUtils.isEmpty(edName.getText().toString().trim())) {
            Toast.makeText(mContext, mContext.getResources().getString(R.string.enter_full_name), Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(edEmail.getText().toString().trim())) {
            Toast.makeText(mContext, mContext.getResources().getString(R.string.enter_email), Toast.LENGTH_SHORT).show();
        } else if (!Consts.isValidEmail(edEmail.getText())) {
            Toast.makeText(mContext, mContext.getResources().getString(R.string.enter_valid_email), Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(edText.getText().toString().trim())) {
            Toast.makeText(mContext, mContext.getResources().getString(R.string.enter_concern), Toast.LENGTH_SHORT).show();
        } else {
            mClick.onDialogClick(edName.getText().toString().trim(),
                    edEmail.getText().toString().trim(),
                    edText.getText().toString().trim());
            dismiss();
        }
    }

    private void initUI() {

    }

    private void initListeners() {

    }

    void hideKeyBoard() {
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(mContext.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(edName.getWindowToken(), 0);
    }
}
