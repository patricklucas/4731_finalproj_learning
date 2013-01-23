package pathtest;



import java.awt.Dimension;
import java.awt.Point;


/**
 * 2D Float Vector with basic math functions. Immutable.
 * 
 * @author efruchter
 * 
 */
public class V2 {

	public final float x, y;

	public final static V2 ZERO = new V2();
	public final static V2 ONE = new V2(1, 1);
	public final static V2 UP_RIGHT = ONE.unit();
	public final static V2 DOWN_RIGHT = ONE.scale(1, -1).unit();
	public final static V2 RIGHT = new V2(1, 0);

	public V2(final float x, final float y) {
		this.x = x;
		this.y = y;
	}

	public V2(final double x, final double y) {
		this.x = (float) x;
		this.y = (float) y;
	}

	public V2(final Dimension dim) {
		this.x = dim.width;
		this.y = dim.height;
	}

	public V2(final float both) {
		this(both, both);
	}

	public V2(final V2 base) {
		this.x = base.getX();
		this.y = base.getY();
	}

	public V2(final Point base) {
		this.x = base.x;
		this.y = base.y;
	}

	public V2() {
		this(0, 0);
	}

	public float getX() {
		return this.x;
	}

	public float getY() {
		return this.y;
	}

	public V2 add(final V2 o) {
		return new V2(this.x + o.getX(), this.y + o.getY());
	}

	public V2 add(final float x, final float y) {
		return new V2(this.x + x, this.y + y);
	}

	public V2 add(final float both) {
		return add(both, both);
	}

	public V2 sub(final V2 o) {
		return new V2(this.x - o.getX(), this.y - o.getY());
	}

	public V2 sub(final float o) {
		return new V2(this.x - o, this.y - o);
	}

	public V2 sub(final float x, final float y) {
		return new V2(this.x - x, this.y - y);
	}

	public V2 scale(final float scalar) {
		return new V2(this.x * scalar, this.y * scalar);
	}

	public V2 scale(final float x, final float y) {
		return new V2(this.x * x, this.y * y);
	}

	public V2 scale(final V2 other) {
		return scale(other.x, other.y);
	}

	public float dot(final V2 o) {
		return this.x * o.getX() + this.y * o.getY();
	}

	private float magCache = -1;

	public float mag() {
		if (magCache == -1) {
			magCache = (float) Math.sqrt(x * x + y * y);
		}
		return magCache;
	}

	public V2 unit() {
		if (x == 0 && y == 0)
			return V2.ZERO;
		return this.scale(1f / this.mag());
	}

	public static V2 min(final V2 a, final V2 b) {
		return new V2(Math.min(a.getX(), b.getX()), Math.min(a.getY(),
				b.getY()));
	}

	public static V2 max(final V2 a, final V2 b) {
		return new V2(Math.max(a.getX(), b.getX()), Math.max(a.getY(),
				b.getY()));
	}

	public String toString() {
		return "[" + getX() + ", " + getY() + "]";
	}

	public float dist(final V2 o) {
		return V2.dist(this, o);
	}

	public static float dist(final V2 a, final V2 b) {
		return (float) Math.sqrt(Math.pow(a.getX() - b.getX(), 2)
				+ Math.pow(a.getY() - b.getY(), 2));
	}

	public Point toPoint() {
		return new Point((int) getX(), (int) getY());
	}

	/**
	 * Rotate the vector by a number of radians.
	 * 
	 * @param angle
	 *            the angle to rotate by.
	 * @return the rotated vector.
	 */
	public V2 rotate(final float angle) {
		return new V2((float) (x * Math.cos(angle) - y * Math.sin(angle)),
				(float) (x * Math.sin(angle) + y * Math.cos(angle)));
	}

	/**
	 * Rotate the vector by a number of degrees.
	 * 
	 * @param angle
	 *            the angle to rotate by.
	 * @return the rotated vector.
	 */
	public V2 rotateDeg(final float angle) {
		return rotate((float) Math.toRadians(angle));
	}

	/**
	 * Get the unit direction from start to end.
	 * 
	 * @param start
	 *            the start vector
	 * @param end
	 *            the destination vector
	 * @return the unit vector pointing from start to end
	 */
	public static V2 getDirectionTo(final V2 start, final V2 end) {
		return end.sub(start).unit();
	}

	/**
	 * Build a unit vector out of the direction.
	 * 
	 * @param direction
	 *            direction in radians.
	 * @return
	 */
	public static V2 buildVector(final float direction) {
		return new V2((float) Math.cos(direction),
				(float) Math.sin(direction)).unit();
	}

	/**
	 * Get a unit vector angled from a to b.
	 */
	public static V2 toward(final V2 a, final V2 b) {
		return b.sub(a).unit();
	}

	@Override
	public boolean equals(final Object o) {
		return o instanceof V2
				&& (x == ((V2) o).x && y == ((V2) o).y);
	}

	/**
	 * Get the casted version of x.
	 * 
	 * @return
	 */
	public int getWidth() {
		return (int) x;
	}

	/**
	 * Get the casted version of y.
	 * 
	 * @return
	 */
	public int getHeight() {
		return (int) y;
	}
}
