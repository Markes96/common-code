package org.mk96.common.util;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;

import org.mk96.common.constant.DateFormatConstant;
import org.mk96.common.type.MkException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.StdDateFormat;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import lombok.Getter;
import lombok.experimental.UtilityClass;

@UtilityClass
public class MkSerializeUtils {

	@Getter
	protected final ObjectMapper mapper;

	static {
		mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

		final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DateFormatConstant.DATE_TIME);

		mapper.registerModule(new JavaTimeModule());
		mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		mapper.setDateFormat(new StdDateFormat().withTimeZone(TimeZone.getTimeZone("UTC")).withColonInTimeZone(true));
		mapper.registerModule(
				new JavaTimeModule().addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(formatter)));
	}

	/**
	 * Method used to deserialize an {@link String} json
	 *
	 * @param <T>   deserialize type
	 * @param type  -> indicates deserialize type
	 * @param value -> json to deserialize
	 * @return Object deserialized
	 */
	public <T> T read(final Class<T> type, final String value) {
		try {
			return mapper.readValue(value, type);
		} catch (final JsonProcessingException e) {
			throw new MkException("Could not deserialize " + value + " on " + type.getSimpleName() + " object", e);
		}
	}

	/**
	 * Method used to deserialize an object to {@link Byte} array json
	 *
	 * @param <T>   deserialize type
	 * @param type  -> indicates serialize type
	 * @param value -> json to serialize
	 * @return object deserialized
	 */
	public <T> T read(final Class<T> type, final byte[] value) {
		try {
			return mapper.readValue(value, type);
		} catch (final IOException e) {
			throw new MkException("Could not deserialize " + value + " on " + type.getSimpleName() + " object", e);
		}
	}

	/**
	 * Method used to serialize an object as {@link String} with default pretty
	 * printer
	 *
	 * @param obj to serialize
	 * @throws JsonProcessingException Intermediate base class for all problems
	 *                                 encountered when processing (parsing,
	 *                                 generating) JSON content that are not pure
	 *                                 I/O problems
	 * @return object serialized
	 */
	public String writeAsString(final Object obj) {
		try {
			return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
		} catch (final JsonProcessingException e) {
			throw new MkException("Could not serialize " + obj.getClass().getSimpleName() + " on string", e);
		}
	}

	/**
	 * Method used to serialize and object as {@link String} with flat printer
	 *
	 * @param obj to serialize
	 * @throws JsonProcessingException Intermediate base class for all problems
	 *                                 encountered when processing (parsing,
	 *                                 generating) JSON content that are not pure
	 *                                 I/O problems
	 * @return object serialized
	 */
	public String writeAsFlatString(final Object obj) {
		try {
			return mapper.writeValueAsString(obj);
		} catch (final JsonProcessingException e) {
			throw new MkException("Could not serialize " + obj.getClass().getSimpleName() + " on flat string", e);
		}
	}

	/**
	 * Method used to serialize and object as {@link byte} array
	 *
	 * @param obj to serialize
	 * @throws JsonProcessingException Intermediate base class for all problems
	 *                                 encountered when processing (parsing,
	 *                                 generating) JSON content that are not pure
	 *                                 I/O problems
	 * @return object serialized
	 */
	public byte[] writeAsBytes(final Object obj) {
		try {
			return mapper.writerWithDefaultPrettyPrinter().writeValueAsBytes(obj);
		} catch (final JsonProcessingException e) {
			throw new MkException("Could not serialize " + obj.getClass().getSimpleName() + " on bytes", e);
		}
	}

	/**
	 * Method used to serialize and object as {@link byte} array with flat printer
	 *
	 * @param obj to serialize
	 * @throws JsonProcessingException Intermediate base class for all problems
	 *                                 encountered when processing (parsing,
	 *                                 generating) JSON content that are not pure
	 *                                 I/O problems
	 * @return object serialized
	 */
	public byte[] writeAsFlatBytes(final Object obj) {
		try {
			return mapper.writeValueAsBytes(obj);
		} catch (final JsonProcessingException e) {
			throw new MkException("Error: Could not serialize " + obj.getClass().getSimpleName() + " on flat bytes", e);
		}
	}

}
