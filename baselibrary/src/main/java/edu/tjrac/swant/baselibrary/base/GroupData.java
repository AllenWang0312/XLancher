package edu.tjrac.swant.baselibrary.base;

import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Observable;

import edu.tjrac.swant.baselibrary.base.interfaze.CompareableData;

/**
 * 修改慎重
 * Created by wpc on 2017/3/31.
 * 初始化流程  setStand  stand    test=null
 *             addTest
 */

public class GroupData<T extends CompareableData> extends Observable {

    T stand;
    ArrayList<T> tests;
    Integer selectIndex=0, lastSelect=0;

    public GroupData() {
        tests=new ArrayList<>();
        hasChange();
    }

    public void removeData() {
        stand = null;
        tests = new ArrayList<>();
        selectIndex=0;
        lastSelect=0;
        hasChange();
    }

    public T getStand() {

        return stand;
    }

    public void setStand(T stand) {
        selectIndex=0;
        lastSelect=0;
        this.stand = stand;
        stand.setStandard(true);
        tests=new ArrayList<>();
        hasChange();
    }

    synchronized public ArrayList<T> getTests() {
        return tests;
    }
    public void addTest(T test) {
        if(tests==null){
            tests=new ArrayList<>();
        }
        tests.add(0, test);
        hasChange();
    }


    public void save(SQLiteDatabase db, String tableName) {
        db.insert(tableName, null, stand.getContentValue());
        for (T lc : tests) {
            db.insert(tableName, null, lc.getContentValue());
        }
    }
    public void saveStand(SQLiteDatabase db, String tableName) {
        db.insert(tableName, null, stand.getContentValue());
        stand.setHasSaved(true);
    }

    public void saveSimple(SQLiteDatabase db, String tableName, int index) {
        if (!tests.get(index).isHasSaved()) {
            db.insert(tableName, null, tests.get(index).getContentValue());
            tests.get(index).setHasSaved(true);
        }
    }

    public boolean hasSimple() {
        if (tests != null && tests.size() > 0) {
            return true;
        }
        return false;
    }

    public void hasChange() {
        notifyObservers();
        setChanged();
    }

    public Integer getSelectIndex() {
        return selectIndex;
    }

    public Integer getLastSelect() {
        return lastSelect;
    }

    public void setSelectIndex(Integer selectIndex) {
        this.lastSelect = this.selectIndex;
        this.selectIndex = selectIndex;
    }

    public Collection<? extends CompareableData> toList() {
        ArrayList<CompareableData> list=new ArrayList<>();
        list.add(stand);
        list.addAll(tests);
        return list;
    }
}
