package com.mrhampson.ksyslog

/**
 *
 * @author Marshall Hampson
 */
interface PacketSender : AutoCloseable {
    fun send(messageBytes:ByteArray);
}