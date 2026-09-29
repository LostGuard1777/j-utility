package utility;

/**
  * Geometry interface. Gives classes some standard transformations, as well as a method to show them.
  *
  * @author John McKeighen
  */
interface Geometry {
  public void translate(float dx, float dy);
  public void scale(float factor);
  public void rotate(float amount);
  public void show();
}

/**
  * Shape interface. Gives objects area and perimeter.
  *
  * @author John McKeighen
  */
interface Shape {
  public float area();
  public float perimeter();
}

/**
  * Point class. Virtually every class here uses this in some way. 2 dimensional.
  *
  * @author John McKeighen
  */
class Point implements Geometry {
	/**
		* The name of the point.
		*/
  String name;
	/**
		* The x-coordinate of the point.
		*/
  float x;
	/**
		* The y-coordinate of the point.
		*/
  float y;

  /**
    * Class constructor.
    *
    * @param name The name of the point. Conventionally a single character like "A" or "P".
    * @param x The x coordinate of the point.
    * @param y The y coordinate of the point.
    */
  public Point(String name, float x, float y) {
    this.name = name;
    this.x = x;
    this.y = y;
  }

  /**
    * Alternative constructor for polar points.
    *
    * @param name The name of the point.
    * @param r The radius, or distance from the origin.
    * @param theta The angle between the point and the x-axis, counterclockwise.
    */
  public static Point polar(String name, float r, float theta){
    float xv = (float) (r * Math.cos(theta));
    float yv = (float) (r * Math.sin(theta));

    return new Point(name, xv, yv);
  }

  /**
    * Gets the radius.
    *
    * @return The distance between this point and the origin.
    */
  public float radius() {
    return (float) Math.sqrt(Math.pow(this.x, 2) + Math.pow(this.y, 2));
  }

  /**
    * Gets the angle.
    *
    * @return The angle between this point and the x-axis.
    */
  public float angle() {
    return (float) Math.atan2(this.x, this.y);
  }

  /**
    * Translates this point.
    *
    * @param dx How far to move on the x-axis.
    * @param dy How far to move on the y-axis.
    */
  public void translate(float dx, float dy) {
    this.x += dx;
    this.y += dy;
  }

  /**
    * Scales this point.
    *
    * @param factor The factor to scale by.
    */
  public void scale(float factor) {
    this.x *= factor;
    this.y *= factor;
  }

  /**
    * Rotates this point by a given angle.
    *
    * @param amount The angle to rotate (in radians).
    */
  public void rotate(float amount) {
    float r = this.radius();
    float theta = this.angle();
    theta += amount;

    this.x = (float) (r * Math.cos(theta));
    this.y = (float) (r * Math.sin(theta));
  }

  /**
    * Dot product operation.
    *
    * @param p1 The first point.
    * @param p2 The second point.
    *
    * @return The dot product of p1 and p2 (the sum of the products of their coordinates).
    */
  public static float dotProduct(Point p1, Point p2) {
    float x1 = p1.x;
    float y1 = p1.y;
    float x2 = p2.x;
    float y2 = p2.y;
    return (x1 * x2) + (y1 * y2);
  }

  /**
    * Distance between two points.
    *
    * @param p1 The first point.
    * @param p2 The second point.
    *
    * @return The distance between p1 and p2, calculated using the Pythagorean Theorem.
    */
  public static float distance(Point p1, Point p2) {
    float dx = p2.x - p1.x;
    float dy = p2.y - p1.y;

    return (float) Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
  }

  /**
    * Measure of an angle between three points.
    * Mathematically would be written as m∠ABC, here written like {@code Point.angle(A, B, C);}.
    *
    * @param A The first point.
    * @param B The second point. The angle is measured from here.
    * @param C The third point.
    *
    * @return The measure of the angle between A, B, and C.
    */
  public static float angle(Point A, Point B, Point C) {
    float a = Point.distance(B, C);
    float b = Point.distance(A, C);
    float c = Point.distance(A, B);

    float num = (float)(Math.pow(a,2) + Math.pow(c,2) - Math.pow(b,2));
    float den = 2 * a * c;

    return (float) Math.acos(num/den);
  }

  /**
    * Prints the point.
    * Result looks like {@code Name(x, y)}
    */
  public void show() {
    System.out.printf("%s(%f,%f)\n",this.name, this.x, this.y);
  }
}

/**
  * Line class. A line extends infinitely in both directions.
	*
	* Now that I think about it, I probably could have extended from the vector class...
  *
  * @author John McKeighen
  */
class Line implements Geometry {
	/**
		* The first point on the line.
		*/
  Point p1;
	/**
		* The second point on the line.
		*/
  Point p2;

  /**
    * Class constructor.
    *
    * @param p1 The first point.
    * @param p2 The second point.
    */
  public Line(Point p1, Point p2) {
    this.p1 = p1;
    this.p2 = p2;
  }

