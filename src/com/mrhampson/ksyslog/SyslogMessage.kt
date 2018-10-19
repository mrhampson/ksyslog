package com.mrhampson.ksyslog

import java.lang.StringBuilder
import java.nio.ByteBuffer
import java.time.Instant
import java.time.format.DateTimeFormatter

val dateFormat:DateTimeFormatter = DateTimeFormatter.ISO_INSTANT
val utf8BOM:Array<Byte> = arrayOf(0xEF.toByte(), 0xBB.toByte(), 0xBF.toByte())

fun toSyslogBytes(
    facility: SyslogFacility,
    level: SyslogLevel,
    version:Int,
    time:Instant?,
    localName:String,
    appName:String?,
    procId:String?,
    message:String):ByteArray {
    val sb = StringBuilder()
    sb.append('<')
    sb.append(calculatePriority(facility, level))
    sb.append('>')
    sb.append(version)
    sb.append(' ')
    sb.append(dateFormat.format(time ?: Instant.now()))
    sb.append(' ')
    sb.append(localName)
    sb.append(' ')
    sb.append(appName ?: '-')
    sb.append(' ')
    sb.append(procId ?: '-')
    sb.append(' ')
    // Not supporting structured data so always putting this
    sb.append('-')
    sb.append(' ')
    val headerString = sb.toString()
    val msgBuf = ByteBuffer.allocate(headerString.length + utf8BOM.size + message.length)
    msgBuf.put(headerString.toByteArray(Charsets.UTF_8))
    msgBuf.put(utf8BOM[0])
    msgBuf.put(utf8BOM[1])
    msgBuf.put(utf8BOM[2])
    msgBuf.put(message.toByteArray(Charsets.UTF_8))
    return msgBuf.array()
}
    
fun calculatePriority(facility: SyslogFacility, level: SyslogLevel):Int {
    return (facility.ordinal shl 3) or level.ordinal
}
