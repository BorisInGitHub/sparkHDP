# Spark with yarn-client

## Installation HDP 2.3.4

http://uprush.github.io/hdp/2014/12/29/hdp-cluster-on-your-laptop/
http://fr.slideshare.net/fabricemourlin/dfinir-un-cluster-sous-hadoop-avec-apache-ambari



## Utilisation du code

* ```mvn clean install```
* Sur le serveur  :

```/usr/hdp/current/hadoop-yarn-client/etc/hadoop/```
* Copie du jar ```sparkHDP-1.0-SNAPSHOT-all.jar``` sur le serveur
* Lancement sur le serveur :

```java -cp sparkHDP-1.0-SNAPSHOT-all.jar Test```




## Astuces


Redirection d'un port du serveur en local :
ssh -R 39580:localhost:39580 XXX

Test de la connection

Sur mon poste je créé une nanoServer :
while true ; do nc -l 39580 <index.html ; done

Sur le servuer, je fais un  wget localhost:39580
