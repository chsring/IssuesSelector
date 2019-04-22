package io.aifo.issuesselector;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * @author srwing
 * @fileName IssuesAdapter
 * @time 2019/4/22 15:15
 * @Email surao@foryou56.com (有问题请邮件)
 * @desc
 */
public class IssuesAdapter extends BaseQuickAdapter<Issues, BaseViewHolder> {

    public IssuesAdapter(@Nullable List<Issues> data) {
        super(R.layout.item_layout, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Issues item) {
        helper.setText(R.id.list_item_text, item.text);
    }
}
