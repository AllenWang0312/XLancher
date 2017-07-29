package edu.tjrac.swant.baselibrary.common.util;

import android.os.Environment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by wpc on 2017/4/14.
 */

public class FileUtils {
    //包名 路径下文件会在 app卸载的时候删除  并且外界不可访问
    public static final String packagename = "color.measurement.com.from_cp20";
    public static final String APP_DATA_PATH = "/data/data/" + packagename;
    public static final String APP_INS_CACHE_PATH = APP_DATA_PATH + "/Ins";
    public static final String SDCARD_PATH = Environment.getExternalStorageDirectory().getPath();

    public static final String file_dir = SDCARD_PATH + "/cpkj";
    public static final String pdf_dir = file_dir + "/pdf";
    public static final String excel_dir = file_dir + "/excel";
    public static final String img_dir = file_dir + "/img";
//    public static final String IMG = SD_xj + "/img";
//    public static final String Excel = SD_xj + "/excel";
//
//    public static final String IMG_NATIVE_DATA = SD_xj + "/imgCache";
//    public static final String IMG_BAIBAN_NAME = "baiban";


    public static boolean writeByteArrayToFile(byte[] bytes, String dirPath, String filename) {
        createOrExistsDir(dirPath);
        createFileIfNotExist(dirPath, filename);

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(new File(dirPath, filename));
            fos.write(bytes);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                fos.close();
                return true;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
    }

    public static boolean createOrExistsDir(String dirPath) {
        return createOrExistsDir(getFileByPath(dirPath));
    }

    public static File getFileByPath(String filePath) {
        return StringUtils.isSpace(filePath) ? null : new File(filePath);
    }

    public static boolean createOrExistsDir(File file) {
        // 如果存在，是目录则返回true，是文件则返回false，不存在则返回是否创建成功
        return file != null && (file.exists() ? file.isDirectory() : file.mkdirs());
    }

    private static void createFileIfNotExist(String dirPath, String filename) {
        File file = new File(dirPath, filename);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean reCreateIfExist(String dirPath, String name) {
        createOrExistsDir(dirPath);
        File f = new File(dirPath, name);
        if (!f.exists()) {
            try {
                return f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            f.delete();
            try {
                return f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static boolean createIfNotExist(String dirPath, String name) {
        createOrExistsDir(dirPath);
        File f = new File(dirPath, name);
        if (!f.exists()) {
            try {
                return f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static void deleteFiles(String path, ArrayList<Integer> select) {
        File dir = new File(path);
        String[] files = dir.list();
        for (Integer index : select) {
            deleteFile(new File(path, files[index]));

        }
    }

    public static void deleteFile(File file) {
        if (file.exists()) {//判断文件是否存在
            if (file.isFile()) {//判断是否是文件
                file.delete();//删除文件
            } else if (file.isDirectory()) {//否则如果它是一个目录
                File[] files = file.listFiles();//声明目录下所有的文件 files[];
                for (int i = 0; i < files.length; i++) {//遍历目录下所有的文件
                    deleteFile(files[i]);//把每个文件用这个方法进行迭代
                }
                file.delete();//删除文件夹
            }
        } else {
        }
    }

    public static String getAbsPath(ArrayList<String> rote) {
        String path = "";
        for (String str : rote) {
            path += "/" + str;
        }
        return path;
    }
}
