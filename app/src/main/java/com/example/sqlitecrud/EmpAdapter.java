package com.example.sqlitecrud;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class EmpAdapter extends ArrayAdapter <Employee> {

    private Context context;
    private int Layout;
    private List<Employee> employeeList;
    DataBaseManager EmpDb;

    public EmpAdapter(Context context , int Layout , List<Employee> emplyeeList,DataBaseManager empDb){
        super(context,Layout,emplyeeList);

        this.context = context;
        this.Layout  = Layout;
        this.employeeList = emplyeeList;
        this.EmpDb = empDb;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(Layout,null);

        TextView textViewName = view.findViewById(R.id.textViewName);
        TextView textViewDept = view.findViewById(R.id.textViewDepartment);
        TextView textViewSalary = view.findViewById(R.id.textViewSalary);
        TextView textViewJoiningDate = view.findViewById(R.id.textViewJoiningDate);

        final Employee employee = employeeList.get(position);
        //adding data to views

        textViewName.setText(employee.getName());
        textViewDept.setText(employee.getDept());
        textViewSalary.setText(String.valueOf(employee.getSalary()));
        textViewJoiningDate.setText(employee.getJoinedDate());

        view.findViewById(R.id.buttonEditEmployee).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateEmployee(employee);
            }
        });

        view.findViewById(R.id.buttonDeleteEmployee).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               DeleteEmployee(employee);
            }
        });
        return view;
    }

    private void DeleteEmployee(final Employee employee) {
        AlertDialog.Builder  builder = new AlertDialog.Builder(context);
        builder.setTitle("Are You sure to delete this Employee?");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                /*String sql = "DELETE FROM employee where id = ?";
                EmpDb.execSQL(sql,new Integer[]{employee.getEmpId()});*/
                EmpDb.DeleteEmployee(employee.getEmpId());
                RefreshEmplyees();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private  void UpdateEmployee(final Employee employee){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.update_layout,null);
        builder.setView(view);
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();

       final EditText editName   = view.findViewById(R.id.EditEmpName);
       final EditText editSalary = view.findViewById(R.id.EditSalary);
       final Spinner spinDepart  = view.findViewById(R.id.SelDepartment);

        editName.setText(employee.getName());
        editSalary.setText(String.valueOf(employee.getSalary()));

        view.findViewById(R.id.UpdateButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editName.getText().toString().trim();
                String salary = editSalary.getText().toString().trim();
                String department = spinDepart.getSelectedItem().toString().trim();
                if (name.isEmpty()) {
                    editName.setError("Please enter a name");
                    editName.requestFocus();
                    return;
                }

                if (salary.isEmpty() || Double.parseDouble(salary) <= 0) {
                    editSalary.setError("Please enter salary");
                    editSalary.requestFocus();
                    return;
                }

               /* String sql = "UPDATE employee set name=?,salary=?,department=? WHERE id=?";
                EmpDb.execSQL(sql,new String[]{name,salary,department,String.valueOf(employee.getEmpId())});*/
                EmpDb.UpdateEmployee(name,Double.parseDouble(salary),department,employee.getEmpId());
                Toast.makeText(context,"Employee "+ name+"Updated",Toast.LENGTH_SHORT).show();

                RefreshEmplyees();
                alertDialog.dismiss();
            }
        });
    }

    private void RefreshEmplyees() {
//        String sql = "Select * from employee";
        Cursor cursor = EmpDb.GetAllEmployees();
        if (cursor.moveToFirst()){
            employeeList.clear();
            do {
                employeeList.add(new Employee(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getDouble(4)
                ));
            }while (cursor.moveToNext());
            notifyDataSetChanged();

        }

    }
}
