package src;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.*;

public class School {

    public School() {

    }

    Student[] studArr = new Student[150];
    int idx=0;

    public boolean AddStudent(Student st)
    {
        if(idx<150)
        {
            studArr[idx] = st;
            idx++;
            return true;
        }
        else
        {
            return false;
        }
    }

    //List student method
    public String ListStudents()
    {
        String tmp = "";
        for(int i=0; i<studArr.length; i++)
        {
            Student tmpStud = studArr[i];
            if(tmpStud != null)
            {
                tmp += tmpStud.ToString();
            }
        }
        return tmp;
    }

    //Search student method
    public String SearchStudent(int id)
    {
        for(int i=0;i<studArr.length; i++)
        {
            Student tmpStud = studArr[i];
            if(tmpStud != null)
            {
                if(tmpStud.GetBusId() == id)
                {
                    return tmpStud.ToString();
                }
            }
        }
        return "Student not found on this bus";
    }

    //Delete student method
    public String DeleteStudent(int id)
    {
        for (int i=0;i<studArr.length;i++)
        {
            Student tmpStud = studArr[i];
            if(tmpStud != null)
            {
                if(tmpStud.GetBusId() == id)
                {
                    studArr[i] = null;
                    return "Student successfully deleted from bus";
                }
            }
        }
        return "Student not found on this bus";
    }

    //Add student to assigned bus
    public String StudentToBus()
    {
        Student stud = new Student();
        for (int i=0;i<studArr.length;i++)
        {
            if(stud.GetBusId()==1) {
                System.out.print(stud.GetName());
            }
        }
        return "Bus number does not exist";
    }

}
