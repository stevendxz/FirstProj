package com.adtis.fistpproj.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ToggleButton;
import com.adtis.fistpproj.R;
import com.adtis.fistpproj.util.CustomToast;


public class Fragment2 extends Fragment{
	private ToggleButton mTogBtn;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {	
		return inflater.inflate(R.layout.fragment2, null);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mTogBtn = (ToggleButton) getActivity().findViewById(R.id.mTogBtn); // 获取到控件
		mTogBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
										 boolean isChecked) {
				// TODO Auto-generated method stub
				if (isChecked) {
					CustomToast.DisplayToast(getActivity(), "选中");
				} else {
					CustomToast.DisplayToast(getActivity(), "未选中");
				}
			}
		});// 添加监听事件
	}
}