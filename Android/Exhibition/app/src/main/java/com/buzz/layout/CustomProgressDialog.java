package com.buzz.layout;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.buzz.activity.R;

import java.text.NumberFormat;

/**
 * Created by buzz on 2015/6/15.
 */
public class CustomProgressDialog extends ProgressDialog {

    private ProgressBar mProgress;
    private TextView mProgressTitle;
    private TextView mProgressNumber;
    private TextView mProgressPercent;
    private TextView mProgressMessage;

    private Handler mViewUpdateHandler;
    private int mMax;
    private CharSequence mMessage;
    private CharSequence mTitle;
    private boolean mHasStarted;
    private int mProgressVal;

    private String TAG = "CommonProgressDialog";
    private String mProgressNumberFormat;
    private NumberFormat mProgressPercentFormat;

    public CustomProgressDialog(Context context) {
        super(context);
        initFormats();
    }

    public CustomProgressDialog(Context context, int theme) {
        super(context, theme);
        initFormats();
    }

    public CustomProgressDialog(Context context, boolean hiddenProcessNumber) {
        super(context);
        if (hiddenProcessNumber) {
            mProgressNumberFormat = null;
            mProgressPercentFormat = NumberFormat.getPercentInstance();
            mProgressPercentFormat.setMaximumFractionDigits(0);
        } else {
            initFormats();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.progress_bar_sweet);
        mProgress = (ProgressBar) findViewById(R.id.progress);
        mProgressNumber = (TextView) findViewById(R.id.progress_number);
        mProgressPercent = (TextView) findViewById(R.id.progress_percent);
        mProgressMessage = (TextView) findViewById(R.id.progress_message);
        mProgressTitle = (TextView) findViewById(R.id.progress_title);
//      LayoutInflater inflater = LayoutInflater.from(getContext());
        mViewUpdateHandler = new Handler() {


            @Override
            public void handleMessage(Message msg) {
                // TODO Auto-generated method stub
                super.handleMessage(msg);
                int progress = mProgress.getProgress();
                int max = mProgress.getMax();
//                double dProgress = (double)progress/(double)(1024 * 1024);
//                double dMax = (double)max/(double)(1024 * 1024);
                double dProgress = (double) progress;
                double dMax = (double) max;
                if (mProgressNumberFormat != null) {
                    String format = mProgressNumberFormat;
                    mProgressNumber.setText(String.format(format, dProgress, dMax));
                } else {
                    mProgressNumber.setText("");
                }
                if (mProgressPercentFormat != null) {
                    double percent = (double) progress / (double) max;
                    SpannableString tmp = new SpannableString(mProgressPercentFormat.format(percent));
                    tmp.setSpan(new StyleSpan(android.graphics.Typeface.BOLD),
                            0, tmp.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    mProgressPercent.setText(tmp);
                } else {
                    mProgressPercent.setText("");
                }
            }

        };
//      View view = inflater.inflate(R.layout.common_progress_dialog, null);
//        mProgress = (ProgressBar) view.findViewById(R.id.progress);
//        mProgressNumber = (TextView) view.findViewById(R.id.progress_number);
//        mProgressPercent = (TextView) view.findViewById(R.id.progress_percent);
//        setView(view);
        //mProgress.setMax(100);
        onProgressChanged();
        if (mMessage != null) {
            setMessage(mMessage);
        }
        if (mTitle != null) {
            setTitle(mTitle);
        }
        if (mMax > 0) {
            setMax(mMax);
        }
        if (mProgressVal > 0) {
            setProgress(mProgressVal);
        }
    }

    private void initFormats() {
        mProgressNumberFormat = "%1.0f/%1.0f";
        mProgressPercentFormat = NumberFormat.getPercentInstance();
        mProgressPercentFormat.setMaximumFractionDigits(0);
    }

    private void onProgressChanged() {
        mViewUpdateHandler.sendEmptyMessage(0);


    }

    public void setProgressStyle(int style) {
        //mProgressStyle = style;
    }

    public int getMax() {
        if (mProgress != null) {
            return mProgress.getMax();
        }
        return mMax;
    }

    public void setMax(int max) {
        if (mProgress != null) {
            mProgress.setMax(max);
            onProgressChanged();
        } else {
            mMax = max;
        }
    }

    public void setIndeterminate(boolean indeterminate) {
        if (mProgress != null) {
            mProgress.setIndeterminate(indeterminate);
        }
//      else {
//            mIndeterminate = indeterminate;
//        }
    }

    public void setProgress(int value) {
        if (mHasStarted) {
            mProgress.setProgress(value);
            onProgressChanged();
        } else {
            mProgressVal = value;
        }
    }


    @Override
    public void setMessage(CharSequence message) {
        // TODO Auto-generated method stub
        //super.setMessage(message);
        if (mProgressMessage != null) {
            mProgressMessage.setText(message);
        } else {
            mMessage = message;
        }
    }

    @Override
    public void setTitle(CharSequence title) {
        if (mProgressTitle != null) {
            mProgressTitle.setText(title);
        } else {
            mTitle = title;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        mHasStarted = true;
    }

    @Override
    protected void onStop() {
        // TODO Auto-generated method stub
        super.onStop();
        mHasStarted = false;
    }


}
