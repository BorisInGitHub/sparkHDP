# Spark with yarn-client

## Installation HDP 2.3.4

http://uprush.github.io/hdp/2014/12/29/hdp-cluster-on-your-laptop/
http://fr.slideshare.net/fabricemourlin/dfinir-un-cluster-sous-hadoop-avec-apache-ambari


http://blog.cloudera.com/blog/2014/05/apache-spark-resource-management-and-yarn-app-models/
http://stackoverflow.com/questions/24637312/spark-driver-in-apache-spark

http://wiki.pentaho.com/display/EAI/Spark+Submit

Version maven pour Spark en fonction de la version de HDP
https://github.com/jjmeyer0/spark-java-archetype/blob/master/src/main/resources/archetype-resources/pom.xml

Essayer le spark-shell depuis mon poste pour voir si cela fonction ...
http://hortonworks.com/hadoop/spark/#section_6



## Utilisation du code

* ```mvn clean install```
* Sur le serveur  :

```/usr/hdp/current/hadoop-yarn-client/etc/hadoop/```
* Copie du jar ```sparkHDP-1.0-SNAPSHOT-all.jar``` sur le serveur
* Lancement sur le serveur :

```java -cp sparkHDP-1.0-SNAPSHOT-all.jar Test```


* Commenter dans yarn-site.xml
```
    <!--<property>-->
      <!--<name>net.topology.script.file.name</name>-->
      <!--<value>/etc/hadoop/conf/topology_script.py</value>-->
    <!--</property>-->
```


## Astuces


Redirection d'un port du serveur en local :
ssh -R 39580:localhost:39580 XXX

Test de la connection

Sur mon poste je créé une nanoServer :
while true ; do nc -l 39580 <index.html ; done

Sur le serveur, je fais un  wget localhost:39580
