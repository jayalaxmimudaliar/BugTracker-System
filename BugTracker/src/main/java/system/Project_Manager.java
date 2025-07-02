package system;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import DB.DataBase;

public class Project_Manager {

  public static void PerformanceView(final String user) {
    final JFrame DevPerformFrame = new JFrame(user + " Performance");
    DevPerformFrame.setSize(300, 200);
    DevPerformFrame.setLocationRelativeTo(null);
    DevPerformFrame.setVisible(true);
    DevPerformFrame.setResizable(false);
    DevPerformFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    DevPerformFrame.getContentPane().setLayout(null);

    JLabel FinishtxtChooseTester = new JLabel();
    FinishtxtChooseTester.setText("Select " + user + ":");
    FinishtxtChooseTester.setBounds(25, 10, 150, 20);
    DevPerformFrame.getContentPane().add(FinishtxtChooseTester);

    List<String> Devs = DataBase.getColumnValues("name", user + "s");

    String Devsarray[] = new String[Devs.size()]; //converting list into string array necessary for Jcombobox
    for (int j = 0; j < Devs.size(); j++) {
      Devsarray[j] = Devs.get(j);
    }

    final JComboBox DevsCombo = new JComboBox(Devsarray);  //shows thier name in dropdown list 
    DevsCombo.setSelectedIndex(0);
    DevsCombo.setBounds(25, 35, 200, 20);
    DevPerformFrame.getContentPane().add(DevsCombo);

    JButton btnSubmit = new JButton("Calculate");
    btnSubmit.setBounds(95, 115, 90, 25);
    DevPerformFrame.getContentPane().add(btnSubmit);

    btnSubmit.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        if (user.equals("Tester"))
          calcTestPerformance(DevPerformFrame, (String) DevsCombo.getSelectedItem()); //give the frame ,selected name
        else if (user.equals("Developer"))
          calcDevPerformance(DevPerformFrame, (String) DevsCombo.getSelectedItem());
      }
    });
  }

  public static void calcDevPerformance(JFrame frame, String DevName) {
    String ID = DataBase.getIDByName(DevName, "id", "Developers", "name");
    List<String> Priorities = DataBase.getColumnspecificValues("priority", "Bugs", "developerid", ID);
    List<String> Levels = DataBase.getColumnspecificValues("level", "Bugs", "developerid", ID);
    List<Long> TimeList = DataBase.getLongColumnspecificValues("timetaken", "Bugs", "developerid", ID);

    int devdonebugs = DataBase.intgetIDByName(DevName, "donebugs", "Developers", "name");
    double performance = Performance.Developer(devdonebugs, Priorities, Levels, TimeList);

    DecimalFormat decimalFormat = new DecimalFormat("#.##");
    String formattedperformance = decimalFormat.format(performance);

    JOptionPane.showMessageDialog(frame, "Performance: " + formattedperformance,
        "Calculation Successful!", JOptionPane.INFORMATION_MESSAGE);
  }

  public static void calcTestPerformance(JFrame frame, String TesterName) {
	  							//("value","tagetcol u want","table","idetifier (where condition))
    String ID = DataBase.getIDByName(TesterName, "id", "Testers", "name");  //get id by name ( 2 parameter says which field)
    List<String> Priorities = DataBase.getColumnspecificValues("priority", "Bugs", "testerid", ID); //list of priority where testerid
    //egg
	    //bugid	  title	      priority	testerid
	    // 1	Login fails	  	High	T1
	    // 2	Button UI off	Low		T2
	    // 3	Crash on save	High	T1
    
    int allbugcount = DataBase.getRowCount("Bugs"); //total row 
    int specificbugcount = DataBase.intgetIDByName(TesterName, "numbugs", "Testers", "name");  //fetches numberbug assign to that tester
    double performance = Performance.Tester(allbugcount, specificbugcount, Priorities);

    DecimalFormat decimalFormat = new DecimalFormat("#.##");
    String formattedperformance = decimalFormat.format(performance);

    JOptionPane.showMessageDialog(frame, "Performance: " + formattedperformance,
        "Calculation Successful!", JOptionPane.INFORMATION_MESSAGE);
  }
}
