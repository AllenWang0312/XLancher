package edu.tjrac.swant.fileopenhelper.openhelper;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by wpc on 2017/1/6.
 */

public class Util {


    public static String getSDcardPath() {
        String str = Environment.getExternalStorageDirectory().getPath();
        Log.i("getSDcardPath", str);
        return str;
    }

//
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
//                                OpenFileHelper.openFile(context, new File(path));
//                            }
//                        }, null);
//                dialog.show(context.getFragmentManager(), "chosefiledialog");
//            }
//
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

    private ArrayList<String> getFileNameListWithPathAndEnd(String path,
                                                            String endwith) {
        ArrayList<String> vediolist = new ArrayList<String>();

        File file = new File(path);
        File[] subFile = file.listFiles();

        for (int iFileLength = 0; iFileLength < subFile.length; iFileLength++) {
            // 判断是否为文件夹
            if (!subFile[iFileLength].isDirectory()) {
                String filename = subFile[iFileLength].getName();

                // 判断是否为MP4结尾
                if (filename.trim().toLowerCase().endsWith(endwith)) {
                    // String strVideoName = filename.substring(0,
                    // filename.length() - 4);
                    // System.out.println("读取到的视频名称："+strVideoName);
                    vediolist.add(filename);
                    // System.out.println("读取到的视频名称1："+strVideoName);
                }
            } else {
                Log.i("getvediofilename", "文件目录有错");
            }
        }
        return vediolist;
    }



    /**
     * 根据文件后缀名获得对应的MIME类型。
     *
     * @param file
     */
    public static String getMIMEType(File file) {

        String type = "*/*";
        String fName = file.getName();
        //获取后缀名前的分隔符"."在fName中的位置。
        int dotIndex = fName.lastIndexOf(".");
        if (dotIndex < 0) {
            return type;
        }
    /* 获取文件的后缀名*/
        String end = fName.substring(dotIndex, fName.length()).toLowerCase();
        if (end == "") return type;
        //在MIME和文件类型的匹配表中找到对应的MIME类型。
        for (int i = 0; i < MIME_MapTable.length; i++) {
            if (end.equals(MIME_MapTable[i][0]))
                type = MIME_MapTable[i][1];
        }

        return type;
    }


//MIME_MapTable是所有文件的后缀名所对应的MIME类型的一个String数组：
    private static final String[][] MIME_MapTable = {
            //{后缀名，MIME类型}
            {".3gp", "video/3gpp"},
            {".apk", "application/vnd.android.package-archive"},
            {".asf", "video/x-ms-asf"},
            {".avi", "video/x-msvideo"},
            {".bin", "application/octet-stream"},
            {".bmp", "image/bmp"},
            {".c", "text/plain"},
            {".class", "application/octet-stream"},
            {".conf", "text/plain"},
            {".cpp", "text/plain"},
            {".doc", "application/msword"},
            {".docx", "application/vnd.openxmlformats-officedocument.wordprocessingml.document"},
            {".xls", "application/vnd.ms-excel"},
            {".xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"},
            {".exe", "application/octet-stream"},
            {".gif", "image/gif"},
            {".gtar", "application/x-gtar"},
            {".gz", "application/x-gzip"},
            {".h", "text/plain"},
            {".htm", "text/html"},
            {".html", "text/html"},
            {".jar", "application/java-archive"},
            {".java", "text/plain"},
            {".jpeg", "image/jpeg"},
            {".jpg", "image/jpeg"},
            {".js", "application/x-javascript"},
            {".log", "text/plain"},
            {".m3u", "audio/x-mpegurl"},
            {".m4a", "audio/mp4a-latm"},
            {".m4b", "audio/mp4a-latm"},
            {".m4p", "audio/mp4a-latm"},
            {".m4u", "video/vnd.mpegurl"},
            {".m4v", "video/x-m4v"},
            {".mov", "video/quicktime"},
            {".mp2", "audio/x-mpeg"},
            {".mp3", "audio/x-mpeg"},
            {".mp4", "video/mp4"},
            {".mpc", "application/vnd.mpohun.certificate"},
            {".mpe", "video/mpeg"},
            {".mpeg", "video/mpeg"},
            {".mpg", "video/mpeg"},
            {".mpg4", "video/mp4"},
            {".mpga", "audio/mpeg"},
            {".msg", "application/vnd.ms-outlook"},
            {".ogg", "audio/ogg"},
            {".pdf", "application/pdf"},
            {".png", "image/png"},
            {".pps", "application/vnd.ms-powerpoint"},
            {".ppt", "application/vnd.ms-powerpoint"},
            {".pptx", "application/vnd.openxmlformats-officedocument.presentationml.presentation"},
            {".prop", "text/plain"},
            {".rc", "text/plain"},
            {".rmvb", "audio/x-pn-realaudio"},
            {".rtf", "application/rtf"},
            {".sh", "text/plain"},
            {".tar", "application/x-tar"},
            {".tgz", "application/x-compressed"},
            {".txt", "text/plain"},
            {".wav", "audio/x-wav"},
            {".wma", "audio/x-ms-wma"},
            {".wmv", "audio/x-ms-wmv"},
            {".wps", "application/vnd.ms-works"},
            {".xml", "text/plain"},
            {".z", "application/x-compress"},
            {".zip", "application/x-zip-compressed"},
            {"", "*/*"}
    };
}
