package com.evive.formprocess;


import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;




public class ProcessForm {
   public static final Logger LOG = LoggerFactory.getLogger(ProcessForm.class);
    public  Mat getImage() {
        Mat image = new Mat();
        for (int fileIndex = 0; fileIndex < 70; fileIndex++) {
            //StringBuilder stringBuilder = new StringBuilder();

            image = Highgui.imread("/home/abhishek/Desktop/HWR/DigitRecognition/Forms/form-1.jpg");
           if(image.empty()) {
               LOG.info("Unable to read Image");
                System.out.println("NO IMAGE");
                System.exit(0);
            }
            Imgproc.resize(image, image, new Size(1000, 1000));
            
        }
        return image;
    }
    
    public static void main(String[] args) {
        System.out.println("test");
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        Mat image = new ProcessForm().getImage();
        Highgui.imwrite("Resizes.jpg", image);
    }
}
