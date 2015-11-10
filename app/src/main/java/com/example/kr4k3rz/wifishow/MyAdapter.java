package com.example.kr4k3rz.wifishow;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.lzyzsd.randomcolor.RandomColor;

import java.util.ArrayList;

import me.drakeet.materialdialog.MaterialDialog;


public class MyAdapter extends RecyclerView.Adapter<MyAdapter.CustomViewHOlder> {
    ArrayList<Networks> networksArrayList;


    public MyAdapter(ArrayList<Networks> networksArrayList) {
        this.networksArrayList = networksArrayList;
    }

    @Override
    public CustomViewHOlder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemview = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view, parent, false);
        return new CustomViewHOlder(itemview);
    }

    @Override
    public void onBindViewHolder(CustomViewHOlder holder, int position) {
        Networks networks = networksArrayList.get(position);
        holder.ssid.setText(networks.getSsid().substring(1, networks.getSsid().length() - 1));
        holder.key_mgmt.setText(networks.getKey_mgmt());


    }


    @Override
    public int getItemCount() {
        return networksArrayList.size();
    }

    class CustomViewHOlder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView ssid;
        TextView key_mgmt;
        CardView cv;


        public CustomViewHOlder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cv);
            ssid = (TextView) itemView.findViewById(R.id.ssid_txt);
            key_mgmt = (TextView) itemView.findViewById(R.id.key_mgmt_txt);
            cv.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            MaterialDialog mMaterialDailog = new MaterialDialog(itemView.getContext())
                    .setTitle("         Password")
                    .setMessage("            "+networksArrayList
                            .get(getAdapterPosition()).getPsk().
                                    substring(1, networksArrayList.get(getAdapterPosition()).getPsk().length() - 1));
            mMaterialDailog.show();
        }
    }

}
