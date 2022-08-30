import java.io.IOException;
import java.nio.charset.Charset;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ZipBase64Test {

	private static final String INPUT =
			"Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.";

	private static final String ENCODED =
			"H4sIAAAAAAAAACXM0QkDMQwE0Va2gCOVpAnFEseCZfssqf8Y7nt4853bHFxRDp19bgQT4pYX2hxhLS1rQ5SL0ThuWOeJYXoAjBU+FWm+DuZoVGqNRCW6/M4elu/a4HIPgXQ+JZ8/b3VLR3sAAAA=";

	@Test
	public void zipAndEncode() throws IOException {
		byte[] encoded = ZipBase64.zipAndEncode(INPUT);

		Assertions.assertEquals(ENCODED, new String(encoded, Charset.defaultCharset()));
		// Assertions.assertEquals(ENCODED.getBytes(Charset.defaultCharset())), encoded); // NOT EQUAL!
	}

	@Test
	public void decodeAndDecompress() throws IOException {
		String decoded = ZipBase64.decodeAndDecompress(ENCODED);

		Assertions.assertEquals(INPUT, decoded);
	}

	@Test
	public void roundtrip() throws IOException {

		byte[] enc = ZipBase64.zipAndEncode(INPUT);

		String decoded = ZipBase64.decodeAndDecompress(new String(enc, Charset.defaultCharset()));

		Assertions.assertEquals(INPUT, decoded);
	}

}
