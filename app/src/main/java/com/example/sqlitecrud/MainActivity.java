package com.example.sqlitecrud;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
  //  public static final String DATABASE_NAME = "EmployeeDB";
    Button ViewEmployees;
    EditText TextName, TextSalary;
    Spinner DepartmentSel;

    DataBaseManager EmpDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewEmployees = (Button) findViewById(R.id.ViewButton);
        TextName = (EditText) findViewById(R.id.EditEmpName);
        TextSalary = (EditText) findViewById(R.id.EditSalary);
        DepartmentSel = (Spinner) findViewById(R.id.SelDepartment);

        findViewById(R.id.AddButton).setOnClickListener(this);
        ViewEmployees.setOnClickListener(this);

        //creating the database
        EmpDb = new DataBaseManager(this);
        //Create the table
//       CreateTable();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.AddButton:

                addEmployee();

                break;
            case R.id.ViewButton:

                startActivity(new Intent(this, EmployeeActivity.class));

                break;
        }
    }

    //In this method we will do the create operation
    private void addEmployee() {
        String Name = TextName.getText().toString().trim();
        String Department = DepartmentSel.getSelectedItem().toString();
        String Salary = TextSalary.getText().toString().trim();

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat DateTime = new SimpleDateFormat("yyyy:mm:dd hh:mm:ss", Locale.US);
        String joinDate = DateTime.format(calendar.getTime());

        if (Name.isEmpty()) {
            TextName.setError("Please enter a name");
            TextName.requestFocus();
            return;
        }

        if (Salary.isEmpty() || Double.parseDouble(Salary) <= 0) {
            TextSalary.setError("Please enter salary");
            TextSalary.requestFocus();
            return;
        }

        /*String sql = "INSERT INTO employee (name,department,joiningdate,salary) VALUES (?,?,?,?)";
        EmpDb.execSQL(sql,new String[]{Name,Department,joinDate,Salary});*/
        if(EmpDb.AddEmployee(Name,Department,Double.parseDouble(Salary),joinDate))
        Toast.makeText(this,"Employee "+Name+" Added",Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this,"Employee "+Name+" Insert Failed!",Toast.LENGTH_SHORT).show();
    }
    /*private void CreateTable(){
        String sql = "CREATE TABLE IF NOT EXISTS employee (\n" +
                "    id INTEGER NOT NULL CONSTRAINT employees_pk PRIMARY KEY AUTOINCREMENT,\n" +
                "    name varchar(200) NOT NULL,\n" +
                "    department varchar(200) NOT NULL,\n" +
                "    joiningdate datetime NOT NULL,\n" +
                "    salary double NOT NULL\n" +
                ");";
        EmpDb.execSQL(sql);
    }*/
}
