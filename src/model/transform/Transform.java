package model.transform;
/**
 * CHANGES: This interface now has an abstract class that will be implemented by the transformation
 * classes. This reduces code duplication as there is shared functionality throughout the
 * different transformations. See AbstractTransform class for additional documentation.
 *<p></p>
 * CHANGE: This package has been renamed from "greyscalepixel" to "transformation" as it is now
 * designed to handle any image transformation types, not just greyscale operations. We changed
 * the implementation of all classes to use matrices to apply the transformations.
 * These transformations are now all uniform across the different variations, from an input and
 * procedural standpoint.
 *</p>
 * <p></p>
 * Transformations modifies the color go a pixel based on its own color, so this differs from an
 * image filter, which is applied separately to each channel. It can be best described as a
 * contained edit on the pixel, where the only relevant variables are the R, G, B of that pixel.
 * There are many types of image transformations and so using an interface to represent the
 * transformation edit type will allow for future transformations to be easily added as classes
 * that implement the interface. Transformations take a matrix similar to a filter, however have
 * slightly different implementations.
 * </p>
 */

public interface Transform {

  /**
   * given a pixel, edit the current pixel and return a new pixel according to its relevant
   * transformation actions.
   * @param current - the pixel being passed in.
   * @return new pixel - int[]
   * @throws IllegalArgumentException - if the passed in pixel cannot be read properly
   */
  int[] edit(int[] current) throws IllegalArgumentException;
}
