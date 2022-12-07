package encapsuled;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

import model.Image;
import model.ImageImpl;
import model.ImageUtil;
import model.filter.Filter;
import model.transform.Histogram;
import model.transform.Transform;

/**
 * Implementation of ImageModel. The model will hold the image and perform
 * operations on the image.
 */
public class ImageModelMosaicImpl implements ImageModelMosaic {
  private final Map<String, Image> images;

  /**
   * Constructor for ImageModelImpl. Initializes the images map.
   *
   * @param path     the path to the image
   * @param filename the name of the image
   */
  public ImageModelMosaicImpl(String path, String filename) {
    this.images = new HashMap<>();
    this.images.put(filename, ImageUtil.readImage(path, filename));
  }

  @Override
  public void mosaic(String filename, String newName, int seed) {
    List<int[]> seeds = new ArrayList<>();
    Map<int[], List<int[]>> clouts = new HashMap<>();
    Image img = images.get(filename);

    int[][][] temp = img.getImage();
    seeds = this.createSeeds(img, seeds, seed);
    this.getCluster(img, clouts, seeds);
    for (int[] seedVal : seeds) {
      temp = colorCloud(seedVal, clouts, temp);
    }
    this.images.put(newName, new ImageImpl(newName, temp));
  }

  private List<int[]> createSeeds(Image img, List<int[]> seeds, int seed) {
    double distance = ((img.getWidth() + img.getHeight()) / seed) + 1;
    List<int[]> firstSeeds = new ArrayList<>();
    List<int[]> secondSeeds = new ArrayList<>();
    List<int[]> thirdSeeds = new ArrayList<>();
    List<int[]> fourthSeeds = new ArrayList<>();
    boolean addSeed = true;
    Random rand = new Random();
    //break the image up into 4 quadrants
    //first quadrant
    while (seeds.size() < seed / 4) {
      int[] seed1 = new int[2];
      seed1[0] = rand.nextInt(img.getHeight() / 2);
      seed1[1] = rand.nextInt(img.getWidth() / 2);
      for (int[] prevSeeds : firstSeeds) {
        if (getDistanceBetweenSeeds(seed1, prevSeeds) < distance) {
          addSeed = false;
          break;
        }
      }
      if (addSeed) {
        seeds.add(seed1);
        firstSeeds.add(seed1);
      }
      addSeed = true;
    }
    //second quadrant
    while (seeds.size() < seed / 2) {
      int[] seed2 = new int[2];
      seed2[0] = rand.nextInt(img.getHeight() / 2);
      seed2[1] = rand.nextInt(img.getWidth() / 2) + img.getWidth() / 2;
      for (int[] prevSeeds : secondSeeds) {
        if (getDistanceBetweenSeeds(seed2, prevSeeds) < distance) {
          addSeed = false;
          break;
        }
      }
      if (addSeed) {
        seeds.add(seed2);
        secondSeeds.add(seed2);
      }
      addSeed = true;
    }
    //third quadrant
    while (seeds.size() < 3 * seed / 4) {
      int[] seed3 = new int[2];
      seed3[0] = rand.nextInt(img.getHeight() / 2) + img.getHeight() / 2;
      seed3[1] = rand.nextInt(img.getWidth() / 2);
      for (int[] prevSeeds : thirdSeeds) {
        if (getDistanceBetweenSeeds(seed3, prevSeeds) < distance) {
          addSeed = false;
          break;
        }
      }
      if (addSeed) {
        seeds.add(seed3);
        thirdSeeds.add(seed3);
      }
      addSeed = true;
    }
    //fourth quadrant
    while (seeds.size() < seed) {
      int[] seed4 = new int[2];
      seed4[0] = rand.nextInt(img.getHeight() / 2) + img.getHeight() / 2;
      seed4[1] = rand.nextInt(img.getWidth() / 2) + img.getWidth() / 2;
      for (int[] prevSeeds : fourthSeeds) {
        if (getDistanceBetweenSeeds(seed4, prevSeeds) < distance) {
          addSeed = false;
          break;
        }
      }
      if (addSeed) {
        seeds.add(seed4);
        fourthSeeds.add(seed4);
      }
      addSeed = true;
    }
    return seeds;
  }

