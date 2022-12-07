
import java.util.Map;
import model.Image;
import model.ImageUtil;
import model.filter.Sharpen;

import model.transform.GreyscaleBlue;
import model.transform.GreyscaleGreen;
import model.transform.GreyscaleIntensity;
import model.transform.GreyscaleLuma;
import model.transform.Sepia;
import model.transform.Transform;
import model.transform.GreyscaleRed;
import model.transform.GreyscaleValue;
import model.ImageModel;
import model.ImageModelImpl;
import model.filter.Blur;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


/**
 * Test class for modelPPM.
 */
public class TestModel {
  String threeByTwoFile = "smallTest";

  ImageModel model32 = new ImageModelImpl("res/smallTest.ppm", threeByTwoFile);
  Image smallTestImg = ImageUtil.readImage("res/smallTest.ppm", threeByTwoFile);
  String twoByTwoFile = "twoByTwo";
  ImageModel model22 = new ImageModelImpl("res/twoByTwo.ppm", twoByTwoFile);

  Image twoByTwoImg = ImageUtil.readImage("res/twoByTwo.ppm", twoByTwoFile);
  String invalidFile = "res/invalid.ppm";

  int[][][] threeByTwoArray = {{{255, 63, 63}, {255, 62, 62}, {250, 59, 132}},
      {{240, 58, 220}, {210, 78, 251}, {253, 62, 75}}};
  int[][][] twoByTwoArray = {{{255, 0, 0}, {0, 255, 0}},
      {{0, 0, 255}, {255, 255, 0}}};



  // test Constructor, ensure arrays are the same
  @Test
  public void testModelConstructor() {

    // test that boards are same
    assertArrayEquals(threeByTwoArray, model32.getImage(threeByTwoFile));
    assertArrayEquals(twoByTwoArray, model22.getImage(twoByTwoFile));

    // test that max are same
    assertEquals(255, model32.getMax(threeByTwoFile));
    assertEquals(255, model22.getMax(twoByTwoFile));


  }

  @Test (expected = IllegalArgumentException.class)
  public void testConstructorException() {
    new ImageModelImpl(invalidFile, "hey");

  }

  @Test (expected = IllegalArgumentException.class)
  public void testConstructorException2() {
    new ImageModelImpl("does not exist.ppm", "hey");

  }

  @Test
  public void testEmptyConstructorAndIsEmpty() {
    ImageModel emptyModel = new ImageModelImpl();
    assertTrue(emptyModel.isEmpty());
  }

  @Test
  public void testIsEmptyFalse() {
    assertFalse(model32.isEmpty());
  }

  // test the empty construct - should be null bc nothing added yet so throws error
  @Test (expected = IllegalStateException.class)
  public void testEmptyConstructorException() {
    ImageModel emptyModel = new ImageModelImpl();
    emptyModel.getImage("hello");
  }

  @Test (expected = IllegalStateException.class)
  public void testEmptyConstructorHeight() {
    ImageModel emptyModel = new ImageModelImpl();
    emptyModel.getHeight("hello");
  }

  @Test (expected = IllegalStateException.class)
  public void testEmptyConstructorMax() {
    ImageModel emptyModel = new ImageModelImpl();
    emptyModel.getMax("hello");
  }

  @Test (expected = IllegalStateException.class)
  public void testEmptyConstructorGetPixel() {
    ImageModel emptyModel = new ImageModelImpl();
    emptyModel.getPixel("hello", 1, 2);
  }





  // test getHeight()
  @Test
  public void testGetHeight() {
    assertEquals(2, model32.getHeight(threeByTwoFile));
    assertEquals(2, model22.getHeight(twoByTwoFile));
  }

  // test getWidth()
  @Test
  public void testGetWidth() {
    assertEquals(3, model32.getWidth(threeByTwoFile));
    assertEquals(2, model22.getWidth(twoByTwoFile));
  }


