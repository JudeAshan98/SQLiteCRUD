package com.example.sqlitecrud;

public class Employee {
    int EmpId;
    String Name ,  Dept , JoinedDate;
    double Salary;


    public Employee(int empId, String name, String dept, String joinedDate, double salary) {
        EmpId = empId;
        Name = name;
        Dept = dept;
        JoinedDate = joinedDate;
        Salary = salary;
    }

    public int getEmpId() {
        return EmpId;
    }

    public String getName() {
        return Name;
    }

    public String getDept() {
        return Dept;
    }

    public String getJoinedDate() {
        return JoinedDate;
    }

    public double getSalary() {
        return Salary;
    }
}
