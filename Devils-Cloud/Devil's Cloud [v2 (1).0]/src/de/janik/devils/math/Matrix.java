package de.janik.devils.math;

import static de.janik.devils.math.MathHelper.Aspect;
import static de.janik.devils.utility.BufferTools.createFloatBuffer;

import java.nio.FloatBuffer;

/**
 * 
 * @author Jan.Marcel.Janik
 * 
 */
public class Matrix
{
	public static final int	CAPACITY	= 16 /* 4*4 */;

	public static final int	P_0_0		= 0b000_0000_0000;
	public static final int	P_0_1		= 0b000_0000_0001;
	public static final int	P_0_2		= 0b000_0000_0010;
	public static final int	P_0_3		= 0b000_0000_0011;
	public static final int	P_1_0		= 0b000_0000_0100;
	public static final int	P_1_1		= 0b000_0000_0101;
	public static final int	P_1_2		= 0b000_0000_0110;
	public static final int	P_1_3		= 0b000_0000_0111;
	public static final int	P_2_0		= 0b000_0000_1000;
	public static final int	P_2_1		= 0b000_0000_1001;
	public static final int	P_2_2		= 0b000_0000_1010;
	public static final int	P_2_3		= 0b000_0000_1011;
	public static final int	P_3_0		= 0b000_0000_1100;
	public static final int	P_3_1		= 0b000_0000_1101;
	public static final int	P_3_2		= 0b000_0000_1110;
	public static final int	P_3_3		= 0b000_0000_1111;

	private FloatBuffer		matrixBuffer;

	public Matrix()
	{
		matrixBuffer = createFloatBuffer(CAPACITY);
	}

	public void load(FloatBuffer buffer)
	{
		if (buffer.capacity() != CAPACITY)
			throw new IllegalArgumentException("The size of the buffer has to be: " + CAPACITY);

		put(P_0_0, buffer.get(P_0_0));
		put(P_0_1, buffer.get(P_0_1));
		put(P_0_2, buffer.get(P_0_2));
		put(P_0_3, buffer.get(P_0_3));
		put(P_1_0, buffer.get(P_1_0));
		put(P_1_1, buffer.get(P_1_1));
		put(P_1_2, buffer.get(P_1_2));
		put(P_1_3, buffer.get(P_1_3));
		put(P_2_0, buffer.get(P_2_0));
		put(P_2_1, buffer.get(P_2_1));
		put(P_2_2, buffer.get(P_2_2));
		put(P_2_3, buffer.get(P_2_3));
		put(P_3_0, buffer.get(P_3_0));
		put(P_3_1, buffer.get(P_3_1));
		put(P_3_2, buffer.get(P_3_2));
		put(P_3_3, buffer.get(P_3_3));
	}

	public Matrix loadIdentity()
	{
		put(P_0_0, 1);
		put(P_0_1, 0);
		put(P_0_2, 0);
		put(P_0_3, 0);
		put(P_1_0, 0);
		put(P_1_1, 1);
		put(P_1_2, 0);
		put(P_1_3, 0);
		put(P_2_0, 0);
		put(P_2_1, 0);
		put(P_2_2, 1);
		put(P_2_3, 0);
		put(P_3_0, 0);
		put(P_3_1, 0);
		put(P_3_2, 0);
		put(P_3_3, 1);

		return this;
	}

	public void multiply(Matrix a)
	{
		for (int x = 0; x < 4; x++)
			for (int y = 0; y < 4; y++)
				put(x, y, a.get(x, 0) * a.get(0, y) + a.get(x, 1) * a.get(1, y) + a.get(x, 2) * a.get(2, y) + a.get(x, 3) * a.get(3, y));
	}

	public Matrix rotate(Vector vector)
	{
		return rotate(vector.getX(), vector.getY(), vector.getZ());
	}

