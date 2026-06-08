package utility;

/**
  * A functional interface for calculating Riemann sums.
  *
  * @author John McKeighen
  * @version 1.1
  */
@FunctionalInterface
interface RiemannSum {
  /**
    * The integrand of the integral.
    *
    * @param t The variable to integrate with respect to.
    * @return The integrand calculated with the input.
    */
  double integrand(double t);

  /**
    * Evaluates the integral.
    *
    * @param a The lower integration bound.
    * @param b The upper integration bound.
    * @param dx The step size.
    *
    * @return An approximation for the integral from a to b, with step size dx.
    *
    * @throws IllegalArgumentException if the upper bound is less than the lower bound.
    *
    * @since 1.1
    */
  default double eval(double a, double b, double dx) throws IllegalArgumentException {
    double runningTotal = 0;
    if (b <= a) {
      throw new IllegalArgumentException("Upper bound cannot be less than lower bound.");
    }
    for (double i = a; i < b; i += dx) {
      runningTotal += integrand(i);
    }
    runningTotal *= dx;
    return runningTotal;
  }
}

/**
  * A class that contains some functions that are frequently used.
  *
  * @author John McKeighen
  * @version 1.2
  */
class Util {
  /**
    * Base 2 logarithm. Calculated using change of base formula.
    *
    * @param arg The number to take the log of.
    * @return Logarithm to base 2.
    * @since 1.0
    */
  public static double log2(double arg) {
    return Math.log(arg)/Math.log(2);
  }
  /**
    * Factorial function. Only works on integers for right now.
    * Might add a gamma function, after I add an integral.
    *
    * @param arg The number to take the factorial of.
    * @return The factorial of the number.
    * @since 1.0
    */
  public static int fact(int arg) {
    int runningTotal = 1;
    for (int i = 1; i <= arg; i++) {
      runningTotal *= i;
    }
    return runningTotal;
  }
  /**
    * Cube root function.
    *
    * @param arg The radicand.
    * @return The cube root of the radicand.
    * @since 1.0
    */
  public static double cbrt(double arg) {
    return Math.pow(arg, 1/3);
  }
  /**
    * Nth root function.
    *
    * @param index The index.
    * @param arg The radicand.
    * @return The nth root of the radicand.
    * @since 1.0
    */
  public static double nthroot(int index, double arg) {
    return Math.pow(arg, 1/index);
  }

  /**
    * Generates a random {@code double} between the given start and end values.
    * 
    * @param start The lower bound.
    * @param end The upper bound.
    *
    * @return A random number between start and end.
    *
    * @throws IllegalArgumentException if start is less than end.
    *
    * @since 1.2
    */
  public static double rand(int start, int end) throws IllegalArgumentException {
    if (start <= end) {
      throw new IllegalArgumentException("Parameter 'start' cannot be less than parameter 'end'");
    }
    double rvalue = Math.random();
    return (end - start) * rvalue + start;
  }

  /**
    * Gets a random element from a series of elements.
    *
    * @param elems The list of elements to pick from.
    * @param <T> The type of the elements.
    * 
    * @return A random element from the list.
    *
    * @throws IllegalArgumentException if the element list is empty. 
    *
    * @since 1.2
    */
  public static <T> T rand(T... elems) throws IllegalArgumentException {
    int size = elems.length;
    if (size == 0) {
      throw new IllegalArgumentException("Element list is empty.");
    }
    int index = (int) Math.round(size * Math.random());
    return elems[index];
  }
}