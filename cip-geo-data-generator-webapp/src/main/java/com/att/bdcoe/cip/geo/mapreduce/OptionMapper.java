package com.att.bdcoe.cip.geo.mapreduce;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;


public class OptionMapper extends Mapper<IntWritable, Text, Text, Text> {

    @Override
    protected void cleanup(org.apache.hadoop.mapreduce.Mapper.Context context)
            throws IOException, InterruptedException {
        super.cleanup(context);
    }

    @Override
    protected void map(IntWritable key, Text value, Context context)
            throws IOException, InterruptedException {
        super.map(key, value, context);
    }

    @Override
    protected void setup(Context context)
            throws IOException, InterruptedException {
        super.setup(context);
    }
}
