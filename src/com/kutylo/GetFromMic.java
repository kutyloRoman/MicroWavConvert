package com.kutylo;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class GetFromMic {
    //параметри аудіо отриманого з мікрофону
    private float sampleRate = 44100;
    private int sampleSizeInBits = 16;
    private int channels = 2;
    private int frameSize = 4;
    private float frameRate = 44100;
    private boolean isBigEndian = false;
    AudioFormat.Encoding encoding = AudioFormat.Encoding.PCM_SIGNED;

    private String err = "No supported lines found";
    AudioFileFormat.Type fileType = AudioFileFormat.Type.WAVE;
    //створення формату аудіо з заданими параметрами
    AudioFormat format = new AudioFormat(encoding, sampleRate, sampleSizeInBits, channels, frameSize, frameRate, isBigEndian);
    //оголошення лінії, з якої будемо отримувати аудіо
    TargetDataLine line;
    DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);

    //процедура зупинки запису
    public void stopRecording() {
        Thread stop = new Thread(new Runnable() {
            public void run() {
                line.stop();
                line.close();
                System.out.println(System.currentTimeMillis());
                System.out.println("Finished");
            }
        });
        stop.start();
    }
    public void getAudioFromMic(){

        if (!AudioSystem.isLineSupported(info)) {
            System.out.println(err);
        }
        //ініціалізація лінії з підходящими параметрами
        try {
            line = (TargetDataLine) AudioSystem.getLine(info);
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException ex){
            System.out.println("Не знайдено мікрофон!!");
        }
        //відкриття лінії для отримання даних
        try {
            line.open(format);
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
        //початок відбору даних з мікрофону
        line.start();
        AudioInputStream inputStream = new AudioInputStream(line);
        //запис аудіо-файлу з відповідними параметрами в вибраному місці
        String filePath = "D://Project/Labs/mzkit/mzkit_lab_3/record.wav";
        File wavFile = new File(filePath);
        try {
            AudioSystem.write(inputStream, fileType, wavFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}


