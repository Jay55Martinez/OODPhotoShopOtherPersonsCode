import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import model.Image;
import model.ImageImpl;
import model.ImageUtil;
import org.junit.Test;

/**
 * Class to test image interface implementations.
 */
public class TestImage {


  Image smallTest = ImageUtil.readImage("res/smallTest.ppm", "smallTest");
  Image twoByTwo = ImageUtil.readImage("res/twoByTwo.ppm", "twoByTwo");


  int[][][] threeByTwoArray = {{{255, 63, 63}, {255, 62, 62}, {250, 59, 132}},
      {{240, 58, 220}, {210, 78, 251}, {253, 62, 75}}};
  int[][][] twoByTwoArray = {{{255, 0, 0}, {0, 255, 0}},
      {{0, 0, 255}, {255, 255, 0}}};

  Image smallTestMaxConstructor = new ImageImpl("arrayImage", threeByTwoArray, 255);

  @Test
  public void testConstructor() {

    // test image 1
    assertEquals(255, smallTest.getMaxValue());
    assertEquals(3, smallTest.getWidth());
    assertEquals(2, smallTest.getHeight());
    assertEquals("smallTest", smallTest.getFilename());
    assertArrayEquals(threeByTwoArray, smallTest.getImage());

    // test image 2
    assertEquals(255, twoByTwo.getMaxValue());
    assertEquals(2, twoByTwo.getWidth());
    assertEquals(2, twoByTwo.getHeight());
    assertEquals("twoByTwo", twoByTwo.getFilename());
    assertArrayEquals(twoByTwoArray, twoByTwo.getImage());

  }

  @Test
  public void testConstructorPNG() {
    Image testPNG = ImageUtil.readImage("res/test.png", "testPNG");

    int[][] pngArrayFirstRow = {{255, 63, 63}, {255, 62, 62}, {250, 59, 132},
        {240, 58, 220}, {210, 78, 251}};


    // assertArrayEquals(threeByTwoArray, manhattanPNG.getImage());
    assertEquals(5, testPNG.getWidth());
    assertEquals(5, testPNG.getHeight());
    assertEquals(255, testPNG.getMaxValue());
    assertEquals("testPNG", testPNG.getFilename());

    System.out.print(Arrays.deepToString(testPNG.getImage()[0]));
    assertArrayEquals(pngArrayFirstRow, testPNG.getImage()[0]);

  }

  @Test
  public void testConstructorArray() {

    // test image 1
    assertEquals(255, smallTestMaxConstructor.getMaxValue());
    assertEquals(3, smallTestMaxConstructor.getWidth());
    assertEquals(2, smallTestMaxConstructor.getHeight());
    assertEquals("arrayImage", smallTestMaxConstructor.getFilename());
    assertArrayEquals(threeByTwoArray, smallTestMaxConstructor.getImage());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidFileException() {
    Image invalidImg = ImageUtil.readImage("res/invalid.ppm", "invalid");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullInputException() {
    Image invalidImg = new ImageImpl("smallTest", null, 255);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullInputException2() {
    Image invalidImg = new ImageImpl(null, twoByTwoArray, 255);
  }

  // test getHeight
  @Test
  public void testGetHeight() {
    assertEquals(2, smallTest.getHeight());
    assertEquals(2, twoByTwo.getHeight());
  }

  // test getWidth
  @Test
  public void testGetWidth() {
    assertEquals(3, smallTest.getWidth());
    assertEquals(2, twoByTwo.getWidth());
  }

  // test getMax
  @Test
  public void testGetMax() {
    assertEquals(255, smallTest.getMaxValue());
    assertEquals(255, twoByTwo.getMaxValue());
  }

  // test getFile
  @Test
  public void testGetFile() {
    assertEquals("smallTest", smallTest.getFilename());
    assertEquals("twoByTwo", twoByTwo.getFilename());
  }

  // test getPixel
  @Test
  public void testGetPixel() {
    assertArrayEquals(new int[]{255, 63, 63}, smallTest.getPixel(0, 0));
    assertArrayEquals(new int[]{255, 62, 62}, smallTest.getPixel(0, 1));
    assertArrayEquals(new int[]{250, 59, 132}, smallTest.getPixel(0, 2));

    assertArrayEquals(new int[]{240, 58, 220}, smallTest.getPixel(1, 0));
    assertArrayEquals(new int[]{210, 78, 251}, smallTest.getPixel(1, 1));
    assertArrayEquals(new int[]{253, 62, 75}, smallTest.getPixel(1, 2));

    assertArrayEquals(new int[]{255, 0, 0}, twoByTwo.getPixel(0, 0));
    assertArrayEquals(new int[]{0, 255, 0}, twoByTwo.getPixel(0, 1));
    assertArrayEquals(new int[]{0, 0, 255}, twoByTwo.getPixel(1, 0));
    assertArrayEquals(new int[]{255, 255, 0}, twoByTwo.getPixel(1, 1));

  }

  // test getImage
  @Test
  public void testGetImage() {
    assertArrayEquals(threeByTwoArray, smallTest.getImage());
    assertArrayEquals(twoByTwoArray, twoByTwo.getImage());
  }
}