package com.evive.formProcess;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfInt;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

/**
 * 
 * @author abhishek
 *
 */
public class GetRectangles {
    private static final Logger LOG = LoggerFactory.getLogger(GetRectangles.class);
    private final String propFile = "src/main/resources/data.properties";
    Properties properties;

    /**
     * Loads the property file.
     */
    public void getProperties() {
        try (FileInputStream fileInput = new FileInputStream(new File(propFile))) {
            properties = new Properties();
            properties.load(fileInput);
        } catch (final FileNotFoundException e) {
            LOG.error(e.getMessage(), e);
        } catch (final IOException e) {
            LOG.error(e.getMessage(), e);
        }

    }
    /**
     * Assign labels to the predicated digits.
     */
    public void assignLabels() {
        final List<Integer> numSquares;
        final List<String> squareNames;
        List<String> predictedLabels = new ArrayList<String>();



        numSquares = new ArrayList<Integer>();

        numSquares.add(Integer.valueOf(properties.getProperty("FIELDS_IN_INPUT_1")));
        numSquares.add(Integer.valueOf(properties.getProperty("FIELDS_IN_INPUT_2")));
        numSquares.add(Integer.valueOf(properties.getProperty("FIELDS_IN_INPUT_3")));
        numSquares.add(Integer.valueOf(properties.getProperty("FIELDS_IN_INPUT_4")));
        numSquares.add(Integer.valueOf(properties.getProperty("FIELDS_IN_INPUT_5")));
        numSquares.add(Integer.valueOf(properties.getProperty("FIELDS_IN_INPUT_6")));
        numSquares.add(Integer.valueOf(properties.getProperty("FIELDS_IN_INPUT_7")));
        numSquares.add(Integer.valueOf(properties.getProperty("FIELDS_IN_INPUT_8")));
        numSquares.add(Integer.valueOf(properties.getProperty("FIELDS_IN_INPUT_9")));
        numSquares.add(Integer.valueOf(properties.getProperty("FIELDS_IN_INPUT_10")));
        numSquares.add(Integer.valueOf(properties.getProperty("FIELDS_IN_INPUT_11")));
        numSquares.add(Integer.valueOf(properties.getProperty("FIELDS_IN_INPUT_12")));


        squareNames = new ArrayList<>();

        squareNames.add(properties.getProperty("NAME_OF_INPUT_FEILDS_1"));
        squareNames.add(properties.getProperty("NAME_OF_INPUT_FEILDS_2"));
        squareNames.add(properties.getProperty("NAME_OF_INPUT_FEILDS_3"));
        squareNames.add(properties.getProperty("NAME_OF_INPUT_FEILDS_4"));
        squareNames.add(properties.getProperty("NAME_OF_INPUT_FEILDS_5"));
        squareNames.add(properties.getProperty("NAME_OF_INPUT_FEILDS_6"));
        squareNames.add(properties.getProperty("NAME_OF_INPUT_FEILDS_7"));
        squareNames.add(properties.getProperty("NAME_OF_INPUT_FEILDS_8"));
        squareNames.add(properties.getProperty("NAME_OF_INPUT_FEILDS_9"));
        squareNames.add(properties.getProperty("NAME_OF_INPUT_FEILDS_10"));
        squareNames.add(properties.getProperty("NAME_OF_INPUT_FEILDS_11"));
        squareNames.add(properties.getProperty("NAME_OF_INPUT_FEILDS_12"));

        try (BufferedReader bufferedReader = Files.newBufferedReader(Paths.get("src/main/resources/input.txt"))) {
            predictedLabels = bufferedReader.lines().collect(Collectors.toList());

        } catch (final IOException e) {
            LOG.error(e.getMessage(), e);
        }


        StringBuilder stringBuilder;
        int index = 0;
        for (final String sqName : squareNames) {
            stringBuilder = new StringBuilder();
            for (int i = 0; i < numSquares.get(squareNames.indexOf(sqName)); i++) {
                stringBuilder.append(predictedLabels.get(index));
                index++;
            }
            LOG.info("{} : {}", sqName, stringBuilder.toString());

        }

    }


