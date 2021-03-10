package org.knime;

import java.io.IOException;
import java.io.OutputStream;

import org.apache.commons.compress.compressors.lz4.FramedLZ4CompressorOutputStream;
import org.knime.DataUtils.RangeArrayState;
import org.openjdk.jmh.annotations.Benchmark;

@SuppressWarnings("javadoc")
public class CommonsCompressBenchmark {

	@Benchmark
	public void rangeDataByteArray(RangeArrayState data) throws IOException {
		try (final OutputStream out = new NoopOutputStream();
				final FramedLZ4CompressorOutputStream compressor = new FramedLZ4CompressorOutputStream(out)) {
			compressor.write(data.data);
		}
	}

	private static final class NoopOutputStream extends OutputStream {

		@Override
		public void write(int b) throws IOException {
			// Do nothing
		}

		@Override
		public void write(byte[] b, int off, int len) throws IOException {
			// Do nothing
		}

	}
}
