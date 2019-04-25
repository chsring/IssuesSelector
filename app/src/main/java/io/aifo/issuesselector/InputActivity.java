package io.aifo.issuesselector;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.LinkedList;
import java.util.List;

/**
 * @author srwing
 * @fileName InputActivity
 * @time 2019/4/22 10:26
 * @Email surao@foryou56.com (有问题请邮件)
 * @desc
 */
public class InputActivity extends AppCompatActivity {

    RecyclerView listView;
    SendMessageLayout sendMessageLayout;
    IssuesAdapter issuesAdapter;
    public static List<Issues> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        listView = findViewById(R.id.listview);
        sendMessageLayout = findViewById(R.id.sml_send_message);
        findViewById(R.id.finish).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(InputActivity.this, AnswerActivity.class));
            }
        });
        list = new LinkedList<>();
        listView.setLayoutManager(new LinearLayoutManager(this));
        issuesAdapter = new IssuesAdapter(list);
        issuesAdapter.setOnItemLongClickListener(new BaseQuickAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
                issuesAdapter.remove(position);
                return false;
            }
        });
        listView.setAdapter(issuesAdapter);
        sendMessageLayout.setAndroidClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(sendMessageLayout.getSendMessageText())) {

                    issuesAdapter.addData(new Issues(1, issuesAdapter.getItemCount() + 1 + ". Android:" + sendMessageLayout.getSendMessageText()));
                    sendMessageLayout.clearMessageText();
                    listView.scrollToPosition(issuesAdapter.getItemCount() - 1);
                }
            }
        });

        sendMessageLayout.setIosClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(sendMessageLayout.getSendMessageText())) {
                    issuesAdapter.addData(new Issues(2, issuesAdapter.getItemCount() + 1 + ". iOS:" + sendMessageLayout.getSendMessageText()));
                    sendMessageLayout.clearMessageText();
                    listView.scrollToPosition(issuesAdapter.getItemCount() - 1);
                }
            }
        });
        EventBus.getDefault().register(this);

        String[] androidQuestions = getResources().getStringArray(R.array.android_question);
        for (String question : androidQuestions) {
            issuesAdapter.addData(new Issues(1, issuesAdapter.getItemCount() + 1 + ". Android:" + question));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(EventMsg event) {
        issuesAdapter.notifyDataSetChanged();
    }

    ;
}
