package edu.tjrac.swant.fileopenhelper.openhelper;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by wpc on 2017/2/11.
 */

public class OpenFileHelper {

    /**
     * 打开文件
     *
     * @param file
     */
    public static void openFile(Context context, File file) {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //设置intent的Action属性
        intent.setAction(Intent.ACTION_VIEW);
        //获取文件file的MIME类型
        String type = Util.getMIMEType(file);
        //设置intent的data和Type属性。
        intent.setDataAndType(Uri.fromFile(file), type);
        //跳转
        context.startActivity(intent);
        //这里最好try一下，有可能会报错。 //比如说你的MIME类型是打开邮箱，但是你手机里面没装邮箱客户端，就会报错。
    }

//    @SuppressLint("NewApi")
//    public static void showChoseFileToPlayDialog(String dirPath,
//                                                 String fileType, final Activity context) {
//        if (new File(dirPath).exists()) {
//            Log.i("目录存在", dirPath);
//            final ArrayList<FileInfo> items = getFileInfoListWithDirPathAndEnd(
//                    dirPath, fileType);
//            if (items.size() == 0) {
//                Toast.makeText(context, "文件夹为空", Toast.LENGTH_SHORT).show();
//            } else {
//                ChoseFileDialog dialog = new ChoseFileDialog(context, items,
//                        new AdapterView.OnItemClickListener() {
//                            @Override
//                            public void onItemClick(AdapterView<?> arg0,
//                                                    View arg1, int arg2, long arg3) {
//                                // TODO Auto-generated method stub
//                                FileInfo fi = items.get(arg2);
//                                String path = fi.getDirPath() + fi.getName();
//                                openFile(context, new File(path));
//                            }
//                        }, null);
//                dialog.show(context.getFragmentManager(), "chosefiledialog");
//            }
//
//            // if (MainActivity.videodatas == null
//            // || MainActivity.videodatas.size() <= 0) {
//            // Toast.makeText(SoftUpdateActivity.this, "路径下不存在视频",
//            // Toast.LENGTH_SHORT).show();
//            // } else {
//            // MainActivity.videolist_layout
//            // .setVisibility(View.VISIBLE);
//            // }
//        } else {
//            Toast.makeText(context, "目录不存在", Toast.LENGTH_SHORT).show();
//        }
//    }

    public static ArrayList<FileInfo> getFileInfoListWithDirPathAndEnd(
            String path, String endwith) {
        ArrayList<FileInfo> vediolist = new ArrayList<FileInfo>();

        File file = new File(path);
        File[] subFile = file.listFiles();
        if (subFile != null) {
            if (subFile.length != 0) {
                for (int iFileLength = 0; iFileLength < subFile.length; iFileLength++) {
                    if (!subFile[iFileLength].isDirectory()) {
                        String filename = subFile[iFileLength].getName();
                        if (filename.trim().toLowerCase().endsWith(endwith)) {
                            vediolist.add(new FileInfo(filename, path, ""));
                        }
                    } else {
                        Log.i("getvediofilename", "文件目录有错");
                    }
                }
            }
        }
        return vediolist;
    }

}
