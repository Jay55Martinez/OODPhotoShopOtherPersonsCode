import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import java.util.Arrays;
import model.Image;
import model.ImageModel;
import model.ImageModelImpl;
import model.ImageUtil;

import org.junit.Test;


/**
 * Test class for image utils.
 */
public class TestImageUtils {


  @Test
  public void readPPMTestPPM() {
    Image smallTestPPM = ImageUtil.readPPM("res/smallTest.ppm", "smallTest");

    int[][][] threeByTwoArray = {{{255, 63, 63}, {255, 62, 62}, {250, 59, 132}},
      {{240, 58, 220}, {210, 78, 251}, {253, 62, 75}}};
    // test that image is as expected
    assertArrayEquals(threeByTwoArray, smallTestPPM.getImage());
    assertEquals(3, smallTestPPM.getWidth());
    assertEquals(2, smallTestPPM.getHeight());
    assertEquals(255, smallTestPPM.getMaxValue());

  }

  @Test
  public void readImageTestPNG() {
    Image testPNG = ImageUtil.readImage("res/test.png", "testPNG");

    assertEquals(5, testPNG.getWidth());
    assertEquals(5, testPNG.getHeight());
    assertEquals(255, testPNG.getMaxValue());

    System.out.print(Arrays.deepToString(testPNG.getImage()));
    // check pixel values of first and last of each row to ensure read in correctly
    assertArrayEquals(new int[]{255, 63, 63}, testPNG.getPixel(0, 0));
    assertArrayEquals(new int[]{253, 62, 75}, testPNG.getPixel(1, 0));
    assertArrayEquals(new int[]{246, 59, 178}, testPNG.getPixel(2, 0));
    assertArrayEquals(new int[]{220, 64, 251}, testPNG.getPixel(3, 0));
    assertArrayEquals(new int[]{156, 145, 251}, testPNG.getPixel(4, 0));

    // check pixel values of first and last of each row to ensure read in correctly
    assertArrayEquals(new int[]{210, 78, 251}, testPNG.getPixel(0, 4));
    assertArrayEquals(new int[]{141, 164, 250}, testPNG.getPixel(1, 4));
    assertArrayEquals(new int[]{89, 227, 208}, testPNG.getPixel(2, 4));
    assertArrayEquals(new int[]{56, 247, 132}, testPNG.getPixel(3, 4));
    assertArrayEquals(new int[]{69, 255, 73}, testPNG.getPixel(4, 4));

  }


  @Test
  public void readImageTestJPG() {
    Image testJPG = ImageUtil.readImage("res/testJPG.jpg", "testJPG");

    System.out.print(Arrays.deepToString(testJPG.getImage()));
    // assertArrayEquals(threeByTwoArray, manhattanPNG.getImage());
    assertEquals(5, testJPG.getWidth());
    assertEquals(5, testJPG.getHeight());
    assertEquals(255, testJPG.getMaxValue());

    // check pixel values of first and last of each row to ensure read in correctly
    assertArrayEquals(new int[]{255, 32, 232}, testJPG.getPixel(0, 0));
    assertArrayEquals(new int[]{254, 39, 220}, testJPG.getPixel(1, 0));
    assertArrayEquals(new int[]{225, 67, 206}, testJPG.getPixel(2, 0));
    assertArrayEquals(new int[]{207, 110, 207}, testJPG.getPixel(3, 0));
    assertArrayEquals(new int[]{187, 159, 210}, testJPG.getPixel(4, 0));

    // check pixel values of first and last of each row to ensure read in correctly
    assertArrayEquals(new int[]{180, 117, 187}, testJPG.getPixel(0, 4));
    assertArrayEquals(new int[]{175, 137, 184}, testJPG.getPixel(1, 4));
    assertArrayEquals(new int[]{159, 173, 174}, testJPG.getPixel(2, 4));
    assertArrayEquals(new int[]{137, 206, 161}, testJPG.getPixel(3, 4));
    assertArrayEquals(new int[]{103, 234, 138}, testJPG.getPixel(4, 4));

  }


  @Test
  public void readImageTestBPM() {
    Image testBPM = ImageUtil.readImage("res/test.bmp", "testBPM");

    // assertArrayEquals(threeByTwoArray, manhattanPNG.getImage());
    assertEquals(5, testBPM.getWidth());
    assertEquals(5, testBPM.getHeight());
    assertEquals(255, testBPM.getMaxValue());

    // check pixel values of first and last of each row to ensure read in correctly
    assertArrayEquals(new int[]{255, 63, 63}, testBPM.getPixel(0, 0));
    assertArrayEquals(new int[]{253, 62, 75}, testBPM.getPixel(1, 0));
    assertArrayEquals(new int[]{246, 59, 178}, testBPM.getPixel(2, 0));
    assertArrayEquals(new int[]{220, 64, 251}, testBPM.getPixel(3, 0));
    assertArrayEquals(new int[]{156, 145, 251}, testBPM.getPixel(4, 0));

    // check pixel values of first and last of each row to ensure read in correctly
    assertArrayEquals(new int[]{210, 78, 251}, testBPM.getPixel(0, 4));
    assertArrayEquals(new int[]{141, 164, 250}, testBPM.getPixel(1, 4));
    assertArrayEquals(new int[]{89, 227, 208}, testBPM.getPixel(2, 4));
    assertArrayEquals(new int[]{56, 247, 132}, testBPM.getPixel(3, 4));
    assertArrayEquals(new int[]{69, 255, 73}, testBPM.getPixel(4, 4));

  }

  // test exception file not found
  @Test (expected = IllegalArgumentException.class)
  public void testExceptionInvalid() {
    ImageUtil.readPPM("res/invalid.ppm", "invalidPPM");
  }

  // test exception not PPM type
  @Test (expected = IllegalArgumentException.class)
  public void testExceptionNotPPM() {
    ImageUtil.readPPM("res/test.png", "invalidPPM");
  }



  @Test
  public void testSave() throws IOException {
    ImageModel model = new ImageModelImpl("res/smallTest.ppm", "hello");
    ImageUtil.saveImage("res/hello.ppm", model.getObject("hello"));
    File saved = new File("res/hello.ppm");
    assertTrue(saved.exists());
  }

  @Test
  public void testSave2() throws IOException {
    ImageModel model = new ImageModelImpl("res/test.png", "hello");
    ImageUtil.saveImage("res/hello.png", model.getObject("hello"));
    File saved = new File("res/hello.png");
    assertTrue(saved.exists());

  }

  @Test
  public void testSave3() throws IOException {
    ImageModel model = new ImageModelImpl("res/testJPG.jpg", "hello");
    ImageUtil.saveImage("res/hello.jpg", model.getObject("hello"));
    File saved = new File("res/hello.jpg");
    assertTrue(saved.exists());

  }


  @Test
  public void newLoad() throws IOException {
    int[][][] threeByTwoArray = {{{255, 63, 63}, {255, 62, 62}, {250, 59, 132}},
        {{240, 58, 220}, {210, 78, 251}, {253, 62, 75}}};
    Image img = ImageUtil.readImage("smallTest.ppm", "test");
    assertArrayEquals(threeByTwoArray, img.getImage());
  }
}
