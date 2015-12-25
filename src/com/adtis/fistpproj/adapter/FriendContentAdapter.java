package com.adtis.fistpproj.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.adtis.fistpproj.R;
import com.adtis.fistpproj.model.ContentModel;

import java.util.List;

public class FriendContentAdapter extends BaseAdapter {
    private Context context;
    private List<ContentModel> list;

    public FriendContentAdapter(Context context, List<ContentModel> list) {
        super();
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if (list != null) {
            return list.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHold hold;
        if (convertView == null) {
            hold = new ViewHold();
            convertView = LayoutInflater.from(context).inflate(
                    R.layout.list_item, null);
            convertView.setTag(hold);
        } else {
            hold = (ViewHold) convertView.getTag();
        }
        hold.textView = (TextView) convertView.findViewById(R.id.tv_list_item);
        hold.imageView = (ImageView) convertView.findViewById(R.id.iv_list_item);

        hold.textView.setText(list.get(position).getText());
        hold.imageView.setImageResource(list.get(position).getImageView());
        return convertView;
    }

    static class ViewHold {
        public ImageView imageView;
        public TextView textView;
    }
}
