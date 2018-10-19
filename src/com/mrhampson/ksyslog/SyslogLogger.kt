package com.mrhampson.ksyslog

import java.util.concurrent.Executor

/**
 *
 * @author Marshall Hampson
 */
class SyslogLogger(
    private val defaultHeaders: SyslogHeaderFields,
    private val packetSender: PacketSender,
    private val executor: Executor) : AutoCloseable {
    
    private val messageByteFormatter = SyslogMessageByteFormatter(defaultHeaders)
    
    fun log(level: SyslogLevel, message:String) {
        executor.execute {
            packetSender.send(messageByteFormatter.format(level, message))
        }
    }
    
    override fun close() {
        packetSender.close()
    }
}