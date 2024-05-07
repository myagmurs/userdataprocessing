package org.example.service;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class SparkService {

    @Autowired
    private SparkSession sparkSession;

    @Value("${file.path}")
    private String filePath;

    public String processDataFromDB() {
        Dataset<Row> dataset = sparkSession.read()
                .format("jdbc")
                .option("url", "jdbc:h2:mem:testdb")
                .option("dbtable", "user_detail")
                .option("user", "sa")
                .option("password", "")
                .load();
        return dataset.showString(20, 20, true);
    }

    public String processDataFromApi(String response) {
        JavaSparkContext javaSparkContext = JavaSparkContext.fromSparkContext(sparkSession.sparkContext());
        JavaRDD<String> rdd = javaSparkContext.parallelize(Arrays.asList(response.split("\n")));

        Dataset<Row> df = sparkSession.createDataFrame(rdd, String.class);

        return df.showString(20, 20, true);
    }

    public String processDataFromFile(String fileName) {
        Dataset<Row> dataset = sparkSession.read()
                .option("header", "true")
                .csv(fileName);
        return dataset.showString(20, 20, true);
    }
}
