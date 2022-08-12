package com.mad.simpletodolist;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    Button button;
    EditText activityText;
    EditText dateText;
    ListView todolist;
    ArrayList<String> itemlist = new ArrayList<>();
    ArrayAdapter<String> adapterList;
    ListView dateView;
    ArrayList<String> datelist = new ArrayList<>();
    ArrayAdapter<String> adapterDate;
    Calendar c = Calendar.getInstance();
    String dueDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.button);
        activityText = findViewById(R.id.activityText);
        todolist = findViewById(R.id.todolist);
        itemlist = filehandlertodo.read(this);
        adapterList = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, itemlist);
        todolist.setAdapter(adapterList);
        dateView = findViewById(R.id.datelistview);
        datelist = filehandlerdate.read(this);
        adapterDate = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, datelist);
        dateView.setAdapter(adapterDate);
        dateText = findViewById(R.id.editTextDate);
        dateText.setText("");

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newActivity = activityText.getText().toString();

                if (!dueDate.equals("") && !activityText.equals("")) {
                    itemlist.add(newActivity);
                    datelist.add(dueDate);
                    activityText.setText("");
                    dateText.setText("");
                    adapterList.notifyDataSetChanged();
                    adapterDate.notifyDataSetChanged();
                    filehandlertodo.write(itemlist, getApplicationContext());
                    filehandlerdate.write(datelist, getApplicationContext());
                }
                else if (!dueDate.equals("")){
                    Toast.makeText(MainActivity.this, "Please Enter a Due Date", Toast.LENGTH_LONG).show();
                }
                else Toast.makeText(MainActivity.this, "Please Enter an Activiy", Toast.LENGTH_LONG).show();
            }
        });

        todolist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder deleter = new AlertDialog.Builder(MainActivity.this);
                deleter.setTitle("Delete Item");
                deleter.setMessage("Do you want to delete this item?");
                deleter.setCancelable(false);
                deleter.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                deleter.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        itemlist.remove(position);
                        datelist.remove(position);
                        adapterDate.notifyDataSetChanged();
                        adapterList.notifyDataSetChanged();
                        filehandlertodo.write(itemlist,getApplicationContext());
                        filehandlerdate.write(datelist,getApplicationContext());
                    }
                });
                AlertDialog alertDialog = deleter.create();
                alertDialog.show();
            }
        });


        dateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateSelect();
            }
        });

        dateView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                dateChange(position);
            }
        });
    }

    public void dateSelect(){
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(dateText.getContext(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        dueDate  = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                        dateText.setText(dueDate);
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    public void dateChange(int position){
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(dateText.getContext(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        dueDate  = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                        datelist.remove(position);
                        datelist.add(position, dueDate);
                        adapterDate.notifyDataSetChanged();
                        filehandlerdate.write(datelist,getApplicationContext());
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }
 }