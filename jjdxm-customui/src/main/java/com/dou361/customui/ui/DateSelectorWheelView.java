package com.dou361.customui.ui;


import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dou361.customui.adapter.StrericWheelAdapter;
import com.dou361.customui.listener.OnWheelChangedListener;
import com.dou361.customui.utils.ResourceUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * ========================================
 * <p>
 * 版 权：dou361.com 版权所有 （C） 2015
 * <p>
 * 作 者：陈冠明
 * <p>
 * 个人网站：http://www.dou361.com
 * <p>
 * 版 本：1.0
 * <p>
 * 创建日期：2016/3/16 10:39
 * <p>
 * 描 述：
 * <p>
 * <p>
 * 修订历史：
 * <p>
 * ========================================
 */
public class DateSelectorWheelView extends RelativeLayout implements
        OnWheelChangedListener {
    private final String flag = this.getClass().getSimpleName();
    private RelativeLayout rlTitle;
    private LinearLayout llWheelViews;
    private TextView tvSubTitle;
    private TextView tvYear;
    private TextView tvMonth;
    private TextView tvDay;
    private TextView tvHour;
    private TextView tvMinute;
    private TextView tvSecond;
    private View line0;
    private TextView tv_empty;
    private TextView tv_line1;
    private TextView tv_line2;
    private WheelView wvYear;
    private WheelView wvMonth;
    private WheelView wvDay;
    private WheelView wvHour;
    private WheelView wvMinute;
    private WheelView wvSecond;
    /**
     * 显示年份数
     */
    private String[] years = new String[141];
    /**
     * 显示月份数
     */
    private String[] months = new String[12];
    /**
     * 显示日数
     */
    private String[] tinyDays = new String[28];
    private String[] smallDays = new String[29];
    private String[] normalDays = new String[30];
    private String[] bigDays = new String[31];
    /**
     * 显示时数
     */
    private String[] hours = new String[24];
    /**
     * 显示分数
     */
    private String[] minutes = new String[60];
    /**
     * 显示秒数
     */
    private String[] seconds = new String[60];

    private StrericWheelAdapter yearsAdapter;
    private StrericWheelAdapter monthsAdapter;
    private StrericWheelAdapter tinyDaysAdapter;
    private StrericWheelAdapter smallDaysAdapter;
    private StrericWheelAdapter bigDaysAdapter;
    private StrericWheelAdapter normalDaysAdapter;

    private StrericWheelAdapter hoursAdapter;
    private StrericWheelAdapter minutesAdapter;
    private StrericWheelAdapter secondsAdapter;
    private int currentDateType;
    private int todayHour;
    private int todayMinute;
    private int todaySecond;

    public DateSelectorWheelView(Context context, AttributeSet attrs,
                                 int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initLayout(context);
    }

    public DateSelectorWheelView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initLayout(context);
    }

    public DateSelectorWheelView(Context context) {
        super(context);
        initLayout(context);
    }

    private void initLayout(Context context) {
        LayoutInflater.from(context).inflate(ResourceUtils.getResourceIdByName(context, "layout", "customui_datepick_date_time_layout"), this,
                true);
        rlTitle = (RelativeLayout) findViewById(ResourceUtils.getResourceIdByName(context, "id", "rl_date_time_title"));
        llWheelViews = (LinearLayout) findViewById(ResourceUtils.getResourceIdByName(context, "id", "ll_wheel_views"));
        tvSubTitle = (TextView) findViewById(ResourceUtils.getResourceIdByName(context, "id", "tv_date_time_subtitle"));
        tvYear = (TextView) findViewById(ResourceUtils.getResourceIdByName(context, "id", "tv_date_time_year"));
        tvMonth = (TextView) findViewById(ResourceUtils.getResourceIdByName(context, "id", "tv_date_time_month"));
        tvHour = (TextView) findViewById(ResourceUtils.getResourceIdByName(context, "id", "tv_date_time_hour"));
        tvMinute = (TextView) findViewById(ResourceUtils.getResourceIdByName(context, "id", "tv_date_time_minute"));
        tvSecond = (TextView) findViewById(ResourceUtils.getResourceIdByName(context, "id", "tv_date_time_second"));
        line0 = findViewById(ResourceUtils.getResourceIdByName(context, "id", "tv_date_time_line0"));
        tv_empty = (TextView) findViewById(ResourceUtils.getResourceIdByName(context, "id", "tv_date_time_empty"));
        tv_line1 = (TextView) findViewById(ResourceUtils.getResourceIdByName(context, "id", "tv_date_time_line1"));
        tv_line2 = (TextView) findViewById(ResourceUtils.getResourceIdByName(context, "id", "tv_date_time_line2"));

        tvDay = (TextView) findViewById(ResourceUtils.getResourceIdByName(context, "id", "tv_date_time_day"));
        wvYear = (WheelView) findViewById(ResourceUtils.getResourceIdByName(context, "id", "wv_date_of_year"));
        wvMonth = (WheelView) findViewById(ResourceUtils.getResourceIdByName(context, "id", "wv_date_of_month"));
        wvDay = (WheelView) findViewById(ResourceUtils.getResourceIdByName(context, "id", "wv_date_of_day"));
        wvHour = (WheelView) findViewById(ResourceUtils.getResourceIdByName(context, "id", "wv_date_of_hour"));
        wvMinute = (WheelView) findViewById(ResourceUtils.getResourceIdByName(context, "id", "wv_date_of_minute"));
        wvSecond = (WheelView) findViewById(ResourceUtils.getResourceIdByName(context, "id", "wv_date_of_second"));
        wvYear.addChangingListener(this);
        wvMonth.addChangingListener(this);
        wvDay.addChangingListener(this);

        wvHour.addChangingListener(this);
        wvMinute.addChangingListener(this);
        wvSecond.addChangingListener(this);
        setData();
        setShowDateType(DateTimeSelectorDialogBuilder.TYPE_YYYYMMDD);
    }

    private void setData() {
        /** 年初始化 */
        for (int i = 0; i < years.length; i++) {
            years[i] = 1960 + i + " 年";
        }
        /** 月初始化 */
        for (int i = 0; i < months.length; i++) {
            if (i < 9) {
                months[i] = "0" + (1 + i) + " 月";
            } else {
                months[i] = (1 + i) + " 月";
            }
        }
        /** 日初始化 */
        for (int i = 0; i < tinyDays.length; i++) {
            if (i < 9) {
                tinyDays[i] = "0" + (1 + i) + " 日";
            } else {
                tinyDays[i] = (1 + i) + " 日";
            }
        }
        for (int i = 0; i < smallDays.length; i++) {
            if (i < 9) {
                smallDays[i] = "0" + (1 + i) + " 日";
            } else {
                smallDays[i] = (1 + i) + " 日";
            }
        }
        for (int i = 0; i < normalDays.length; i++) {
            if (i < 9) {
                normalDays[i] = "0" + (1 + i) + " 日";
            } else {
                normalDays[i] = (1 + i) + " 日";
            }
        }
        for (int i = 0; i < bigDays.length; i++) {
            if (i < 9) {
                bigDays[i] = "0" + (1 + i) + " 日";
            } else {
                bigDays[i] = (1 + i) + " 日";
            }
        }
        /** 时初始化 */
        for (int i = 0; i < hours.length; i++) {
            if (i <= 9) {
                hours[i] = "0" + i + " 时";
            } else {
                hours[i] = i + " 时";
            }
        }
        /** 分初始化 */
        for (int i = 0; i < minutes.length; i++) {
            if (i <= 9) {
                minutes[i] = "0" + i + " 分";
            } else {
                minutes[i] = i + " 分";
            }
        }
        /** 秒初始化 */
        for (int i = 0; i < seconds.length; i++) {
            if (i <= 9) {
                seconds[i] = "0" + i + " 秒";
            } else {
                seconds[i] = i + " 秒";
            }
        }


        yearsAdapter = new StrericWheelAdapter(years);
        monthsAdapter = new StrericWheelAdapter(months);
        tinyDaysAdapter = new StrericWheelAdapter(tinyDays);
        smallDaysAdapter = new StrericWheelAdapter(smallDays);
        normalDaysAdapter = new StrericWheelAdapter(normalDays);
        bigDaysAdapter = new StrericWheelAdapter(bigDays);

        hoursAdapter = new StrericWheelAdapter(hours);
        minutesAdapter = new StrericWheelAdapter(minutes);
        secondsAdapter = new StrericWheelAdapter(seconds);

        wvYear.setAdapter(yearsAdapter);
        wvYear.setCurrentItem(getTodayYear());
        wvYear.setCyclic(true);
        wvMonth.setAdapter(monthsAdapter);
        wvMonth.setCurrentItem(getTodayMonth());
        wvMonth.setCyclic(true);
        if (isBigMonth(getTodayMonth() + 1)) {
            wvDay.setAdapter(bigDaysAdapter);
        } else if (getTodayMonth() == 1
                && isLeapYear(wvYear.getCurrentItemValue().subSequence(0, 4)
                .toString().trim())) {
            wvDay.setAdapter(smallDaysAdapter);
        } else if (getTodayMonth() == 1) {
            wvDay.setAdapter(tinyDaysAdapter);
        } else {
            wvDay.setAdapter(normalDaysAdapter);
        }
        wvDay.setCurrentItem(getTodayDay());
        wvDay.setCyclic(true);

        wvHour.setAdapter(hoursAdapter);
        wvHour.setCurrentItem(getTodayHour());
        wvHour.setCyclic(true);
        wvMinute.setAdapter(minutesAdapter);
        wvMinute.setCurrentItem(getTodayMinute());
        wvMinute.setCyclic(true);
        wvSecond.setAdapter(secondsAdapter);
        wvSecond.setCurrentItem(getTodaySecond());
        wvSecond.setCyclic(true);
    }

    /**
     * 设置显示的样式
     */
    public void setShowDateType(int type) {
        currentDateType = type;
        switch (type) {
            case DateTimeSelectorDialogBuilder.TYPE_YYYYMM:
                line0.setVisibility(View.GONE);
                tvDay.setVisibility(View.GONE);
                tv_empty.setVisibility(View.GONE);
                tv_line1.setVisibility(View.GONE);
                tv_line2.setVisibility(View.GONE);
                tvHour.setVisibility(View.GONE);
                tvMinute.setVisibility(View.GONE);
                tvSecond.setVisibility(View.GONE);
                wvHour.setVisibility(View.GONE);
                wvMinute.setVisibility(View.GONE);
                wvSecond.setVisibility(View.GONE);
                wvYear.setStyle(18, 4);
                wvMonth.setStyle(18, 4);
                wvDay.setStyle(18, 4);
                wvHour.setStyle(18, 4);
                wvMinute.setStyle(18, 4);
                wvSecond.setStyle(18, 4);
                break;
            case DateTimeSelectorDialogBuilder.TYPE_YYYYMMDD:
                line0.setVisibility(View.VISIBLE);
                tvDay.setVisibility(View.VISIBLE);
                tv_empty.setVisibility(View.GONE);
                tv_line1.setVisibility(View.GONE);
                tv_line2.setVisibility(View.GONE);
                tvHour.setVisibility(View.GONE);
                tvMinute.setVisibility(View.GONE);
                tvSecond.setVisibility(View.GONE);
                wvHour.setVisibility(View.GONE);
                wvMinute.setVisibility(View.GONE);
                wvSecond.setVisibility(View.GONE);
                wvYear.setStyle(14, 2);
                wvMonth.setStyle(14, 2);
                wvDay.setStyle(14, 2);
                wvHour.setStyle(14, 2);
                wvMinute.setStyle(14, 2);
                wvSecond.setStyle(14, 2);
                break;
            case DateTimeSelectorDialogBuilder.TYPE_YYYYMMDDHHMM:
                line0.setVisibility(View.VISIBLE);
                tvDay.setVisibility(View.VISIBLE);
                tv_empty.setVisibility(View.VISIBLE);
                tv_line1.setVisibility(View.VISIBLE);
                tvHour.setVisibility(View.VISIBLE);
                tvMinute.setVisibility(View.VISIBLE);
                wvHour.setVisibility(View.VISIBLE);
                wvMinute.setVisibility(View.VISIBLE);
                tvSecond.setVisibility(View.GONE);
                tv_line2.setVisibility(View.GONE);
                wvSecond.setVisibility(View.GONE);
                wvYear.setStyle(14, 2);
                wvMonth.setStyle(14, 2);
                wvDay.setStyle(14, 2);
                wvHour.setStyle(14, 2);
                wvMinute.setStyle(14, 2);
                wvSecond.setStyle(14, 2);
                break;
            case DateTimeSelectorDialogBuilder.TYPE_YYYYMMDDHHMMSS:
                line0.setVisibility(View.VISIBLE);
                tvDay.setVisibility(View.VISIBLE);
                tv_empty.setVisibility(View.VISIBLE);
                tv_line1.setVisibility(View.VISIBLE);
                tv_line2.setVisibility(View.VISIBLE);
                tvHour.setVisibility(View.VISIBLE);
                tvMinute.setVisibility(View.VISIBLE);
                tvSecond.setVisibility(View.VISIBLE);
                wvHour.setVisibility(View.VISIBLE);
                wvMinute.setVisibility(View.VISIBLE);
                wvSecond.setVisibility(View.VISIBLE);
                wvYear.setStyle(14, 2);
                wvMonth.setStyle(14, 2);
                wvDay.setStyle(14, 2);
                wvHour.setStyle(14, 2);
                wvMinute.setStyle(14, 2);
                wvSecond.setStyle(14, 2);
                break;
        }
    }

    /**
     * 设置当前显示的年份
     *
     * @param year
     */
    public void setCurrentYear(String year) {
        boolean overYear = true;
        year = year + " 年";
        for (int i = 0; i < years.length; i++) {
            if (year.equals(years[i])) {
                wvYear.setCurrentItem(i);
                overYear = false;
                break;
            }
        }
        if (overYear) {
            Log.e(flag, "设置的年份超出了数组的范围");
        }
    }

    /**
     * 设置当前显示的月份
     *
     * @param month
     */
    public void setCurrentMonth(String month) {
        month = month + " 月";
        for (int i = 0; i < months.length; i++) {
            if (month.equals(months[i])) {
                wvMonth.setCurrentItem(i);
                break;
            }
        }
    }

    /**
     * 设置当前显示的日期号
     *
     * @param day 14
     */
    public void setCurrentDay(String day) {
        day = day + " 日";
        for (int i = 0; i < smallDays.length; i++) {
            if (day.equals(smallDays[i])) {
                wvDay.setCurrentItem(i);
                break;
            }
        }
    }

    /**
     * 获取选择的日期的值
     *
     * @return
     */
    public String getSelectedDate() {
        switch (currentDateType) {
            case DateTimeSelectorDialogBuilder.TYPE_YYYYMM:
                return tvYear.getText().toString().trim() + "-"
                        + tvMonth.getText().toString().trim();
            case DateTimeSelectorDialogBuilder.TYPE_YYYYMMDD:
                return tvYear.getText().toString().trim() + "-"
                        + tvMonth.getText().toString().trim() + "-"
                        + tvDay.getText().toString().trim();
            case DateTimeSelectorDialogBuilder.TYPE_YYYYMMDDHHMM:
                return tvYear.getText().toString().trim() + "-"
                        + tvMonth.getText().toString().trim() + "-"
                        + tvDay.getText().toString().trim() + " "
                        + tvHour.getText().toString().trim() + ":"
                        + tvMinute.getText().toString().trim();
            case DateTimeSelectorDialogBuilder.TYPE_YYYYMMDDHHMMSS:
                return tvYear.getText().toString().trim() + "-"
                        + tvMonth.getText().toString().trim() + "-"
                        + tvDay.getText().toString().trim() + " "
                        + tvHour.getText().toString().trim() + ":"
                        + tvMinute.getText().toString().trim() + ":"
                        + tvSecond.getText().toString().trim();
        }
        return tvYear.getText().toString().trim() + "-"
                + tvMonth.getText().toString().trim() + "-"
                + tvDay.getText().toString().trim() + " "
                + tvHour.getText().toString().trim() + ":"
                + tvMinute.getText().toString().trim() + ":"
                + tvSecond.getText().toString().trim();
    }

    /**
     * 设置标题的点击事件
     *
     * @param onClickListener
     */
    public void setTitleClick(OnClickListener onClickListener) {
        rlTitle.setOnClickListener(onClickListener);
    }

    /**
     * 设置日期选择器的日期转轮是否可见
     *
     * @param visibility
     */
    public void setDateSelectorVisiblility(int visibility) {
        llWheelViews.setVisibility(visibility);
    }

    public int getDateSelectorVisibility() {
        return llWheelViews.getVisibility();
    }

    /**
     * 判断是否是闰年
     *
     * @param year
     * @return
     */
    private boolean isLeapYear(String year) {
        int temp = Integer.parseInt(year);
        return temp % 4 == 0 && (temp % 100 != 0 || temp % 400 == 0) ? true
                : false;
    }

    /**
     * 判断是否是大月
     *
     * @param month
     * @return
     */
    private boolean isBigMonth(int month) {
        boolean isBigMonth = false;
        switch (month) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                isBigMonth = true;
                break;

            default:
                isBigMonth = false;
                break;
        }
        return isBigMonth;
    }

    int currentMonth = 1;

    @Override
    public void onChanged(WheelView wheel, int oldValue, int newValue) {
        String trim = null;
        if (wheel.getId() == wvYear.getId()) {
            trim = (wvYear.getCurrentItemValue())
                    .trim().split(" ")[0];
            tvYear.setText(trim);
            if (isLeapYear(trim)) {
                if (currentMonth == 2) {
                    wvDay.setAdapter(smallDaysAdapter);
                } else if (isBigMonth(currentMonth)) {
                    wvDay.setAdapter(bigDaysAdapter);
                } else {
                    wvDay.setAdapter(normalDaysAdapter);
                }
            } else if (currentMonth == 2) {
                wvDay.setAdapter(tinyDaysAdapter);
            } else if (isBigMonth(currentMonth)) {
                wvDay.setAdapter(bigDaysAdapter);
            } else {
                wvDay.setAdapter(smallDaysAdapter);
            }
        } else if (wheel.getId() == wvMonth.getId()) {
            trim = (wvMonth.getCurrentItemValue())
                    .trim().split(" ")[0];
            currentMonth = Integer.parseInt(trim);
            tvMonth.setText(trim);
            switch (currentMonth) {
                case 1:
                case 3:
                case 5:
                case 7:
                case 8:
                case 10:
                case 12:
                    wvDay.setAdapter(bigDaysAdapter);
                    break;
                case 2:
                    String yearString =
                            (wvYear.getCurrentItemValue()).trim().split(" ")[0];
                    if (isLeapYear(yearString)) {
                        wvDay.setAdapter(smallDaysAdapter);
                    } else {
                        wvDay.setAdapter(tinyDaysAdapter);
                    }
                    break;
                default:
                    wvDay.setAdapter(smallDaysAdapter);
                    break;
            }
        } else if (wheel.getId() == wvDay.getId()) {
            tvDay.setText((wvDay.getCurrentItemValue())
                    .trim().split(" ")[0]);
        } else if (wheel.getId() == wvHour.getId()) {
            tvHour.setText((wvHour.getCurrentItemValue())
                    .trim().split(" ")[0]);
        } else if (wheel.getId() == wvMinute.getId()) {
            tvMinute.setText((wvMinute.getCurrentItemValue())
                    .trim().split(" ")[0]);
        } else if (wheel.getId() == wvSecond.getId()) {
            tvSecond.setText((wvSecond.getCurrentItemValue())
                    .trim().split(" ")[0]);
        }

    }

    public int getTitleId() {
        if (rlTitle != null) {
            return rlTitle.getId();
        }
        return 0;
    }

    /**
     * 获取今天的日期
     *
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    private String getToday() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
        String str = formatter.format(curDate);
        return str;
    }

    /**
     * 获取当天的年份
     *
     * @return
     */
    private int getTodayYear() {
        int position = 0;
        String today = getToday();
        String year = today.substring(0, 4);
        year = year + " 年";
        for (int i = 0; i < years.length; i++) {
            if (year.equals(years[i])) {
                position = i;
                break;
            }
        }
        return position;
    }

    /**
     * 获取当前日期的月数的位置
     *
     * @return
     */
    private int getTodayMonth() {
        // 2015年12月01日
        int position = 0;
        String today = getToday();
        String month = today.substring(5, 7);
        month = month + " 月";
        for (int i = 0; i < months.length; i++) {
            if (month.equals(months[i])) {
                position = i;
                break;
            }
        }
        return position;
    }

    /**
     * 获取当前日期的天数的日子
     *
     * @return
     */
    private int getTodayDay() {
        // 2015年12月01日
        int position = 0;
        String today = getToday();
        String day = today.substring(8, 10);
        day = day + " 日";
        for (int i = 0; i < bigDays.length; i++) {
            if (day.equals(bigDays[i])) {
                position = i;
                break;
            }
        }
        return position;
    }

    public int getTodayHour() {
        int position = 0;
        String today = getToday();
        String hour = today.substring(12, 14);
        hour = hour + " 时";
        for (int i = 0; i < hours.length; i++) {
            if (hour.equals(hours[i])) {
                position = i;
                break;
            }
        }
        return position;
    }

    public int getTodayMinute() {
        int position = 0;
        String today = getToday();
        String minute = today.substring(15, 17);
        minute = minute + " 分";
        for (int i = 0; i < minutes.length; i++) {
            if (minute.equals(minutes[i])) {
                position = i;
                break;
            }
        }
        return position;
    }

    public int getTodaySecond() {
        int position = 0;
        String today = getToday();
        String second = today.substring(18, 20);
        second = second + " 秒";
        for (int i = 0; i < seconds.length; i++) {
            if (second.equals(seconds[i])) {
                position = i;
                break;
            }
        }
        return position;
    }
}
