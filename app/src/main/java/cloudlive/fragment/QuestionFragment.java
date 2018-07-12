package cloudlive.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.administrator.myprogect.R;
import com.talkfun.sdk.module.QuestionEntity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cloudlive.adapter.QuestionAdapter;
import cloudlive.interfaces.IDispatchQuestion;

public class QuestionFragment extends Fragment implements IDispatchQuestion {
    @BindView(R.id.chat_lv)
    ListView questionLv;
    private QuestionAdapter questionAdapter;
    private ArrayList<QuestionEntity> questionEntitiesList = new ArrayList<>();
    private Unbinder unbinder;

    public QuestionFragment() {

    }

    public static QuestionFragment create(ArrayList<QuestionEntity> list) {
        QuestionFragment qf = new QuestionFragment();
        Bundle bundle = new Bundle();
//        bundle.putString("question", tag);
        bundle.putSerializable("list", list);
        qf.setArguments(bundle);
        return qf;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && questionAdapter != null)
            questionAdapter.notifyDataSetChanged();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments().getSerializable("list") != null) {
            questionEntitiesList = (ArrayList<QuestionEntity>) getArguments().getSerializable("list");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View layout = inflater.inflate(R.layout.livein_chat_fragment_layout, container, false);
        unbinder = ButterKnife.bind(this, layout);
        questionAdapter = new QuestionAdapter(getActivity());
        questionLv.setAdapter(questionAdapter);
        questionAdapter.setItems(questionEntitiesList);
        return layout;
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("list", questionEntitiesList);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        try {
            if (savedInstanceState == null)
                return;
            ArrayList<QuestionEntity> list = (ArrayList<QuestionEntity>) savedInstanceState.getSerializable("list");
            if (list != null && list.size() > 0) {
                if (questionAdapter != null) {
                    questionEntitiesList = list;
                    questionAdapter.setItems(list);
                }
            }
        } catch (NullPointerException e) {

        }
    }

    @Override
    public void setQuestion(QuestionEntity questionEntity) {
        if (questionEntity != null) {
            if (questionAdapter != null) {
                questionAdapter.appendQuestion(questionEntity);
            }
            if (questionLv != null) {
                questionLv.setSelection(questionAdapter.getCount() - 1);
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
//        ButterKnife.unbind(this);

    }

    /**
     * 清空聊天信息
     */
    public void clearAllQuestionMessage() {
        if (questionAdapter == null) return;
        questionAdapter.clearItems();
    }

}