  /**
    * Alternative constructor using slope and y-intercept.
    *
    * @param m The slope of the line.
    * @param b The y-intercept of the line.
    */
  public Line(float m, float b) {
    var firstPoint = new Point("P1",0,b);
    var secondPoint = new Point("P2",1,m + b);
    this.p1 = firstPoint;
    this.p2 = secondPoint;
  }

  /**
    * Translates the line.
    *
    * @param dx How far to move along the x-axis.
    * @param dy How far to move along the y-axis.
    */
  public void translate(float dx, float dy) {
    this.p1.translate(dx, dy);
    this.p2.translate(dx, dy);
  }

  /**
    * John write a description here
    *
    * @param factor The amount to scale by.
    *
    * @deprecated Dilating each point in the line would scale only the y-intercept, which is not what I imagine people would use this function for. Use {@link Line#scaleSlope(float)} to scale the slope.
    */
  @Deprecated(since = "1.0", forRemoval = false)
  public void scale(float factor) {}

  /**
    * Multiplies the slope by a given factor.
    *
    * @param factor The factor to scale by.
    */
  public void scaleSlope(float factor) {
    /* Get values */
    var b = this.yInt();
    var p1 = this.p1;
    var p2 = this.p2;

    /* Move the points down */
    p1.translate(0, -b);
    p2.translate(0, -b);

    /* Scale the y-values */
    p1.y *= factor;
    p2.y *= factor;

    /* Move the points back up */
    p1.translate(0, b);
    p2.translate(0, b);
  }
    
  
  /**
    * Rotates the line.
    *
    * @param amount the angle (in radians) to rotate by.
    */
  public void rotate(float amount) {
    this.p1.rotate(amount);
    this.p2.rotate(amount);
  }

  /**
    * Gets slope.
    *
    * @return The slope of the line, calculated using rise over run.
    */
  public float slope() {
    Point p1 = this.p1;
    Point p2 = this.p2;

    float rise = p2.y - p1.y;
    float run = p2.x - p1.x;

    return rise/run;
  }

  /**
    * Gets the y-intercept.
    *
    * @return The y-intercept of the line.
    */
  public float yInt() {
    return (this.p1.y - (this.p1.x * this.slope()));
  }

  /**
    * Checks whether a point is on this line or not.
    *
    * @param p The point to check.
    *
    * @return A boolean representing whether the {@code Point} is on the line or not.
    */
  public boolean isOnLine(Point p) {
    float x = p.x;
    float y = p.y;

    float slope = this.slope();

    float b = this.yInt();

    return (y == (slope * x) + b);
  }

  /**
    * Prints the line.
    * Result will look like {@code Line AB}.
    */
  public void show() {
    System.out.printf("Line %s%s\n",this.p1.name,this.p2.name);
  }
}

/**
  * VECTOR! OH YEAH!
  *
  * @author John McKeighen
  */
class Vector implements Geometry {
	/**
		* The start of the vector.
		*/
  Point start;
	/**
		* The end of the vector.
		*/
  Point end;

  /**
    * Class constructor.
    *
    * @param start The initial point of the vector.
    * @param end The terminal point of the vector.
    */
  public Vector(Point start, Point end) {
    this.start = start;
    this.end = end;
  }

  /**
    * Translates the vector.
    *
    * @param dx How far to move on the x-axis.
    * @param dy How far to move on the y-axis.
    */
  public void translate(float dx, float dy) {
    this.start.translate(dx, dy);
    this.end.translate(dx, dy);
  }

  /**
    * Scales the vector.
    *
    * @param factor The amount to scale by.
    */
  public void scale(float factor) {
    this.start.scale(factor);
    this.end.scale(factor);
  }

  /**
    * Rotates the vector.
    *
    * @param amount The angle (in radians) to rotate the vector.
    */
  public void rotate(float amount) {
    this.start.rotate(amount);
    this.end.rotate(amount);
  }

  /**
    * Prints this vector.
    * Result will look like {@code Start->End(x1,y1),(x2,y2)}
    */
  public void show() {
    System.out.printf("%s->%s(%f, %f),(%f, %f)\n",this.start.name, this.end.name, this.start.x, this.start.y, this.end.x, this.end.y);
  }

  /**
    * A vector is a line with both direction and MAGNITUDE! OH YEAH!
    *
    * @return The length of this vector.
    */
  public float magnitude() {
    Point movedPoint = new Point("TEST",this.end.x - this.start.x, this.end.y - this.start.y);
    return movedPoint.radius();
  }
}

/**
  * Triangle object.
  *
  * @author John McKeighen
  */
class Triangle implements Geometry, Shape {
	/**
		* The first point.
		*/
  Point A;
	/**
		* The second point.
		*/
  Point B;
	/**
		* The third point.
		*/
  Point C;

