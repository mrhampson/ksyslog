package com.mrhampson.ksyslog

import java.lang.StringBuilder
import java.nio.ByteBuffer
import java.time.Instant
import java.time.format.DateTimeFormatter

/**
 *
 * @author Marshall Hampson
 */
class SyslogMessageByteFormatter(
        private val defaultHeaderFields: SyslogHeaderFields) {
    
    private val dateFormat: DateTimeFormatter = DateTimeFormatter.ISO_INSTANT
    private val utf8BOM:Array<Byte> = arrayOf(0xEF.toByte(), 0xBB.toByte(), 0xBF.toByte())
    
    fun format(level:SyslogLevel, message:String):ByteArray {
        return toSyslogBytes(level, defaultHeaderFields, Instant.now(), message)
    }
    
    fun format(level:SyslogLevel, time:Instant, message:String):ByteArray {
        return toSyslogBytes(level, defaultHeaderFields, time, message)
    }
    
    fun format(level: SyslogLevel, time: Instant, overrideHeader:SyslogHeaderFields, message: String):ByteArray {
        return toSyslogBytes(level, overrideHeader, time, message)
    }
    
    private fun toSyslogBytes(
            level: SyslogLevel,
            headerFields: SyslogHeaderFields,
            time:Instant,
            message:String):ByteArray {
        val sb = StringBuilder()
        sb.append('<')
        sb.append(calculatePriority(headerFields.facility, level))
        sb.append('>')
        sb.append(headerFields.version)
        sb.append(' ')
        sb.append(dateFormat.format(time ?: Instant.now()))
        sb.append(' ')
        sb.append(headerFields.localName)
        sb.append(' ')
        sb.append(headerFields.appName ?: '-')
        sb.append(' ')
        sb.append(headerFields.procId ?: '-')
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

    private fun calculatePriority(facility: SyslogFacility, level: SyslogLevel):Int {
        return (facility.ordinal shl 3) or level.ordinal
    }
}