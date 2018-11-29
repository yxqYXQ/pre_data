import java.io.*;
import java.util.*;

/**
 * @author Yangxiaoqing
 * @ClassName: File_User_All
 * @Desciption: 统计用户公共的文件
 * @date 2018/11/28 14:56
 * @Version 1.0
 */
public class File_User_All {
    private static   HashMap<String,Integer> userMap = new HashMap<String,Integer>();//用户访问文件和访问次数
    @SuppressWarnings("unchecked")
    public static void main(String args[]){
//        read("");
//        write("");
        read("G:\\开题\\data_new\\");

        write("G:\\开题\\data_new\\");
    }
    @SuppressWarnings("unchecked")
    public static void read(String path){
        try{
            BufferedReader reader = new BufferedReader(new FileReader(path+"UserG_file.csv"));//换成你的文件名
            String line = null;
            while ((line = reader.readLine()) != null) {
                String item[] = line.split(",");

                //遍历每个用户访问过的url，并统计每个url的访问次数
                for (int i = 2; i <item.length ; i++) {
                    String urls[]=item[i].split(";");//取每个用户访问最多的url为该用户常用url
                    String url = urls[0];
                    int count = Integer.parseInt(urls[1]);
                    if(userMap.get(url)!=null){
                        count+=userMap.get(url);
                    }
                    userMap.put(url,count);
                }
            }

        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    @SuppressWarnings("unchecked")
    public static void write(String path){
        File csv = new File(path+"User_file_All.csv"); // CSV数据文件

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