  /**
    * Class constructor.
    *
    * @param A The first point.
    * @param B The second point.
    * @param C The third point.
    */
  public Triangle(Point A, Point B, Point C) {
    this.A = A;
    this.B = B;
    this.C = C;
  }

  /**
    * Translates the entire shape in a given direction.
    *
    * @param dx How far to move on the x-axis.
    * @param dy How far to move on the y-axis.
    */
  public void translate(float dx, float dy) {
    this.A.translate(dx, dy);
    this.B.translate(dx, dy);
    this.C.translate(dx, dy);
  }

  /**
    * Scales the triangle.
    *
    * @param factor The amount to scale by.
    */
  public void scale(float factor) {
    this.A.scale(factor);
    this.B.scale(factor);
    this.C.scale(factor);
  }

  /**
    * Rotates each vertex by a given angle in radians.
    *
    * @param amount The angle (in radians) to rotate by.
    */
  public void rotate(float amount) {
    this.A.rotate(amount);
    this.B.rotate(amount);
    this.C.rotate(amount);
  }

  /**
    * Prints this triangle.
    *
    * Results look like {@code Triangle ABC  A(x1, y1)  B(x2, y2)  C(x3, y3)}
    */
  public void show() {
    System.out.printf("Triangle %s%s%s\n",this.A.name, this.B.name, this.C.name);
    this.A.show();
    this.B.show();
    this.C.show();
  }

  /**
    * Gets the perimeter.
    *
    * @return The perimeter of the triangle.
    */
  public float perimeter() {
    float a = Point.distance(this.B, this.C);
    float b = Point.distance(this.A, this.C);
    float c = Point.distance(this.A, this.B);

    return a + b + c;
  }

  /**
    * Gets area.
    *
    * @return The area of the triangle, calculated using Heron's formula.
    */
  public float area() {
    float a = Point.distance(this.B, this.C);
    float b = Point.distance(this.A, this.C);
    float c = Point.distance(this.A, this.B);

    float s = this.perimeter()/2;

    return (float) Math.sqrt(s * (s-a) * (s-b) * (s-c));
  }
}

/**
  * Circle class.
  *
  * @author John McKeighen
  */
class Circle implements Geometry, Shape {
	/**
		* The center of the circle.
		*/
  Point center;
	/**
		* The radius of the circle.
		*/
  float radius;
	/**
		* The name of the circle.
		*/
  String name;

  /**
    * Class constructor.
    *
    * @param name The name of the circle.
    * @param center The center of the circle, given by a {@code Point} object.
    * @param radius The radius of the circle.
    */
  public Circle(String name, Point center, float radius) {
    this.name = name;
    this.center = center;
    this.radius = radius;
  }

  /**
    * Alternative constructor with x and y coordinates instead of a {@code Point}.
    *
    * @param name The name of the circle.
    * @param xc The x-coordinate of the circle's center.
    * @param yc The y-coordinate of the circle's center.
    * @param radius The radius of the circle.
    */
  public Circle(String name, float xc, float yc, float radius) {
    this.name = name;
    this.center = new Point("Center(" + name + ")", xc, yc);
    this.radius = radius;
  }

  /**
    * Translates the circle.
    *
    * @param dx How far to move on the x-axis.
    * @param dy How far to move on the y-axis.
    */
  public void translate(float dx, float dy) {
    this.center.translate(dx, dy);
  }

  /**
    * Scales the circle. Does not scale the center point because that would complicate things, so users must manually scale the center.
    *
    * @param factor The amount to scale by.
    */
  public void scale(float factor) {
    this.radius *= factor;
  }

  /**
    * John write a description here
    *
    * @deprecated Rotating a circle does not do anything geometrically, but the method is in an interface so i did not provide an actual method.
    */
  @Deprecated(since = "1.0", forRemoval = false)
  public void rotate(float amount) {}

  /**
    * Gets a point on the circle.
    *
    * @param angle The angle of the point relative to the center.
    *
    * @return A point on the circle with the given angle.
    */
  public Point pointOnCircle(float angle) {
    float X = (float)((this.radius * Math.cos(angle)) + this.center.x);
    float Y = (float)((this.radius * Math.sin(angle)) + this.center.y);

    return new Point("Point on Circle " + this.name, X, Y);
  }

  /**
    * Gets the circumference.
    *
    * @return The circumference of this circle, calculated using C = 2πr
    */
  public float perimeter() {
    return (float)(2 * Math.PI * this.radius);
  }

  /**
    * Gets area.
    *
    * @return The area of this circle, calculated using A = πr².
    */
  public float area() {
    return (float)(Math.PI * Math.pow(this.radius, 2));
  }

  /**
    * Prints the circle.
    * Result will look like {@code Circle Name   Center = c   Radius = r}
    */
  public void show() {
    System.out.printf("Circle %s \n Center = (%f, %f), Radius = %f\n", this.name, this.center.x, this.center.y, this.radius);
  }
}