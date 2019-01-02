
import java.io.*; 
import java.awt.Color;
import javax.swing.JOptionPane;
import java.awt.Dimension;
import java.awt.font.*;
import java.awt.Graphics;
import java.awt.Label;
import java.awt.event.ActionListener;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.event.ChangeListener;
import javax.swing.JFrame;


public class GUI extends JFrame{

private static final long serialVersionUID = 1L; 
  
	
    public static boolean validateInput(String data){
        if(data.equals("Y") || data.equals("y") || data.equals("n") || data.equals("N")){
            return true;
        }else{
            return false;
        }
    }

    public static int searchStudent(String name,String surName){
        
        return -1;
    }

    public static void validateAndProcess(String name, String surName, int busID, String sasa,String path){

        String temp = sasa.trim();
        if(validateInput(temp)){

                boolean saas;
                if(temp.equals("y") || temp.equals("Y")){
                    saas = true;
                }else{
                    saas = false;
                }
                Student std = new Student(name,surName,busID,saas);

            addToFile(path,std);
            
        }else{
            JOptionPane.showMessageDialog(null, "input Y or N for the Attendet after school", "InfoBox: " + "Invalid Input", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public static void addToFile(String path,Student std){
        String serializedObject = "";
          // serialize the object
         try {
            //creating the serialized object
             ByteArrayOutputStream bo = new ByteArrayOutputStream();
             ObjectOutputStream so = new ObjectOutputStream(bo);
             so.writeObject(std);
             so.flush();
             serializedObject = bo.toString();

             //adding to the database (writing to the file)
             BufferedWriter out = new BufferedWriter( 
                   new FileWriter(path, true)); 
            out.write(serializedObject + "\n"); 
            out.close(); 

         } catch (Exception e) {
             System.out.println(e);
         }

    }

    public static void removeAndSave(String path,JTextArea textArea){

        //deleting all the previous content of the file
        PrintWriter writer;
        try {
            writer = new PrintWriter(path);
            writer.print("");
            writer.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        


        String text = textArea.getText();
        String adjusted = text.replaceAll("(?m)^[ \t]*\r?\n", "");

        for(String line : adjusted.split("\\n")){
            String objarray[] = line.split(",");
            int busID = Character.getNumericValue(objarray[2].charAt(2));;
            boolean saas = false;
            if(objarray[3].equals("Y") || objarray[3].equals("y")){
                saas = true;
            }else{
                saas = false;
            }
            Student std = new Student(objarray[0].trim().replaceAll("\\s",""),objarray[1].trim().replaceAll("\\s",""),busID,saas);
            addToFile(path, std);
        }

    }

    public static String getFileData(String path){

            StringBuilder sb = new StringBuilder();

            try { 
                BufferedReader in = new BufferedReader( 
                            new FileReader(path)); 
      
                String mystring; 
                while ((mystring = in.readLine()) != null) { 
                    

                     // deserialize the object
                     try {
                         byte b[] = mystring.getBytes(); 
                         ByteArrayInputStream bi = new ByteArrayInputStream(b);
                         ObjectInputStream si = new ObjectInputStream(bi);
                         Student obj = (Student) si.readObject();
                         //System.out.print(obj.ToString()); 
                         String temp;
                         if(obj.GetASA()){
                            temp = "Y";
                         }else{
                            temp = "N";
                         }
                         sb.append(obj.GetName()+", "+obj.GetSurname()+", B"+obj.GetBusId()+
                            ", "+temp + "\n"); 
                     } catch (Exception e) {
                         System.out.println(e);
                     }  
                } 
            } 
            catch (IOException e) { 
                System.out.println("Exception Occurred" + e); 
            }
            return sb.toString();
    }

    //main method
    public static void main (String[] args) {

        JFrame window = new JFrame("School Bus Manager");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(1000, 750); //sets window dimensions
        window.setLocationRelativeTo(null); //centers the window
        window.setBackground(Color.LIGHT_GRAY);
        window.setVisible(true); //makes window show
        window.getContentPane().setLayout(null);

        JPanel panel = new JPanel();
        panel.setLayout(null); //allow freedom to place anything anywhere
        window.add(panel); //adds the panel to the jframe

        //Button that opens new JDialog to add, delete or search a student
        //DELETE STUDENT BUTTON

        JButton delstudent = new JButton();
        panel.add(delstudent);
        delstudent.setBounds(750, 160, 150, 50);
        delstudent.setText("Delete Student");
        delstudent.setToolTipText("Delete Student from a Bus");
        delstudent.setForeground(Color.WHITE);
        delstudent.setBackground(Color.BLUE);
        window.add(delstudent);

        delstudent.addActionListener(new ActionListener() {

                public void actionPerformed(java.awt.event.ActionEvent e)
                {
                    JPanel sPanel = new JPanel();
                    window.add(sPanel);
                    sPanel.add(new JLabel("addS"));
                    sPanel.setLayout(null);

                    JDialog sWindow = new JDialog(window, "Delete Student");
                    sWindow.setDefaultCloseOperation(sWindow.HIDE_ON_CLOSE);
                    sWindow.setSize(500,500);
                    sWindow.setLocationRelativeTo(null);
                    sWindow.setBackground(Color.LIGHT_GRAY);
                    sWindow.add(sPanel);
                    sWindow.setVisible(true);
                    sWindow.getContentPane().setLayout(null);
                    
                    JLabel delstud = new JLabel();
                    delstud.setBounds(15, 10, 700, 300);
                    delstud.setText("To delete a student, simply click on one of the bus buttons and manually remove");
                    sWindow.add(delstud);
                    sPanel.add(delstud);
                    JLabel delstud2 = new JLabel();
                    delstud2.setBounds(170, 10, 700, 330);
                    delstud2.setText("the student from the list");
                    sWindow.add(delstud2);
                    sPanel.add(delstud2);
                }
            });

        //SEARCH STUDENT BUTTON

        JButton sercstudent = new JButton();
        panel.add(sercstudent);
        sercstudent.setBounds(750, 245, 150, 50);
        sercstudent.setText("Search Student");
        sercstudent.setToolTipText("Search Student's Bus ID");
        sercstudent.setForeground(Color.WHITE);
        sercstudent.setBackground(Color.BLUE);
        window.add(sercstudent);

        sercstudent.addActionListener(new ActionListener() {

                public void actionPerformed(java.awt.event.ActionEvent e)
                {
                    JPanel sPanel = new JPanel();
                    window.add(sPanel);
                    sPanel.add(new JLabel("addS"));
                    sPanel.setLayout(null);

                    JDialog sWindow = new JDialog(window, "Search Student's BUS ID");
                    sWindow.setDefaultCloseOperation(sWindow.HIDE_ON_CLOSE);
                    sWindow.setSize(500,500);
                    sWindow.setLocationRelativeTo(null);
                    sWindow.setBackground(Color.LIGHT_GRAY);
                    sWindow.add(sPanel);
                    sWindow.setVisible(true);
                    sWindow.getContentPane().setLayout(null);

                    JLabel topLable = new JLabel();
                    topLable.setBounds(30, 10, 400, 60);
                    topLable.setText("Enter Student Details and click search");
                    sWindow.add(topLable);
                    sPanel.add(topLable);

                    JLabel b1labelS = new JLabel();
                    b1labelS.setBounds(30, 60, 150, 60);
                    b1labelS.setText("Student name:");
                    sWindow.add(b1labelS);
                    sPanel.add(b1labelS);
                    JTextField b1labelStext = new JTextField();
                    b1labelStext.setBounds(190,80,120,20);
                    b1labelStext.setEditable(true);
                    sWindow.add(b1labelStext);
                    sPanel.add(b1labelStext);

                    JLabel b1labelS2 = new JLabel();
                    b1labelS2.setBounds(30, 120, 150, 60);
                    b1labelS2.setText("Student surname:");
                    sWindow.add(b1labelS2);
                    sPanel.add(b1labelS2);
                    JTextField b1labelS2text = new JTextField();
                    b1labelS2text.setBounds(190,140,120,20);
                    b1labelS2text.setEditable(true);
                    sWindow.add(b1labelS2text);
                    sPanel.add(b1labelS2text);

                    //save button
                    JButton searchBtn = new JButton();
                    searchBtn.setBounds(30,200,230,30);
                    searchBtn.setText("Search");
                    searchBtn.setVisible(true);

                    window.add(searchBtn);
                    sWindow.add(searchBtn);
                    sPanel.add(searchBtn);

                    JLabel bottomLable = new JLabel();
                    bottomLable.setBounds(30, 240, 400, 60);
                    bottomLable.setText("Student is in the bus :");
                    sWindow.add(bottomLable);
                    sPanel.add(bottomLable);

                    searchBtn.addActionListener(new ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent d)
                        {
                            
                        };
                    });

                }
            });
        //WEEKLY NOTICES

        JButton weeknotice = new JButton();
        panel.add(weeknotice);
        weeknotice.setBounds(750, 325, 150, 50);
        weeknotice.setText("Weely Notices");
        weeknotice.setToolTipText("View/Edit Weekly Notices");
        weeknotice.setForeground(Color.WHITE);
        weeknotice.setBackground(Color.BLUE);
        window.add(weeknotice);

        weeknotice.addActionListener(new ActionListener() {

                public void actionPerformed(java.awt.event.ActionEvent e)
                {
                    JPanel sPanel = new JPanel();
                    window.add(sPanel);
                    sPanel.add(new JLabel("addS"));
                    sPanel.setLayout(null);

                    JDialog sWindow = new JDialog(window, "Weekly Notices");
                    sWindow.setDefaultCloseOperation(sWindow.HIDE_ON_CLOSE);
                    sWindow.setSize(500,500);
                    sWindow.setLocationRelativeTo(null);
                    sWindow.setBackground(Color.LIGHT_GRAY);
                    sWindow.add(sPanel);
                    sWindow.setVisible(true);
                    sWindow.getContentPane().setLayout(null);

                }
            });

        //BUTTON 1 CODE

        Color btnSecond = Color.RED;

        JButton b1 = new JButton();
        panel.add(b1);
        b1.setBounds(90, 75, 110, 200);
        b1.setText("P1");
        b1.setToolTipText("Click to approve bus arrival");
        b1.setForeground(Color.WHITE);
        b1.setBackground(Color.GREEN);
        window.add(b1);

        String[] busArr = new String[8];
        for(int i =0; i<8; i++) {
            busArr[i]= "";
        }

        //changes the color when the button is pressed
        b1.addActionListener(new ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent d)
                {
                    if(b1.getBackground().equals(Color.GREEN)) {
                        b1.setBackground(btnSecond);
                    }
                    else
                        b1.setBackground(Color.GREEN);

                    JPanel sPanel = new JPanel();
                    window.add(sPanel);
                    sPanel.add(new JLabel("addS"));
                    sPanel.setLayout(null);

                    JDialog sWindow = new JDialog(window, "Bus 1");
                    sWindow.setDefaultCloseOperation(sWindow.HIDE_ON_CLOSE);
                    sWindow.setSize(500,500);
                    sWindow.setLocationRelativeTo(null);
                    sWindow.setBackground(Color.LIGHT_GRAY);
                    sWindow.add(sPanel);
                    sWindow.setVisible(true);
                    sWindow.getContentPane().setLayout(null);

                    JLabel b1labelS = new JLabel();
                    b1labelS.setBounds(30, 10, 150, 60);
                    b1labelS.setText("Student name:");
                    sWindow.add(b1labelS);
                    sPanel.add(b1labelS);
                    JTextField b1labelStext = new JTextField();
                    b1labelStext.setBounds(190,30,60,20);
                    b1labelStext.setEditable(true);
                    sWindow.add(b1labelStext);
                    sPanel.add(b1labelStext);

                    JLabel b1labelS2 = new JLabel();
                    b1labelS2.setBounds(30, 70, 150, 60);
                    b1labelS2.setText("Student surname:");
                    sWindow.add(b1labelS2);
                    sPanel.add(b1labelS2);
                    JTextField b1labelS2text = new JTextField();
                    b1labelS2text.setBounds(190,90,60,20);
                    b1labelS2text.setEditable(true);
                    sWindow.add(b1labelS2text);
                    sPanel.add(b1labelS2text);

                    JLabel b1labelS3 = new JLabel();
                    b1labelS3.setBounds(30, 130, 150, 60);
                    b1labelS3.setText("Bus ID:");
                    sWindow.add(b1labelS3);
                    sPanel.add(b1labelS3);
                    JLabel b1labelS3text = new JLabel();
                    b1labelS3text.setBounds(190,150,60,20);
                    b1labelS3text.setText("Bus 1");
                    sWindow.add(b1labelS3text);
                    sPanel.add(b1labelS3text);

                    JLabel b1labelS4 = new JLabel();
                    b1labelS4.setBounds(30, 190, 170, 60);
                    b1labelS4.setText("Ater school activity (Y/N):");
                    sWindow.add(b1labelS4);
                    sPanel.add(b1labelS4);
                    JTextField b1labelS4text = new JTextField();
                    b1labelS4text.setBounds(190,210,60,20);
                    b1labelS4text.setEditable(true);
                    sWindow.add(b1labelS4text);
                    sPanel.add(b1labelS4text);

                    JButton dataa = new JButton();
                    dataa.setBounds(20,330,230,30);
                    dataa.setText("Add Student");
                    dataa.setVisible(true);

                    window.add(dataa);
                    sWindow.add(dataa);
                    sPanel.add(dataa);

                    //save button
                    JButton saveBtn = new JButton();
                    saveBtn.setBounds(20,365,230,30);
                    saveBtn.setText("Save");
                    saveBtn.setVisible(true);

                    window.add(saveBtn);
                    sWindow.add(saveBtn);
                    sPanel.add(saveBtn);
                    

                    JTextArea show = new JTextArea();
                    show.setBounds(260,10,210,500);
                    show.setEditable(true);


                    show.setText(getFileData("appData/bus1.txt"));


                    sWindow.add(show);
                    sPanel.add(show);

                    dataa.addActionListener(new ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent d)
                        {
                            validateAndProcess(b1labelStext.getText(),b1labelS2text.getText(),1, 
                            b1labelS4text.getText(),"appData/bus1.txt");
                            show.setText(getFileData("appData/bus1.txt"));
                        };
                    });

                    saveBtn.addActionListener(new ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent d)
                        {
                            removeAndSave("appData/bus1.txt",show);
                            show.setText(getFileData("appData/bus1.txt"));
                        };
                    });

                        
                }});

        //BUTTON 2 CODE




        JButton b2 = new JButton();
        panel.add(b2);
        b2.setBounds(230, 75, 110, 200);
        b2.setText("P2");
        b2.setToolTipText("Click to approve bus arrival");
        b2.setForeground(Color.WHITE);
        b2.setBackground(Color.GREEN);
        window.add(b2);

        String[] busArr1 = new String[8];
        for(int i =0; i<8; i++) {
            busArr1[i]= "";
        }

        //changes the color when the button is pressed
        b2.addActionListener(new ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e)
                {
                    if(b2.getBackground().equals(Color.GREEN)) {
                        b2.setBackground(btnSecond);
                    }
                    else
                        b2.setBackground(Color.GREEN);

                    JPanel sPanel = new JPanel();
                    window.add(sPanel);
                    sPanel.add(new JLabel("addS"));
                    sPanel.setLayout(null);

                    JDialog sWindow = new JDialog(window, "Bus 2");
                    sWindow.setDefaultCloseOperation(sWindow.HIDE_ON_CLOSE);
                    sWindow.setSize(500,500);
                    sWindow.setLocationRelativeTo(null);
                    sWindow.setBackground(Color.LIGHT_GRAY);
                    sWindow.add(sPanel);
                    sWindow.setVisible(true);
                    sWindow.getContentPane().setLayout(null);

                    JLabel labelS = new JLabel();
                    labelS.setBounds(30, 10, 150, 60);
                    labelS.setText("Student name:");
                    sWindow.add(labelS);
                    sPanel.add(labelS);
                    JTextField labelStext = new JTextField();
                    labelStext.setBounds(190,30,60,20);
                    labelStext.setEditable(true);
                    sWindow.add(labelStext);
                    sPanel.add(labelStext);

                    JLabel labelS2 = new JLabel();
                    labelS2.setBounds(30, 70, 150, 60);
                    labelS2.setText("Student surname:");
                    sWindow.add(labelS2);
                    sPanel.add(labelS2);
                    JTextField labelS2text = new JTextField();
                    labelS2text.setBounds(190,90,60,20);
                    labelS2text.setEditable(true);
                    sWindow.add(labelS2text);
                    sPanel.add(labelS2text);

                    JLabel labelS3 = new JLabel();
                    labelS3.setBounds(30, 130, 150, 60);
                    labelS3.setText("Bus ID:");
                    sWindow.add(labelS3);
                    sPanel.add(labelS3);
                    JLabel labelS3text = new JLabel();
                    labelS3text.setBounds(190,150,60,20);
                    labelS3text.setText("Bus 2");
                    sWindow.add(labelS3text);
                    sPanel.add(labelS3text);

                    JLabel labelS4 = new JLabel();
                    labelS4.setBounds(30, 190, 170, 60);
                    labelS4.setText("Ater school activity (Y/N):");
                    sWindow.add(labelS4);
                    sPanel.add(labelS4);
                    JTextField labelS4text = new JTextField();
                    labelS4text.setBounds(190,210,60,20);
                    labelS4text.setEditable(true);
                    sWindow.add(labelS4text);
                    sPanel.add(labelS4text);

                    JButton dataa = new JButton();
                    dataa.setBounds(20,330,230,30);
                    dataa.setText("Add Student");
                    dataa.setVisible(true);
                    window.add(dataa);
                    sWindow.add(dataa);
                    sPanel.add(dataa);

                    //save button
                    JButton saveBtn = new JButton();
                    saveBtn.setBounds(20,365,230,30);
                    saveBtn.setText("Save");
                    saveBtn.setVisible(true);

                    window.add(saveBtn);
                    sWindow.add(saveBtn);
                    sPanel.add(saveBtn);

                    JTextArea show = new JTextArea();
                    show.setBounds(260,10,210,500);
                    show.setEditable(true);

                    show.setText(getFileData("appData/bus2.txt"));

                    sWindow.add(show);
                    sPanel.add(show);

                    dataa.addActionListener(new ActionListener() {
                            public void actionPerformed(java.awt.event.ActionEvent e)
                            {


                                validateAndProcess(labelStext.getText(),labelS2text.getText(),2, 
                                    labelS4text.getText(),"appData/bus2.txt");
                                show.setText(getFileData("appData/bus2.txt"));


                            };

                    });


                    saveBtn.addActionListener(new ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent d)
                        {
                            removeAndSave("appData/bus2.txt",show);
                            show.setText(getFileData("appData/bus2.txt"));
                        };
                    });
                }});

        //BUTTON 3 CODE

        JButton b3 = new JButton();
        panel.add(b3);
        b3.setBounds(370, 75, 110, 200);
        b3.setText("P3");
        b3.setToolTipText("Click to approve bus arrival");
        b3.setForeground(Color.WHITE);
        b3.setBackground(Color.GREEN);
        window.add(b3);

        String[] busArr2 = new String[8];
        for(int i =0; i<8; i++) {
            busArr2[i]= "";
        }

        //changes the color when the button is pressed
        b3.addActionListener(new ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e)
                {
                    if(b3.getBackground().equals(Color.GREEN)) {
                        b3.setBackground(btnSecond);
                    }
                    else
                        b3.setBackground(Color.GREEN);

                    JPanel sPanel = new JPanel();
                    window.add(sPanel);
                    sPanel.add(new JLabel("addS"));
                    sPanel.setLayout(null);

                    JDialog sWindow = new JDialog(window, "Bus 3");
                    sWindow.setDefaultCloseOperation(sWindow.HIDE_ON_CLOSE);
                    sWindow.setSize(500,500);
                    sWindow.setLocationRelativeTo(null);
                    sWindow.setBackground(Color.LIGHT_GRAY);
                    sWindow.add(sPanel);
                    sWindow.setVisible(true);
                    sWindow.getContentPane().setLayout(null);

                    JLabel labelS = new JLabel();
                    labelS.setBounds(30, 10, 150, 60);
                    labelS.setText("Student name:");
                    sWindow.add(labelS);
                    sPanel.add(labelS);
                    JTextField labelStext = new JTextField();
                    labelStext.setBounds(190,30,60,20);
                    labelStext.setEditable(true);
                    sWindow.add(labelStext);
                    sPanel.add(labelStext);

                    JLabel labelS2 = new JLabel();
                    labelS2.setBounds(30, 70, 150, 60);
                    labelS2.setText("Student surname:");
                    sWindow.add(labelS2);
                    sPanel.add(labelS2);
                    JTextField labelS2text = new JTextField();
                    labelS2text.setBounds(190,90,60,20);
                    labelS2text.setEditable(true);
                    sWindow.add(labelS2text);
                    sPanel.add(labelS2text);

                    JLabel labelS3 = new JLabel();
                    labelS3.setBounds(30, 130, 150, 60);
                    labelS3.setText("Bus ID:");
                    sWindow.add(labelS3);
                    sPanel.add(labelS3);
                    JLabel labelS3text = new JLabel();
                    labelS3text.setBounds(190,150,60,20);
                    labelS3text.setText("Bus 3");
                    sWindow.add(labelS3text);
                    sPanel.add(labelS3text);

                    JLabel labelS4 = new JLabel();
                    labelS4.setBounds(30, 190, 170, 60);
                    labelS4.setText("Ater school activity (Y/N):");
                    sWindow.add(labelS4);
                    sPanel.add(labelS4);
                    JTextField labelS4text = new JTextField();
                    labelS4text.setBounds(190,210,60,20);
                    labelS4text.setEditable(true);
                    sWindow.add(labelS4text);
                    sPanel.add(labelS4text);

                    JButton dataa = new JButton();
                    dataa.setBounds(20,330,230,30);
                    dataa.setText("Add Student");
                    dataa.setVisible(true);
                    window.add(dataa);
                    sWindow.add(dataa);
                    sPanel.add(dataa);

                     //save button
                     JButton saveBtn = new JButton();
                     saveBtn.setBounds(20,365,230,30);
                     saveBtn.setText("Save");
                     saveBtn.setVisible(true);
 
                     window.add(saveBtn);
                     sWindow.add(saveBtn);
                     sPanel.add(saveBtn);

                    JTextArea show = new JTextArea();
                    show.setBounds(260,10,210,500);
                    show.setEditable(true);

                    show.setText(getFileData("appData/bus3.txt"));

                    sWindow.add(show);
                    sPanel.add(show);

                    dataa.addActionListener(new ActionListener() {
                            public void actionPerformed(java.awt.event.ActionEvent e)
                            {

                                validateAndProcess(labelStext.getText(),labelS2text.getText(),3, 
                                    labelS4text.getText(),"appData/bus3.txt");
                                show.setText(getFileData("appData/bus3.txt"));


                            };

                    });

                    saveBtn.addActionListener(new ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent d)
                        {
                            removeAndSave("appData/bus3.txt",show);
                            show.setText(getFileData("appData/bus3.txt"));
                        };
                    });

                }});

        //BUTTON 4 CODE

        JButton b4 = new JButton();
        panel.add(b4);
        b4.setBounds(510, 75, 110, 200);
        b4.setText("P4");
        b4.setToolTipText("Click to approve bus arrival");
        b4.setForeground(Color.WHITE);
        b4.setBackground(Color.GREEN);
        window.add(b4);

        String[] busArr4 = new String[8];
        for(int i =0; i<8; i++) {
            busArr4[i]= "";
        }
        //changes the color when the button is pressed
        b4.addActionListener(new ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent h)
                {
                    if(b4.getBackground().equals(Color.GREEN)) {
                        b4.setBackground(btnSecond);
                    }
                    else
                        b4.setBackground(Color.GREEN);

                    JPanel sPanel = new JPanel();
                    window.add(sPanel);
                    sPanel.add(new JLabel("addS"));
                    sPanel.setLayout(null);

                    JDialog sWindow = new JDialog(window, "Bus 4");
                    sWindow.setDefaultCloseOperation(sWindow.HIDE_ON_CLOSE);
                    sWindow.setSize(500,500);
                    sWindow.setLocationRelativeTo(null);
                    sWindow.setBackground(Color.LIGHT_GRAY);
                    sWindow.add(sPanel);
                    sWindow.setVisible(true);
                    sWindow.getContentPane().setLayout(null);

                    JLabel labelS = new JLabel();
                    labelS.setBounds(30, 10, 150, 60);
                    labelS.setText("Student name:");
                    sWindow.add(labelS);
                    sPanel.add(labelS);
                    JTextField labelStext = new JTextField();
                    labelStext.setBounds(190,30,60,20);
                    labelStext.setEditable(true);
                    sWindow.add(labelStext);
                    sPanel.add(labelStext);

                    JLabel labelS2 = new JLabel();
                    labelS2.setBounds(30, 70, 150, 60);
                    labelS2.setText("Student surname:");
                    sWindow.add(labelS2);
                    sPanel.add(labelS2);
                    JTextField labelS2text = new JTextField();
                    labelS2text.setBounds(190,90,60,20);
                    labelS2text.setEditable(true);
                    sWindow.add(labelS2text);
                    sPanel.add(labelS2text);

                    JLabel labelS3 = new JLabel();
                    labelS3.setBounds(30, 130, 150, 60);
                    labelS3.setText("Bus ID:");
                    sWindow.add(labelS3);
                    sPanel.add(labelS3);
                    JLabel labelS3text = new JLabel();
                    labelS3text.setBounds(190,150,60,20);
                    labelS3text.setText("Bus 4");
                    sWindow.add(labelS3text);
                    sPanel.add(labelS3text);

                    JLabel labelS4 = new JLabel();
                    labelS4.setBounds(30, 190, 170, 60);
                    labelS4.setText("Ater school activity (Y/N):");
                    sWindow.add(labelS4);
                    sPanel.add(labelS4);
                    JTextField labelS4text = new JTextField();
                    labelS4text.setBounds(190,210,60,20);
                    labelS4text.setEditable(true);
                    sWindow.add(labelS4text);
                    sPanel.add(labelS4text);

                    JButton dataa = new JButton();
                    dataa.setBounds(20,330,230,30);
                    dataa.setText("Add Student");
                    dataa.setVisible(true);
                    window.add(dataa);
                    sWindow.add(dataa);
                    sPanel.add(dataa);

                    //save button
                    JButton saveBtn = new JButton();
                    saveBtn.setBounds(20,365,230,30);
                    saveBtn.setText("Save");
                    saveBtn.setVisible(true);

                    window.add(saveBtn);
                    sWindow.add(saveBtn);
                    sPanel.add(saveBtn);

                    JTextArea show = new JTextArea();
                    show.setBounds(260,10,210,500);
                    show.setEditable(true);

                    show.setText(getFileData("appData/bus4.txt"));

                    sWindow.add(show);
                    sPanel.add(show);

                    dataa.addActionListener(new ActionListener() {
                            public void actionPerformed(java.awt.event.ActionEvent h)
                            {
                                validateAndProcess(labelStext.getText(),labelS2text.getText(),4, 
                                    labelS4text.getText(),"appData/bus4.txt");
                                show.setText(getFileData("appData/bus4.txt"));
                            };

                    });

                    saveBtn.addActionListener(new ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent d)
                        {
                            removeAndSave("appData/bus4.txt",show);
                            show.setText(getFileData("appData/bus4.txt"));
                        };
                    });

                }});

        //BUTTON 5 CODE

        JButton b5 = new JButton();
        panel.add(b5);
        b5.setBounds(90, 350, 110, 200);
        b5.setText("P5");
        b5.setToolTipText("Click to approve bus arrival");
        b5.setForeground(Color.WHITE);
        b5.setBackground(Color.GREEN);
        window.add(b5);

        String[] busArr5 = new String[8];
        for(int i =0; i<8; i++) {
            busArr5[i]= "";
        }
        //changes the color when the button is pressed
        b5.addActionListener(new ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent h)
                {
                    if(b5.getBackground().equals(Color.GREEN)) {
                        b5.setBackground(btnSecond);
                    }
                    else
                        b5.setBackground(Color.GREEN);

                    JPanel sPanel = new JPanel();
                    window.add(sPanel);
                    sPanel.add(new JLabel("addS"));
                    sPanel.setLayout(null);

                    JDialog sWindow = new JDialog(window, "Bus 5");
                    sWindow.setDefaultCloseOperation(sWindow.HIDE_ON_CLOSE);
                    sWindow.setSize(500,500);
                    sWindow.setLocationRelativeTo(null);
                    sWindow.setBackground(Color.LIGHT_GRAY);
                    sWindow.add(sPanel);
                    sWindow.setVisible(true);
                    sWindow.getContentPane().setLayout(null);

                    JLabel labelS = new JLabel();
                    labelS.setBounds(30, 10, 150, 60);
                    labelS.setText("Student name:");
                    sWindow.add(labelS);
                    sPanel.add(labelS);
                    JTextField labelStext = new JTextField();
                    labelStext.setBounds(190,30,60,20);
                    labelStext.setEditable(true);
                    sWindow.add(labelStext);
                    sPanel.add(labelStext);

                    JLabel labelS2 = new JLabel();
                    labelS2.setBounds(30, 70, 150, 60);
                    labelS2.setText("Student surname:");
                    sWindow.add(labelS2);
                    sPanel.add(labelS2);
                    JTextField labelS2text = new JTextField();
                    labelS2text.setBounds(190,90,60,20);
                    labelS2text.setEditable(true);
                    sWindow.add(labelS2text);
                    sPanel.add(labelS2text);

                    JLabel labelS3 = new JLabel();
                    labelS3.setBounds(30, 130, 150, 60);
                    labelS3.setText("Bus ID:");
                    sWindow.add(labelS3);
                    sPanel.add(labelS3);
                    JLabel labelS3text = new JLabel();
                    labelS3text.setBounds(190,150,60,20);
                    labelS3text.setText("Bus 5");
                    sWindow.add(labelS3text);
                    sPanel.add(labelS3text);

                    JLabel labelS4 = new JLabel();
                    labelS4.setBounds(30, 190, 170, 60);
                    labelS4.setText("Ater school activity (Y/N):");
                    sWindow.add(labelS4);
                    sPanel.add(labelS4);
                    JTextField labelS4text = new JTextField();
                    labelS4text.setBounds(190,210,60,20);
                    labelS4text.setEditable(true);
                    sWindow.add(labelS4text);
                    sPanel.add(labelS4text);

                    JButton dataa = new JButton();
                    dataa.setBounds(20,330,230,30);
                    dataa.setText("Add Student");
                    dataa.setVisible(true);
                    window.add(dataa);
                    sWindow.add(dataa);
                    sPanel.add(dataa);

                    //save button
                    JButton saveBtn = new JButton();
                    saveBtn.setBounds(20,365,230,30);
                    saveBtn.setText("Save");
                    saveBtn.setVisible(true);

                    window.add(saveBtn);
                    sWindow.add(saveBtn);
                    sPanel.add(saveBtn);

                    JTextArea show = new JTextArea();
                    show.setBounds(260,10,210,500);
                    show.setEditable(true);

                    show.setText(getFileData("appData/bus5.txt"));

                    sWindow.add(show);
                    sPanel.add(show);

                    dataa.addActionListener(new ActionListener() {
                            public void actionPerformed(java.awt.event.ActionEvent h)
                            {

                                validateAndProcess(labelStext.getText(),labelS2text.getText(),5, 
                                    labelS4text.getText(),"appData/bus5.txt");
                                show.setText(getFileData("appData/bus5.txt"));


                            };

                    });

                    saveBtn.addActionListener(new ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent d)
                        {
                            removeAndSave("appData/bus5.txt",show);
                            show.setText(getFileData("appData/bus5.txt"));
                        };
                    });

                }});

        //BUTTON 6 CODE

        JButton b6 = new JButton();
        panel.add(b6);
        b6.setBounds(230, 350, 110, 200);
        b6.setText("P6");
        b6.setToolTipText("Click to approve bus arrival");
        b6.setForeground(Color.WHITE);
        b6.setBackground(Color.GREEN);
        window.add(b6);
        
        String[] busArr6 = new String[8];
        for(int i =0; i<8; i++) {
            busArr6[i]= "";
        }
        //changes the color when the button is pressed
        b6.addActionListener(new ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e)
                {
                    if(b6.getBackground().equals(Color.GREEN))
                        b6.setBackground(btnSecond);
                    else
                        b6.setBackground(Color.GREEN);
                    
                    JPanel sPanel = new JPanel();
                    window.add(sPanel);
                    sPanel.add(new JLabel("addS"));
                    sPanel.setLayout(null);

                    JDialog sWindow = new JDialog(window, "Bus 6");
                    sWindow.setDefaultCloseOperation(sWindow.HIDE_ON_CLOSE);
                    sWindow.setSize(500,500);
                    sWindow.setLocationRelativeTo(null);
                    sWindow.setBackground(Color.LIGHT_GRAY);
                    sWindow.add(sPanel);
                    sWindow.setVisible(true);
                    sWindow.getContentPane().setLayout(null);

                    JLabel labelS = new JLabel();
                    labelS.setBounds(30, 10, 150, 60);
                    labelS.setText("Student name:");
                    sWindow.add(labelS);
                    sPanel.add(labelS);
                    JTextField labelStext = new JTextField();
                    labelStext.setBounds(190,30,60,20);
                    labelStext.setEditable(true);
                    sWindow.add(labelStext);
                    sPanel.add(labelStext);

                    JLabel labelS2 = new JLabel();
                    labelS2.setBounds(30, 70, 150, 60);
                    labelS2.setText("Student surname:");
                    sWindow.add(labelS2);
                    sPanel.add(labelS2);
                    JTextField labelS2text = new JTextField();
                    labelS2text.setBounds(190,90,60,20);
                    labelS2text.setEditable(true);
                    sWindow.add(labelS2text);
                    sPanel.add(labelS2text);

                    JLabel labelS3 = new JLabel();
                    labelS3.setBounds(30, 130, 150, 60);
                    labelS3.setText("Bus ID:");
                    sWindow.add(labelS3);
                    sPanel.add(labelS3);
                    JLabel labelS3text = new JLabel();
                    labelS3text.setBounds(190,150,60,20);
                    labelS3text.setText("Bus 6");
                    sWindow.add(labelS3text);
                    sPanel.add(labelS3text);

                    JLabel labelS4 = new JLabel();
                    labelS4.setBounds(30, 190, 170, 60);
                    labelS4.setText("Ater school activity (Y/N):");
                    sWindow.add(labelS4);
                    sPanel.add(labelS4);
                    JTextField labelS4text = new JTextField();
                    labelS4text.setBounds(190,210,60,20);
                    labelS4text.setEditable(true);
                    sWindow.add(labelS4text);
                    sPanel.add(labelS4text);

                    JButton dataa = new JButton();
                    dataa.setBounds(20,330,230,30);
                    dataa.setText("Add Student");
                    dataa.setVisible(true);
                    window.add(dataa);
                    sWindow.add(dataa);
                    sPanel.add(dataa);

                    //save button
                    JButton saveBtn = new JButton();
                    saveBtn.setBounds(20,365,230,30);
                    saveBtn.setText("Save");
                    saveBtn.setVisible(true);

                    window.add(saveBtn);
                    sWindow.add(saveBtn);
                    sPanel.add(saveBtn);

                    JTextArea show = new JTextArea();
                    show.setBounds(260,10,210,500);
                    show.setEditable(true);

                    show.setText(getFileData("appData/bus6.txt"));

                    sWindow.add(show);
                    sPanel.add(show);

                    dataa.addActionListener(new ActionListener() {
                            public void actionPerformed(java.awt.event.ActionEvent h)
                            {

                                validateAndProcess(labelStext.getText(),labelS2text.getText(),6, 
                                    labelS4text.getText(),"appData/bus6.txt");
                                show.setText(getFileData("appData/bus6.txt"));

                            };

                    });

                    saveBtn.addActionListener(new ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent d)
                        {
                            removeAndSave("appData/bus6.txt",show);
                            show.setText(getFileData("appData/bus6.txt"));
                        };
                    });

                }});

        //BUTTON 7 CODE

        JButton b7 = new JButton();
        panel.add(b7);
        b7.setBounds(370, 350, 110, 200);
        b7.setText("P7");
        b7.setToolTipText("Click to approve bus arrival");
        b7.setForeground(Color.WHITE);
        b7.setBackground(Color.GREEN);
        window.add(b7);
        
        String[] busArr7 = new String[8];
        for(int i =0; i<8; i++) {
            busArr7[i]= "";
        }
        //changes the color when the button is pressed
        b7.addActionListener(new ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e)
                {
                    if(b7.getBackground().equals(Color.GREEN))
                        b7.setBackground(btnSecond);
                    else
                        b7.setBackground(Color.GREEN);
                    
                    JPanel sPanel = new JPanel();
                    window.add(sPanel);
                    sPanel.add(new JLabel("addS"));
                    sPanel.setLayout(null);

                    JDialog sWindow = new JDialog(window, "Bus 7");
                    sWindow.setDefaultCloseOperation(sWindow.HIDE_ON_CLOSE);
                    sWindow.setSize(500,500);
                    sWindow.setLocationRelativeTo(null);
                    sWindow.setBackground(Color.LIGHT_GRAY);
                    sWindow.add(sPanel);
                    sWindow.setVisible(true);
                    sWindow.getContentPane().setLayout(null);

                    JLabel labelS = new JLabel();
                    labelS.setBounds(30, 10, 150, 60);
                    labelS.setText("Student name:");
                    sWindow.add(labelS);
                    sPanel.add(labelS);
                    JTextField labelStext = new JTextField();
                    labelStext.setBounds(190,30,60,20);
                    labelStext.setEditable(true);
                    sWindow.add(labelStext);
                    sPanel.add(labelStext);

                    JLabel labelS2 = new JLabel();
                    labelS2.setBounds(30, 70, 150, 60);
                    labelS2.setText("Student surname:");
                    sWindow.add(labelS2);
                    sPanel.add(labelS2);
                    JTextField labelS2text = new JTextField();
                    labelS2text.setBounds(190,90,60,20);
                    labelS2text.setEditable(true);
                    sWindow.add(labelS2text);
                    sPanel.add(labelS2text);

                    JLabel labelS3 = new JLabel();
                    labelS3.setBounds(30, 130, 150, 60);
                    labelS3.setText("Bus ID:");
                    sWindow.add(labelS3);
                    sPanel.add(labelS3);
                    JLabel labelS3text = new JLabel();
                    labelS3text.setBounds(190,150,60,20);
                    labelS3text.setText("Bus 7");
                    sWindow.add(labelS3text);
                    sPanel.add(labelS3text);

                    JLabel labelS4 = new JLabel();
                    labelS4.setBounds(30, 190, 170, 60);
                    labelS4.setText("Ater school activity (Y/N):");
                    sWindow.add(labelS4);
                    sPanel.add(labelS4);
                    JTextField labelS4text = new JTextField();
                    labelS4text.setBounds(190,210,60,20);
                    labelS4text.setEditable(true);
                    sWindow.add(labelS4text);
                    sPanel.add(labelS4text);

                    JButton dataa = new JButton();
                    dataa.setBounds(20,330,230,30);
                    dataa.setText("Add Student");
                    dataa.setVisible(true);
                    window.add(dataa);
                    sWindow.add(dataa);
                    sPanel.add(dataa);

                    //save button
                    JButton saveBtn = new JButton();
                    saveBtn.setBounds(20,365,230,30);
                    saveBtn.setText("Save");
                    saveBtn.setVisible(true);

                    window.add(saveBtn);
                    sWindow.add(saveBtn);
                    sPanel.add(saveBtn);


                    JTextArea show = new JTextArea();
                    show.setBounds(260,10,210,500);
                    show.setEditable(true);

                    show.setText(getFileData("appData/bus7.txt"));

                    sWindow.add(show);
                    sPanel.add(show);

                    dataa.addActionListener(new ActionListener() {
                            public void actionPerformed(java.awt.event.ActionEvent h)
                            {

                                validateAndProcess(labelStext.getText(),labelS2text.getText(),7, 
                                    labelS4text.getText(),"appData/bus7.txt");
                                show.setText(getFileData("appData/bus7.txt"));

                            };
                    });

                    saveBtn.addActionListener(new ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent d)
                        {
                            removeAndSave("appData/bus7.txt",show);
                            show.setText(getFileData("appData/bus7.txt"));
                        };
                    });

                }});
            ;

        //BUTTON 8 CODE

        JButton b8 = new JButton();
        panel.add(b8);
        b8.setBounds(510, 350, 110, 200);
        b8.setText("P8");
        b8.setToolTipText("Click to approve bus arrival");
        b8.setForeground(Color.WHITE);
        b8.setBackground(Color.GREEN);
        window.add(b8);

        //changes the color when the button is pressed
        b8.addActionListener(new ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e)
                {
                    if(b8.getBackground().equals(Color.GREEN))
                        b8.setBackground(btnSecond);
                    else
                        b8.setBackground(Color.GREEN);
                }
        });

    }}
