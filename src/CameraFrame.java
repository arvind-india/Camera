import org.opencv.core.Core;
import org.opencv.highgui.VideoCapture;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by tsotne on 4/14/17.
 */
public class CameraFrame extends JFrame implements ActionListener {
    CameraPanel cp;
    CameraFrame() {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        VideoCapture list=new VideoCapture(0);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        cp=new CameraPanel();
        Thread thread=new Thread(cp);
        JMenu camera=new JMenu("Camera");
        JMenuBar bar=new JMenuBar();
        bar.add(camera);
        /*
        JMenuItem cam=new JMenuItem("Source "+0);
        cam.addActionListener(this);
        camera.add(cam);
        list.release();
        */
        //if u have more than one camera
        int i=1;
        while (list.isOpened())
        {
            JMenuItem cam=new JMenuItem("Source "+i);
            cam.addActionListener(this);
            camera.add(cam);
            list.release();
            list=new VideoCapture(i);
            i++;
        }
        thread.start();
        add(cp);
        setJMenuBar(bar);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500,500);
        setVisible(true);
    }

    public static void main(String[] args) {
        CameraFrame cf=new CameraFrame();
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        JMenuItem source=(JMenuItem)e.getSource();
        int num=Integer.parseInt(source.getText().substring(7))-1;
        cp.switchCamera(num);
    }
}