  private int getDistanceBetweenSeeds(int[] seed1, int[] seed2) {
    int x1 = seed1[0];
    int y1 = seed1[1];
    int x2 = seed2[0];
    int y2 = seed2[1];
    return (int) Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
  }

  private Map<int[], List<int[]>> getCluster(Image img, Map<int[],
          List<int[]>> clouts, List<int[]> seeds) {
    this.initCluster(clouts, seeds);
    for (int width = 0; width < img.getHeight(); width++) {
      for (int height = 0; height < img.getWidth(); height++) {
        ;
        clouts.get(getClosestSeed(width, height, seeds)).add(new int[]{width, height});
      }
    }
    return clouts;
  }

  private Map<int[], List<int[]>> initCluster(Map<int[], List<int[]>> clouts, List<int[]> seeds) {
    for (int[] seed : seeds) {
      ArrayList<int[]> cluster = new ArrayList<>();
      clouts.put(seed, cluster);
    }
    return clouts;
  }

  private int[] getClosestSeed(int pixX, int pixY, List<int[]> seeds) {
    int[] closestSeed = new int[2];
    int minDistance = Integer.MAX_VALUE;
    int distance = 0;
    for (int[] seedVal : seeds) {
      distance = manhattanDistance(pixX, pixY, seedVal);
      if (distance < minDistance) {
        minDistance = distance;
        closestSeed = seedVal;
      }
    }
    return closestSeed;
  }

  private int manhattanDistance(int pixelX, int pixelY, int[] seed) {
    return Math.abs(pixelX - seed[0]) + Math.abs(pixelY - seed[1]);
  }

  private int[][][] colorCloud(int[] seed, Map<int[], List<int[]>> clouts, int[][][] temp) {
    int[] color = new int[3];
    for (int[] pixel : clouts.get(seed)) {
      int[] values = temp[pixel[0]][pixel[1]];
      color[0] += values[0];
      color[1] += values[1];
      color[2] += values[2];
    }
    color[0] /= clouts.get(seed).size();
    color[1] /= clouts.get(seed).size();
    color[2] /= clouts.get(seed).size();
    for (int[] pixel : clouts.get(seed)) {
      temp[pixel[0]][pixel[1]] = new int[]{color[0], color[1], color[2]};
    }
    return temp;
  }

  @Override
  public void flip(String filename, String newName, boolean axis) {
    // boolean parameter: false = horizontal, true = vertical

    Image img = images.get(filename);
    // temp represents essentially an empty image that will be filled in and returned as flipped
    // image at end of method. Size is set to the
    int[][][] temp = new int[img.getHeight()][img.getWidth()][3];

    // double for loop to iterate through image
    for (int i = 0; i < img.getHeight(); i++) {
      for (int j = 0; j < img.getWidth(); j++) {
        if (axis) { // if vertical flip
          temp[i][img.getWidth() - j - 1] = img.getPixel(i, j);
        } else { // if horizontal flip
          temp[img.getHeight() - i - 1][j] = img.getPixel(i, j);
        }
      }
    }

    // put the temp as a new PPM image in the images map.
    // use the newName as the file name, temp as the pixel array, and then the max value
    // from the img as the new max value.
    this.images.put(newName, new ImageImpl(newName, temp));
  }

  @Override
  public int[][][] getImage(String filename) throws IllegalStateException {

    try {
      return images.get(filename).getImage();
    } catch (NullPointerException e) {
      throw new IllegalStateException("Cannot find image.");
    }
  }

  @Override
  public Image getObject(String filename) {
    return images.get(filename);
  }

  @Override
  public void transform(String filename, String newName, Transform option)
          throws IllegalArgumentException {
    // temp represents essentially an empty image that will be filled in and returned as flipped
    // image at end of method.
    Image img = images.get(filename);

    int[][][] temp = new int[img.getHeight()][img.getWidth()][3];
    int[] current;

    // iterate through image using nested for loop
    for (int i = 0; i < img.getHeight(); i++) {
      for (int j = 0; j < img.getWidth(); j++) {
        current = img.getPixel(i, j);// current pixel
        // call edit on the option type and it will delegate
        temp[i][j] = checkMax(filename, option.edit(current));
      }
    }
    this.images.put(newName, new ImageImpl(newName, temp));
  }

