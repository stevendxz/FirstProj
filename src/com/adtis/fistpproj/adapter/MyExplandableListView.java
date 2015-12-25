package com.adtis.fistpproj.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.adtis.fistpproj.R;

/**
 * 自定义ExplandableListView
 * Title: MyExplandableListView
 * Description:
 * Company: iceTree
 *
 * @author zoeice
 * @date 2012-7-9
 */
public class MyExplandableListView extends ExpandableListView
        implements OnChildClickListener, OnGroupClickListener {

    ExpandInfoAdapter adapter;
    String[] str_group_items_ = {"第一组", "第二组", "第三组", "第四组", "第五组"};
    String[][] str_child_items_ = {{"这是第一条", "这是第二条"}, {"这是第一条", "这是第二条", "这是第三条"}, {"这是第一条"}, {"这是第一条", "这是第二条"}, {"这是第一条", "这是第二条", "这是第三条", "这是第四条"}};
    private Context context_;

    public MyExplandableListView(Context context) {
        super(context);
        context_ = context;
        /* 隐藏默认箭头显示 */
        this.setGroupIndicator(null);
		/* 隐藏垂直滚动条 */
        this.setVerticalScrollBarEnabled(false);

		/* 监听child，group点击事件 */
        this.setOnChildClickListener(this);
        this.setOnGroupClickListener(this);

        setCacheColorHint(Color.TRANSPARENT);
        setDividerHeight(0);
        setChildrenDrawnWithCacheEnabled(false);
        setGroupIndicator(null);

		/*隐藏选择的黄色高亮*/
        ColorDrawable drawable_tranparent_ = new ColorDrawable(Color.TRANSPARENT);
        setSelector(drawable_tranparent_);

		/*设置adapter*/
        adapter = new ExpandInfoAdapter();
        setAdapter(adapter);
    }


    public MyExplandableListView(Context context, AttributeSet attrs) {
        this(context);
        // TODO Auto-generated constructor stub
    }

    @Override
    public boolean onChildClick(ExpandableListView parent, View v,
                                int groupPosition, int childPosition, long id) {
        // TODO Auto-generated method stub
        Toast.makeText(getContext(), "hi，你竟然点击了第" + (groupPosition + 1) + "组的第" + (childPosition + 1) + "条！", Toast.LENGTH_LONG).show();
        return false;
    }

    @Override
    public boolean onGroupClick(ExpandableListView parent, View v,
                                int groupPosition, long id) {
        // TODO Auto-generated method stub
        return false;
    }

    public class ExpandInfoAdapter extends BaseExpandableListAdapter {
        LinearLayout mGroupLayout;

        //++++++++++++++++++++++++++++++++++++++++++++
        // child's stub

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            // TODO Auto-generated method stub
            return str_child_items_[groupPosition][childPosition];
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            // TODO Auto-generated method stub
            return childPosition;
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            // TODO Auto-generated method stub
            return str_child_items_[groupPosition].length;
        }

        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView,
                                 ViewGroup parent) {
            // TODO Auto-generated method stub
            TextView txt_child;
            if (null == convertView) {
                convertView = LayoutInflater.from(context_).inflate(R.layout.child_item, null);
            }
			/*判断是否是最后一项，最后一项设计特殊的背景*/
            if (isLastChild) {
                convertView.setBackgroundResource(R.color.whitesmoke);
            } else {
                convertView.setBackgroundResource(R.color.whitesmoke);
            }

            txt_child = (TextView) convertView.findViewById(R.id.id_child_txt);
            txt_child.setText(str_child_items_[groupPosition][childPosition]);

            return convertView;
        }

        //++++++++++++++++++++++++++++++++++++++++++++
        // group's stub

        @Override
        public Object getGroup(int groupPosition) {
            // TODO Auto-generated method stub
            return str_group_items_[groupPosition];
        }

        @Override
        public int getGroupCount() {
            // TODO Auto-generated method stub
            return str_group_items_.length;
        }

        @Override
        public long getGroupId(int groupPosition) {
            // TODO Auto-generated method stub
            return groupPosition;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            TextView txt_group;
            if (null == convertView) {
                convertView = LayoutInflater.from(context_).inflate(R.layout.group_layout, null);
            }
			/*判断是否group张开，来分别设置背景图*/
            if (isExpanded) {
                convertView.setBackgroundResource(R.color.gray);
            } else {
                convertView.setBackgroundResource(R.color.white);
            }

            txt_group = (TextView) convertView.findViewById(R.id.id_group_txt);
            txt_group.setText(str_group_items_[groupPosition]);
            return convertView;
        }

        @Override
        public boolean isChildSelectable(int arg0, int arg1) {
            // TODO Auto-generated method stub
            return true;
        }

        @Override
        public boolean hasStableIds() {
            // TODO Auto-generated method stub
            return false;
        }

    }

}

