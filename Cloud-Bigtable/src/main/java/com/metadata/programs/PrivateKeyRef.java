package com.metadata.programs;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import com.google.api.client.util.PemReader;
import com.google.api.client.util.SecurityUtils;
import com.google.api.client.util.PemReader.Section;

public class PrivateKeyRef {
	public static void main(String[] args) throws IOException {

		String privateKeyPkcs8 = "-----BEGIN PRIVATE KEY-----\nMIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQDOr7hv8LONEu63\nu4vEFyqImIQwPj04Y+sgVL9eCGuksxq4pu51MfV7omVFFJ1Zg8s7ZfRBCFBC7bCE\nPRKlIVWkvvU0lp5rZarNoop3nil+QtYoJMzWLYUL1nLHWH1XQf6FLe5Pxn5XMlzE\ncnRM1/bR9Qo18sXDCCpgzknTKlSfVWGi7o8OVc24qgBCtbNN++cO0Tkz6IGA5IY+\noVB01T/NvtksAGJ+dkeN8ooclBIcLmxM10YVJEkgstvXPTnRBLDAX9/AyDmcgcVr\nBd6L3tC6BenQW53UGB7W1fRgVnxm6AlXO1yDXwDFAUxg60szg4NbadD+ebq14SPV\nyGwzBIT3AgMBAAECggEAIF607BMui7GjYXMl+JgIOUxpp4SX4fUEJhQfxIJdGXZT\ngQfj4MbqQ2qzoZYQ7qwNcEgPcP/0UgcX1h4ahXIrD5o4nPvZ48gQcBQ8y28h4Hxn\nPfJFgmWuqKP1ZfMd7kSPsBv5btUPhGiGzt1+CWrpZQiTiM902Y611GN6fG/swOc+\nCO3PJxPaz0ceB441A3iBkEq4yBZsuybCk7u7xdwhfYo2sP9c3STdIi3Q7tCLTvrh\n1KCM7vWCznB3yxXVn3FJsTax8cRcNBhhSh0Foov5I+Z2eomex3h2MIeOEyBYnyuB\ncDygCxCA/BkMPHkEKnnkwCyfvhhl3PxhTj4Rkj9dgQKBgQD61/pXUnr5/B+0lsG0\nhPYi1J3DsggljA84JRXGy6tcSwCa3yA/rLE2tZfBfU2Bp12Pwx33JGivrLXcr9Zz\n+WeZTRNzdBjG02TFme4W1c63/x8vi+tWkJT9ZTbBpTZ+AVAHJkugqG7XFrqfh4jn\nd5tlY4Z4sOn8bEOHyAeCo8/rbwKBgQDS719dUtNlt03niiBgg6Wqd7/nK7K+4pa1\nZTXASvycb37i1ZqNdPG0STdjixYGZEzAN3UHhR+4e0oPbUwEkDVOdXvVPfZVPlLw\nMOkYiFRxuYOzQj3Di4/1haGgmz07RCs/jcRtpXMnkhdgsIx5mHA3qAp0kZk43Zza\npDdwb3Ta+QKBgQDlcazRNua5xr6RhtxD7OLfVS/BkxUUkxpQtstGfYGO+CPjhXiQ\nBbxLWKspFx5QG/De0RCVwT3ogsgOxuAYUOdMOE40attG8pHYlUZMqOFfOAMkkI2B\nLtq5NV1yn1ZKx5SWFZdTqv1HzMiDp4u4fZUslm6n8tr0Q2s5xePowUIsDQKBgGCz\nptek6g9eqtN0aoV0tRViktLLIMYMISWGAc9nUUnyf4xEevdQ3qJVUpRLJkzF3L3e\nbsOEz1cHBpvt8oTBC/8tMxk+r9jCmslM0wAROA1vDCa5PpGQQO5BVijIIpkyFlWr\ni5rs188TVa5ZIf142jFZ03k/aqw8RPWjhVsBbeQZAoGAJ9UEZp0r/I0fnuT5Gv6K\nGJzpZpOkiogCh8MOKd5Herod3bAp2BS/lf/e4yItwWrUJZEy9Sd7oZ1u3GSWPkBF\n7H4XgO2JLyYa+iE4j/hY1Onz2QOeg2yUlF2nSq6jfM9uAAHl+ntjysYYnUX4nKpB\n5NwzJ/LcOPxK8Q6OE6xuXtQ=\n-----END PRIVATE KEY-----\n";

		Reader reader = new StringReader(privateKeyPkcs8);
		Section section = PemReader.readFirstSectionAndClose(reader, "PRIVATE KEY");

		if (section == null) {
			throw new IOException("Invalid PKCS#8 data.");
		}
		byte[] bytes = section.getBase64DecodedBytes();
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(bytes);
		try {
			KeyFactory keyFactory = SecurityUtils.getRsaKeyFactory();
			PrivateKey s =  keyFactory.generatePrivate(keySpec);
			System.out.println(s);
		} catch (NoSuchAlgorithmException | InvalidKeySpecException exception) {
			exception.printStackTrace();
		}

	}

}