  // test getMaxValue()
  @Test
  public void testGetMax() {
    assertEquals(255, model32.getMax(threeByTwoFile));
    assertEquals(255, model22.getMax(twoByTwoFile));

    // reduce from 255 and check again, should still be 255
    model32.brightness(threeByTwoFile, "reduced", -10);
    assertEquals(255, model32.getMax("reduced"));
  }


  // getPixel()
  @Test
  public void testGetPixel() {
    assertArrayEquals(new int[]{255, 63, 63}, model32.getPixel(threeByTwoFile, 0, 0));
    assertArrayEquals(new int[]{255, 62, 62}, model32.getPixel(threeByTwoFile, 0, 1));
    assertArrayEquals(new int[]{250, 59, 132}, model32.getPixel(threeByTwoFile, 0, 2));

    assertArrayEquals(new int[]{240, 58, 220}, model32.getPixel(threeByTwoFile, 1, 0));
    assertArrayEquals(new int[]{210, 78, 251}, model32.getPixel(threeByTwoFile, 1, 1));
    assertArrayEquals(new int[]{253, 62, 75}, model32.getPixel(threeByTwoFile, 1, 2));

    assertArrayEquals(new int[]{255, 0, 0}, model22.getPixel(twoByTwoFile, 0, 0));
    assertArrayEquals(new int[]{0, 255, 0}, model22.getPixel(twoByTwoFile, 0, 1));
    assertArrayEquals(new int[]{0, 0, 255}, model22.getPixel(twoByTwoFile, 1, 0));
    assertArrayEquals(new int[]{255, 255, 0}, model22.getPixel(twoByTwoFile, 1, 1));

  }


  // test an invalid State exception for invalid pixel position input
  @Test(expected = IllegalStateException.class)
  public void getPixelInvalid() {
    model32.getPixel(threeByTwoFile, 10, 10);
  }


  // test an invalid State exception for invalid pixel position input
  @Test(expected = IllegalStateException.class)
  public void getPixelInvalid2() {
    model32.getPixel(threeByTwoFile, -1, 3);
  }

  // test an invalid State exception for invalid pixel position input
  @Test(expected = IllegalStateException.class)
  public void getPixelInvalid3() {
    model32.getPixel(threeByTwoFile, 1, -3);
  }

  // test an invalid State exception for invalid pixel position input
  @Test(expected = IllegalStateException.class)
  public void getPixelInvalid4() {
    model32.getPixel(threeByTwoFile, 1, 11);
  }

  // test an invalid State exception for invalid pixel position input
  @Test(expected = IllegalStateException.class)
  public void getPixelInvalid5() {
    model32.getPixel(threeByTwoFile, 11, 1);
  }


  // test flip image horizontally on a 3x2 image
  // boolean parameter: false = horizontal, true = vertical
  @Test
  public void flipImageHorizontal32() {
    String newFile = "newFile";
    String newFile2 = "hey";
    // void method so need to test side effects.
    assertArrayEquals(threeByTwoArray, model32.getImage(threeByTwoFile));
    // image before flip, as expected

    model32.flip(threeByTwoFile, newFile, false); // flip image

    // new flipped photo should match this list.
    int[][][] threeByTwoArrayFlippedHorizontal = {{{240, 58, 220}, {210, 78, 251}, {253, 62, 75}},
        {{255, 63, 63}, {255, 62, 62}, {250, 59, 132}}};

    assertArrayEquals(threeByTwoArrayFlippedHorizontal, model32.getImage(newFile));

    // flip it again horizontally
    model32.flip(newFile, newFile2, false);
    int[][][] threeByTwoArrayFlippedHorizontal2 = {{{255, 63, 63}, {255, 62, 62}, {250, 59, 132}},
        {{240, 58, 220}, {210, 78, 251}, {253, 62, 75}}};

    assertArrayEquals(threeByTwoArrayFlippedHorizontal2, model32.getImage(newFile2));
  }

