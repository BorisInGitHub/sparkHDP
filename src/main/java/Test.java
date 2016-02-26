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

    private static final String cluster = "vps242352.ovh.net";


    public static void main(String[] args) {
        LOGGER.info("Démarrage");

        // Configuration du user SPARK
        System.setProperty("HADOOP_USER_NAME", "spark"); // Or Add VM option : -DHADOOP_USER_NAME=spark


        //System.setProperty("HADOOP_CONF_DIR", "/home/breynard/IdeaProjects/sparkHDP/src/main/resources");

        // Sur le serveur  : /usr/hdp/current/hadoop-yarn-client/etc/hadoop/
        // Copie du jar sparkHDP-1.0-SNAPSHOT-all.jar sur le serveur
        // Lancement sur le serveur : java -cp sparkHDP-1.0-SNAPSHOT-all.jar Test
        System.setProperty("HADOOP_CONF_DIR", "/usr/hdp/current/hadoop-yarn-client/etc/hadoop/");

        try (JavaSparkContext javaSparkContext = modeHDP()) {
            LOGGER.info("UI on http://{}:4040", cluster);
            LOGGER.info("UI on http://{}:18080", cluster);
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

                .set("spark.executor.memory", "512M")
                .set("spark.yarn.maxAppAttempts", "2")


                .set("spark.driver.log.level", "INFO")
                .set("spark.eventLog.dir", "hdfs:///user/spark/")
                .set("spark.eventLog.enabled", "true")

                //# sudo -u spark hdfs dfs -mkdir -p /user/spark
                //# sudo -u spark hdfs dfs -put /usr/hdp/current/spark-client/lib/spark-assembly-*.jar /user/spark/spark-assembly.jar
                //# sudo -u spark hdfs dfs -chmod -R 777 /user/spark
                //# sudo -u spark hdfs dfs -ls /user/spark/
                .set("spark.yarn.jar", "hdfs:///user/spark/spark-assembly.jar");


        SparkContext sparkContext = new SparkContext(sparkConf);
        return new JavaSparkContext(sparkContext);

        // Il essaye de se connecter à moi pour me donner la réponse ... mais je ne suis pas sur le même réseau !!!
        //SPARK_LOCAL_IP
    }


}
