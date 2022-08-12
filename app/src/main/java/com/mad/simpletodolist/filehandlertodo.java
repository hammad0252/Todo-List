package com.mad.simpletodolist;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class filehandlertodo {
    public static final String FILENAME = "todolist.dat";

    public static void write(ArrayList<String> item, Context context){
        try {
            FileOutputStream fos = context.openFileOutput(FILENAME, Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(item);
            oos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<String> read(Context context){
        ArrayList<String> ilist = null;
        try {
            FileInputStream fis = context.openFileInput(FILENAME);
            ObjectInputStream ois = new ObjectInputStream(fis);
            ilist = (ArrayList<String>) ois.readObject();
        } catch (FileNotFoundException e) {
            ilist = new ArrayList<>();
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return ilist;
    }
}
