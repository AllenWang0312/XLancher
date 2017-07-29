package edu.tjrac.swant.fileopenhelper.openhelper;//package color.measurement.com.from_cp20.manager.file;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.io.File;
import java.util.ArrayList;

import edu.tjrac.swant.baselibrary.base.ProgressDialogActivity;
import edu.tjrac.swant.baselibrary.common.interfaze.OnItemClickListener;
import edu.tjrac.swant.baselibrary.common.interfaze.OnItemLongClickListener;
import edu.tjrac.swant.baselibrary.common.util.FileUtils;
import edu.tjrac.swant.baselibrary.common.util.StringUtils;
import edu.tjrac.swant.baselibrary.dialog.NamedDialog;
import edu.tjrac.swant.baselibrary.util.T;
import edu.tjrac.swant.baselibrary.common.util.VibrationUtils;
import edu.tjrac.swant.fileopenhelper.R;

/**
 * Created by wpc on 2017/6/15.
 */

public class FileExploreActivity extends ProgressDialogActivity {

    Context mContext;
    ArrayList<String> rote;
    Toolbar mToolbar;
    RecyclerView mRvPathIndicateView;
    RecyclerView mRvFileView;
    boolean multi_select = false;
    FileDirAdapter dirAdapter;
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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        Intent i = getIntent();
        String dir = i.getStringExtra("rootDir");
        if (!StringUtils.isEmpty(dir)) {
            rote = new ArrayList<>();
            rote.add(dir);
        }
        setContentView(R.layout.activity_file_explore);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mRvPathIndicateView = (RecyclerView) findViewById(R.id.rv_path_indicate_view);
        mRvFileView = (RecyclerView) findViewById(R.id.rv_file_view);
        enableBackPress(mToolbar, "文件管理");

        mRvPathIndicateView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        mRvFileView.setLayoutManager(new GridLayoutManager(mContext, 4));
        refeshFileView(rote);
        refeshDirView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.file_explore, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.delete_file) {
            if (adapter.isMulti_selcet()) {
                ArrayList<Integer> select = adapter.getSelect_index();
                if (select.size() > 0) {
                    FileUtils.deleteFiles(FileUtils.getAbsPath(rote), select);
                    refeshFileView(rote);
                } else {
                    T.showWarning(mContext, "请勾选项目");
                }
            } else {
                T.showWarning(mContext, "长按勾选项目");
            }
        } else if (id == R.id.create_new_dir) {
            final NamedDialog named = new NamedDialog(mContext, "名称", "文件夹名");
            named.setPositive(new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String name= named.getEditText().getText().toString().trim();
                    if(StringUtils.isEmpty(name)){
                        T.showWarning(mContext,"文件夹名不能为空");
                    }else {
                        if (FileUtils.createOrExistsDir(new File(FileUtils.getAbsPath(rote),name))) {
                            T.showSuccess(mContext,"创建成功");
                            refeshFileView(rote);
                        }else {
                            T.showError(mContext,"创建失败");
                        }
                    }

                }
            });
            named.show(getSupportFragmentManager(), "named");

        } else if (id == android.R.id.home) {
            if (rote.size() > 1) {
                rote.remove(rote.size() - 1);
                refeshFileView(rote);
                refeshDirView();
            } else {
                return super.onOptionsItemSelected(item);
            }
        }
        return true;
    }

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

    void refeshFileView(ArrayList<String> rote) {
        String path = FileUtils.getAbsPath(rote);
        adapter = new FileExploreAdapter(mContext, path, multi_select);
        adapter.setOnItemClick(mOnItemClick);
        adapter.setOnItemLongClickListener(mOnItemLongClickListener);
        mRvFileView.setAdapter(adapter);
    }

}
