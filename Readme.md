This tool was designed to test the working of an ActiveMQ instance.  
It'll send and receive the `This is test-msg` text message using the ZABBIX.QUEUE queue on an ActiveMQ instance.

Requirments:  
```
Java version v.1.8.+
ActiveMQ v.5+
```

Usage:  
`tester.jar tcp://Domain_or_IP:Port`

Output:
0 = ok
1 = error - cannot read 
2 = fatal error, see a stacktrace
