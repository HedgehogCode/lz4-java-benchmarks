package org.knime;

import java.io.IOException;

import org.knime.DataUtils.RangeArrayState;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;

import io.airlift.compress.lz4.Lz4Compressor;

@SuppressWarnings("javadoc")
public class AircompressorBenchmark {

	/** State class initializing the compressor */
	@State(Scope.Thread)
	public static class CompressorState {
		public Lz4Compressor compressor;

		@Setup(Level.Invocation)
		public void setup() {
			compressor = new Lz4Compressor();
		}
	}

	@Benchmark
	public byte[] rangeDataByteBuffer(CompressorState compressor, RangeArrayState data) throws IOException {
		// TODO remove some disadvantages
		final int inputLength = data.data.length;
		final int maxOutputLength = compressor.compressor.maxCompressedLength(inputLength);
		final byte[] output = new byte[maxOutputLength];
		compressor.compressor.compress(data.data, 0, inputLength, output, 0, maxOutputLength);
		return output;
	}
}
