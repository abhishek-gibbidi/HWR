package com.evive.formProcess;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfFloat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Size;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.HOGDescriptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * 
 * @author abhishek
 *
 */
public class FeactureExtractor {
    private static final Logger LOG = LoggerFactory.getLogger(FeactureExtractor.class);

    public Mat preProcessImage(Mat image) {
        Imgproc.cvtColor(image, image, Imgproc.COLOR_BGR2GRAY);
        Imgproc.GaussianBlur(image, image, new Size(9, 9), 0, 0);
        Imgproc.adaptiveThreshold(image, image, 255, Imgproc.ADAPTIVE_THRESH_GAUSSIAN_C, Imgproc.THRESH_BINARY_INV, 11,
                2);
        return image;
    }

    public Mat removeBoundries(Mat image) {
        final Mat new_image = image;
        for (int i = 0; i < image.rows(); i++) {
            for (int j = 0; j < image.cols(); j++) {
                if (i > 10 && j < image.cols() - 5 && i < image.rows() - 5 && j > 10) {
                    if (image.get(i, j)[0] < 255) {
                        new_image.put(i, j, 0);
                    } else {
                        new_image.put(i, j, 255);
                    }
                } else {
                    new_image.put(i, j, 255);
                }
            }
        }

        return new_image;
    }

    public MatOfFloat getHOGFeatures(Mat image, int fileIndex) {

        final Mat processImage = preProcessImage(image);
        final Mat final_image = removeBoundries(processImage);
        Imgproc.resize(final_image, final_image, new Size(64, 64));
        final HOGDescriptor hog =
                new HOGDescriptor(new Size(32, 32), new Size(32, 32), new Size(16, 16), new Size(16, 16), 9);
        final MatOfPoint locations = new MatOfPoint();
        final MatOfFloat descriptors = new MatOfFloat();
        hog.compute(final_image, descriptors, new Size(32, 32), new Size(0, 0), locations);
        LOG.info("Descriptor : {} ", descriptors.toList());
        return descriptors;
    }

    public void collectHOG() {
        LOG.info("Loading Image");
        final Mat image = Highgui.imread(getClass().getResource("/lena.png").getPath());
        if (image.empty()) {
            LOG.error("No image");

        } else {
            LOG.info("Computing HOG");
            getHOGFeatures(image, 0);
        }
    }

    public static void main(String[] args) {
        final FeactureExtractor fe = new FeactureExtractor();
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        fe.collectHOG();

    }

}