  @Test
  public void testGetImage() {
    String threeByTwoFile = "smallTest";
    String twoByTwoFile = "twoByTwo";
    assertArrayEquals(threeByTwoArray, model32.getImage(threeByTwoFile));
    assertArrayEquals(twoByTwoArray, model22.getImage(twoByTwoFile));
  }

  // test flip image horizontally on a 2x2 image
  // boolean parameter: false = horizontal, true = vertical
  @Test
  public void flipImageHorizontal22() {
    String newFile = "newFile";
    String newFile2 = "hey";   // void method so need to test side effects.
    assertArrayEquals(twoByTwoArray, model22.getImage(twoByTwoFile));
    // image before flip, as expected

    model22.flip(twoByTwoFile, newFile, false); // flip image


    // new flipped photo should match this list.
    int[][][] twoByTwoArrayFlippedHorizontal = {{{0, 0, 255}, {255, 255, 0}},
        {{255, 0, 0}, {0, 255, 0}}};

    assertArrayEquals(twoByTwoArrayFlippedHorizontal, model22.getImage(newFile));

    // flip it again horizontally
    model22.flip(newFile, newFile2, false);

    int[][][] twoByTwoArrayFlippedHorizontal2 = {{{255, 0, 0}, {0, 255, 0}},
        {{0, 0, 255}, {255, 255, 0}}};

    assertArrayEquals(twoByTwoArrayFlippedHorizontal2, model22.getImage(newFile2));
  }


  // test flip image vertically on a 3x2 image
  // boolean parameter: false = horizontal, true = vertical
  @Test
  public void flipImageVertical32() {
    String newFile = "newFile";
    String newFile2 = "hey";   // void method so need to test side effects.
    // void method so need to test side effects.
    assertArrayEquals(threeByTwoArray, model32.getImage(threeByTwoFile));
    // image before flip, as expected

    model32.flip(threeByTwoFile, newFile, true); // flip image


    // new flipped photo should match this list.
    int[][][] threeByTwoFlippedVertical = {{{250, 59, 132}, {255, 62, 62}, {255, 63, 63}},
        {{253, 62, 75}, {210, 78, 251}, {240, 58, 220}}};

    assertArrayEquals(threeByTwoFlippedVertical, model32.getImage(newFile));


    // flip it again horizontally
    model32.flip(newFile, newFile2, true);
    int[][][] threeByTwoFlippedVertical2 = {{{255, 63, 63}, {255, 62, 62}, {250, 59, 132}},
        {{240, 58, 220}, {210, 78, 251}, {253, 62, 75}}};

    assertArrayEquals(threeByTwoFlippedVertical2, model32.getImage(newFile2));
  }


  // test flip image vertically on a 2x2 image
  // boolean parameter: false = horizontal, true = vertical
  @Test
  public void flipImageVertical22() {
    String newFile = "newFile";
    String newFile2 = "hey";
    assertArrayEquals(twoByTwoArray, model22.getImage(twoByTwoFile));
    // image before flip, as expected

    model22.flip(twoByTwoFile, newFile, true); // flip image

    // new flipped photo should match this list.
    int[][][] twoByTwoArrayFlippedVertical = {{{0, 255, 0}, {255, 0, 0}},
        {{255, 255, 0}, {0, 0, 255}}};

    assertArrayEquals(twoByTwoArrayFlippedVertical, model22.getImage(newFile));

    // flip it again horizontally
    model22.flip(newFile, newFile2, true);
    int[][][] twoByTwoArrayFlippedVertical2 = {{{255, 0, 0}, {0, 255, 0}},
        {{0, 0, 255}, {255, 255, 0}}};

    assertArrayEquals(twoByTwoArrayFlippedVertical2, model22.getImage(newFile2));
  }


