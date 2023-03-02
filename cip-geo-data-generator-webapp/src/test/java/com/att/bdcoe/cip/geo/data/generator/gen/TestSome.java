package com.att.bdcoe.cip.geo.data.generator.gen;


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

public class TestSome {

    public static void main(String[] arg) {
        Configuration conf = new Configuration();

        Job job = null;
        try {
            job = new Job(conf, "word count");

            job.setJarByClass(TestSome.class);
            job.setMapperClass(TestMapper.class);
//Uncomment this to
//job.setCombinerClass(IntSumReducer.class);
            job.setReducerClass(TestReducer.class);
//    job.setOutputKeyClass(Text.class);
//    job.setOutputValueClass(IntWritable.class);
            FileInputFormat.addInputPath(job, new Path("/input"));
            FileOutputFormat.setOutputPath(job, new Path("/output"));


            System.exit(job.waitForCompletion(true) ? 0 : 1);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


    }


}
