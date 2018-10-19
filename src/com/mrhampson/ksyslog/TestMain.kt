package com.mrhampson.ksyslog

/**
 *
 * @author Marshall Hampson
 */

fun main(args : Array<String>) {
    var totalTime:Long = 0
    var sampleCount = 0
    for (i in 1..1000000) {
        val start = System.nanoTime()
        val message = toSyslogBytes(
                SyslogFacility.securityMessages4,
                SyslogLevel.LEVEL_CRITICAL,
                1,
                null,
                "marshalls-mac0",
                null,
                null,
                "Test Message!")
        totalTime += System.nanoTime() - start
        sampleCount++
    }
    println("Avg: " + totalTime/sampleCount.toDouble())
}