  @Override
  public void filter(String filename, String newName, Filter option) {
    Image img = images.get(filename);
    int[][][] temp = new int[img.getHeight()][img.getWidth()][3];

    for (int i = 0; i < img.getHeight(); i++) {
      for (int j = 0; j < img.getWidth(); j++) {            // for each pixel in the image
        temp[i][j] = checkMax(filename, option.edit(i, j, img.getImage()));     // apply the filter
        System.out.println("infant loop?");
        System.out.println(i);
        System.out.println(j);
      }
    }
    this.images.put(newName, new ImageImpl(newName, temp));
  }

  @Override
  public void brightness(String filename, String newName, int adjustment) {
    // temp represents essentially an empty image that will be filled in and returned as flipped
    // image at end of method.
    Image img = images.get(filename);

    int[][][] temp = new int[img.getHeight()][img.getWidth()][3];

    // nested for loop to iterate through image
    for (int i = 0; i < img.getHeight(); i++) {
      for (int j = 0; j < img.getWidth(); j++) {

        // alter each pixel value by the given adjustment and call checkMax on the new pixel
        temp[i][j] = checkMax(filename,
                new int[]{img.getPixel(i, j)[0] + adjustment,
                        img.getPixel(i, j)[1] + adjustment,
                        img.getPixel(i, j)[2] + adjustment});
      }
    }

    // image gets overwritten to the new adjusted image
    this.images.put(newName, new ImageImpl(newName, temp));
  }

  /**
   * Check that the given pixel of a file adheres to its max pixel value and does not go below 0.
   *
   * @param filename - the image being references
   * @param pixel    - the pixel
   * @return - a new pixel based on adhering to max and min pixel rules (0 < pixel channel < max)
   */
  private int[] checkMax(String filename, int[] pixel) {
    int max = getMax(filename);
    for (int i = 0; i < 3; i++) {
      if (pixel[i] > max) {
        pixel[i] = max;
      } else if (pixel[i] < 0) {
        pixel[i] = 0;
      }
    }
    return pixel;
  }

  @Override
  public boolean isEmpty() {
    return images.isEmpty();
  }

  /**
   * Add an image to our images list.
   *
   * @param path     - the file path
   * @param filename - the name of the image
   */
  @Override
  public void addImage(String path, String filename) {
    this.images.put(filename, ImageUtil.readImage(path, filename));

  }

  @Override
  public int getHeight(String filename) throws IllegalStateException {
    try {
      return this.images.get(filename).getHeight();
    } catch (NullPointerException e) {
      throw new IllegalStateException("No Images Found in Map");
    }
  }

  @Override
  public int getWidth(String filename) throws IllegalStateException {
    try {
      return this.images.get(filename).getWidth();
    } catch (NullPointerException e) {
      throw new IllegalStateException("No Images Found in Map");
    }
  }

  @Override
  public int getMax(String filename) throws IllegalStateException {
    try {
      return this.images.get(filename).getMaxValue();
    } catch (NullPointerException e) {
      throw new IllegalStateException("No Images Found in Map");
    }
  }

  @Override
  public int[] getPixel(String filename, int r, int c) throws IllegalStateException {
    try {
      return this.images.get(filename).getPixel(r, c);
    } catch (NullPointerException e) {
      throw new IllegalStateException("No Images Found in Map");
    }
  }

  @Override
  public Map<Integer, Integer> getHistData(String filename, Histogram type) {
    Image img = images.get(filename);
    Map<Integer, Integer> frequencyDistribution = new TreeMap<>(); // treemap -> distribution rep
    int total = img.getHeight() * img.getWidth();
    int max = 0;

    frequencyDistribution.put(-1, total);
    for (int i = 0; i < 256; i++) {
      frequencyDistribution.put(i, 0);
    }

    // nested for loop to iterate through image
    for (int i = 0; i < img.getHeight(); i++) {
      for (int j = 0; j < img.getWidth(); j++) {
        int pixelValue = type.value(img.getImage()[i][j]);
        int newFrequency = frequencyDistribution.get(pixelValue) + 1;
        frequencyDistribution.put(pixelValue, newFrequency);
        if (newFrequency > max) {
          max = newFrequency;
        }
      }
    }

    frequencyDistribution.put(-2, max);

    return frequencyDistribution;
  }

  @Override
  public ArrayList<String> getImages() {
    ArrayList<String> imageList = new ArrayList<>(this.images.keySet());
    return imageList;
  }
}
