import java.io.*;
import java.util.HashMap;

/**
 * @author Yangxiaoqing
 * @ClassName: Http_data
 * @Desciption: 统计所有用户的的访问http情况，分别存放在http文件下
 * @date 2018/11/15 9:50
 * @Version 1.0
 */
public class Http_data {
    public static void main(String[] args) {
        try {
          // String path1 = "",path2="",path="http/";
           String path1="G:\\开题\\用户行为\\数据集\\r6.2\\";
            String path2="G:\\开题\\用户行为\\数据集\\r6.2\\LDAP\\";
            String path="G:\\开题\\用户行为\\data_new\\http\\";
            BufferedReader reader = new BufferedReader(new FileReader(path1+"http.csv"));//换成你的文件名
            BufferedReader reader1 = new BufferedReader(new FileReader(path2+"2009-12.csv"));//换成你的文件名

            reader1.readLine();//第一行信息，为标题信息，不用，如果需要，注释掉
            String line = null;
            String line1 = null;
            HashMap<String, String> userMap = new HashMap<>();
            //用户信息
            int col = 2;
            String str = "";
            while ((line1 = reader1.readLine()) != null) {
                String item[] = line1.split(",");//一行数组
                String colValue = item[col - 1];//这里
                for (int i = 0; i <item.length ; i++) {
                    str += item[i]+",";
                }
                String str1 = str.substring(0,str.length()-1);
                userMap.put(colValue, str1);
                str = "";
            }

            //设备信息
            col = 3;
            while ((line = reader.readLine()) != null) {
                String item[] = line.split(",");//一行数组
                String colValue = item[col - 1];//这里
                String userMessage = userMap.get(colValue);
                if(userMessage!=null&&!userMessage.equals("")) {
                    String http = item[0] + "," + item[1] + "," + item[3] + "," + item[4] + "," + item[5];
                    write(path,"http_" + colValue + "_2009_12", userMessage, http);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("ok");
    }

    public static void write(String path, String str1, String str2,String path1) {

        try {
            File csv = new File(path1 + path + ".csv"); // CSV数据文件

            BufferedWriter bw = new BufferedWriter(new FileWriter(csv, true)); // 附加
            // 添加新的数据行
            bw.write(str1 + "," + str2);
            bw.newLine();
            bw.close();
        } catch (Exception e) {
            // File对象的创建过程中的异常捕获
            e.printStackTrace();
        }
    }
}


