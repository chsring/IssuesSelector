package io.aifo.issuesselector;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;


/**
 * Created by lt.dong
 * 发送消息 layout
 */

public class SendMessageLayout extends RelativeLayout {

    private EditText editText;
    private Button btnIos, btnAndroid;

    private View rootView;

    public SendMessageLayout(Context context) {
        super(context);
        initView();
    }

    public SendMessageLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public SendMessageLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }


    private void initView() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        rootView = inflater.inflate(R.layout.view_message_board_send_message, this, true);

        btnIos = this.findViewById(R.id.btn_iOS);
        btnAndroid = this.findViewById(R.id.btn_Android);
        editText = this.findViewById(R.id.et_send_message_content);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (!StringUtils.isEmpty(s.toString())) {
                    btnIos.setVisibility(VISIBLE);
                    btnAndroid.setVisibility(VISIBLE);

                } else {
                    btnIos.setVisibility(GONE);
                    btnAndroid.setVisibility(GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    public String getSendMessageText() {
        return editText.getText().toString();
    }

    public void clearMessageText() {
        editText.setText("");
    }


    public void setAndroidClickListener(OnClickListener l) {
        btnAndroid.setOnClickListener(l);
    }

    public void setIosClickListener(OnClickListener l) {
        btnIos.setOnClickListener(l);
    }
}