  // test brightness adjustment method
  @Test
  public void testBrightness() {
    String newFile = "newFile";
    String newFile2 = "hey";    // void method so test side effects
    int[][][] threeByTwoArray = {{{255, 63, 63}, {255, 62, 62}, {250, 59, 132}},
        {{240, 58, 220}, {210, 78, 251}, {253, 62, 75}}};

    assertArrayEquals(threeByTwoArray, model32.getImage(threeByTwoFile));
    // check image before changes

    model32.brightness(threeByTwoFile, newFile, 10);

    int[][][] threeByTwoArrayPlus10 = {{{255, 73, 73}, {255, 72, 72}, {255, 69, 142}},
        {{250, 68, 230}, {220, 88, 255}, {255, 72, 85}}};

    assertArrayEquals(threeByTwoArrayPlus10, model32.getImage(newFile));

    model32.brightness(newFile, newFile2, -15);

    int[][][] threeByTwoArrayMinus15 = {{{240, 58, 58}, {240, 57, 57}, {240, 54, 127}},
        {{235, 53, 215}, {205, 73, 240}, {240, 57, 70}}};

    assertArrayEquals(threeByTwoArrayMinus15, model32.getImage(newFile2));
  }


  // test greyscaleRed method on a 3x2 image
  @Test
  public void testTransformRed32() {
    String newFile = "newFile";
    // void method so need to test side effects
    // test image before
    assertArrayEquals(threeByTwoArray, model32.getImage(threeByTwoFile));
    // red
    model32.transform(threeByTwoFile, newFile, new GreyscaleRed());

    int[][][] threeByTwoArrayRedScale = {{{255, 255, 255}, {255, 255, 255}, {250, 250, 250}},
        {{240, 240, 240}, {210, 210, 210}, {253, 253, 253}}};

    assertArrayEquals(threeByTwoArrayRedScale, model32.getImage(newFile)); // test side effects

  }

  // test greyscaleGreen method on a 3x2 image
  @Test
  public void testTransformGreen32() {
    String newFile = "newFile";
    // void method so need to test side effects
    // test image before
    assertArrayEquals(threeByTwoArray, model32.getImage(threeByTwoFile));
    // red
    model32.transform(threeByTwoFile, newFile, new GreyscaleGreen());

    int[][][] threeByTwoArrayGreenScale = {{{63, 63, 63}, {62, 62, 62}, {59, 59, 59}},
        {{58, 58, 58}, {78, 78, 78}, {62, 62, 62}}};


    assertArrayEquals(threeByTwoArrayGreenScale, model32.getImage(newFile)); // test side effects

  }


  // test greyscaleBlue method on a 3x2 image
  @Test
  public void testTransformBlue32() {
    String newFile = "newFile";
    // void method so need to test side effects
    // test image before
    assertArrayEquals(threeByTwoArray, model32.getImage(threeByTwoFile));
    // red
    model32.transform(threeByTwoFile, newFile, new GreyscaleBlue());

    int[][][] threeByTwoArrayBlueScale = {{{63, 63, 63}, {62, 62, 62}, {132, 132, 132}},
        {{220, 220, 220}, {251, 251, 251}, {75, 75, 75}}};

    assertArrayEquals(threeByTwoArrayBlueScale, model32.getImage(newFile)); // test side effects

  }

  // test greyscaleValue method on a 3x2 image
  @Test
  public void testTransformValue32() {
    String newFile = "newFile";
    // void method so need to test side effects
    // test image before
    assertArrayEquals(threeByTwoArray, model32.getImage(threeByTwoFile));
    // red
    model32.transform(threeByTwoFile, newFile, new GreyscaleValue());

    int[][][] threeByTwoArrayValueScale = {{{255, 255, 255}, {255, 255, 255}, {250, 250, 250}},
        {{240, 240, 240}, {251, 251, 251}, {253, 253, 253}}};

    assertArrayEquals(threeByTwoArrayValueScale, model32.getImage(newFile)); // test side effects

  }

