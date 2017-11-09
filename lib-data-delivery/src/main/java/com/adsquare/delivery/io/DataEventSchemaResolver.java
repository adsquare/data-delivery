/*
 * Copyright (c) 2017 adsquare GmbH
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.adsquare.delivery.io;

import java.util.HashMap;
import java.util.Map;

import org.apache.avro.Schema;
import org.apache.avro.SchemaNormalization;

import com.adsquare.delivery.events.DataEvent;

/**
 * Helper class, which supports resolving {@link Schema} from a given
 * fingerprint
 */
public class DataEventSchemaResolver {

	private static final Map<Long, Schema> resolver = new HashMap<>();

	static {
		resolver.put(SchemaNormalization.fingerprint64(SchemaNormalization.toParsingForm(DataEvent.SCHEMA$).getBytes()), DataEvent.getClassSchema());
	}

	/**
	 * @param schemaFingerprint
	 *            the fingerprint (SchemaNormalization.fingerprint64) of the
	 *            {@link Schema}
	 * @return the {@link Schema} matching the given fingerprint or
	 *         <code>null</code> if not found
	 */
	public Schema resolveSchema(long schemaFingerprint) {
		return resolver.get(schemaFingerprint);
	}
}
