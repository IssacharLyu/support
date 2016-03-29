
package com.broil.support.widget.imageviewpager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.List;

import mobi.app.anjd.R;

/**
 * TODO<功能增强ViewPager 1设置导航点,2设置无限循环(View个数小于等于2时不能无限循环),3设置自动切换>
 *
 * @version: V1.0
 */
public class ImageViewPager extends RelativeLayout implements Runnable {

	private static final int DEFAULT_CHANGE_INTERVAL = 3000;
	private static final int DEFAULT_DOTS_PADDING = 10;
	private static final int DEFAULT_DOTS_HEIGHT = 40;
	private static final float DEFAULT_DOTS_BG_ALPHA = 0.5f;
	private static final int DEFAULT_DOTS_GRAVITY = Gravity.RIGHT;
	private static final int DEFAULT_PAGER_POSITION = 1000;
	private static final boolean DEFAULT_PAGER_AUTOCHANGE = false;
	private static final boolean DEFAULT_PAGER_RECYCLE = false;

	private ViewPager viewPager;
	private LinearLayout viewDots;
	private OnTouchListener onTouchListener;
	private OnPageChangeListener onPageChangeListener;

	/** 页面个数 */
	private int viewSize;

	/** 记录上一个Page的位置 */
	private int lastPagerPosition;
	/** Page切换的间隔时间 */
	private int changeInterval;
	/** 是否自动切换Pager */
	private boolean isAutoChange;
	/** 防止MotionEvent.ACTION_UP 结束5秒内自动切换page */
	private boolean isReadyChange = true;
	/** 是否无线循环 */
	private boolean isRecycle;
	/** 是否添加导航点 */
	private boolean isAddDots;

	private int dotsViewHeight;
	/** 导航点 之间的左右间隔 */
	private int dotsPadding;
	/** 导航点 距父Layout左右两边的margin值*/
	private int dotsMargin;
	/** 导航点 选中背景图id */
	private int dotsFocusImageId;
	/** 导航点 未选中背景图id */
	private int dotsBlurImageId;
	/** 导航点 在水平方向的位置 */
	private int dotsGravity;
	/** 导航点 的背景图 */
	private Drawable dotsBackground;
	/** 导航点 背景图的透明度 */
	private float dotsBgAlpha;

	private Context mContext;
	/** 导航点 图标 */
	private ImageView[] dotsImages;

