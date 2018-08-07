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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.function.Supplier;
import java.util.stream.Stream;

import org.apache.avro.file.CodecFactory;
import org.apache.avro.file.DataFileWriter;
import org.apache.avro.io.BinaryEncoder;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.io.EncoderFactory;
import org.apache.avro.specific.SpecificDatumWriter;

import com.adsquare.delivery.events.DataEvent;

import lombok.Cleanup;
import lombok.Data;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * Supporting class to serialize {@link DataEvent}s to various formats
 */
@Slf4j
public class DataEventSerializer {

	@Getter
	private final static DatumWriter<DataEvent> WRITER = new SpecificDatumWriter<>(DataEvent.getClassSchema());

	/**
	 * @param event
	 *            the input
	 * @return the avro byte[] representation of the data event
	 */
	public final static byte[] toBytes(final DataEvent event) {
		return DataEventSerializer.toBytes(WRITER, event);
	}

	/**
	 * Writes the given {@link DataEvent}s from the eventStream into the given
	 * file using avro's object container format (see {@link DataFileWriter}).
	 * 
	 * @param eventStream
	 *            a stream of valid and normalized {@link DataEvent}.
	 * @param file
	 *            the output file
	 * @throws IOException
	 */
	public static void writeToFile(Stream<DataEvent> eventStream, File file) throws IOException {
		@Cleanup
		FileOutputStream out = new FileOutputStream(file);
		writeToStream(eventStream, () -> out);
	}

	/**
	 * Writes the given {@link DataEvent}s from the eventStream into the
	 * {@link OutputStream} using avro's object container format (see
	 * {@link DataFileWriter}). Please note: As this method is creating the
	 * {@link OutputStream} via the {@link Supplier}, the {@link OutputStream}
	 * is as well closed by this method.
	 * 
	 * 
	 * @param eventStream
	 *            a stream of valid and normalized {@link DataEvent}.
	 * @param outSupplier
	 *            a {@link Supplier} of an output stream
	 * @throws IOException
	 */
	public static void writeToStream(Stream<DataEvent> eventStream, Supplier<OutputStream> outSupplier) throws IOException {
		final OutputStream out = outSupplier.get();
		@Cleanup
		final DataFileWriter<DataEvent> writer = new DataFileWriter<>(DataEventSerializer.getWRITER());
		writer.setSyncInterval(1024 * 1024);
		writer.setCodec(CodecFactory.deflateCodec(9));
		writer.setMeta("created_at", new Date().getTime());
		writer.create(DataEvent.SCHEMA$, out);

		eventStream.forEach(event -> {
			try {
				writer.append(event);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		});
	}

	private final static <T> byte[] toBytes(final DatumWriter<T> writer, final T event) {
		final ByteArrayOutputStream out = new ByteArrayOutputStream();
		final BinaryEncoder encoder = EncoderFactory.get().binaryEncoder(out, null);

		try {
			writer.write(event, encoder);
			encoder.flush();
			out.close();
		} catch (Exception ex) {
			log.error("error writing object to byte array", ex);
		}
		return out.toByteArray();
	}

}
