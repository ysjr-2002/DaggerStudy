package com.visitor.obria.yourapplication.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.visitor.obria.yourapplication.R;
import com.visitor.obria.yourapplication.dao.PersonBean;

import java.util.List;

public class PersonAdapter extends BaseAdapter {

    private Context mContext;
    private List<PersonBean> mPersonBeans;

    public PersonAdapter(Context context, List<PersonBean> list) {

        this.mContext = context;
        this.mPersonBeans = list;
    }

    @Override
    public int getCount() {
        return mPersonBeans.size();
    }

    @Override
    public Object getItem(int position) {
        return this.mPersonBeans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_card_view, null);

            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 100);
            convertView.setLayoutParams(lp);
            viewHolder = new ViewHolder();
            viewHolder.mName = convertView.findViewById(R.id.tv_name);
            viewHolder.mCard = convertView.findViewById(R.id.tv_card);
            viewHolder.mViewPhoto = convertView.findViewById(R.id.iv_photo);
            convertView.setTag(viewHolder);
        } else {

            viewHolder = (ViewHolder) convertView.getTag();
        }

        PersonBean bean = mPersonBeans.get(position);
        viewHolder.mName.setText(bean.getName());
        viewHolder.mCard.setText(bean.getCardno());

        if (!TextUtils.isEmpty(bean.getPath())) {

            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = false;
            options.inSampleSize = 4;
            Bitmap bitmap = BitmapFactory.decodeFile(bean.getPath(), options);
            viewHolder.mViewPhoto.setImageBitmap(bitmap);
        }
        return convertView;
    }

    class ViewHolder {
        TextView mName;
        TextView mCard;
        ImageView mViewPhoto;
    }
}
