package org.example;

import java.lang.management.ManagementFactory;
import java.util.Set;

import javax.management.MBeanServer;
import javax.management.ObjectInstance;
import javax.management.ObjectName;

public class MBean {

    public static void main(String[] args) throws Exception {

        String objectName = "com.javacodegeeks.snippets.enterprise:type=Hello";

        MBeanServer server = ManagementFactory.getPlatformMBeanServer();

        // Construct the ObjectName for the Hello MBean we will register
        ObjectName mbeanName = new ObjectName(objectName);

        Hello mbean = new Hello();

        server.registerMBean(mbean, mbeanName);

        Set<ObjectInstance> instances = server.queryMBeans(new ObjectName(objectName), null);

        ObjectInstance instance = (ObjectInstance) instances.toArray()[0];

        System.out.println("Class Name:t" + instance.getClassName());
        System.out.println("Object Name:t" + instance.getObjectName());

    }

    static class Hello implements HelloMBean {

        private String message = "Hello World";

        @Override
        public String getMessage() {
            return this.message;
        }

        @Override
        public void sayHello() {
            System.out.println(message);
        }

        @Override
        public void setMessage(String message) {
            this.message = message;
        }

    }

    static interface HelloMBean {

        // operations

        public void sayHello();

        // attributes

        // a read-write attribute called Message of type String
        public String getMessage();
        public void setMessage(String message);

    }

}