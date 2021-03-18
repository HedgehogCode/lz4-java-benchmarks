package org.knime;

import org.knime.DataUtils.RangeArrayState;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;

import net.jpountz.lz4.LZ4Compressor;
import net.jpountz.lz4.LZ4Factory;

@SuppressWarnings("javadoc")
public class LZ4JavaUnsafeBenchmark {

	/** State class initializing the compressor */
	@State(Scope.Thread)
	public static class CompressorState {
		public LZ4Compressor compressor;

		@Setup(Level.Invocation)
		public void setup() {
			final LZ4Factory factory = LZ4Factory.unsafeInstance();
			compressor = factory.fastCompressor();
		}
	}

	@Benchmark
	public byte[] rangeDataByteArray(CompressorState compressor, RangeArrayState data) {
		return compressor.compressor.compress(data.data);
	}
}
