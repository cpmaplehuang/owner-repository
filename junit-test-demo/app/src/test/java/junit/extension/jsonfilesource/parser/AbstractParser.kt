package junit.extension.jsonfilesource.parser

import junit.extension.jsonfilesource.JsonFileSource
import org.junit.jupiter.api.extension.ExtensionContext
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.lang.reflect.Type
import java.nio.charset.Charset
import java.util.stream.Collectors

abstract class AbstractParser {
    open fun parser(
        context: ExtensionContext,
        inputStream: InputStream,
        charset: Charset,
        annotation: JsonFileSource
    ): Any {
        return getObject(context, inputStream, charset, annotation)
    }

    private fun getClass(context: ExtensionContext): Class<*> {
        return context.requiredTestMethod.parameterTypes[0]
    }

    private fun getType(context: ExtensionContext): Type {
        return context.requiredTestMethod.parameters[0].parameterizedType
    }

    private fun getObject(
        context: ExtensionContext,
        inputStream: InputStream,
        charset: Charset,
        annotation: JsonFileSource
    ): Any {
        val type: Type = getType(context)
        val cls = getClass(context)
        return if (String::class.java.isAssignableFrom(cls)) {
            BufferedReader(InputStreamReader(inputStream, charset)).lines()
                .collect(Collectors.joining())
        } else readObject(inputStream, charset, annotation, type)
    }

    protected abstract fun readObject(
        inputStream: InputStream,
        charset: Charset,
        annotation: JsonFileSource,
        type: Type
    ): Any
}
