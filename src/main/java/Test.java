import org.apache.spark.SparkConf;
import org.apache.spark.SparkContext;
import org.apache.spark.api.java.JavaSparkContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Created by breynard on 26/02/16.
 */
public class Test {
    private static final Logger LOGGER = LoggerFactory.getLogger(Test.class);


    public static void main(String[] args) {
        LOGGER.info("Démarrage");

        // Configuration du user SPARK
        System.setProperty("HADOOP_USER_NAME", "spark"); // Or Add VM option : -DHADOOP_USER_NAME=spark

//        System.clearProperty("HADOOP_CONF_DIR");
//        System.setProperty("YARN_CONF_DIR", "/home/breynard/IdeaProjects/sparkHDP/src/main/yarn-conf/");

        // Sur le serveur  : /usr/hdp/current/hadoop-yarn-client/etc/hadoop/
        // Copie du jar sparkHDP-1.0-SNAPSHOT-all.jar sur le serveur
        // Lancement sur le serveur : java -cp sparkHDP-1.0-SNAPSHOT-all.jar Test

        // TODO : Actuellement les fichiers yarn-conf sont mis dans les ressources ... pas top top ...

        // $HADOOP_CONF_DIR utiliser dans yarn-site.xml
        System.setProperty("HADOOP_CONF_DIR", "/usr/hdp/current/hadoop-yarn-client/etc/hadoop/");

        try (JavaSparkContext javaSparkContext = modeHDP()) {
            LOGGER.info("UI on http://192.168.1.16:4040");
            LOGGER.info("UI on http://CLUSTER:18080");
            List<String> data = Arrays.asList("a", "b", "c", "d");

            //val file = sc.textFile("hdfs://vagrant-ubuntu-trusty-64:8020/user/spark/aggrego/test.csv")

            //javaSparkContext.parallelize(data).saveAsTextFile("hdfs://vagrant-ubuntu-trusty-64:8020/user/spark/aggrego/test7.csv");
            //LOGGER.info("----------------\n\n\n\n\n\n\n\n\n\n\n\n\nOK BORIS.");

            long collect = javaSparkContext.parallelize(data).count();
            LOGGER.info("----------------\n\n\n\n\n\n\n\n\n\n\n\n\nResult {}.", collect);

        } catch (Throwable t) {
            LOGGER.error("Error ", t);
        }
        System.exit(0);
    }

    private static JavaSparkContext modeHDP() {
        SparkConf sparkConf = new SparkConf();
        sparkConf.setAppName("Aggrego Test")
                .setMaster("yarn-client")

                .set("spark.local.ip","127.0.0.1") // helps when multiple network interfaces are present. The driver must be in the same network as the master and slaves
                // Il essaye de se connecter à moi pour me donner la réponse ... mais je ne suis pas sur le même réseau !!!
                .set("spark.driver.host", "127.0.0.1")
                .set("spark.driver.port", "43470")
//                .set("spark.fileserver.port", "33466")

                // spark.repl.class.uri,http://192.168.2.133:60639)
                // spark.fileserver.uri,http://192.168.2.133:38494)


                .set("spark.executor.memory", "512M")
                .set("spark.yarn.maxAppAttempts", "2")


                .set("spark.driver.log.level", "INFO")
                .set("spark.eventLog.dir", "hdfs:///user/spark")// TODO : Mettre dans un sous répertoire : hdfs:///user/spark/eventLog par exemple.
                .set("spark.eventLog.enabled", "true")

                //# sudo -u spark hdfs dfs -mkdir -p /user/spark
                //# sudo -u spark hdfs dfs -put /usr/hdp/current/spark-client/lib/spark-assembly-*.jar /user/spark/spark-assembly.jar
                //# sudo -u spark hdfs dfs -chmod -R 777 /user/spark
                //# sudo -u spark hdfs dfs -ls /user/spark/
                .set("spark.yarn.jar", "hdfs:///user/spark/spark-assembly.jar");


        SparkContext sparkContext = new SparkContext(sparkConf);
        return new JavaSparkContext(sparkContext);
    }


}
