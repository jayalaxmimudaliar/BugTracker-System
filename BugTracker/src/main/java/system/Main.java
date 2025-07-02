package system;

import java.awt.EventQueue;

import GUI.HomePage;

public class Main {

  public static void main(String[] args) {
    EventQueue.invokeLater(new Runnable() { //(it will run when it is free so that no overlapping of GUI occurs
      public void run() {
        try {
          HomePage frame = new HomePage();
          frame.homePage();
          // frame.setVisible(true);
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    });

  }
}
