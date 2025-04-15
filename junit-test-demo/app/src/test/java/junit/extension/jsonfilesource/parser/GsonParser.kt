package junit.extension.jsonfilesource.parser

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter
import junit.extension.jsonfilesource.JsonFileSource
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.lang.reflect.Type
import java.nio.charset.Charset
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalAccessor
import java.util.Calendar
import java.util.Date
import java.util.GregorianCalendar


class GsonParser : AbstractParser() {

    companion object {
        private val GSON = GsonBuilder()
            .disableHtmlEscaping()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .registerTypeHierarchyAdapter(ZonedDateTime::class.java, ZonedDateTimeAdapter())
            .registerTypeAdapter(Date::class.java, DateAdapter())
            .registerTypeAdapter(LocalDateTime::class.java, LocalDateTimeAdapter())
            .registerTypeAdapter(LocalDate::class.java, LocalDateAdapter())
            .registerTypeHierarchyAdapter(Calendar::class.java, CalendarAdapter())
            .create()

    }

    override fun readObject(
        inputStream: InputStream,
        charset: Charset,
        annotation: JsonFileSource,
        type: Type
    ): Any {
        val jsonParser: Gson = GSON
        val reader = InputStreamReader(inputStream, charset)
        return jsonParser.fromJson(reader, type)
    }


}

private abstract class DateTimeAdapter<T> : TypeAdapter<T>() {

    companion object {
        val FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(ZoneId.systemDefault())

        val FORMATTERS: List<DateTimeFormatter> = listOf(
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(ZoneId.systemDefault()),
            DateTimeFormatter.ISO_OFFSET_DATE_TIME,
            DateTimeFormatter.ISO_DATE,
            DateTimeFormatter.ISO_LOCAL_DATE_TIME
        )

    }

    abstract fun from(time: Long): T?

    abstract fun from(temporalAccessor: TemporalAccessor): T?

    @Throws(IOException::class)
    override fun write(out: JsonWriter, value: T?) {
        if (value == null) {
            out.nullValue()
            return
        }
        out.value(value.toString())
    }

    @Throws(IOException::class)
    override fun read(`in`: JsonReader): T? {
        val token: JsonToken = `in`.peek()
        if (token === JsonToken.NUMBER) {
            return from(`in`.nextLong())
        } else if (token === JsonToken.STRING) {
            val time: String = `in`.nextString()
            for (formatter in FORMATTERS) {
                try {
                    val temporalAccessor = formatter.parse(time)
                    return from(temporalAccessor)
                } catch (e: Exception) {
                    // ignore
                }
            }
        } else if (token === JsonToken.NULL) {
            `in`.nextNull()
            return null
        }
        throw IllegalArgumentException("Unsupported date time format: " + `in`.nextString())
    }
}

private class DateAdapter : DateTimeAdapter<Date>() {
    override fun from(time: Long): Date {
        return Date(time);
    }

    override fun from(temporalAccessor: TemporalAccessor): Date {
        return Date.from(Instant.from(temporalAccessor));
    }

    override fun write(out: JsonWriter, value: Date?) {
        if (value == null) {
            out.nullValue();
            return;
        }
        out.value(FORMATTER.format(ZonedDateTime.from(value.toInstant().atZone(ZoneId.systemDefault()))));
    }
}

private class ZonedDateTimeAdapter : DateTimeAdapter<ZonedDateTime>() {
    override fun from(time: Long): ZonedDateTime {
        return ZonedDateTime.ofInstant(Instant.ofEpochMilli(time), ZoneId.systemDefault())
    }

    override fun from(temporalAccessor: TemporalAccessor): ZonedDateTime {
        return ZonedDateTime.from(temporalAccessor)
    }
}

private class LocalDateTimeAdapter : DateTimeAdapter<LocalDateTime>() {
    override fun from(time: Long): LocalDateTime {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(time), ZoneId.systemDefault())
    }

    override fun from(temporalAccessor: TemporalAccessor): LocalDateTime {
        return LocalDateTime.from(temporalAccessor)
    }
}

private class LocalDateAdapter : DateTimeAdapter<LocalDate>() {
    override fun from(time: Long): LocalDate {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(time), ZoneId.systemDefault())
            .toLocalDate()
    }

    override fun from(temporalAccessor: TemporalAccessor): LocalDate {
        return LocalDate.from(temporalAccessor)
    }
}

private class CalendarAdapter : DateTimeAdapter<Calendar>() {
    override fun from(time: Long): Calendar {
        return GregorianCalendar.from(
            ZonedDateTime.ofInstant(
                Instant.ofEpochMilli(
                    time
                ), ZoneId.systemDefault()
            )
        )
    }

    override fun from(temporalAccessor: TemporalAccessor): Calendar? {
        return GregorianCalendar.from(ZonedDateTime.from(temporalAccessor))
    }
}