package org.knime;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;

@SuppressWarnings("javadoc")
public class DataUtils {

	private static final int DATA_SIZE = 1_000_000;

	@State(Scope.Benchmark)
	public static class RangeArrayState {
		public byte[] data;

		@Setup(Level.Trial)
		public void setup() {
			data = createRangeArray();
		}
	}

	@State(Scope.Benchmark)
	public static class RangeBufferState {
		private byte[] array = createRangeArray();
		public ByteBuffer data;

		@Setup(Level.Invocation)
		public void setup() {
			data = ByteBuffer.wrap(array);
		}
	}

	@State(Scope.Thread)
	public static class RangeStreamState {
		public InputStream data;

		@Setup(Level.Invocation)
		public void setup() {
			data = createRangeStream();
		}
	}

	private static byte[] createRangeArray() {
		final byte[] data = new byte[DATA_SIZE];
		for (int i = 0; i < DATA_SIZE; i++) {
			data[i] = (byte) i;
		}
		return data;
	}

	private static InputStream createRangeStream() {
		return new InputStream() {
			int position = 0;

			@Override
			public int read() throws IOException {
				if (position >= DATA_SIZE) {
					return -1;
				}
				final int val = position;
				position++;
				return val & 0xFF;
			}

			@Override
			public int read(byte[] b, int off, int len) throws IOException {
				final int startPositon = position;
				for (int i = 0; i < len && position < DATA_SIZE; position++, i++) {
					b[i + off] = (byte) position;
				}
				return position - startPositon;
			}
		};
	}
}
