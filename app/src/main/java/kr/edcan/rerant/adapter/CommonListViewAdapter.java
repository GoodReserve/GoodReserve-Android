package kr.edcan.rerant.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import kr.edcan.rerant.R;
import kr.edcan.rerant.model.CommonListData;

/**
 * Created by KOHA_DESKTOP on 2016. 6. 19..
 */
public class CommonListViewAdapter extends ArrayAdapter<CommonListData> {
    ArrayList<CommonListData> arrayList;
    Context c;

    public CommonListViewAdapter(Context context, ArrayList<CommonListData> arrayList) {
        super(context, 0, arrayList);
        this.arrayList = arrayList;
        this.c = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(c).inflate(R.layout.common_listview_content, null);

        CommonListData data = getItem(position);
        TextView title = (TextView) view.findViewById(R.id.commonListViewTitle);
        TextView content = (TextView) view.findViewById(R.id.commonListViewContent);
        title.setText(data.getTitle());
        content.setText(data.getContent());
        return view;
    }
}
