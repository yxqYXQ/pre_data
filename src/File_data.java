import java.io.*;
import java.util.HashMap;

/**
 * @author Yangxiaoqing
 * @ClassName: File_data
 * @Desciption: 统计所有用户的file操作情况， 放在file文件下

 *@date 2018/11/15 9:58
 * @Version 1.0
 */
public class File_data {
    public static void main(String[] args) {
        try {

//            BufferedReader reader = new BufferedReader(new FileReader("file.csv"));//换成你的文件名
//           BufferedReader reader1 = new BufferedReader(new FileReader("2009-12.csv"));//换成你的文件名
            BufferedReader reader = new BufferedReader(new FileReader("G:\\开题\\用户行为\\数据集\\r6.2\\file.csv"));//换成你的文件名
            BufferedReader reader1 = new BufferedReader(new FileReader("G:\\开题\\用户行为\\数据集\\r6.2\\LDAP\\2009-12.csv"));//换成你的文件名
            reader1.readLine();//第一行信息，为标题信息，不用，如果需要，注释掉
            reader.readLine();//第一行信息，为标题信息，不用，如果需要，注释掉
            String line = null;
            String line1 = null;
            HashMap<String, String> userMap = new HashMap<>();
            //用户信息
            int col = 2;
            String str = "";
            HashMap map = decoy();
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
                    String pc = item[3];
                    String dir = item[4];
                    String key ="\"" +pc+"\""+","+"\""+dir+"\"";
                    String flag = "0";//不是诱饵文件
                    if(map.get(key)!=null){
                        flag="1";
                    }
                    String file = item[0]+","+item[1]+","+item[3]+","+item[4]+","+flag+","+item[5]+","+item[6]+","+item[7];
                    write("file_"+colValue+"_2009_12", userMessage, file);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //获取诱饵文件
    public static HashMap<String, String> decoy() {
        HashMap<String, String> map = new HashMap<>();
        try {

            BufferedReader reader = new BufferedReader(new FileReader("G:\\开题\\用户行为\\数据集\\r6.2\\decoy_file.csv"));//换成你的文件名
           // BufferedReader reader = new BufferedReader(new FileReader("decoy_file.csv"));//换成你的文件名
            reader.readLine();//第一行信息，为标题信息，不用，如果需要，注释掉
            String line = null;

            while ((line = reader.readLine()) != null) {
                String item[] = line.split(",");//一行数组
                String pc = item[1];
                String dir = item[0];
                String str = pc+","+dir;
                map.put(str, pc);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(map.size());
        return map;
    }
    public static void write(String path, String str1, String str2) {

        try {
           File csv = new File("file/" + path + ".csv"); // CSV数据文件

            //File csv = new File("G:\\开题\\data_new\\file\\" + path + ".csv"); // CSV数据文件
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

