package com.mrhampson.ksyslog

import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress

/**
 *
 * @author Marshall Hampson
 */
class DatagramPacketSender(
    private val destAddress: InetAddress, 
    private val destPort:Int) : PacketSender {
    
    private val socket = DatagramSocket(destPort)
    
    override fun send(messageBytes:ByteArray) {
        val packet = DatagramPacket(messageBytes, messageBytes.size, destAddress, destPort)
        println(String(messageBytes))
        socket.send(packet)
    }
    
    override fun close() {
        socket.close()
    }
}