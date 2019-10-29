package com.kutylo;

import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.TargetDataLine;

    public class SimpleAudioRecorder extends Thread {

        private TargetDataLine m_line;
        private AudioFileFormat.Type m_targetType;
        private AudioInputStream m_audioInputStream;
        private File m_outputFile;

        public SimpleAudioRecorder(TargetDataLine line,
                AudioFileFormat.Type targetType, File file) {
            m_line = line;
            m_audioInputStream = new AudioInputStream(line);
            m_targetType = targetType;
            m_outputFile = file;
        }

        public void start() {
            super.start();
            m_line.start();
        }

        public void stopRecording() {
            m_line.stop();
            m_line.close();
        }

        public void run() {
            try {
                AudioSystem.write(m_audioInputStream, m_targetType, m_outputFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

}

