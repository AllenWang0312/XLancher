package edu.tjrac.swant.fileopenhelper.openhelper;//package color.measurement.com.from_cp20.manager.file;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import java.io.File;
import java.util.ArrayList;

import edu.tjrac.swant.baselibrary.common.interfaze.OnItemClickListener;
import edu.tjrac.swant.fileopenhelper.R;

/**
 * Created by wpc on 2017/6/14.
 */
@SuppressLint({"NewApi", "ValidFragment"})
public class FileExploreDialog extends DialogFragment {
    Context mContext;

    ArrayList<String> rote;
    RecyclerView mRvPathIndicateView;
    RecyclerView mRvFileView;

    public FileExploreDialog(String root) {
        rote = new ArrayList<>();
        rote.add(root);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        mContext = getActivity();
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("文件管理");
        View v = LayoutInflater.from(mContext).inflate(R.layout.dialog_fileexplore, null);
        mRvPathIndicateView = (RecyclerView) v.findViewById(R.id.rv_path_indicate_view);
        mRvFileView = (RecyclerView) v.findViewById(R.id.rv_file_view);

        mRvPathIndicateView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        mRvFileView.setLayoutManager(new GridLayoutManager(mContext, 4));
        refeshFileView(rote);
        refeshDirView();
        builder.setView(v);
        return builder.create();
    }
    FileDirAdapter dirAdapter;
    private void refeshDirView() {
        dirAdapter = new FileDirAdapter(rote, mContext);
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
    FileExploreAdapter adapter;
    void refeshFileView(ArrayList<String> rote) {
        String path = "";
        for (String str : rote) {
            path += "/" + str;
        }
        adapter = new FileExploreAdapter(mContext, path,false);
        adapter.setOnItemClick(mOnItemClick);
//        adapter.setOnItemLongClickListener(mOnItemLongClickListener);
        mRvFileView.setAdapter(adapter);
    }
    private OnItemClickListener mOnItemClick = new OnItemClickListener() {
        @Override
        public void onItemClick(View view, int position) {
            String path = adapter.getPath();
            String[] childs = adapter.getChilds();
            if (new File(path, childs[position]).isDirectory()) {
                rote.add(childs[position]);
                refeshFileView(rote);
            } else {
                OpenFileHelper.openFile(mContext, new File(path, childs[position]));
            }
        }
    };
//    private OnItemLongClickListener mOnItemLongClickListener = new OnItemLongClickListener() {
//        @Override
//        public void onItemLongClick(int positon) {
//            Intent share = new Intent(Intent.ACTION_SEND);
//            ComponentName component = new ComponentName("com.tencent.mobileqq", "com.tencent.mobileqq.activity.JumpActivity");
//            share.setComponent(component);
//            File file = new File(adapter.getPath(), adapter.getChilds()[positon]);
//            System.out.println("file " + file.exists());
//            share.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
//            share.setType("*/*");
//            startActivity(Intent.createChooser(share, "发送"));
//        }
//    };



}
