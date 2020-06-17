package com.example.sqlitecrud;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class EmployeeActivity extends AppCompatActivity {

    DataBaseManager EmpDb;
    List<Employee> employeeList;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee);
        EmpDb = new DataBaseManager(this);

        employeeList = new ArrayList<>();
        listView = (ListView)findViewById(R.id.emplyeeListView);
        ViewEmplyees();
    }

    private void ViewEmplyees(){
//        String sql = "Select * from employee";

        Cursor cursor = EmpDb.GetAllEmployees();
        if (cursor.moveToFirst()){
            do {
                employeeList.add(new Employee(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getDouble(4)
                        ));
            }while (cursor.moveToNext());
            EmpAdapter empAdapter = new EmpAdapter(this,R.layout.empl_ayout_list,employeeList,EmpDb);
            listView.setAdapter(empAdapter);
        }

    }
}
