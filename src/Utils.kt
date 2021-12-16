import java.io.File
import java.math.BigInteger
import java.security.MessageDigest

private fun getFile(name: String) = File("src", "$name.txt")

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = getFile(name).readLines()

/**
 * Reads the file raw
 */
fun readText(name: String) = getFile(name).readText()

/**
 * Reads lines and parse into int
 */
fun readInputAsInts(name: String) = readInput(name).map(String::toInt)

/**
 * Reads lines and splits the input based on the separator. Useful for inputs like Day04.txt
 */
fun readInputWithSeparator(name: String, separator: Regex) = getFile(name).readText().split(separator)

/**
 * Converts string to md5 hash.
 */
fun String.md5(): String = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray())).toString(16)
