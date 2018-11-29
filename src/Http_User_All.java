import java.io.*;
import java.util.*;

/**
 * @author Yangxiaoqing
 * @ClassName: Http_User_All
 * @Desciption: 所有用户公共的url
 * @date 2018/11/26 20:13
 * @Version 1.0
 */
public class Http_User_All {
    @SuppressWarnings("unchecked")
    public static void main(String args[]){
        read();
    }
    @SuppressWarnings("unchecked")
    public static void read(){
        try{
            HashMap<String,Integer> map = new HashMap<String,Integer>();//用户url和访问次数
            BufferedReader reader = new BufferedReader(new FileReader("G:\\开题\\data_new\\UserG_Http.csv"));//换成你的文件名
            //BufferedReader reader = new BufferedReader(new FileReader("UserG_Http.csv"));//换成你的文件名
            //reader.readLine();//第一行信息，为标题信息，不用，如果需要，注释掉
            String line = null;
            while ((line = reader.readLine()) != null) {
                String item[] = line.split(",");

                //遍历每个用户访问过的url，并统计每个url的访问次数
                for (int i = 2; i <item.length ; i++) {
                    String urls[]=item[i].split(";");//取每个用户访问最多的url为该用户常用url
                    String url = urls[0];
                    int count = Integer.parseInt(urls[1]);
                    if(map.get(url)!=null){
                        count+=map.get(url);
                    }
                    map.put(url,count);
                }
            }
            write(map);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    @SuppressWarnings("unchecked")
    public static void write(HashMap<String,Integer> userMap){
        File csv = new File("G:\\开题\\data_new\\User_Http_All.csv"); // CSV数据文件
        //File csv = new File("User_Http_All.csv"); // CSV数据文件
        BufferedWriter bw = null; // 附加
        try {
            bw = new BufferedWriter(new FileWriter(csv, true));
            List<Map.Entry<String, Integer>> list = new ArrayList<Map.Entry<String, Integer>>(userMap.entrySet());
            Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
                    // 降序排序
                public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                        return -o1.getValue().compareTo(o2.getValue());
                    }
                });
                for (Map.Entry<String, Integer> mapping : list) {
                    String key1 = mapping.getKey();
                    Integer value = mapping.getValue();
                    bw.write(key1  + "," +  value);
                    bw.newLine();
                }
            bw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
