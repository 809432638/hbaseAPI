package com.yangkai.demo;


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.HBaseAdmin;

import java.io.IOException;


/**
 * @Author 嫌疑人:杨某
 * @Date 2019/11/08 11:26
 * @Version 1.0
 */
public class HbaseDemo {

    // 获取Configuration对象
      public static Configuration conf;
      static {
          //使用HBaseConfiguration的单例方法实例化
           conf = HBaseConfiguration.create();
          conf.set("hbase.zookeeper.quorum", "192.166.9.102");
          conf.set("hbase.zookeeper.property.clientPort", "2181");
      }

      // 判断表是否存在
    public static boolean isTableExist(String tableName) throws IOException {
        //在HBase中管理、访问表需要先创建HBaseAdmin对象
        //Connection connection = ConnectionFactory.createConnection(conf);
       //HBaseAdmin admin = (HBaseAdmin) connection.getAdmin();
        HBaseAdmin admin = new HBaseAdmin(conf);
        return admin.tableExists(tableName);
    }


    // 创建表
    public static void createTable(String tableName, String... columnFamily) throws IOException {
        HBaseAdmin admin = new HBaseAdmin(conf);
        if (isTableExist(tableName)){
            System.out.println("表" + tableName + "已存在");
        }else {
            //创建表属性对象,表名需要转字节
            HTableDescriptor descriptor = new HTableDescriptor(TableName.valueOf(tableName));
            //创建多个列族
            for (String cf:columnFamily){
                descriptor.addFamily(new HColumnDescriptor(cf));
            }
            //根据对表的配置，创建表
            admin.createTable(descriptor);
            System.out.println("表" + tableName + "创建成功！");
        }
    }


    //删除表
    public static void dropTable(String tableName) throws IOException {
        HBaseAdmin admin = new HBaseAdmin(conf);
        if (isTableExist(tableName)){
            admin.disableTable(tableName);
            admin.deleteTable(tableName);
            System.out.println("表" + tableName + "删除成功！");
        }else{
            System.out.println("表" + tableName + "不存在！");
        }
    }

}
