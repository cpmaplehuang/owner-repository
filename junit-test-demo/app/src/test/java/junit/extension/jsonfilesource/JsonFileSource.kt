package junit.extension.jsonfilesource

import junit.extension.jsonfilesource.parser.AbstractParser
import junit.extension.jsonfilesource.parser.GsonParser
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.ArgumentsProvider
import org.junit.jupiter.params.provider.ArgumentsSource
import org.junit.jupiter.params.support.AnnotationConsumer
import java.io.File
import java.io.InputStream
import java.nio.charset.Charset
import java.util.stream.Stream


@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)  // 保留到运行时
@ArgumentsSource(JsonFileArgumentsProvider::class)
annotation class JsonFileSource(val resources: Array<String>,
                                val encoding : String = "UTF-8")

class JsonFileArgumentsProvider : ArgumentsProvider, AnnotationConsumer<JsonFileSource> {

    private lateinit var annotation: JsonFileSource
    private lateinit var parser : AbstractParser
    override fun accept(annotation: JsonFileSource) {
        this.annotation = annotation
        this.parser = createParser(annotation)
    }

    override fun provideArguments(context: ExtensionContext): Stream<out Arguments> {
        checkParams(context)
        return annotation.resources.map {
            openClasspathResource(context.requiredTestClass, it)
        }.map {
            parser.parser(context, it, Charset.forName(annotation.encoding), annotation)
        }.stream().flatMap { toStream(it) }
    }

    private fun createParser(annotation: JsonFileSource) : AbstractParser {
        // 可以適配多個JSON解释器，例如FastJson等
        return GsonParser()
    }


    private fun checkParams(context: ExtensionContext){
        val parameterCount = context.requiredTestMethod.parameterCount
        if (context.requiredTestMethod.parameterCount != 1) {
            throw IllegalArgumentException(
                "Exactly one type of input must be provided in the @"
                        + JsonFileSource::class.java.simpleName + " annotation, but there were $parameterCount")
        }
    }
    private fun openClasspathResource(baseClass: Class<*>, path: String) : InputStream {
        val finalPath = if (path.startsWith(File.separator)) {
            path
        } else {
            "${File.separator}$path"
        }
        val result = baseClass.getResourceAsStream(finalPath)
        if (result == null) {
            throw IllegalArgumentException("Resource path [$finalPath] can not open!")
        }
        return result
    }

    private fun toStream(o: Any): Stream<Arguments> {
        return Stream.of(Arguments.of(o))
    }
}



