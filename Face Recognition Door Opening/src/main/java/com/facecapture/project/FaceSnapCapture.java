package com.facecapture.project;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import com.amazonaws.samples.MadeBucket;
import com.amazonaws.samples.SendingImage;
import com.compare.faces.CompareFaces;
import com.s3objects.listing.ListS3Objects;

public class FaceSnapCapture {
	public static void main(String[] args) {
		@SuppressWarnings("unused")
		MainFrame object = new MainFrame(); // runs the mainframe constructor and frame will opened on screen
	}
}

@SuppressWarnings("serial")
class MainFrame extends JFrame {
	public MainFrame() {

		JButton b1 = new JButton("Open WebCam"); // buttons created
//		b1.setFont(new Font("Arial",Font.BOLD,15));
		// JButton b2 = new JButton("Take SnapShot");
		JLabel l1 = new JLabel("Facial Recognition Attendance System"); // label created

		add(b1);
		// add(b2);
		add(l1);
		b1.setBounds(210, 300, 240, 50);
		// b2.setBounds(230, 330, 200, 50);

		b1.addActionListener(new ActionListener() { // listener on b1

			public void actionPerformed(ActionEvent e) {
				try {
					// creating bucket in aws s3 by calling the create bucket code
					MadeBucket.createBucket();
					// calling python script for image capture
					Process pr = Runtime.getRuntime()
							.exec("python C:\\Users\\DA1041TU\\Desktop\\PythonScript\\OpenWebCam.py");
					BufferedReader in = new BufferedReader(new InputStreamReader(pr.getInputStream()));
					String ret;
					int count = 0;
					while ((ret = in.readLine()) != null) {
						count++;
						System.out.println(ret);
						// if image has been clicked upload it to aws s3 by calling upload to aws s3
						// code
						if (Character.compare(ret.charAt(0), 'I') == 0) {
							SendingImage.uploadingImage(ret); // uploading image clicked to aws s3
							String[] arr = ret.split(" ");
							String sourceImg = arr[0];
							System.out.println(ret + " and Uploaded to AWS S3");
							// get all the name or object key name of the images store as a face database in
							// separate aws s3 bucket
							List<String> imageList = ListS3Objects.getS3ObjectsList();
							System.out.println("imagelist is" + imageList);
							Boolean flag = false;
							for (String targetImg : imageList) {
								if (CompareFaces.faceMatched(sourceImg, targetImg)) {
									System.out.println("Access Granted :)");
									flag = true;
									break;
								}
							}
							if (flag == false) {
								System.out.println("Access Denied :(");
							}

						}
					}
					System.out.println("Total images captured and uploaded : " + (count - 2));
					// System.out.println(ret);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		setVisible(true);
		setTitle("Facial Recognition Attendance System");
		setResizable(false);
		setBounds(10, 10, 700, 600);
		// setSize(400,400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		setLayout(new FlowLayout()); // setLayout(new FlowLayout()); // Constructs a new FlowLayout with a centered
//		alignment and a default 5-unit
//		horizontal and vertical gap.

	}

	public void paint(Graphics g) {
		g.setColor(Color.black); // setting background color (black)

		g.fillRect(1, 1, 692, 592); // filling background rectangle which leaves 4 unit width and 4 unit height
									// each side of frame as frame size id (700,600)

		g.setColor(Color.red); // borders color (red)
		g.fillRect(0, 0, 30, 600);
		g.fillRect(670, 0, 30, 600); // border of background rectangle
		g.fillRect(684, 0, 3, 592);
		g.fillRect(0, 570, 670, 30);

		g.setColor(Color.yellow); // Facial Recognition Attendance System Heading at frame where level are
									// selected
		g.setFont(new Font("arial", Font.BOLD, 40));
		g.drawString("Facial Recognition", 180, 100);

		g.setColor(Color.yellow); // Facial Recognition Attendance System Heading at frame where level are
									// selected
		g.setFont(new Font("arial", Font.BOLD, 30));
		g.drawString("Attendance System", 210, 145);

		g.setColor(Color.green); // choose option string
		g.setFont(new Font("arial", Font.BOLD, 25));
		g.drawString("Choose Option", 252, 250);

		g.setColor(Color.white); // choose option string
		g.setFont(new Font("arial", Font.BOLD, 15));
		g.drawString("Instructions : ", 60, 470);
		g.setFont(new Font("arial", Font.PLAIN, 15));
		g.drawString("1. Press S to Capture and Save Image", 60, 500);
		g.drawString("2. Press Q to exit Webcam ", 60, 525);
	}

}