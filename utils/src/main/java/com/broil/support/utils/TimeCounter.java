package com.broil.support.utils;

import android.os.CountDownTimer;
import android.widget.Button;

/**
 * TODO<请描述这个类是干什么的>
 *
 * @author: broil
 * @date: 2016/4/27
 * @version: V1.0
 */

public class TimeCounter extends CountDownTimer{

    private Button btn;

    public TimeCounter(Button btn, long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
        this.btn = btn;
    }

    @Override
    public void onTick(long millisUntilFinished) {
        //计时过程显示
        btn.setEnabled(false);
        btn.setText(millisUntilFinished / 1000 + "秒后重发");
    }

    @Override
    public void onFinish() {
        //计时完毕时触发
        btn.setEnabled(true);
        btn.setText("发送验证码");
    }
}