	public Matrix rotate(float x, float y, float z)
	{
		Matrix rotX = new Matrix();
		Matrix rotY = new Matrix();
		Matrix rotZ = new Matrix();

		x = (float) MathHelper.degreesToRadians(x);
		y = (float) MathHelper.degreesToRadians(y);
		z = (float) MathHelper.degreesToRadians(z);

		rotZ.put(P_0_0, (float) Math.cos(z));
		rotZ.put(P_0_1, -(float) Math.sin(z));
		rotZ.put(P_0_2, 0);
		rotZ.put(P_0_3, 0);
		rotZ.put(P_1_0, (float) Math.sin(z));
		rotZ.put(P_1_1, (float) Math.cos(z));
		rotZ.put(P_1_2, 0);
		rotZ.put(P_1_3, 0);
		rotZ.put(P_2_0, 0);
		rotZ.put(P_2_1, 0);
		rotZ.put(P_2_2, 1);
		rotZ.put(P_2_3, 0);
		rotZ.put(P_3_0, 0);
		rotZ.put(P_3_1, 0);
		rotZ.put(P_3_2, 0);
		rotZ.put(P_3_3, 1);

		rotY.put(P_0_0, 1);
		rotY.put(P_0_1, 0);
		rotY.put(P_0_2, 0);
		rotY.put(P_0_3, 0);
		rotY.put(P_1_0, 0);
		rotY.put(P_1_1, (float) Math.cos(x));
		rotY.put(P_1_2, -(float) Math.sin(x));
		rotY.put(P_1_3, 0);
		rotY.put(P_2_0, 0);
		rotY.put(P_2_1, (float) Math.sin(x));
		rotY.put(P_2_2, (float) Math.cos(x));
		rotY.put(P_2_3, 0);
		rotY.put(P_3_0, 0);
		rotY.put(P_3_1, 0);
		rotY.put(P_3_2, 0);
		rotY.put(P_3_3, 1);

		rotX.put(P_0_0, (float) Math.cos(y));
		rotX.put(P_0_1, 0);
		rotX.put(P_0_2, -(float) Math.sin(y));
		rotX.put(P_0_3, 0);
		rotX.put(P_1_0, 0);
		rotX.put(P_1_1, 1);
		rotX.put(P_1_2, 0);
		rotX.put(P_1_3, 0);
		rotX.put(P_2_0, (float) Math.sin(y));
		rotX.put(P_2_1, 0);
		rotX.put(P_2_2, (float) Math.cos(y));
		rotX.put(P_2_3, 0);
		rotX.put(P_3_0, 0);
		rotX.put(P_3_1, 0);
		rotX.put(P_3_2, 0);
		rotX.put(P_3_3, 1);

		//rotY.multiply(rotX);

		//rotZ.multiply(rotY);

		//load(rotZ.getMatrixBuffer());

		load((Multilply(rotZ, Multilply(rotY, rotX))).getMatrixBuffer());

		return this;
	}

	public void setZero()
	{
		put(P_0_0, 0);
		put(P_0_1, 0);
		put(P_0_2, 0);
		put(P_0_3, 0);
		put(P_1_0, 0);
		put(P_1_1, 0);
		put(P_1_2, 0);
		put(P_1_3, 0);
		put(P_2_0, 0);
		put(P_2_1, 0);
		put(P_2_2, 0);
		put(P_2_3, 0);
		put(P_3_0, 0);
		put(P_3_1, 0);
		put(P_3_2, 0);
		put(P_3_3, 0);
	}

	public FloatBuffer store(FloatBuffer src)
	{
		if (src.capacity() != CAPACITY)
			throw new IllegalArgumentException("The size of the buffer has to be: " + CAPACITY);

		src.put(P_0_0, get(P_0_0));
		src.put(P_0_1, get(P_0_1));
		src.put(P_0_2, get(P_0_2));
		src.put(P_0_3, get(P_0_3));
		src.put(P_1_0, get(P_1_0));
		src.put(P_1_1, get(P_1_1));
		src.put(P_1_2, get(P_1_2));
		src.put(P_1_3, get(P_1_3));
		src.put(P_2_0, get(P_2_0));
		src.put(P_2_1, get(P_2_1));
		src.put(P_2_2, get(P_2_2));
		src.put(P_2_3, get(P_2_3));
		src.put(P_3_0, get(P_3_0));
		src.put(P_3_1, get(P_3_1));
		src.put(P_3_2, get(P_3_2));
		src.put(P_3_3, get(P_3_3));

		return src;
	}

	public Matrix scale(Vector vector)
	{
		return scale(vector.getX(), vector.getY(), vector.getZ());
	}

	public Matrix scale(float x, float y, float z)
	{
		put(P_0_0, x);
		put(P_0_1, 0);
		put(P_0_2, 0);
		put(P_0_3, 0);
		put(P_1_0, 0);
		put(P_1_1, y);
		put(P_1_2, 0);
		put(P_1_3, 0);
		put(P_2_0, 0);
		put(P_2_1, 0);
		put(P_2_2, z);
		put(P_2_3, 0);
		put(P_3_0, 0);
		put(P_3_1, 0);
		put(P_3_2, 0);
		put(P_3_3, 1);

		return this;
	}

	public Matrix translate(Vector vector)
	{
		return translate(vector.getX(), vector.getY(), vector.getZ());
	}

