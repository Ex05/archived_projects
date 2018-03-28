package de.janik.devils.math;

import static de.janik.devils.math.MathHelper.pow2;

/**
 * My representation of a Vector.
 * 
 * @author Jan.Marcel.Janik
 * 
 */
public class Vector
{

	/**
	 * The number of elements(floats) which form a vector.
	 */
	public static final int	ELEMENTS	= 3;

	/**
	 * The x,y,z value of the Vector.
	 */
	public float			x, y, z;

	public Vector(float x, float y, float z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public Vector(Vector v)
	{
		this(v.getX(), v.getY(), v.getZ());
	}

	public Vector()
	{
		this(0, 0, 0);
	}

	public float length()
	{
		return (float) Math.sqrt((pow2(x) + pow2(y) + pow2(z)));
	}

	public Vector add(Vector v)
	{
		this.x += v.getX();
		this.y += v.getY();
		this.z += v.getZ();

		return this;
	}

	public void add(float scalar)
	{
		add(new Vector(scalar, scalar, scalar));
	}

	public void subtract(Vector v)
	{
		this.x -= v.getX();
		this.y -= v.getY();
		this.z -= v.getZ();
	}

	public void subtract(float scalar)
	{
		subtract(new Vector(scalar, scalar, scalar));
	}

	public Vector multiply(Vector v)
	{
		this.x *= v.getX();
		this.y *= v.getY();
		this.z *= v.getZ();

		return this;
	}

	public Vector multiply(float scalar)
	{
		this.x *= scalar;
		this.y *= scalar;
		this.z *= scalar;

		return this;
	}

	public void divide(Vector v)
	{
		this.x /= v.getX();
		this.y /= v.getY();
		this.z /= v.getZ();
	}

	public void divide(float scalar)
	{
		divide(new Vector(scalar, scalar, scalar));
	}

	public Vector normalVector(Vector v)
	{
		return Normal(this, v);
	}

	public Vector normalize()
	{
		float length = length();

		this.x = x / length;
		this.y = y / length;
		this.z = z / length;

		return this;
	}

	// <-toString, hashCode & equals->

	@Override
	public String toString()
	{
		return "Vector [x=" + x + ", y=" + y + ", z=" + z + "]";
	}

	// <-Getter & Setter->

	public float getX()
	{
		return x;
	}

	public Vector setX(float x)
	{
		this.x = x;
		return this;
	}

	public float getY()
	{
		return y;
	}

	public Vector setY(float y)
	{
		this.y = y;
		return this;
	}

	public float getZ()
	{
		return z;
	}

	public Vector setZ(float z)
	{
		this.z = z;
		return this;
	}

	// <-Static's->

	public static float Scalar(Vector v)
	{
		return v.length();
	}

	public static Normal Normal(Vector v1, Vector v2)
	{
		// KreuzProdukt xD
		float n1 = (v1.getY() * v2.getZ()) - (v2.getY() * v1.getZ());
		float n2 = (v1.getZ() * v2.getX()) - (v2.getZ() * v1.getX());
		float n3 = (v1.getX() * v2.getY()) - (v2.getX() * v1.getY());

		return new Normal(n1, n2, n3);
	}

	public static Vector Multiply(Vector v1, Vector v2)
	{
		Vector v = new Vector(v1);
		v.multiply(v2);
		return v;
	}

	public static Vector Divide(Vector v1, Vector v2)
	{
		Vector v = new Vector(v1);
		v.divide(v2);
		return v;
	}

	public static Vector Add(Vector v1, Vector v2)
	{
		Vector v = new Vector(v1);
		v.add(v2);
		return v;
	}

	public static Vector Subtract(Vector v1, Vector v2)
	{
		Vector v = new Vector(v1);
		v.subtract(v2);
		return v;
	}

	public static float Distance(Vector a, Vector b)
	{
		return Subtract(b, a).length();
	}

	public static Vector Normalize(Vector v)
	{
		return v.normalize();
	}
}
