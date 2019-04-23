package io.aifo.issuesselector;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;

import java.util.LinkedList;
import java.util.List;

/**
 * Description:
 * Created by moxiaohao
 * Date: 2019/4/22
 * Email: shenlei@foryou56.com
 */
public class AnswerActivity extends AppCompatActivity {

    private TextView tvReturn, tvAndroid, tvIos, tvStudent, tvIssues;
    private Button button;

    private List<String> android = new LinkedList<String>() {{
        add("侯其新");
        add("沈雷");
        add("董良廷");
        add("李亚威");
        add("刘海鹏");
        add("杨明轩");
        add("苏饶");
    }};

    private List<String> ios = new LinkedList<String>() {{
        add("侯其新");
        add("于龙");
        add("陈波涛");
        add("岑志军");
        add("李娜");
        add("吴栋");
        add("郭克");
    }};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);
        tvReturn = findViewById(R.id.finish);
        tvAndroid = findViewById(R.id.tv_android);
        tvIos = findViewById(R.id.tv_ios);
        tvStudent = findViewById(R.id.tv_student);
        tvIssues = findViewById(R.id.tv_issues);
        button = findViewById(R.id.btn_get);
        refreshView(1);
        refreshView(2);
        tvReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (InputActivity.list.size() > 0) {
                    getIssues();
                    EventBus.getDefault().post(new EventMsg());
                } else
                    Toast.makeText(AnswerActivity.this, "题目已经回答完毕", Toast.LENGTH_SHORT).show();
            }
        });

    }

    /**
     * @param type 1 安卓，2 iOS
     */
    private void refreshView(int type) {
        StringBuilder text = new StringBuilder();
        text.append(getTitle(type));
        for (int i = 0; i < getSize(type); i++) {
            text.append(getName(type, i));
        }
        getTextView(type).setText(text);
    }

    private List<String> getStudentArray(int type) {
        return type == 1 ? android : ios;
    }

    private int getSize(int type) {
        return type == 1 ? android.size() : ios.size();
    }

    private TextView getTextView(int type) {
        return type == 1 ? tvAndroid : tvIos;
    }

    private String getName(int type, int i) {
        return type == 1 ? android.get(i) + "  " : ios.get(i) + "  ";
    }

    private String getTitle(int type) {
        return type == 1 ? "Android: " : "iOS: ";
    }

    private int getRandomPos(int count) {
        return (int) (Math.random() * count);
    }

    private void getIssues() {
        int count = InputActivity.list.size();
        int i = getRandomPos(count);
        Log.i("issues-size", "count: " + count + ",i: " + i);
        //随机抽题
        Issues issue = InputActivity.list.get(i);
        tvIssues.setText(issue.text);
        generateStudent(issue.type, issue);
    }

    private void generateStudent(int type, Issues issues) {
        //随机生成答题者
        tvStudent.setText("答题者: " + getStudent(type, issues));
    }

    private String getStudent(int type, Issues issues) {
        int count = getSize(type);
        int i = getRandomPos(count);
        if (getStudentArray(type).size() == 0) {
            Toast.makeText(AnswerActivity.this, getTitle(type) + "该组所有同学已经答题完毕", Toast.LENGTH_SHORT).show();
            return "";
        }
        String text = getStudentArray(type).remove(i);
        InputActivity.list.remove(issues);
        refreshView(type);
        return text;
    }
}
