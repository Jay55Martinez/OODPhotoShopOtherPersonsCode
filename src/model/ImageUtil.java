package model;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.FileInputStream;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;


/**
 * This class contains utility methods to be used on images.
 */
public class ImageUtil {

  /**
   * CHANGE: added readImage to support different image types in Java ImageIO library.
   * readImage will take a path, which is the location of file in computer, and a filename, which
   * will be the name of the file in the model map.
   * @param path - location of image in computer
   * @param filename - name of image to be stored in ImageModel implementation's map
   * @return Image
   * @throws RuntimeException - cannot read file using ImageIO
   */
  // NEW IMAGE UTILS
  public static Image readImage(String path, String filename) {
    File file = new File(path);
    BufferedImage buff = null;
    try {
      buff = ImageIO.read(file);
    } catch (IOException e) { // if imageIO cant read it, we will try to read as PPM, if that fails
      // exception will be thrown
      return readPPM(path, filename);
    }

    int width;
    int height;
    try {
      width = buff.getWidth();
      height = buff.getHeight();
    } catch (NullPointerException e) { // try to read as PPM if exception caught
      return readPPM(path, filename);
    }

    int[][][] arr = new int[height][width][3];
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        int pixel = buff.getRGB(j, i);
        int r = (pixel & 0xff0000) >> 16;
        int g = (pixel & 0x00ff00) >> 8;
        int b = pixel & 0x0000ff;
        arr[i][j] = new int[]{r, g, b};
      }
    }
    return new ImageImpl(filename, arr); // return new ImageImpl from filename and new array formed
  }



  /**
   * read in a PPM image with a given path name and filename. path is the location of file in
   * computer and filename is the name of the image to be stored in our map.
   * @param path - location of image in computer
   * @param filename - name of image to be stored in ImageModel implemenation's map
   * @return Image - PPM format
   * @throws IllegalArgumentException -if it is not a PPM file
   */
  public static Image readPPM(String path, String filename) throws IllegalArgumentException {
    Scanner sc;

    try {
      sc = new Scanner(new FileInputStream(path));
    } catch (FileNotFoundException e) {
      System.out.println("File " + path + " not found!");
      throw new IllegalArgumentException("File not found");
    }
    StringBuilder builder = new StringBuilder();
    //read the file line by line, and populate a string. This will throw away any comment lines
    while (sc.hasNextLine()) {
      String s = sc.nextLine();
      if (s.charAt(0) != '#') {
        builder.append(s).append(System.lineSeparator());
      }
    }

    //now set up the scanner to read from the string we just built
    sc = new Scanner(builder.toString());

    String token;

    token = sc.next();
    if (!token.equals("P3")) {
      throw new IllegalArgumentException("Invalid PPM file: plain RAW file should begin with P3");
    }

    int width = sc.nextInt();
    int height = sc.nextInt();
    int maxValue = sc.nextInt();

    int[][][] arr = new int[height][width][3];

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        int r = sc.nextInt();
        int g = sc.nextInt();
        int b = sc.nextInt();
        arr[i][j] = new int[]{r, g, b};
      }
    }
    return new ImageImpl(filename, arr, maxValue); // return new Image will be PPM format.
  }

  /**
   * Save image takes a path (file location in computer) and image writes the file to a new file.
   * @param path - file location in computer
   * @param image - Image to be saved
   * @throws RuntimeException - if cannot write image to file
   */
  public static void saveImage(String path, Image image) throws RuntimeException {
    int height = image.getHeight();
    int width = image.getWidth();
    String type = path.substring(path.length() - 3, path.length());
    if (type.equals("ppm")) {
      try {
        savePPM(path, image);
        return;
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }

    File out = new File(path);
    BufferedImage buff = new BufferedImage(width,
        height,
        BufferedImage.TYPE_INT_RGB);
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        int r = image.getPixel(i, j)[0] << 16;
        int g = image.getPixel(i, j)[1] << 8;
        int b = image.getPixel(i, j)[2];
        buff.setRGB(j, i, r + g + b);
      }
    }

    try {
      System.out.println(path + " " + image);
      ImageIO.write(buff, type, out);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }


  /**
   * SavePPMimage takes a path (file location in computer) and image writes the file to a new file.
   * @param path - file location in computer
   * @param image - Image to be saved
   * @throws IOException - if issues arise
   */
  public static void savePPM(String path, Image image)
      throws IOException {
    int imageHeight = image.getHeight();
    int imageWidth = image.getWidth();
    int max = image.getMaxValue();
    FileOutputStream out = new FileOutputStream(path);
    out.write(("P3" + System.lineSeparator()).getBytes());
    out.write(("# Created by controller.PhotoProcessing" + System.lineSeparator()).getBytes());
    out.write((imageWidth + " " + imageHeight + System.lineSeparator()).getBytes());
    out.write((max + System.lineSeparator()).getBytes());
    for (int i = 0; i < imageHeight; i++) {
      for (int j = 0; j < imageWidth; j++) {
        out.write((image.getPixel(i, j)[0] + System.lineSeparator()).getBytes());
        out.write((image.getPixel(i, j)[1] + System.lineSeparator()).getBytes());
        out.write((image.getPixel(i, j)[2] + System.lineSeparator()).getBytes());
      }
    }
    out.close();
  }

  /**
   * Display an image as ImageIcon to be showed in view when given an Image object.
   * @param image - image to be displayed
   * @return ImageIcon
   */
  public static ImageIcon displayImage(Image image) {
    saveImage("display/display.png", image);
    return new ImageIcon("display/display.png");
  }

}

