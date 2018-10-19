package com.mrhampson.ksyslog

import java.net.InetAddress
import java.util.concurrent.Executors

/**
 *
 * @author Marshall Hampson
 */

fun main(args : Array<String>) {
    val defaults = SyslogHeaderFields(
            facility = SyslogFacility.local0,
            version = 1,
            appName = "%LIVEACTION",
            localName = "marshall-macbook",
            procId = null)
    val logger = SyslogLogger(defaults, DatagramPacketSender(InetAddress.getLoopbackAddress(), 1234), Executors.newSingleThreadExecutor())
    logger.use {
        logger.log(SyslogLevel.LEVEL_ALERT, "Testing 123")
    }
}