  // test greyscaleIntensity method on a 3x2 image
  @Test
  public void testTransformIntensity32() {
    String newFile = "newFile";
    // void method so need to test side effects
    // test image before
    assertArrayEquals(threeByTwoArray, model32.getImage(threeByTwoFile));
    // red
    model32.transform(threeByTwoFile, newFile, new GreyscaleIntensity());

    int[][][] threeByTwoArrayIntensityScale = {{{127, 127, 127}, {126, 126, 126}, {147, 147, 147}},
        {{172, 172, 172}, {179, 179, 179}, {130, 130, 130}}};

    assertArrayEquals(threeByTwoArrayIntensityScale, model32.getImage(newFile));
    // test side effects
  }


  // test greyscaleLuma method on a 3x2 image
  @Test
  public void testTransformLuma32() {
    String newFile = "newFile";
    // void method so need to test side effects
    // test image before
    assertArrayEquals(threeByTwoArray, model32.getImage(threeByTwoFile));
    // red
    model32.transform(threeByTwoFile, newFile, new GreyscaleLuma());

    int[][][] threeByTwoArrayLumaScale = {{{103, 103, 103}, {103, 103, 103}, {104, 104, 104}},
        {{108, 108, 108}, {118, 118, 118}, {103, 103, 103}}};

    assertArrayEquals(threeByTwoArrayLumaScale, model32.getImage(newFile)); // test side effects

  }

  // test greyscaleLuma method on a 3x2 image
  @Test
  public void testTransformSepia() {
    String newFile = "newFile";
    // void method so need to test side effects
    // test image before
    assertArrayEquals(threeByTwoArray, model32.getImage(threeByTwoFile));
    // red
    model32.transform(threeByTwoFile, newFile, new Sepia());

    int[][][] threeByTwoArraySepia = {{{160, 142, 111}, {159, 141, 110}, {168, 149, 116}},
        {{180, 160, 125}, {189, 168, 131}, {161, 143, 111}}};


    assertArrayEquals(threeByTwoArraySepia, model32.getImage(newFile)); // test side effects

  }

  // test greyscaleLuma method on a 3x2 image
  @Test
  public void testTransformValue() {
    String newFile = "newFile";
    // void method so need to test side effects
    // test image before
    assertArrayEquals(threeByTwoArray, model32.getImage(threeByTwoFile));
    // red
    model32.transform(threeByTwoFile, newFile, new GreyscaleValue());

    int[][][] threeByTwoArrayValue = {{{255, 255, 255}, {255, 255, 255}, {250, 250, 250}},
        {{240, 240, 240}, {251, 251, 251}, {253, 253, 253}}};

    assertArrayEquals(threeByTwoArrayValue, model32.getImage(newFile)); // test side effects

  }

  // test greyscale on Red
  @Test
  public void testEditTypeRed() {
    /// void method so need to test side effects
    int[] pixel = new int[]{240, 40, 50};
    // test the void methods effect on individual pixels, to then test them later on entire images
    // red
    Transform redGreyscale = new GreyscaleRed();
    assertArrayEquals(new int[]{240, 240, 240}, redGreyscale.edit(pixel));
  }

  // test greyscale on  Green
  @Test
  public void testEditTypeGreen() {
    /// void method so need to test side effects
    int[] pixel = new int[]{240, 40, 50};
    // test the void methods effect on individual pixels, to then test them later on entire images
    // red
    Transform greenGreyscale = new GreyscaleGreen();
    assertArrayEquals(new int[]{40, 40, 40}, greenGreyscale.edit(pixel));
  }

  // test greyscale on Blue
  @Test
  public void testEditTypeBlue() {
    /// void method so need to test side effects
    int[] pixel = new int[]{240, 40, 50};
    Transform blueGreyscale = new GreyscaleBlue();
    assertArrayEquals(new int[]{50, 50, 50}, blueGreyscale.edit(pixel));
  }