	public Matrix translate(float x, float y, float z)
	{
		put(P_0_0, 1);
		put(P_0_1, 0);
		put(P_0_2, 0);
		put(P_0_3, x);
		put(P_1_0, 0);
		put(P_1_1, 1);
		put(P_1_2, 0);
		put(P_1_3, y);
		put(P_2_0, 0);
		put(P_2_1, 0);
		put(P_2_2, 1);
		put(P_2_3, z);
		put(P_3_0, 0);
		put(P_3_1, 0);
		put(P_3_2, 0);
		put(P_3_3, 1);

		return this;
	}

	public void createProjection(float fov, float width, float height, float zNear, float zFar)
	{
		float aspect = Aspect(width, height);
		float tanHalfFov = (float) Math.tan(Math.toRadians(fov / 2));
		float frustumLength = zNear - zFar;

		float xScale = 1.0f / (tanHalfFov * aspect);
		float yScale = 1.0f / tanHalfFov;
		float zScale = (-zNear - zFar) / frustumLength;
		float zW = 2 * zFar * zNear / frustumLength;

		put(P_0_0, xScale);
		put(P_0_1, 0);
		put(P_0_2, 0);
		put(P_0_3, 0);
		put(P_1_0, 0);
		put(P_1_1, yScale);
		put(P_1_2, 0);
		put(P_1_3, 0);
		put(P_2_0, 0);
		put(P_2_1, 0);
		put(P_2_2, zScale);
		put(P_2_3, zW);
		put(P_3_0, 0);
		put(P_3_1, 0);
		put(P_3_2, 1);
		put(P_3_3, 0);

	}

	public void createProjectionLWJGL_style(float fov, float width, float height, float zNear, float zFar)
	{
		float fieldOfView = fov;
		float aspectRatio = (float) width / (float) height;
		float near_plane = zNear;
		float far_plane = zFar;

		float y_scale = this.coTangent(this.degreesToRadians(fieldOfView / 2f));
		float x_scale = y_scale / aspectRatio;
		float frustum_length = far_plane - near_plane;

		put(P_0_0, x_scale);
		put(P_0_1, 0);
		put(P_0_2, 0);
		put(P_0_3, 0);
		put(P_1_0, 0);
		put(P_1_1, y_scale);
		put(P_1_2, 0);
		put(P_1_3, 0);
		put(P_2_0, 0);
		put(P_2_1, 0);
		put(P_2_2, -((far_plane + near_plane) / frustum_length));
		put(P_2_3, -1);
		put(P_3_0, 0);
		put(P_3_1, 0);
		put(P_3_2, -((2 * near_plane * far_plane) / frustum_length));
		put(P_3_3, 0);
	}

	private float coTangent(float angle)
	{
		return (float) (1f / Math.tan(angle));
	}

	private float degreesToRadians(float degrees)
	{
		return degrees * (float) (Math.PI / 180d);
	}

	public FloatBuffer store()
	{
		return store(createFloatBuffer(CAPACITY));
	}

	// <Getter & Setter>
	public int getSize()
	{
		// return matrixBuffer.capacity();
		return CAPACITY;
	}

	public Matrix put(int position, float value)
	{
		matrixBuffer.put(position, value);

		return this;
	}

	public Matrix put(int x, int y, float value)
	{
		int position = 0;

		position = 4 * x;
		for (int j = 0; j < y; j++)
			position++;

		put(position, value);

		return this;
	}

	public float get(int position)
	{
		return matrixBuffer.get(position);
	}

	private float get(int x, int y)
	{
		int position = 0;

		position = 4 * x;
		for (int j = 0; j < y; j++)
			position++;

		return get(position);
	}

	public FloatBuffer getMatrixBuffer()
	{
		return matrixBuffer;
	}

	// <-Static->
	public static Matrix Load(FloatBuffer src, Matrix dst)
	{
		dst.load(src);

		return dst;
	}

	public static Matrix Load(Matrix src, Matrix dst)
	{
		dst.load(src.getMatrixBuffer());

		return dst;
	}

	public static Matrix LoadIdentityMatrix(Matrix dst)
	{
		dst.loadIdentity();

		return dst;
	}

	public static Matrix SetZero(Matrix dst)
	{
		dst.setZero();

		return dst;
	}

	public static FloatBuffer Store(FloatBuffer src, Matrix dst)
	{
		dst.store(src);

		return src;
	}

	public static FloatBuffer Store(Matrix dst)
	{
		return dst.store();
	}

	public static Matrix Multilply(Matrix a, Matrix b)
	{
		Matrix dst = new Matrix();

		for (int x = 0; x < 4; x++)
			for (int y = 0; y < 4; y++)
				dst.put(x, y, a.get(x, 0) * b.get(0, y) + a.get(x, 1) * b.get(1, y) + a.get(x, 2) * b.get(2, y) + a.get(x, 3) * b.get(3, y));

		return dst;
	}
}
