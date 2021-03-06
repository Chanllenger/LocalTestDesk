package com.test.table;

import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.java.StreamTableEnvironment;
import org.apache.flink.table.descriptors.Json;
import org.apache.flink.table.descriptors.Kafka;
import org.apache.flink.table.descriptors.Rowtime;
import org.apache.flink.table.descriptors.Schema;
import org.apache.flink.types.Row;

import java.util.Properties;

/**
 * @author zhangzhiqiang
 * @date 2019/7/15 14:45
 */
public class SqlTumbleWindowEventTimeStream {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);
        env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);
        StreamTableEnvironment tableEnv = StreamTableEnvironment.create(env);

        Properties p = new Properties();
        p.setProperty("bootstrap.servers", "localhost:9092");
        p.setProperty("group.id", "test");

        Kafka kafka = new Kafka().properties(p).topic("user").version("0.10");

        Schema schema = new Schema()
                .field("userId", Types.STRING)
                .field("name", Types.STRING)
                .field("age", Types.STRING)
                .field("sex", Types.STRING)
                //.field("createTime", Types.BIG_DEC)
                .field("updateTime", Types.BIG_DEC)
                // 设置 rowtime，把字段timestamp设置为Event Time的水印时间戳
                .field("rowTime", Types.SQL_TIMESTAMP).rowtime(new Rowtime().timestampsFromField("createTime").watermarksPeriodicBounded(60000));

        tableEnv.connect(kafka)
                .withSchema(schema)
                .withFormat(new Json().deriveSchema())
                .inAppendMode()
                .registerTableSource("Users");

        Table table = tableEnv.sqlQuery("select * from Users");
        tableEnv.toAppendStream(table, TypeInformation.of(Row.class)).print("row:");

        Table table1 = tableEnv.sqlQuery("SELECT userId, TUMBLE_START(rowTime, INTERVAL '1' MINUTE) AS rStart, COUNT(1) AS countNum from Users GROUP BY TUMBLE(rowTime, INTERVAL '1' MINUTE),userId");
        tableEnv.toAppendStream(table1, TypeInformation.of(Row.class)).print("tumble:");

        Table table2 = tableEnv.sqlQuery("SELECT userId,TUMBLE_START(rowTime, INTERVAL '1' MINUTE) AS rStart, TUMBLE_END(rowTime, INTERVAL '1' MINUTE) AS rEnd, TUMBLE_ROWTIME(rowTime,INTERVAL '1' MINUTE) AS pTime,COUNT(1) AS countNum from Users GROUP BY TUMBLE(rowTime, INTERVAL '1' MINUTE),userId");
        tableEnv.toAppendStream(table2, TypeInformation.of(Row.class)).print("tumble2:");

        Table table3 = tableEnv.sqlQuery("SELECT userId,HOP_START(rowTime, INTERVAL '1' MINUTE,INTERVAL '5' MINUTE) as hStart,COUNT(1) AS countNum from Users GROUP BY HOP(rowTime,INTERVAL '1' MINUTE,INTERVAL '5' MINUTE),userId");
        tableEnv.toAppendStream(table3, TypeInformation.of(Row.class)).print("hop:");

        Table table4 = tableEnv.sqlQuery("SELECT userId,SESSION_START(rowTime,INTERVAL '1' MINUTE) AS sStart,COUNT(1) AS countNum from Users GROUP BY SESSION(rowTime,INTERVAL '1' MINUTE),userId");
        tableEnv.toAppendStream(table4, TypeInformation.of(Row.class)).print("session:");

        env.execute("SqlTumbleWindowProcessTimeStream");
    }
}