  // test greyscale on Luma
  @Test
  public void testEditTypeLuma() {
    /// void method so need to test side effects
    int[] pixel = new int[]{240, 40, 50};
    Transform lumaGreyscale = new GreyscaleLuma();
    assertArrayEquals(new int[]{83, 83, 83}, lumaGreyscale.edit(pixel));
  }

  // test greyscaleValue exception
  @Test
  public void testEditTypeValue() {
    /// void method so need to test side effects
    int[] pixel = new int[]{240, 40, 50};
    Transform valueGreyscale = new GreyscaleValue();
    assertArrayEquals(new int[]{240, 240, 240}, valueGreyscale.edit(pixel));
  }

  // test greyscale on Intensity
  @Test
  public void testEditTypeIntensity() {
    /// void method so need to test side effects
    int[] pixel = new int[]{240, 40, 50};
    Transform intensityGreyscale = new GreyscaleIntensity();
    assertArrayEquals(new int[]{110, 110, 110}, intensityGreyscale.edit(pixel));
  }

  // test greyscale on Intensity
  @Test
  public void testEditTypeSepia() {
    /// void method so need to test side effects
    int[] pixel = new int[]{240, 40, 50};
    Transform intensityGreyscale = new Sepia();
    assertArrayEquals(new int[]{134, 119, 93}, intensityGreyscale.edit(pixel));
  }

  // test addImage
  @Test
  public void testAddImage() {

    ImageModel model = new ImageModelImpl(); // empty model

    model.addImage("res/smallTest.ppm", "small");

    // test the added image is what we expect
    assertArrayEquals(threeByTwoArray, model.getImage("small"));

    // do it again with 2x2 image
    model.addImage("res/twoByTwo.ppm", "smaller");
    assertArrayEquals(twoByTwoArray, model.getImage("smaller"));

  }

  @Test
  public void testBlurSharpenFilter() throws IOException {
    Image image = ImageUtil.readImage("res/hello.png", "city");

    ImageModel model = new ImageModelImpl(image);

    model.filter("city", "blur", new Blur());

    model.filter("city", "sharpen", new Sharpen());

    assertArrayEquals(new int[]{15, 3, 9}, model.getPixel("blur", 0, 0));
    assertArrayEquals(new int[]{0, 0, 0}, model.getPixel("sharpen", 0, 0));

    ImageUtil.saveImage("blur.ppm", model.getObject("blur"));
    ImageUtil.saveImage("sharpen.ppm", model.getObject("sharpen"));

  }

  @Test
  public void testGetHistDataBlue() {
    Map<Integer, Integer> result = model32.getHistData("smallTest", new GreyscaleBlue());
    int[][][] smallTestArray = new int[][][]{{{255, 63, 63}, {255, 62, 62}, {250, 59, 132}},
      {{240, 58, 220}, {210, 78, 251}, {253, 62, 75}}};
    int count63 = result.get(63);
    int count62 = result.get(62);
    int count132 = result.get(132);
    int count220 = result.get(220);
    int count251 = result.get(251);
    int count75 = result.get(75);


    assertEquals(count63, 1);
    assertEquals(count62, 1);
    assertEquals(count132, 1);
    assertEquals(count220, 1);
    assertEquals(count251, 1);
    assertEquals(count75, 1);

  }

  @Test
  public void testGetHistDataRed() {
    Map<Integer, Integer> result = model32.getHistData("smallTest", new GreyscaleRed());
    int[][][] smallTestArray = new int[][][]{{{255, 63, 63}, {255, 62, 62}, {250, 59, 132}},
        {{240, 58, 220}, {210, 78, 251}, {253, 62, 75}}};
    int count255 = result.get(255);
    int count250 = result.get(250);
    int count240 = result.get(240);
    int count210 = result.get(210);
    int count253 = result.get(253);

    assertEquals(count255, 2);
    assertEquals(count250, 1);
    assertEquals(count240, 1);
    assertEquals(count210, 1);
    assertEquals(count253, 1);

  }




}

