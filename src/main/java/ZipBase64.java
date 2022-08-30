import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Base64;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public final class ZipBase64 {

	private ZipBase64() {
		//
	}

	public static byte[] zipAndEncode(final String input) throws IOException {
		byte[] encBytes = null;

		final byte[] zipBytes = compress(input);
		if ((zipBytes != null) && (zipBytes.length > 0)) {
			encBytes = Base64.getEncoder().encode(zipBytes);
		}

		return encBytes;
	}

	/**
	 * No reencoding with ".toString()". Work on ByteArray
	 *
	 * @param input
	 *
	 * @return {@link byte[]}
	 *
	 * @throws IOException
	 */
	private static byte[] compress(final String input) throws IOException {

		if ((input == null) || (input.length() == 0)) {
			return new byte[0];
		}

		final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		final GZIPOutputStream gzip = new GZIPOutputStream(byteArrayOutputStream);
		gzip.write(input.getBytes(Charset.defaultCharset()));
		gzip.close();

		return byteArrayOutputStream.toByteArray();
	}

	public static String decodeAndDecompress(String input) throws IOException {
		final byte[] decodedBytes = Base64.getDecoder().decode(input);
		return decompress(decodedBytes);
	}

	private static String decompress(final byte[] compressed) throws IOException {
		StringBuilder stringBuilder = new StringBuilder();

		if ((compressed == null) || (compressed.length == 0)) {
			return "";
		}

		if (isCompressed(compressed)) {
			final GZIPInputStream gis = new GZIPInputStream(new ByteArrayInputStream(compressed));
			final BufferedReader bufferedReader =
					new BufferedReader(new InputStreamReader(gis, Charset.defaultCharset()));

			String line;
			while ((line = bufferedReader.readLine()) != null) {
				stringBuilder.append(line);
			}
		}
		else {
			return new String(compressed, Charset.defaultCharset());
		}

		return stringBuilder.toString();
	}

	private static boolean isCompressed(final byte[] compressed) {
		return (compressed[0] == (byte) (GZIPInputStream.GZIP_MAGIC))
				&& (compressed[1] == (byte) (GZIPInputStream.GZIP_MAGIC >> 8));
	}

}
