package com.evive.formprocess;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;

class test {

    public void readImage() {
        Mat image = Highgui.imread(getClass().getResource("/lena.png").getPath());
        
        int distPercentageThreshold = 2;
        int distThreshold = image.cols() * distPercentageThreshold / 100;
        Mat pyr = new Mat();
        Mat timg = new Mat();
        Mat gray = new Mat();
        Mat gray0 = new Mat(image.size(), CvType.CV_8U);
                
        Imgproc.cvtColor(image, image, Imgproc.COLOR_BGR2GRAY);
        Highgui.imwrite("gray.png", image);
        Size size = new Size(image.cols()/2, image.rows()/2);
        Imgproc.pyrDown(image, pyr, size);
        Imgproc.pyrUp(pyr, timg, image.size());
        
    }

}


public class FormProcesser {
    public static void main(String[] args) {
        System.out.println("test");
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        new test().readImage();
    }
}
