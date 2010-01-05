#!/usr/bin/env groovy

// Inspired by http://github.com/blue64/my-ip

currentIp = ('http://whatsmyip.us/'.toURL().text =~ /\b\d{1,3}\.\d{1,3}\.\d{1,3}\.\d{1,3}\b/)[0]
println currentIp

def ipLog = new File('ip.log')
recentIp = ipLog.readLines().last().tokenize(',').last().trim()

if (currentIp != recentIp) {
    println "IP Address has changed, it was $recentIp, now it's $currentIp"
    ipLog << "${new Date().format('yyyy-MM-dd HH:mm:ss')}, $currentIp\n"

    ["git", "add", "ip.log"].execute()
    ["git", "commit", "-m", "$currentIp"].execute()
    ["git", "push"].execute()
}
