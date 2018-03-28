package de.janik.devils.utility;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;
import java.nio.ShortBuffer;

/**
 * A utility class, to handle Java-NIO buffer.
 * 
 * @author Jan.Marcel.Janik
 * 
 */
public class BufferTools
{
	/*
	 * The amount of bytes, which has to be reserved in a buffer, to store a
	 * single value of one of the given data-types.
	 */
	/**
	 * The amount of bytes, in one "byte"
	 */
	public static final byte	SIZE_BYTE	= 0x1;
	/**
	 * The amount of bytes, in one "short"
	 */
	public static final byte	SIZE_SHORT	= 0x2;
	/**
	 * The amount of bytes, in one "integer"
	 */
	public static final byte	SIZE_INT	= 0x4;
	/**
	 * The amount of bytes, in one "float"
	 */
	public static final byte	SIZE_FLOAT	= 0x4;
	/**
	 * The amount of bytes, in one "long"
	 */
	public static final byte	SIZE_LONG	= 0x8;
	/**
	 * The amount of bytes, in one "double"
	 */
	public static final byte	SIZE_DOUBLE	= 0x8;

	// <Byte-Buffer>
	/**
	 * 
	 * @param size
	 * @return An emty byteBuffer with the size of <b>size</b>
	 */
	public static ByteBuffer createByteBuffer(int size)
	{
		ByteBuffer temp = ByteBuffer.allocateDirect(SIZE_BYTE * size);
		temp.order(ByteOrder.nativeOrder());

		return temp;
	}

	/**
	 * 
	 * @param bytes
	 * @return A byteBuffer filled with the values of <b>bytes</b>
	 */
	public static ByteBuffer createByteBufferDirect(byte... bytes)
	{
		ByteBuffer temp = ByteBuffer.allocateDirect(SIZE_BYTE * bytes.length);
		temp.order(ByteOrder.nativeOrder());

		temp.put(bytes).flip();

		return temp;
	}

	// <Short-Buffer>
	/**
	 * 
	 * @param size
	 * @return An emty shortBuffer with the size of <b>size</b>
	 */
	public static ShortBuffer createShortBuffer(int size)
	{
		ByteBuffer temp = ByteBuffer.allocateDirect(SIZE_SHORT * size);
		temp.order(ByteOrder.nativeOrder());

		return temp.asShortBuffer();
	}

	/**
	 * 
	 * @param shorts
	 * @return A bshortBuffer filled with the values of <b>shorts</b>
	 */
	public static ShortBuffer createShortBufferDirect(short... shorts)
	{
		ByteBuffer temp = ByteBuffer.allocateDirect(SIZE_SHORT * shorts.length);
		temp.order(ByteOrder.nativeOrder());

		temp.asShortBuffer().put(shorts).flip();

		return temp.asShortBuffer();
	}

	// <Int-Buffer>
	/**
	 * 
	 * @param size
	 * @return An emty intBuffer with the size of <b>size</b>
	 */
	public static IntBuffer createIntBuffer(int size)
	{
		ByteBuffer temp = ByteBuffer.allocateDirect(SIZE_INT * size);
		temp.order(ByteOrder.nativeOrder());

		return temp.asIntBuffer();
	}

	/**
	 * 
	 * @param ints
	 * @return A intBuffer filled with the values of <b>ints</b>
	 */
	public static IntBuffer createIntBufferDirect(int... ints)
	{
		ByteBuffer temp = ByteBuffer.allocateDirect(SIZE_INT * ints.length);
		temp.order(ByteOrder.nativeOrder());

		temp.asIntBuffer().put(ints).flip();

		return temp.asIntBuffer();
	}

	// <Long-Buffer>
	/**
	 * 
	 * @param size
	 * @return An emty longBuffer with the size of <b>size</b>
	 */
	public static LongBuffer createLongBuffer(int size)
	{
		ByteBuffer temp = ByteBuffer.allocateDirect(SIZE_LONG * size);
		temp.order(ByteOrder.nativeOrder());

		return temp.asLongBuffer();
	}

	/**
	 * 
	 * @param longs
	 * @return A longBuffer filled with the values of <b>longs</b>
	 */
	public static LongBuffer createLongBufferDirect(long... longs)
	{
		ByteBuffer temp = ByteBuffer.allocateDirect(SIZE_LONG * longs.length);
		temp.order(ByteOrder.nativeOrder());

		temp.asLongBuffer().put(longs).flip();

		return temp.asLongBuffer();
	}

