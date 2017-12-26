package com.huntkey.rx.sceo;

import org.apache.zookeeper.ZooKeeper;

import java.util.List;

/**
 * Created by liangh on 2017/10/12 0012.
 */
public class testZKGetPartitionNum {

    public int getPartitionNumByTopic(String topic) {

        try{
            ZooKeeper zk = new ZooKeeper("10.3.98.163:2181", 10000, null);

            //String partitionInfo = new String(zk.getData("/brokers/topics/test_dynamic_partitions/partitions", false, null));
            List<String> partitionList = zk.getChildren("/brokers/topics/"+topic+"/partitions",false);

            return partitionList.size();
        }catch(Exception e){
            e.printStackTrace();
        }
        return 0;
    }

    public static void main(String args[]) {

        testZKGetPartitionNum t = new testZKGetPartitionNum();

        int num1 = t.getPartitionNumByTopic("mykafka");

        System.out.println(num1);
    }

}
