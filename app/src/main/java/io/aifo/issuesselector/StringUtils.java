package io.aifo.issuesselector;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.widget.EditText;
import android.widget.TextView;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.regex.Pattern;

/**
 *
 */

public class StringUtils {
    public static String touzi_ed_values22 = "";
    private static String TAG = "StringUtils";

    /**
     * 描述：是否是中文.
     *
     * @param str 指定的字符串
     */
    public static Boolean isChinese(String str) {
        Boolean isChinese = true;
        String chinese = "[\u0391-\uFFE5]";
        if (!isEmpty(str)) {
            //获取字段值的长
            // 度，如果含中文字符，则每个中文字符长度为2，否则为1
            for (int i = 0; i < str.length(); i++) {
                //获取一个字符
                String temp = str.substring(i, i + 1);
                //判断是否为中文字符
                if (temp.matches(chinese)) {
                } else {
                    isChinese = false;
                    break;
                }
            }
        } else {
            isChinese = false;
        }
        return isChinese;
    }

    /**
     * @des:只设置Textview字体的颜色，没有设置textView的点击事件
     * @start,end,都是相对于hint.length()长度而言，
     */
    public static void setTextViewColor(Context context, String hint, TextView tv,
                                        int start, int end, int colorid) {
        SpannableString spanText = new SpannableString(hint);
        spanText.setSpan(new ForegroundColorSpan(context.getResources().getColor(
                colorid)), start, end, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
//		ClickableSpan clickSpan = new NoLineClickSpan(Constant.PHONE_NUMBER);
//		spanText.setSpan(clickSpan, start, end, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
//		tv.setMovementMethod(LinkMovementMethod.getInstance());
//		tv.setHighlightColor(Color.TRANSPARENT);
        tv.setText(spanText);
    }

    public static void setClikableText(Context context, String hint, TextView tx,
                                       int start, int end, ClickableSpan clickableSpan) {
        SpannableString spanText = new SpannableString(hint);
        spanText.setSpan(clickableSpan, start, end, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        tx.setMovementMethod(LinkMovementMethod.getInstance());
        tx.setHighlightColor(Color.TRANSPARENT);
        tx.setText(spanText);

    }

    /**
     * 描述：判断一个字符串是否为null或空值.
     *
     * @param str 指定的字符串
     */
    public static boolean isEmpty(String str) {
        return str == null || str.trim().length() == 0;
    }

    /**
     * @描述：判断一个字符串是否为null或0.
     */
    public static boolean isNullOrZero(String str) {
        if (TextUtils.isEmpty(str)) {
            return true;
        }
        try {
            if (Float.valueOf(str) == 0) {
                return true;
            }
        } catch (Exception e) {
            return true;
        }
        return false;
    }

    private static final int MIN_DELAY_TIME= 1000;  // 两次点击间隔不能少于1000ms
    private static long lastClickTime;

    public static boolean isFastClick() {
        boolean flag = true;
        long currentClickTime = System.currentTimeMillis();
        if ((currentClickTime - lastClickTime) >= MIN_DELAY_TIME) {
            flag = false;
        }
        lastClickTime = currentClickTime;
        return flag;
    }

    /**
     * @des:修改文字的大小, textSize:是相对大小
     */
    public static void setTextViewBig(String hint, float textSize, TextView tv, int start, int end) {
        SpannableString spanString = new SpannableString(hint);
        //SpannableStringBuilder ssb = new SpannableStringBuilder(content);
        spanString.setSpan(new RelativeSizeSpan(textSize), start, end, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        tv.setText(spanString);
//		spanString.setSpan(new AbsoluteSizeSpan(textSize),start,end, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
//		tv.setText(spanString);
    }

    /**
     * @des:修改文字的大小, :适合一行出现多个变大的字的情况
     * @textSize:是相对大小
     */
    public static void setTextViewBig2(float textSize, SpannableString spanString, int start, int end) {
        //SpannableString spanString = new SpannableString(hint);
        spanString.setSpan(new RelativeSizeSpan(textSize), start, end, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        //tv.setText(spanString);
    }

    /**
     * @Des:处理身份证的*显示：341**** 9898  四位分割
     */
    public static String setIDNumberView(String numberStr) {
        String str = "";
        if (numberStr.length() >= 15) {
            char[] idCardChars = numberStr.toCharArray();
            for (int i = 0; i < idCardChars.length; i++) {
                if (i > 3 && i < idCardChars.length - 4) {
                    idCardChars[i] = '*';
                }

            }
            //加 " "
            for (int i = 0; i < idCardChars.length; i++) {
                if (i % 4 == 0 && i > 0) {
                    str += " ";
                }
                str += idCardChars[i];
            }


        } else {
            str = numberStr;
        }


        return str;
    }

    //格式：2015年05年20日
    public static String[] getYearMonDayArr(String formatStr) {
        String[] timeArr = {"0", "0", "0"};
        if (!TextUtils.isEmpty(formatStr)) {
            timeArr = formatStr.split("-");
        }
        return timeArr;
    }

    public static String formatDouble(float f) {
        return String.format("%.2f", f);
    }

    public static String formatDouble(String str) {
        String value = "";
        if (!TextUtils.isEmpty(str)) {
            try {
                value = String.format("%.2f", Float.valueOf(str));
            } catch (Exception e) {
                value = str;
            }
        }
        return value;
    }

    public static String rvZeroAndDot(String s) {
        if (s.isEmpty()) {
            return null;
        }
        if (s.indexOf(".") > 0) {
            s = s.replaceAll("0+?$", "");//去掉多余的0
            s = s.replaceAll("[.]$", "");//如最后一位是.则去掉
        }
        return s;
    }

    public static float setTwoFloat(String valueFloat, float defaultException) {
        float result;
        try {
            result = new BigDecimal(valueFloat).setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
        } catch (Exception e) {
            result = defaultException;
        }
        return result;
    }

    /**
     * @param :defaultExceptionStr：出现异常返回的值
     * @des:保留2位小数
     */
    public static String setTwoDouble(String valueDouble, String defaultExceptionStr) {
        //double fpay = new BigDecimal(valueDouble).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        //mTotalPriceEditText.setText(fpay + "");
        //return  new BigDecimal(valueDouble).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue()+"";
        String str = "";
        try {
            str = new BigDecimal(valueDouble).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue() + "";
        } catch (Exception e) {
            str = defaultExceptionStr;
        }
        return str;
    }

    /**
     * @param :defaultExceptionStr：出现异常返回的值
     * @des:保留4位小数，返回doublele类型
     */
    public static Double setFourDouble(String valueDouble, double defaultExcepDouble) {
        //double fpay = new BigDecimal(valueDouble).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        //mTotalPriceEditText.setText(fpay + "");
        //return  new BigDecimal(valueDouble).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue()+"";
        double dou = 0;
        try {
            dou = new BigDecimal(valueDouble).setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
        } catch (Exception e) {
            dou = defaultExcepDouble;
        }
        return dou;
    }

    /**
     * @des：实现类似：180****1234效果
     */
    public static String getStylePhoneText(String str) {
        String tempStr;
        if (str.length() > 7) {
            char[] chars = str.toCharArray();
            for (int i = 0; i < chars.length; i++) {
                if (i > 2 && i <= 6) {
                    chars[i] = '*';
                }
            }
            tempStr = new String(chars);
        } else {
            tempStr = str;
        }
        return tempStr;
    }

    /**
     * @des：实现名字一部分变***
     */
    public static String getStyleName(String str) {
        String tempStr = "";
        if (str != null && str.length() > 0) {
            char[] chars = str.toCharArray();
            if (chars.length == 2) {
                chars[0] = '*';
            } else if (chars.length > 2) {
                for (int i = 0; i < chars.length - 2; i++) {
                    chars[i] = '*';
                }
            }

            tempStr = new String(chars);
        }
        return tempStr;
    }

    /**
     * @des：实现金额变***
     */
    public static String getStyleMoneyInvisable(String str) {
        String tempStr = "";
        if (str != null && str.length() > 0) {
            str = str.replace(".", "");
            char[] chars = str.toCharArray();
            for (int i = 0; i < chars.length; i++) {
                chars[i] = '*';
            }
            tempStr = new String(chars);
        }
        return tempStr;
    }

    /**
     * @des：实现类似银行卡加2个空格：6228 2323 7832
     */
    public static String setTextStyle(String str) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        StringBuilder sb = new StringBuilder(str);
        int length = str.length() / 4 + str.length();
        for (int i = 0; i < length; i++) {
            if (i % 5 == 0) {
                sb.insert(i, " ");
            }
        }
        sb.deleteCharAt(0);
        return sb.toString();
    }

    /**
     * @描述：是否包含中文.
     */
    public static Boolean isContainChinese(String str) {
        Boolean isChinese = false;
        String chinese = "[\u0391-\uFFE5]";
        if (!isEmpty(str)) {
            //获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1
            for (int i = 0; i < str.length(); i++) {
                //获取一个字符
                String temp = str.substring(i, i + 1);
                if (temp.matches(chinese)) {//判断是否为中文字符
                    isChinese = true;
                } else {
                }
            }
        }
        return isChinese;
    }

    /**
     * @des:切换密码状态的工具
     */
//    public static void setPasswordState(boolean passwordState, TextView tv, ImageView imageView) {
//
//        if (passwordState) {//密码状态
//            //imageView.setBackgroundResource(R.drawable.lib_icon_password2);
//            imageView.setImageResource(R.drawable.lib_icon_password);
//            tv.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
//        } else {// 明文状态
//            //imageView.setBackgroundResource(R.drawable.lib_icon_password);
//            imageView.setImageResource(R.drawable.lib_icon_password2);
//            tv.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
//        }
//    }
    public static String addComma(String str, EditText edtext) {
        touzi_ed_values22 = edtext.getText().toString().trim().replaceAll(",", "");
        boolean neg = false;
        if (str.startsWith("-")) {  //处理负数
            str = str.substring(1);
            neg = true;
        }
        String tail = null;
        if (str.indexOf('.') != -1) { //处理小数点
            tail = str.substring(str.indexOf('.'));
            str = str.substring(0, str.indexOf('.'));
        }
        StringBuilder sb = new StringBuilder(str);
        sb.reverse();
        for (int i = 3; i < sb.length(); i += 4) {
            sb.insert(i, ',');
        }
        sb.reverse();
        if (neg) {
            sb.insert(0, '-');
        }
        if (tail != null) {
            sb.append(tail);
        }
        return sb.toString();
    }

    /**
     * 千分位逗号分隔
     */
    public static String convertNumComma(String num) {
        DecimalFormat nf = new DecimalFormat("#,###.##");
        return nf.format(num);
    }

    /**
     * 千分位逗号分隔
     */
    public static String convertMoney(String num) {
        double money = 0;
        if (!isEmpty(num)) {
            money = Double.parseDouble(num);
            DecimalFormat nf = new DecimalFormat("#,###.##");
            return "¥" + nf.format(money);
        } else {
            return "";
        }


    }


    public static String getJson(Context context, String fileName) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            AssetManager assetManager = context.getAssets();
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    assetManager.open(fileName)));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    public static boolean isNum(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(str).matches();
    }

    //join方法主要将list转为string，并用逗号分隔开
    public static String join(final Object[] array, final String separator) {
        if (array == null) {
            return null;
        }
        return join(array, separator, 0, array.length);
    }

    public static String join(final Object[] array, String separator, final int startIndex, final int endIndex) {
        if (array == null) {
            return null;
        }
        if (separator == null) {
            separator = "";
        }

        // endIndex - startIndex > 0:   Len = NofStrings *(len(firstString) + len(separator))
        //           (Assuming that all Strings are roughly equally long)
        final int noOfItems = endIndex - startIndex;
        if (noOfItems <= 0) {
            return "";
        }

        final StringBuilder buf = new StringBuilder(noOfItems * 16);

        for (int i = startIndex; i < endIndex; i++) {
            if (i > startIndex) {
                buf.append(separator);
            }
            if (array[i] != null) {
                buf.append(array[i]);
            }
        }
        return buf.toString();
    }

    //格式化人民币样式 ： ¥2,000.00
    public static String getRMBStyleString(String str) {
        if (TextUtils.isEmpty(str))
            return "";
        try {
            NumberFormat df = NumberFormat.getCurrencyInstance(Locale.CHINA);
            return df.format(Long.valueOf(str));
        } catch (Exception e) {
            return str;
        }
    }

    /**
     *去掉 ,
     * */
    public static String getNumberDeletePoint(String number){

        String num = "";
        if (StringUtils.isEmpty(number)){
            return "";
        }

        String[] split = number.split(",");

        for (int i = 0; i < split.length; i++) {

            num += split[i];
        }
        return num;

    }
    //格式化人民币样式 ：¥  2,000.00
    public static String getRMBStyleString2(String str) {

        String resultMoney =  "";
        if (TextUtils.isEmpty(str))
            return "";
        try {
            String[] strMoneys = str.split("\\.");
            NumberFormat df = new DecimalFormat("#,##0");
            resultMoney =  df.format(Long.valueOf(strMoneys[0]));
            if (strMoneys.length == 2) {
                if (strMoneys[1].length() == 1) {
                    resultMoney = resultMoney  +"."+ strMoneys[1] +"0";
                }else if(strMoneys[1].length() == 2){
                    resultMoney = resultMoney  +"."+ strMoneys[1];
                }else if(strMoneys[1].length() > 2){
                    resultMoney = resultMoney  +"."+ strMoneys[1].substring(0,2);
                }
            } else {
                resultMoney = resultMoney + ".00";
            }
        } catch (Exception e) {
            return "¥ " + str;
        }

        return "¥ " + resultMoney;
    }

    //格式化人民币样式 ： 2,000.00
    public static String getRMBStyleString4(double str) {

        String resultMoney =  "";

        try {

            NumberFormat df = new DecimalFormat("#,##0.00");
            resultMoney =  df.format(str);

        } catch (Exception e) {
            return  str+"";
        }

        return  resultMoney;
    }

    //格式化人民币样式 ： 2,000.00
    public static String getRMBStyleString3(String str) {

        if (isEmpty(str)){
            return "";
        }
        String resultMoney =  "";

        try {

            NumberFormat df = new DecimalFormat("#,##0.00");
            resultMoney =  df.format(Double.parseDouble(str));

        } catch (Exception e) {
            return  str+"";
        }

        return  resultMoney;
    }


    /**
     * 数字转汉字【新】
     *
     * @param num
     * @return
     */
    private static String nums[] = {"零", "一", "二", "三", "四", "五", "六", "七", "八", "九"};

    private static String pos_units[] = {"", "十", "百", "千"};

    private static String weight_units[] = {"", "万", "亿"};

    public static String numberToChinese(int num) {
        if (num == 0) {
            return "零";
        }

        int weigth = 0;//节权位
        String chinese = "";
        String chinese_section = "";
        boolean setZero = false;//下一小节是否需要零，第一次没有上一小节所以为false
        while (num > 0) {
            int section = num % 10000;//得到最后面的小节
            if (setZero) {//判断上一小节的千位是否为零，是就设置零
                chinese = nums[0] + chinese;
            }
            chinese_section = sectionTrans(section);
            if (section != 0) {//判断是都加节权位
                chinese_section = chinese_section + weight_units[weigth];
            }
            chinese = chinese_section + chinese;
            chinese_section = "";

            setZero = (section < 1000) && (section > 0);
            num = num / 10000;
            weigth++;
        }
        if ((chinese.length() == 2 || (chinese.length() == 3)) && chinese.contains("一十")) {
            chinese = chinese.substring(1, chinese.length());
        }
        if (chinese.indexOf("一十") == 0) {
            chinese = chinese.replaceFirst("一十", "十");
        }

        return chinese;
    }
    /**
     * 将每段数字转汉子
     * @param section
     * @return
     */
    public static String sectionTrans(int section) {
        StringBuilder section_chinese = new StringBuilder();
        int pos = 0;//小节内部权位的计数器
        boolean zero = true;//小节内部的置零判断，每一个小节只能有一个零。
        while (section > 0) {
            int v = section % 10;//得到最后一个数
            if (v == 0) {
                if (!zero) {
                    zero = true;//需要补零的操作，确保对连续多个零只是输出一个
                    section_chinese.insert(0, nums[0]);
                }
            } else {
                zero = false;//有非零数字就把置零打开
                section_chinese.insert(0, pos_units[pos]);
                section_chinese.insert(0, nums[v]);
            }
            pos++;
            section = section / 10;
        }

        return section_chinese.toString();
    }


}