	// <Float-Buffer>
	/**
	 * 
	 * @param size
	 * @return An emty floatBuffer with the size of <b>size</b>
	 */
	public static FloatBuffer createFloatBuffer(int size)
	{
		ByteBuffer temp = ByteBuffer.allocateDirect(SIZE_FLOAT * size);
		temp.order(ByteOrder.nativeOrder());

		return temp.asFloatBuffer();
	}

	/**
	 * 
	 * @param floats
	 * @return A flaotBuffer filled with the values of <b>floats</b>
	 */
	public static FloatBuffer createFloatBufferDirect(float... floats)
	{
		ByteBuffer tmp = ByteBuffer.allocateDirect(SIZE_FLOAT * floats.length);
		tmp.order(ByteOrder.nativeOrder());

		tmp.asFloatBuffer().put(floats).flip();

		return tmp.asFloatBuffer();
	}

	// <Double-Buffer>
	/**
	 * 
	 * @param size
	 * @return An emtydoubleBuffer with the size of <b>size</b>
	 */
	public static DoubleBuffer createDoubleBuffer(int size)
	{
		ByteBuffer temp = ByteBuffer.allocateDirect(SIZE_DOUBLE * size);
		temp.order(ByteOrder.nativeOrder());

		return temp.asDoubleBuffer();
	}

	/**
	 * 
	 * @param doubles
	 * @return A doubleBuffer filled with the values of <b>doubles</b>
	 */
	public static DoubleBuffer createDoubleBufferDirect(double... doubles)
	{
		ByteBuffer temp = ByteBuffer.allocateDirect(SIZE_LONG * doubles.length);
		temp.order(ByteOrder.nativeOrder());

		temp.asDoubleBuffer().put(doubles).flip();

		return temp.asDoubleBuffer();
	}

	/**
	 * Toggles the mode (read/write) and set's the positon back to 0
	 * 
	 * @param buffer
	 */
	public static void flip(Buffer buffer)
	{
		buffer.flip();
	}

	public static void rewind(Buffer buffer)
	{
		buffer.rewind();
	}

	/**
	 * Mark's the current position, to return to this, call reset()
	 * 
	 * @param buffer
	 */
	public static void mark(Buffer buffer)
	{
		buffer.mark();
	}

	/**
	 * Set's the position, to the point mark() was called.
	 * 
	 * @param buffer
	 */
	public static void reset(Buffer buffer)
	{
		buffer.reset();
	}

	/**
	 * Set's the position to 0, and the limit to capasity
	 * 
	 * @param buffer
	 */
	public static void clear(Buffer buffer)
	{
		buffer.clear();
	}

	/**
	 * Print's some information of the buffer (capasity,limit, position,
	 * readOnly, direct) + the values, it's holding.
	 * 
	 * @param buffer
	 */
	public static void printBuffer(Buffer buffer)
	{
		int capasity = buffer.capacity();
		int limit = buffer.limit();
		int position = buffer.position();
		boolean direct = buffer.isDirect();
		boolean readOnly = buffer.isReadOnly();

		System.out.print("Capasity: [" + capasity + "] \t");
		System.out.print("Limit: [" + limit + "] \t");
		System.out.print("Position: [" + position + "] \t");
		System.out.print("Direct: [" + direct + "] \t");
		System.out.println("ReadOnly: [" + readOnly + "]");

		System.out.print("Values: ");

		if (buffer instanceof ByteBuffer)
		{
			while (buffer.hasRemaining())
			{
				System.out.print(((ByteBuffer) buffer).get());

				if (buffer.hasRemaining())
					System.out.print(",");

			}
		}
		else
			if (buffer instanceof IntBuffer)
			{
				while (buffer.hasRemaining())
				{
					System.out.print(((IntBuffer) buffer).get());

					if (buffer.hasRemaining())
						System.out.print(",");
				}
			}
			else
				if (buffer instanceof FloatBuffer)
				{
					while (buffer.hasRemaining())
					{
						System.out.print(((FloatBuffer) buffer).get());

						if (buffer.hasRemaining())
							System.out.print(",");
					}
				}
				else
					if (buffer instanceof LongBuffer)
					{
						while (buffer.hasRemaining())
						{
							System.out.print(((LongBuffer) buffer).get());

							if (buffer.hasRemaining())
								System.out.print(",");
						}
					}
					else
						if (buffer instanceof DoubleBuffer)
						{
							while (buffer.hasRemaining())
							{
								System.out.print(((DoubleBuffer) buffer).get());

								if (buffer.hasRemaining())
									System.out.print(",");
							}
						}
						else
							if (buffer instanceof ShortBuffer)
							{
								while (buffer.hasRemaining())
								{
									System.out.print(((ShortBuffer) buffer).get());

									if (buffer.hasRemaining())
										System.out.print(",");
								}
							}
		System.out.print("\n");
	}
}
