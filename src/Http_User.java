import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author Yangxiaoqing
 * @ClassName: Http_User
 * @Desciption: 用户访问最多的url
 * @date 2018/11/26 20:13
 * @Version 1.0
 */
public class Http_User {
    @SuppressWarnings("unchecked")
    public static void main(String args[]){
       read();
    }
    @SuppressWarnings("unchecked")
    public static void read(){
        try{
            HashMap<String,String> map = new HashMap<String,String>();//用户编号和url
            BufferedReader reader = new BufferedReader(new FileReader("G:\\开题\\data_new\\UserG_Http.csv"));//换成你的文件名
            //BufferedReader reader = new BufferedReader(new FileReader("UserG_Http.csv"));//换成你的文件名
            //reader.readLine();//第一行信息，为标题信息，不用，如果需要，注释掉
            String line = null;
            while ((line = reader.readLine()) != null) {
                String item[] = line.split(",");
                String userName = item[0];
                String urls[]=item[1].split(";");//取每个用户访问最多的url为该用户常用url
                String url = urls[0];
                map.put(userName,url);
            }
            write(map);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    @SuppressWarnings("unchecked")
    public static void write(HashMap<String, String> userMap){
        File csv = new File("G:\\开题\\data_new\\User_Http.csv"); // CSV数据文件
        //File csv = new File("User_PC.csv"); // CSV数据文件
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
