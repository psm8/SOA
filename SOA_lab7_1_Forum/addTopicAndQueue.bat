C:\wildfly-18.0.1.Final\bin\jboss-cli.bat
connect
jms-queue add --queue-address=SOA_LabQueue --entries=java:jboss/exported/jms/queue/SOA_lab,jms/queue/SOA_lab
jms-topic add --topic-address=SOA_LabTopic --entries=jms/topic/SOA_lab,java:jboss/exported/jms/topic/SOA_lab