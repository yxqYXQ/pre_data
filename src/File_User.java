import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Yangxiaoqing
 * @ClassName: File_User
 * @Desciption: 统计每个用户经常访问的文件
 * @date 2018/11/28 14:57
 * @Version 1.0
 */
public class File_User {
    private static HashMap<String,String> userMap = new HashMap<String,String>();//用户编号和文件名
    public static void main(String args[]){
//      read("");
//      write("");
      read("G:\\开题\\data_new\\");
      write("G:\\开题\\data_new\\");
    }
    public static void read(String path){
        try{
            BufferedReader reader = new BufferedReader(new FileReader(path+"UserG_file.csv"));//换成你的文件名
            String line = null;
            while ((line = reader.readLine()) != null) {
                String item[] = line.split(",");
                String userName = item[0];
                String fileNames[]=item[1].split(";");//取每个用户访问最多的file为该用户常用file
                String fileName = fileNames[0];
                userMap.put(userName,fileName);
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    @SuppressWarnings("unchecked")
    public static void write(String path){
        File csv = new File(path+"User_file.csv"); // CSV数据文件
        BufferedWriter bw = null; // 附加
        try {
            bw = new BufferedWriter(new FileWriter(csv, true));
            for (Map.Entry<String, String> entry : userMap.entrySet()) {
                String key = entry.getKey() ;
                String value =entry.getValue();
                bw.write(key+","+value);
                bw.newLine();
            }

            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
