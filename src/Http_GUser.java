import java.io.*;
import java.util.*;

/**
 * @author Yangxiaoqing
 * @ClassName: Http_GUser
 * @Desciption: 统计用户访问Http的url的情况
 * @date 2018/11/26 19:10
 * @Version 1.0
 */
public class Http_GUser {
    private static ArrayList<String> list = new ArrayList<String>();
    //获取用户的姓名
    @SuppressWarnings("unchecked")
    public static void readUser(){
        try{
           // BufferedReader reader = new BufferedReader(new FileReader("G:\\开题\\用户行为\\数据集\\r6.2\\LDAP\\2009-12.csv"));//换成你的文件名
            BufferedReader reader = new BufferedReader(new FileReader("2009-12.csv"));//换成你的文件名
            reader.readLine();//第一行信息，为标题信息，不用，如果需要，注释掉
            String line = null;
            while ((line = reader.readLine()) != null) {
                String item[] = line.split(",");
                list.add(item[1]);
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    @SuppressWarnings("unchecked")
    public static void write(HashMap<String, HashMap<String,Integer>> userMap){
       // File csv = new File("G:\\开题\\data_new\\UserG_Http.csv"); // CSV数据文件
         File csv = new File("UserG_Http.csv"); // CSV数据文件
        BufferedWriter bw = null; // 附加
        try {
            bw = new BufferedWriter(new FileWriter(csv, true));
            Iterator it = userMap.entrySet().iterator();
            int total = 0;
            while(it.hasNext()){
                Map.Entry entry = (Map.Entry) it.next();
                String key = (String)entry.getKey();
                HashMap<String,Integer> map1 = (HashMap<String,Integer>) entry.getValue();
                List<Map.Entry<String, Integer>> list = new ArrayList<Map.Entry<String, Integer>>(map1.entrySet());
                Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
                    // 降序排序
                    public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                        return -o1.getValue().compareTo(o2.getValue());
                    }
                });
                String pcNames="";
                for (Map.Entry<String, Integer> mapping : list) {
                    String key1 = mapping.getKey();
                    Integer value = mapping.getValue();
                    pcNames += key1 + ";" + value + ",";
                    total +=value;
                }
                String  pc  = pcNames.substring(0, pcNames.length() - 1);

                bw.write(key + "," + pc);
                bw.newLine();
            }
            System.out.println("total"+total);

            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    @SuppressWarnings("unchecked")
    public static void read(){
        try{
            HashMap<String, HashMap<String,Integer>> map = new HashMap<>();//参数分别为：用户名、http的url、对应的访问次数
             //BufferedReader reader = new BufferedReader(new FileReader("G:\\开题\\用户行为\\数据集\\r6.2\\http.csv"));//换成你的文件名
           BufferedReader reader = new BufferedReader(new FileReader("http.csv"));//换成你的文件名
            reader.readLine();//第一行信息，为标题信息，不用，如果需要，注释掉
            String line = null;

            while ((line = reader.readLine()) != null) {
                String item[] = line.split(",");
                String https = item[4];//http地址
                String httpNews[] = https.split("//");
                String http1 = httpNews[0];
                String http2[] = httpNews[1].split("/");
                String http = http1+"//"+http2[0];
                String userName = item[2];//用户id
                HashMap<String,Integer> newMap = new HashMap<String,Integer>();//参数分别为：http的url、对应的访问次数
                //判断是否是2009-12的员工
                if(list.contains(userName)){
                    //判断是否map中是否以已经存放了该用户
                    if(map.get(userName)!=null){
                        newMap = map.get(userName);
                        //判断url路径是否已经存在
                        if( newMap.get(http)!=null) {
                            int count = newMap.get(http);
                            count = count + 1;
                            newMap.put(http,count);
                        }else{
                            newMap.put(http,1);
                        }
                    }else{
                        newMap.put(http,1);
                    }
                    map.put(userName,newMap);
                }

            }

            write(map);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    @SuppressWarnings("unchecked")
    public static void main(String args[]){
        readUser();
        read();

    }
}
