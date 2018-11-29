import java.io.*;
import java.util.*;

/**
 * @author Yangxiaoqing
 * @ClassName: PC_GUser
 * @Desciption:统计用户的共享PC
 * @date 2018/11/18 15:27
 * @Version 1.0
 */
public class PC_User_All {
    static  HashMap<String,Integer>  userMap = new HashMap<>();
    @SuppressWarnings("unchecked")
    public static void main(String args[]) {
       read("G:\\开题\\data_new\\UserG_PC.csv");
     //read("UserG_PC.csv");
      File csv = new File("G:\\开题\\data_new\\User_PC_All.csv"); // CSV数据文件
      //File csv = new File("User_PC_All.csv"); // CSV数据文件
        BufferedWriter bw = null; // 附加
        try {
            bw = new BufferedWriter(new FileWriter(csv, true));
           Iterator it = userMap.entrySet().iterator();
            List<Map.Entry<String, Integer>> list = new ArrayList<Map.Entry<String, Integer>>(userMap.entrySet());
            Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
                // 降序排序
                public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                    return -o1.getValue().compareTo(o2.getValue());
                }
            });
          String pcNames="";
          for (Map.Entry<String, Integer> mapping : list) {
                  String key1 = mapping.getKey();
                  pcNames =String.valueOf(mapping.getValue());
                  bw.write(key1 + "," + pcNames);
                  bw.newLine();
          }
        bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @SuppressWarnings("unchecked")
    public static void read(String path) {
        try{
        BufferedReader reader = new BufferedReader(new FileReader(path));//换成你的文件名

        String line = null;

        while ((line = reader.readLine()) != null) {
            String item[] = line.split(",");//一行数组
            for(int j = 2;j<item.length;j++){
                String PC = item[j].split("-")[0]+"-"+item[j].split("-")[1];
                int counts = Integer.parseInt(item[j].split("-")[2]);
                if(userMap.get(PC)!=null){
                    int count = userMap.get(PC)+1;
                    userMap.put(PC,count);
                }else{
                    userMap.put(PC,counts);
                }
            }
            }
        } catch(
        Exception e)

        {
            e.printStackTrace();
        }
    }
}
