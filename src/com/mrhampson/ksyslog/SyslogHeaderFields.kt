package com.mrhampson.ksyslog

/**
 *
 * @author Marshall Hampson
 */
data class SyslogHeaderFields(
    val facility: SyslogFacility,
    val version:Int,
    val localName:String,
    val appName:String?,
    val procId:String?)
