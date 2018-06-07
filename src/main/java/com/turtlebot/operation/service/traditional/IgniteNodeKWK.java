package com.turtlebot.operation.service.traditional;

import com.turtlebot.operation.dataobject.Server;
import com.turtlebot.operation.dataobject.User;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.Ignition;
import org.apache.ignite.cache.query.FieldsQueryCursor;
import org.apache.ignite.cache.query.QueryCursor;
import org.apache.ignite.cache.query.SqlFieldsQuery;
import org.apache.ignite.lang.IgniteCallable;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class IgniteNodeKWK {
    private IgniteCache<String, String> firstTestcache;
    private Ignite ignite;
    private UUID nodeID;

    public IgniteNodeKWK() {
        ignite = Ignition.start("examples/config/example-ignite.xml");
        // 调用ignite方法向cache中创建键值对类型
        firstTestcache = ignite.getOrCreateCache("firstTestCache");
        nodeID = ignite.cluster().localNode().id();
    }

    public UUID getNodeID() {
        return nodeID;
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        IgniteNodeKWK igniteNodeKWK = new IgniteNodeKWK();
        //igniteTest.userStoreEg();
        //igniteTest.prepare();
        //igniteTest.insertData();
        //igniteTest.firstcount();
        System.out.println("ignite node kwk's uuid is " + igniteNodeKWK.getNodeID());

        System.out.print("开始输入KV，输入88退出。\ninput>");
        while (true) {
            Scanner sc = new Scanner(System.in);
            String kv = sc.nextLine();
            if (kv.equals("88")){
                System.out.println("input key value finish.");
                break;
            }else{
                String[] token = kv.split(" ");
                if (token.length == 2){
                    igniteNodeKWK.setKV(token[0], token[1]);
                    System.out.println("successfully set key: " + token[0] + ", value: " + token[1]);
                    System.out.print("input>");
                }else{
                    System.out.print("invalid input. example: key1 value1\ninput>");
                    continue;
                }
            }
        }

        System.out.print("开始获取了KV，输入88退出。\ninput>");

        while (true) {
            Scanner sc = new Scanner(System.in);
            String getkey = sc.nextLine();
            if (getkey.equals("88")){
                break;
            }else{
                System.out.println(igniteNodeKWK.getKV(getkey));
                System.out.print("input>");
                continue;
            }
        }
        System.out.println("Do not get any key. Program finishes.");
    }

    public void userStoreEg(){
        Ignition.setClientMode(true);
        try (Ignite ignite = Ignition.start("src/main/resources/spring/spring-dao.xml")) {
            try (IgniteCache<Integer, User> cache = ignite.getOrCreateCache("userCache")) {
                // Load cache with data from the database.
                cache.loadCache(null);
                // Execute query on cache.
                QueryCursor<List<?>> cursor = cache.query(new SqlFieldsQuery("select * from user"));
                System.out.println(cursor.getAll());
            }
        }
    }

    public void setKV(HashMap hashMap){

    }

    public void setKV(String key, String value){
        // 初始化键值对
        firstTestcache.put(key, value);
    }

    public String getKV(String key){
        // 从cache中根据键获取值，并且向集群中的所有节点发出广播
        final String[] res = new String[1];
        ignite.compute().broadcast(() -> {
            res[0] = "cache 中的数据：" + key + " --> " + firstTestcache.get(key);

            //向 redis 中插入数据
            // new RedisString().setStringKV("127.0.0.1",s1, s2);
        });

        // 从集群cache中获取数据
        HashSet keySets = new HashSet();
        keySets.add(1);
        keySets.add(2);
        Map cacheMap = firstTestcache.getAll(keySets);
        return res[0];
    }

    public void prepare() throws ClassNotFoundException, SQLException {
        // Register JDBC driver.
        Class.forName("org.apache.ignite.IgniteJdbcThinDriver");
        // Open JDBC connection.
        //jdbc:mysql://123.206.70.190:3306/turtlebot
        Connection conn = DriverManager.getConnection("jdbc:ignite:thin://127.0.0.1/");

        // Create database tables.
        try (Statement stmt = conn.createStatement()) {


            // Create table based on REPLICATED template.
            stmt.executeUpdate("CREATE TABLE City (" +
                    " id LONG PRIMARY KEY, name VARCHAR) " +
                    " WITH \"template=replicated\"");
            // Create table based on PARTITIONED template with one backup.
            stmt.executeUpdate("CREATE TABLE Person (" +
                    " id LONG, name VARCHAR, city_id LONG, " +
                    " PRIMARY KEY (id, city_id)) " +
                    " WITH \"backups=1, affinityKey=city_id\"");
            // Create an index on the City table.
            stmt.executeUpdate("CREATE INDEX idx_city_name ON City (name)");
            // Create an index on the Person table.
            stmt.executeUpdate("CREATE INDEX idx_person_name ON Person (name)");


        }
    }

    public void insertData(){
        // Connecting to the cluster.
        Ignite ignite = Ignition.start();
        // Getting a reference to an underlying cache created for server table above.
        IgniteCache<String, Server> serverCache = ignite.cache("SQL_PUBLIC_SERVER");

        // Getting a reference to an underlying cache created for user table above.
        IgniteCache<String, User> userCache = ignite.cache("SQL_PUBLIC_USER");

        // Inserting entries into server.
        SqlFieldsQuery query = new SqlFieldsQuery(
                "INSERT INTO server (ip, username, password, description) VALUES (?, ?, ?, ?)");
        serverCache.query(query.setArgs("1.1.1.1", "testuser", "testpass", "test描述一下")).getAll();

        // Inserting entries into user.
        query = new SqlFieldsQuery(
                "INSERT INTO user (name, gender, age, description) VALUES (?, ?, ?, ?)");
        userCache.query(query.setArgs("John Doe", "1", 3, "testmiiaoshumiaoshu")).getAll();
    }

    public void getData(){
        // Connecting to the cluster.
        Ignite ignite = Ignition.start();

        // Getting a reference to an underlying cache created for City table above.
        IgniteCache<String, Server> serverCache = ignite.cache("SQL_PUBLIC_SERVER");

        // Querying data from the cluster using a distributed JOIN.
        SqlFieldsQuery query = new SqlFieldsQuery("SELECT * From server");
        FieldsQueryCursor<List<?>> cursor = serverCache.query(query);
        Iterator<List<?>> iterator = cursor.iterator();
        while (iterator.hasNext()) {
            List<?> row = iterator.next();
            System.out.println(row.get(0) + ", " + row.get(1));
        }
    }

    public void firstcount(){
        try (Ignite ignite = Ignition.start("apache-ignite-2.3.0-src/config/example-kube.xml")) {
            Collection<IgniteCallable<Integer>> calls = new ArrayList<>();
            // Iterate through all the words in the sentence and create Callable jobs.
            for (final String word : "Count characters using callable".split(" "))
                calls.add(word::length);
            // Execute collection of Callables on the grid.
            Collection<Integer> res = ignite.compute().call(calls);
            // Add up all the results.
            int sum = res.stream().mapToInt(Integer::intValue).sum();
            System.out.println("Total number of characters is '" + sum + "'.");
        }
    }

}
