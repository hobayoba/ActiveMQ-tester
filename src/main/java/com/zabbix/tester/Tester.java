package com.zabbix.tester;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class Tester {

    private static final Logger LOG = LoggerFactory.getLogger(Tester.class);

    private Tester() {
    }

    public static void main(String[] args) throws JMSException {

        String url = null;
        if (args.length > 0) {
            url = args[0];
        } else {
            System.out.println("Usage: tester.jar tcp://IPorDomain:PORT");
            System.exit(0);
        }

        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
        Destination destination = new ActiveMQQueue("ZABBIX.QUEUE");

        Connection connection = connectionFactory.createConnection();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
//Sending
        MessageProducer producer = session.createProducer(destination);
        TextMessage messageSend = session.createTextMessage();
        messageSend.setText("This is test-msg");
        connection.start();
        try {
            producer.send(messageSend);
        } catch (JMSException e) {
            //Fatal
            System.out.println("2");
        }
//Receiving
        MessageConsumer consumer = session.createConsumer(destination);
        Message messageGet = null;
        try {
            messageGet = consumer.receive();
            if (messageGet == null) {
                //Error
                System.out.println("1");
                connection.close();
            } else
                //Clean up queue
                for (; ; ) {
                    messageGet = null;
                    messageGet = consumer.receive(2000);
                    if (messageGet == null) ;
                    break;
                }
            //Ok
            System.out.println("0");
        } catch (JMSException e) {
            //Fatal
            System.out.println("2");
        }
        connection.close();
    }
}