	public ImageViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		this.mContext = context;

		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ImageViewPagerAttrs);

		try {
			isAddDots = a.getBoolean(R.styleable.ImageViewPagerAttrs_dotsIsAdd, false);
			if (true == isAddDots) {
				dotsViewHeight = a.getDimensionPixelSize(R.styleable.ImageViewPagerAttrs_dotsViewHeight,
						DEFAULT_DOTS_HEIGHT);
				dotsPadding = a
						.getDimensionPixelSize(R.styleable.ImageViewPagerAttrs_dotsSpacing, DEFAULT_DOTS_PADDING);
				dotsMargin = a.getDimensionPixelSize(R.styleable.ImageViewPagerAttrs_dotsMargin, 0);
				dotsFocusImageId = a.getResourceId(R.styleable.ImageViewPagerAttrs_dotsFocusImage, R.drawable.circle_guide_select);
				dotsBlurImageId = a.getResourceId(R.styleable.ImageViewPagerAttrs_dotsBlurImage, R.drawable.circle_guide_unselect);
				dotsBackground = a.getDrawable(R.styleable.ImageViewPagerAttrs_dotsBackground);
				dotsBgAlpha = a.getFloat(R.styleable.ImageViewPagerAttrs_dotsBgAlpha, DEFAULT_DOTS_BG_ALPHA);
				dotsGravity = a.getInt(R.styleable.ImageViewPagerAttrs_dotsGravity, DEFAULT_DOTS_GRAVITY);
			}
			changeInterval = a.getInt(R.styleable.ImageViewPagerAttrs_changeInterval, DEFAULT_CHANGE_INTERVAL);

			isAutoChange = a.getBoolean(R.styleable.ImageViewPagerAttrs_autoChange, DEFAULT_PAGER_AUTOCHANGE);
			isRecycle = a.getBoolean(R.styleable.ImageViewPagerAttrs_recycleble, DEFAULT_PAGER_RECYCLE);
		} finally {
			a.recycle();
		}

		initView();
	}

	private void initView() {
		viewPager = new ViewPager(getContext());

		LayoutParams paramsPager = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		addView(viewPager, paramsPager);

		if (isAddDots)
			initDots();
	}

	/**
	 * 添加并设置 导航点layout
	 */
	@SuppressLint("NewApi")
	private void initDots() {
		viewDots = new LinearLayout(getContext());

		if (dotsBackground != null) {
			dotsBackground.setAlpha((int) (dotsBgAlpha * 255));
			viewDots.setBackgroundDrawable(dotsBackground);
		}

		viewDots.setGravity(dotsGravity | Gravity.CENTER_VERTICAL);
		viewDots.setOrientation(LinearLayout.HORIZONTAL);

		LayoutParams paramsDost = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		paramsDost.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
		viewDots.setPadding(dotsMargin, 0, dotsMargin, 0);

		addView(viewDots, paramsDost);
	}

	/**
	 * 填充ViewPager
	 * 
	 * @param views
	 */
	public void setViewPagerViews(List<View> views) {

		if (views == null || views.size() == 0) {
			return;
		}
		viewSize = views.size();
		if (viewSize <= 2) {//当View个数小于2时，去掉无限循环
			isRecycle = false;
		}
		
		lastPagerPosition = isRecycle ? DEFAULT_PAGER_POSITION * viewSize : 0;

		viewPager.setAdapter(new ViewPagerAdapter(views, isRecycle));
		viewPager.setOnPageChangeListener(pageChangeListener);
		viewPager.setOnTouchListener(onTouchListener == null ? pagerTouchListener : (isAutoChange ? pagerTouchListener
				: onTouchListener));

		if (isAddDots)
			addDots(viewSize);
		viewPager.setCurrentItem(lastPagerPosition);

		if (isAutoChange && viewSize > 1)// 开启自动切换Pager的线程
			new Thread(this).start();
	}
	/**
	 * 
	 */
	public void setOnTouchListener(OnTouchListener onTouchListener) {
		this.onTouchListener = onTouchListener;
	}
	/**
	 * 
	 * @param onPageChangeListener
	 */
	public void setOnPagerChangeListener(OnPageChangeListener onPageChangeListener) {
		this.onPageChangeListener = onPageChangeListener;
	}
	/**
	 * 返回 当前选择的Item
	 * @return
	 */
	public int getCurrentItem() {
		return lastPagerPosition;
	}

	/***
	 * ViewPager 默认PageChange监听器
	 */
	private OnPageChangeListener pageChangeListener = new OnPageChangeListener() {

		@Override
		public void onPageSelected(int arg0) {
			// TODO Auto-generated method stub
			switchToDot(arg0);
			if (onPageChangeListener != null)
				onPageChangeListener.onPageSelected(arg0);
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			// TODO Auto-generated method stub
			if (onPageChangeListener != null)
				onPageChangeListener.onPageScrolled(arg0, arg1, arg2);
		}

		@Override
		public void onPageScrollStateChanged(int arg0) {
			// TODO Auto-generated method stub
			if (onPageChangeListener != null)
				onPageChangeListener.onPageScrollStateChanged(arg0);
		}
	};
	/***
	 * ViewPager 默认Touch监听器
	 */
	private OnTouchListener pagerTouchListener = new OnTouchListener() {

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			// TODO Auto-generated method stub
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
			case MotionEvent.ACTION_MOVE:
				isAutoChange = false;
				isReadyChange = false;
				break;
			case MotionEvent.ACTION_UP:
				isAutoChange = true;
				break;
			default:
				isAutoChange = true;
				break;
			}
			if (onTouchListener != null) {
				return onTouchListener.onTouch(v, event);
			} else {
				return false;
			}
		}
	};

	/**
	 * 更换选中的导航点图标
	 * 
	 * @param index
	 */
	private void switchToDot(int index) {
		if (isAddDots) {
			dotsImages[lastPagerPosition % viewSize].setImageResource(dotsBlurImageId);
			dotsImages[index % viewSize].setImageResource(dotsFocusImageId);
		}
		lastPagerPosition = index;
	}

	/**
	 * 添加导航圆点 到LinearLayout
	 * 
	 * @param size
	 */
	private void addDots(int size) {

		dotsImages = new ImageView[size];

		for (int i = 0; i < size; i++) {
			ImageView imageView = new ImageView(mContext);
			// 设置小圆点imageview的参数
			imageView.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
					LayoutParams.WRAP_CONTENT));
			imageView.setPadding(dotsPadding, 0, dotsPadding, 0);
			// 将小圆点layout添加到数组中
			dotsImages[i] = imageView;
			// 默认选中的是第一张图片，此时第一个小圆点是选中状态，其他不是
			if (i == 0) {
				dotsImages[i].setImageResource(dotsFocusImageId);
			} else {
				dotsImages[i].setImageResource(dotsBlurImageId);
			}
			// 将imageviews添加到小圆点视图组
			viewDots.addView(dotsImages[i]);
		}
	}

	@Override
	public void run() {
		while (true) {
			try {
				Thread.sleep(changeInterval);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			if (isAutoChange && isReadyChange) {
				int nextPosition = lastPagerPosition + 1;
				nextPosition = isRecycle ? nextPosition : nextPosition % viewSize;
				pageHandler.sendEmptyMessage(nextPosition);
			} else {
				isReadyChange = true;
			}
		}
	}

	Handler pageHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			viewPager.setCurrentItem(msg.what);
		}
	};

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {

		View child = this.getChildAt(0);
		child.layout(0, 0, getWidth(), getHeight());

		if (changed && isAddDots) {
			child = this.getChildAt(1);
			child.measure(r - l, dotsViewHeight);
			child.layout(0, getHeight() - (int) dotsViewHeight, getWidth(), getHeight());
		}
	}
}
