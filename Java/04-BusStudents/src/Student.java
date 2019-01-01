import java.io.*;

public class Student implements Serializable{

    private String sName;
    private String sSurname;
    private int sBusid;
    private boolean sASA;

    public Student() {
        //parameterless constructor
        //main default constructor in every class
        sName = "";
        sSurname = "";
        sBusid = 0 ;
        sASA = false;
    }

    public Student(String nm, String sn, int sbid, boolean sas) {
        sName = nm;
        sSurname = sn;
        sBusid = sbid;
        sASA = sas;

    }


    public static void main(String [] args){
        Student myObject = new Student("nm","sd", 43,true);
         String serializedObject = "";
          // serialize the object
         try {
             ByteArrayOutputStream bo = new ByteArrayOutputStream();
             ObjectOutputStream so = new ObjectOutputStream(bo);
             so.writeObject(myObject);
             so.flush();
             serializedObject = bo.toString();
         } catch (Exception e) {
             System.out.println(e);
         }

         // deserialize the object
         try {
             byte b[] = serializedObject.getBytes(); 
             ByteArrayInputStream bi = new ByteArrayInputStream(b);
             ObjectInputStream si = new ObjectInputStream(bi);
             Student obj = (Student) si.readObject();
             System.out.print(obj.ToString());  
         } catch (Exception e) {
             System.out.println(e);
         }     


    }

    //getters
    public String GetName()
    {
        return sName;
    }

    public String GetSurname()
    {
        return sSurname;
    }

    public int GetBusId()
    {
        return sBusid;
    }

    public boolean GetASA()
    {
        return sASA;
    }
    //setters
    public void SetName(String nm) {
        sName = nm;
    }

    public void SetSurname(String sn) {
        sSurname = sn;
    }

    public void SetBusId(int sbid) {
        sBusid = sbid;
    }

    public void SetASA(boolean sas) {
        sASA = sas;
    }

    public String ToString()
    {
        return "\nName: \t" + this.GetName() + "\nSurname " +
        this.GetSurname() + "\nBus number \t" + this.GetBusId() +
"\nAfter School " + this.GetASA();
    }


}
