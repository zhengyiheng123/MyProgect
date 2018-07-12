package cloudlive.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.administrator.myprogect.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import cloudlive.imageload.GlideImageLoader;
import cloudlive.module.BrevityGroup;

public class GroupChatListAdapter extends BAdapter<BrevityGroup> {
//    private List<BrevityGroup> lists;
//    private LayoutInflater layoutInflater;
//    private Context mContext;

    public GroupChatListAdapter(Context context) {
        super(context);
//        layoutInflater = LayoutInflater.from(context);
//        lists = brevityGroups;
//        mContext = context;
    }

//    @Override
//    public int getCount() {
//        return lists.size();
//    }
//
//    @Override
//    public Object getItem(int position) {
//        return lists.get(position);
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return position;
//    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = loadView(R.layout.private_chat_list_left_item, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
//        Glide.with(context).load(itemList.get(position).getAvatar()).into(viewHolder.img);
        GlideImageLoader.create(viewHolder.img).load(itemList.get(position).getAvatar());
        viewHolder.groupName.setText(itemList.get(position).getGroupId());
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.img)
        ImageView img;
        @BindView(R.id.group_name)
        TextView groupName;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

}
