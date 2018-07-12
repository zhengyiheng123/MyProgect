package cloudlive.dialog;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.example.administrator.myprogect.R;

import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by ccy on 2017/11/6.
 */

public abstract class BaseDialog extends DialogFragment implements View.OnClickListener {
    protected WeakReference<Context> mContext;
    protected View layout;
    @BindView(R.id.iv_icon)
    protected ImageView icon;
    @BindView(R.id.tv_title)
    protected TextView title;
    @BindView(R.id.cancel)
    protected ImageView cancal;
    @BindView(R.id.ll_content)
    protected LinearLayout llContent;
    private Unbinder unbinder;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = new WeakReference<Context>(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        layout = inflater.inflate(R.layout.base_dialog_fragment, container);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        unbinder = ButterKnife.bind(this, layout);
        initView();
        initEvent();
        return layout;
    }

    protected void initView() {
        View view = View.inflate(getActivity(), addContentLayout(), null);
        llContent.addView(view);
    }

    protected void initEvent() {
        cancal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

    }

    abstract protected int addContentLayout();

    @Override
    public void show(FragmentManager manager, String tag) {
        FragmentTransaction ft = manager.beginTransaction();
        ft.add(this, tag);
        ft.commitAllowingStateLoss();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public void onClick(View v) {

    }
}
