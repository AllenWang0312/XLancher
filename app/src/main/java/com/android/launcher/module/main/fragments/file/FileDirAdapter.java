package com.android.launcher.module.main.fragments.file;//package color.measurement.com.from_cp20.manager.file;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import edu.tjrac.swant.baselibrary.common.interfaze.OnItemClickListener;
import edu.tjrac.swant.baselibrary.common.util.UiUtils;
import edu.tjrac.swant.fileopenhelper.R;

/**
 * Created by wpc on 2017/6/15.
 */

public class FileDirAdapter extends RecyclerView.Adapter<FileDirAdapter.FileDirViewHolder> {
    ArrayList<String> rote;
    Context mContext;

    public FileDirAdapter(ArrayList<String> rote, Context context) {
        this.rote = rote;
        mContext = context;
    }

    OnItemClickListener mOnItemClickListener;
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }
    @Override
    public FileDirViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new FileDirViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_dir_file_explore, null));
    }

    @Override
    public void onBindViewHolder(FileDirViewHolder holder, final int position) {
        holder.dir.setText(rote.get(position));
        UiUtils.setDrawableRight(mContext, holder.dir, R.drawable.ic_keyboard_arrow_right_black_24dp);
        holder.dir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onItemClick(v, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return rote.size();
    }

    class FileDirViewHolder extends RecyclerView.ViewHolder {
        TextView dir;

        public FileDirViewHolder(View itemView) {
            super(itemView);
            dir = (TextView) itemView;
        }
    }
}