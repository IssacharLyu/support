/* 
 * @Title:  ViewPagerAdapter.java 
 * @Copyright:  XXX Co., Ltd. Copyright YYYY-YYYY,  All rights reserved 
 * @Description:  TODO<���������ļ�����ʲô��> 
 * @author:  YangHe 
 * @data:  2014-7-28 ����9:30:49 
 * @version:  V1.0 
 */

package com.broil.support.widget.imageviewpager;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import java.util.List;

public class ViewPagerAdapter extends PagerAdapter {

	private List<View> viewList = null;
	private int viewSize = 0;
	private boolean isRecycle = false;

	public ViewPagerAdapter(List<View> views, boolean isRecycle) {
		super();
		this.viewList = views;
		this.viewSize = views.size();
		this.isRecycle = isRecycle;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return isRecycle ? Integer.MAX_VALUE : viewSize;
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		// TODO Auto-generated method stub
		return arg0 == arg1;
	}

	@Override
	public Object instantiateItem(View container, int position) {
		// TODO Auto-generated method stub
		if (isRecycle) {
			position = position % viewSize;
		}
		if (viewList.get(position).getParent() != null) {
            ((ViewPager) viewList.get(position)
            		.getParent()).removeView(viewList.get(position));  
        }  
		((ViewPager) container).addView(viewList.get(position), 0);
        return viewList.get(position); 
//		((ViewPager)container).addView(viewList.get(position));
//		return viewList.get(position);
	}

	@Override
	public void destroyItem(View container, int position, Object object) {
		// TODO Auto-generated method stub
		//((ViewPager) container).removeView((View) object);
	}

}
