package com.buzz.layout;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.buzz.activity.R;

/**
 * Created by buzz on 2015/6/15.
 */
public class CustomDialog extends Dialog {
    public CustomDialog(Context context) {
        super(context);
    }
    public CustomDialog(Context context, int theme) {
        super(context, theme);
    }

    /**
     * Helper class for creating a custom dialog
     */



    public static class Builder {
        private Context context;
        private String title; // 对话框标题
        private String message; // 对话框内容
        private String backButtonText; // 对话框返回按钮文本
        private String confirmButtonText; // 对话框确定文本
        private View contentView;

        // 对话框按钮监听事件
        private OnClickListener
                backButtonClickListener,
                confirmButtonClickListener;

        public Builder(Context context) {
            this.context = context;
        }

        /**
         * 使用字符串设置对话框消息
         * @paramtitle
         * @return
         */
        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }

        /**
         * 使用资源设置对话框消息
         * @paramtitle
         * @return
         */
        public Builder setMessage(int message) {
            this.message = (String) context.getText(message);
            return this;
        }

        /**
         * 使用资源设置对话框标题信息
         * @param title
         * @return
         */
        public Builder setTitle(int title) {
            this.title = (String) context.getText(title);
            return this;
        }

        /**
         * 使用字符串设置对话框标题信息
         * @param title
         * @return
         */
        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        /**
         * 设置自定义的对话框内容
         * @param v
         * @return
         */
        public Builder setContentView(View v) {
            this.contentView = v;
            return this;
        }

        /**
         * 设置back按钮的事件和文本
         * @param backButtonText
         * @param listener
         * @return
         */
        public Builder setBackButton(int backButtonText, OnClickListener listener) {
            this.backButtonText = (String)context.getText(backButtonText);
            this.backButtonClickListener = listener;
            return this;
        }

        /**
         * 设置back按钮的事件和文本
         * @param backButtonText
         * @param listener
         * @return
         */
        public Builder setBackButton(String backButtonText, OnClickListener listener) {
            this.backButtonText = backButtonText;
            this.backButtonClickListener = listener;
            return this;
        }

        /**
         * 设置确定按钮事件和文本
         * @param confirmButtonText
         * @param listener
         * @return
         */
        public Builder setConfirmButton(int confirmButtonText, OnClickListener listener) {
            this.confirmButtonText = (String)context.getText(confirmButtonText);
            this.confirmButtonClickListener = listener;
            return this;
        }

        /**
         * 设置确定按钮事件和文本
         * @param confirmButtonText
         * @param listener
         * @return
         */
        public Builder setConfirmButton(String confirmButtonText, OnClickListener listener) {
            this.confirmButtonText = confirmButtonText;
            this.confirmButtonClickListener = listener;
            return this;
        }

        /**
         * 创建自定义的对话框
         */
        public CustomDialog create() {
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            // 实例化自定义的对话框主题
            final CustomDialog dialog = new CustomDialog(context, R.style.CustomDialog);

            View layout = inflater.inflate(R.layout.mydialog, null);
            dialog.addContentView(layout,
                    new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

            // 设置对话框标题
            ((TextView) layout.findViewById(R.id.dialog_title)).setText(title);

            // 设置对话框内容
            if (message != null) {
                TextView dlgMsg = (TextView)layout.findViewById(R.id.dialog_message);
                dlgMsg.setText(message);
            } else if (contentView != null) {
                // if no message set
                // 如果没有设置对话框内容，添加contentView到对话框主体
                ((LinearLayout) layout.findViewById(R.id.dialog_content)).removeAllViews();
                ((LinearLayout) layout.findViewById(R.id.dialog_content)).addView(
                        contentView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            }

            // 设置返回按钮事件和文本
            if (backButtonText != null) {
                Button bckButton = ((Button) layout.findViewById(R.id.dialog_back));
                bckButton.setText(backButtonText);

                if (backButtonClickListener != null) {
                    bckButton.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            backButtonClickListener.onClick(dialog, DialogInterface.BUTTON_NEGATIVE);
                        }
                    });
                }
            } else {
                layout.findViewById(R.id.dialog_back).setVisibility(View.GONE);
            }

            // 设置确定按钮事件和文本
            if (confirmButtonText != null) {
                Button cfmButton = ((Button) layout.findViewById(R.id.dialog_confirm));
                cfmButton.setText(confirmButtonText);

                if (confirmButtonClickListener != null) {
                    cfmButton.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            confirmButtonClickListener.onClick(dialog, DialogInterface.BUTTON_POSITIVE);
                        }
                    });
                }
            } else {
                layout.findViewById(R.id.dialog_confirm).setVisibility(View.GONE);
            }

            dialog.setContentView(layout);

            return dialog;

        }


    }

}
