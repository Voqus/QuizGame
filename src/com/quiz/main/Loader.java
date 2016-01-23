package com.quiz.main;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import com.quiz.frames.StartGUI;

/**
 * Main class
 * 
 * @author Voqus
 */
public class Loader {
    public static void main(String[] args)
    {
        try
        {
            for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels())
            {
                if ("Nimbus".equals(info.getName()))
                {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                } // if
            } // for
        } // try
        catch (Exception e) //  multicatch not supported before java7 so general type implemented here
        {
            e.getStackTrace();
        } // catch
        
        // Lambda Expression Java8+
        //SwingUtilities.invokeLater(()-> {new StartGUI();});
        SwingUtilities.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                new StartGUI();
            } // run
        }); // invokeLater
    } // main
}
