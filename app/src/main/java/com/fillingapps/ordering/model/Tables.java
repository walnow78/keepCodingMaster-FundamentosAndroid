package com.fillingapps.ordering.model;

import android.content.Context;
import android.content.Intent;

import java.lang.ref.WeakReference;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Tables {

    public static final String TABLE_LIST_CHANGED_ACTION = "com.fillingapps.ordering.model.Tables.TABLE_LIST_CHANGED_ACTION";

    private static Tables ourInstance;
    private WeakReference<Context> mContext;

    private List<Table> mTables;

    public static Tables getInstance(Context context) {
        if (ourInstance == null || ourInstance.mContext.get() == null) {
            if (ourInstance == null) {
                // 2) No tenemos la instancia, vamos a crearla a partir de las preferencias
                ourInstance = new Tables(context);
            } else if (ourInstance.mContext.get() == null) {
                // 3) He perdido la referencia al contexto, no puedo seguir usando el objeto
                // sin ella, así que la creo de nuevo
                ourInstance.mContext = new WeakReference<>(context);
            }
        }
        return ourInstance;
    }

    public Tables(Context context) {
        mContext = new WeakReference<>(context);
        mTables = new LinkedList<>();
        for (int i = 1; i < 15; i++) {
            mTables.add(new Table(i));
        }
        Collections.sort(mTables);
    }

    public List<Table> getTables() {
        return mTables;
    }

    public void cleanAllTables() {
        for (int i = 0; i < mTables.size(); i++) {
            Table table = mTables.get(i);
            table.cleanTable();
            mTables.set(i, table);
        }
        sendDataSetChangedIntent();
    }

    public void cleanTable(int tableNumber) {
        for (int i = 0; i < mTables.size(); i++) {
            Table table = mTables.get(i);
            if (table.getTableNumber() == tableNumber) {
                table.cleanTable();
                mTables.set(i, table);
                sendDataSetChangedIntent();
            }
        }
    }

    public void deleteTable(int tableNumber) {
        for (int i = 0; i < mTables.size(); i++) {
            Table table = mTables.get(i);
            if (table.getTableNumber() == tableNumber) {
                mTables.remove(i);
                sendDataSetChangedIntent();
            }
        }
    }

    public void setNumberOfFellows(int numberOfFellows, int tableNumber) {
        for (int i = 0; i < mTables.size(); i++) {
            Table table = mTables.get(i);
            if (table.getTableNumber() == tableNumber) {
                table.setNumberOfFellows(numberOfFellows);
                sendDataSetChangedIntent();
            }
        }
    }

    protected void sendDataSetChangedIntent() {
        if (mContext.get() != null) {
            Intent broadcast = new Intent(TABLE_LIST_CHANGED_ACTION);
            mContext.get().sendBroadcast(broadcast);
        }
    }

    public int getNumberOfFellowsForTable(int tableNumber) {
        int numberOfFellows = 0;
        for (int i = 0; i < mTables.size(); i++) {
            Table table = mTables.get(i);
            if (table.getTableNumber() == tableNumber) {
                numberOfFellows = table.getNumberOfFellows();
                break;
            }
        }
        return numberOfFellows;
    }

    public Table getTable(int tableNumber) {
        Table desiredTable = null;
        for (int i = 0; i < mTables.size(); i++) {
            Table table = mTables.get(i);
            if (table.getTableNumber() == tableNumber) {
                desiredTable = table;
                break;
            }
        }
        return desiredTable;
    }

    public void updateTable(Table table){
        for (int i = 0; i < mTables.size(); i++) {
            if (mTables.get(i).isTheSame(table)) {
                mTables.set(i, table);
                sendDataSetChangedIntent();
                break;
            }
        }
    }

    public void addPlate(Plate plate, int tableNumber){
        for (int i = 0; i < mTables.size(); i++) {
            Table table = mTables.get(i);
            if (table.getTableNumber() == tableNumber) {
                table.addPlate(plate);
                sendDataSetChangedIntent();
                break;
            }
        }
    }

    public void addPlate(Plate plate, String notes, int tableNumber){
        for (int i = 0; i < mTables.size(); i++) {
            Table table = mTables.get(i);
            if (table.getTableNumber() == tableNumber) {
                table.addPlate(plate, notes);
                sendDataSetChangedIntent();
                break;
            }
        }
    }

    public void removePlate(Plate plate, int tableNumber){
        for (int i = 0; i < mTables.size(); i++) {
            Table table = mTables.get(i);
            if (table.getTableNumber() == tableNumber) {
                table.removePlate(plate);
                sendDataSetChangedIntent();
                break;
            }
        }
    }

    public void updatePlate(Plate plate, int tableNumber){
        for (int i = 0; i < mTables.size(); i++) {
            Table table = mTables.get(i);
            if (table.getTableNumber() == tableNumber) {
                table.updatePlate(plate);
                sendDataSetChangedIntent();
                break;
            }
        }
    }
}
