package com.kutylo;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

public class JWindow extends JFrame {

    private JButton startButton;
    private JButton stopButton;
    private JButton convertButton;

    private JLabel infArea;
    private JComboBox microphoneDevicesComboBox;

    Thread recordThread;
    GetFromMic recordMic;

    public JWindow(){
        super("Kutylo lab");
        initComponents();
        setVisible(true);
    }

    private void initComponents() {
        infArea = new JLabel();
        infArea.setText("Press any button");


        microphoneDevicesComboBox = new JComboBox();
        startButton = new JButton();
        stopButton = new JButton();
        convertButton = new JButton();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        startButton.setText("START RECORDING");
        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                startButtonAction(evt);
            }
        });

        stopButton.setText("STOP RECORDING");
        stopButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                stopButtonAction(evt);
            }
        });

        convertButton.setText("CONVERT WAV");
        convertButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                ConvertButtonAction(evt);
            }
        });

        microphoneDevicesComboBox.setModel(new DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        microphoneDevicesComboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                microphoneDevicesAction(evt);
            }
        });


        microphoneDevicesComboBox.removeAllItems();
        for(Mixer.Info s: printMicrophones()) microphoneDevicesComboBox.addItem(s);

        convertButton.setAlignmentX(JComponent.CENTER_ALIGNMENT);

        Container cont = this.getContentPane();
        cont.setLayout(new GridLayout(1,1,0,0));
        Box box=Box.createVerticalBox();
        Box btnBox=Box.createHorizontalBox();
        box.add(infArea);
        box.add(microphoneDevicesComboBox);
        box.add(convertButton);
        btnBox.add(startButton);
        btnBox.add(stopButton);
        box.add(btnBox);
        add(box);
        pack();

    }

    private void startButtonAction(java.awt.event.ActionEvent evt) {

            infArea.setText("Record is running");
            recordThread = new Thread(new Runnable() {
                public void run() {
                    recordMic = new GetFromMic();
                    recordMic.getAudioFromMic();
                }
            });
            recordThread.start();
    }


    private void stopButtonAction(java.awt.event.ActionEvent evt) {
        infArea.setText("Record is stopping");
        recordMic.stopRecording();
        recordThread.stop();

    }

    private void ConvertButtonAction(java.awt.event.ActionEvent evt) {
        infArea.setText("Convert start");
        if(ConvertWav.convertToBinary())
            infArea.setText("Convert successful");
        else
            infArea.setText("Convert fall");

    }

    private void microphoneDevicesAction(java.awt.event.ActionEvent evt) {
    }

    public LinkedList<Mixer.Info> printMicrophones(){

        Mixer.Info[] mixerInfos = AudioSystem.getMixerInfo();
        LinkedList<Mixer.Info> devicesInfos = new LinkedList<>();
        for (Mixer.Info info: mixerInfos){
            Mixer m = AudioSystem.getMixer(info);
            Line.Info[] lineInfos = m.getTargetLineInfo();

            if(lineInfos.length>=1 && lineInfos[0].getLineClass().equals(TargetDataLine.class)){//Only prints out info is it is a Microphone
                devicesInfos.add(info);
                System.out.println("Line Name: " + info.getName());//The name of the AudioDevice
                System.out.println("Line Description: " + info.getDescription());//The type of audio device
                for (Line.Info lineInfo:lineInfos){
                    System.out.println ("\t"+"---"+lineInfo);
                    Line line;
                    try {
                        line = m.getLine(lineInfo);

                    } catch (LineUnavailableException e) {
                        e.printStackTrace();
                        return null;
                    }
                    System.out.println("\t-----"+line);
                }
            }
        }
        return devicesInfos;
    }

}