    /**
     * 
     * @param Point pt1
     * @param Point pt2
     * @param Point pt0
     * @return double
     */
    public double angle(final Point pt1, final Point pt2, final Point pt0) {
        final double dx1 = pt1.x - pt0.x;
        final double dy1 = pt1.y - pt0.y;
        final double dx2 = pt2.x - pt0.x;
        final double dy2 = pt2.y - pt0.y;
        return (dx1 * dx2 + dy1 * dy2) / Math.sqrt((dx1 * dx1 + dy1 * dy1) * (dx2 * dx2 + dy2 * dy2) + 1e-10);
    }

    /**
     * 
     * @param Mat image
     * @param List<List<Point> > squares
     * Given a image this function locates all the squares present in the image.
     */
    public void findSquares(final Mat image, List<List<Point>> squares) {
        squares.clear();
        image.cols();
        Integer.valueOf(properties.getProperty("distPercentageThreshold"));
        final Mat pyr = new Mat();
        final Mat gray = new Mat();
        Imgproc.pyrDown(image, pyr, new Size(image.cols() / 2, image.rows() / 2));
        final Mat timg = new Mat();
        Imgproc.pyrUp(pyr, timg, image.size());
        final List<MatOfPoint> contours = new ArrayList<>();

        for (int i = 0; i < Integer.valueOf(properties.getProperty("COLOR_PANNEL_THRESHOLD")); i++) {
            final MatOfInt ch = new MatOfInt(i, 0);
            final Mat gray0 = new Mat(image.size(), CvType.CV_8U);
            final List<Mat> src = Arrays.asList(timg);
            final List<Mat> des = Arrays.asList(gray0);
            Core.mixChannels(src, des, ch);

            for (int j = 0; j < Integer.valueOf(properties.getProperty("THRESHOLD_LEVEL_TRY")); j++) {
                if (j == 0) {
                    final int CONTRAST_THRESHOLD = Integer.valueOf(properties.getProperty("CONTRAST_THRESHOLD"));
                    Imgproc.Canny(gray0, gray, 0, CONTRAST_THRESHOLD, 5, false);
                    Imgproc.dilate(gray, gray, new Mat(), new Point(-1, -1), 1);
                } else {
                    // gray = gray0 >= (j + 1) * 255 / Integer.valueOf(properties.getProperty("THRESHOLD_LEVEL_TRY"));
                }
                final Mat hierarchy = new Mat();
                Imgproc.findContours(gray, contours, hierarchy, Imgproc.RETR_LIST, Imgproc.CHAIN_APPROX_SIMPLE);
                final MatOfPoint2f approx = new MatOfPoint2f();

                for (int k = 0; k < contours.size(); k++) {

                    Imgproc.approxPolyDP(new MatOfPoint2f(contours.get(k)), approx,
                            Imgproc.arcLength(new MatOfPoint2f(contours.get(k)), true) * 0.02, true);
                    List<Point> approx_list = approx.toList();
                    if(approx_list.size() == 4 && Math.abs(Imgproc.contourArea(new MatOfPoint2f(approx))) > 1000) {
                        double maxCosine = 0;
                        for (int m = 2; m < 5; ++m) {
                            // find the maximum cosine of the angle between joint edges
                            double cosine = Math.abs(
                                    angle(approx_list.get(m % 4), approx_list.get(m - 2),
                                            approx_list.get(m - 1)));
                            maxCosine = Math.max(maxCosine, cosine);
                        }
                    }
                }
            }
        }
    }


    
    public static void main(final String[] args) {
        final GetRectangles gr = new GetRectangles();
        gr.getProperties();
        gr.assignLabels();
    }

}
