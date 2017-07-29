package com.android.launcher.module.main.fragments.file;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.launcher.R;

import java.io.File;
import java.util.ArrayList;

import edu.tjrac.swant.baselibrary.common.interfaze.OnItemClickListener;
import edu.tjrac.swant.baselibrary.common.interfaze.OnItemLongClickListener;
import edu.tjrac.swant.baselibrary.common.util.FileUtils;
import edu.tjrac.swant.baselibrary.common.util.StringUtils;
import edu.tjrac.swant.baselibrary.common.util.VibrationUtils;
import edu.tjrac.swant.fileopenhelper.openhelper.FileExploreAdapter;
import edu.tjrac.swant.fileopenhelper.openhelper.OpenFileHelper;

/**
 * Created by wpc on 2017/7/26.
 */

public class FileFragment extends Fragment {

    public static final String ROOT_DIR="";
    Context mContext;
    ArrayList<String> rote;
    Toolbar mToolbar;
    RecyclerView mRvPathIndicateView;
    RecyclerView mRvFileView;
    boolean multi_select = false;
    edu.tjrac.swant.fileopenhelper.openhelper.FileDirAdapter dirAdapter;
    FileExploreAdapter adapter;

    private OnItemClickListener mOnItemClick = new OnItemClickListener() {
        @Override
        public void onItemClick(View view, int position) {

            String path = adapter.getPath();
            String[] childs = adapter.getChilds();
            if (new File(path, childs[position]).isDirectory()) {
                rote.add(childs[position]);
                refeshFileView(rote);
                refeshDirView();
            } else {
                OpenFileHelper.openFile(mContext, new File(path, childs[position]));
            }
        }
    };
    private OnItemLongClickListener mOnItemLongClickListener = new OnItemLongClickListener() {
        @Override
        public void onItemLongClick(View v, int positon) {
            VibrationUtils.vibrate(mContext,50);
            if (adapter.multi_selcet) {
                adapter.setMulti_selcet(false);
            } else {
                adapter.setMulti_selcet(true);
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.activity_file_explore,container,false);
        if (!StringUtils.isEmpty(ROOT_DIR)) {
            rote = new ArrayList<>();
            rote.add(ROOT_DIR);
        }
        mToolbar = (Toolbar) v.findViewById(edu.tjrac.swant.fileopenhelper.R.id.toolbar);
        mRvPathIndicateView = (RecyclerView) v.findViewById(edu.tjrac.swant.fileopenhelper.R.id.rv_path_indicate_view);
        mRvFileView = (RecyclerView) v.findViewById(edu.tjrac.swant.fileopenhelper.R.id.rv_file_view);
//        enableBackPress(mToolbar, "文件管理");

        mRvPathIndicateView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        mRvFileView.setLayoutManager(new GridLayoutManager(mContext, 4));
        refeshFileView(rote);
        refeshDirView();
        return v;
    }


    private void refeshDirView() {
        dirAdapter = new edu.tjrac.swant.fileopenhelper.openhelper.FileDirAdapter(rote, mContext);
        dirAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                int size = rote.size();
                for (int i = position + 1; i < size; i++) {
                    rote.remove(i);
                }
                refeshFileView(rote);
                refeshDirView();
            }
        });
        mRvPathIndicateView.setAdapter(dirAdapter);
    }

    void refeshFileView(ArrayList<String> rote) {
        String path = FileUtils.getAbsPath(rote);
        adapter = new FileExploreAdapter(mContext, path, multi_select);
        adapter.setOnItemClick(mOnItemClick);
        adapter.setOnItemLongClickListener(mOnItemLongClickListener);
        mRvFileView.setAdapter(adapter);
    }

}
