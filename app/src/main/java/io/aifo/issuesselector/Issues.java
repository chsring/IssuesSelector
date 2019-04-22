package io.aifo.issuesselector;

/**
 * Description:
 * Created by moxiaohao
 * Date: 2019/4/22
 * Email: shenlei@foryou56.com
 */
public class Issues {

    public Issues(int type, String text) {
        this.type = type;
        this.text = text;
    }

    public int type;//1 安卓，2 ios
    public String text;
}
