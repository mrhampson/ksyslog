package com.mrhampson.ksyslog

import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress
import java.time.Instant

/**
 *
 * @author Marshall Hampson
 */

fun main(args : Array<String>) {
    var formatter = SyslogMessageByteFormatter(SyslogHeaderFields(
        facility = SyslogFacility.local0,
        version = 1,
        appName = "%LIVEACTION",
        localName = "marshall-macbook",
        procId = null))
    val socket = DatagramSocket(4321)
    socket.use {
        val packetData = formatter.format(SyslogLevel.LEVEL_ALERT, Instant.now(), "Testing123")
        var packet = DatagramPacket(packetData, packetData.size, InetAddress.getLoopbackAddress(), 1234)
        socket.send(packet)
    }
}