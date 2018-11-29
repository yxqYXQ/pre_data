import java.io.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * @author Yangxiaoqing
 * @ClassName: CountData_Logon
 * @Desciption: 统计每个用户的以天的操作文件的情况
 * @date 2018/11/16 14:04
 * @Version 1.0
 */
public class CountData_File {
    private static String fileNameNew;
    public static  void walk( String path,String path1 ) {

        File root = new File( path );
        File[] list = root.listFiles();

        if (list == null) return;

        for ( File f : list ) {
            // System.out.println( "File:" + f.getAbsoluteFile() );
            String filePath = f.getAbsolutePath();

            read(filePath,path1);
        }
    }
    public static void main(String[] args) {
//        String path1= "file2/";
//        fw.walk("file1" ,path1);
          String path1= "G:\\开题\\data_new\\file2\\";
          walk("G:\\开题\\data_new\\file1\\" ,path1);
    }
    //写入表头
    public static void writeHeader(String path){
        try {
            File csv = new File(path); // CSV数据文件
            BufferedWriter bw = new BufferedWriter(new FileWriter(csv, true));
            String header ="role,projects,business_unit,functional_unit,department,team,supervisor";
            for (int i = 0; i < 256; i++) {
                header+=",to_remove,from_remove,date,activity,isDecoy,isCommon,count";
            }
            bw.write(header);
            bw.newLine();
            bw.close();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
    //读取一行，获取用户的信息
    public static String  readFirst(String path){
        String userMesage = "";
        try {
            BufferedReader reader1 = new BufferedReader(new FileReader(path));//鎹㈡垚浣犵殑鏂囦欢鍚?
            String line1 = null;
            String strNew[] = null;
            while ((line1 = reader1.readLine()) != null) {
                strNew = line1.split(",");
                break;
            }
            for (int i = 0; i <7; i++) {
                userMesage += strNew[i]+",";
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return userMesage;
    }
    public static void read(String path,String path1){
        try {
            HashSet<String> set = new HashSet<>();
            String userMessage = readFirst(path);
            BufferedReader reader1 = new BufferedReader(new FileReader(path));//换成你的文件名
            reader1.readLine();//第一行信息，为标题信息，不用，如果需要，注释掉
            String line1 = null;
            File tempFile =new File( path.trim());
            String fileName = tempFile.getName();
            fileNameNew = fileName;

            HashMap<String,String> map = new HashMap<>();
            while ((line1 = reader1.readLine()) != null) {
                String item[] = line1.split(",");//一行数组
                String str = item[item.length - 6]+"," +item[item.length - 5] + ","+item[item.length - 4] + "," +item[item.length - 7] + "," + item[item.length - 3] + "," +item[item.length - 2] + "," + item[item.length - 1];
                String time = item[item.length - 4];
                String isDecoy = item[item.length-3];
                String time1[] = time.split(" ");
                String isCommon = item[item.length-2];
                String activity =item[item.length - 7];
                String from_remove = item[item.length-5];
                String to_remove =item[item.length - 6];
                set.add(time1[0]);
                String key=to_remove+","+from_remove+","+time+ "," +activity + "," +  isDecoy + "," + isCommon;
                map.put(key, str);
            }
            write(userMessage,path1+fileNameNew,set,map);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void write(String userMessage,String path,HashSet<String> set,HashMap<String,String> map){
        try {
            writeHeader(path);
            File csv = new File(path); // CSV数据文件
            BufferedWriter bw = new BufferedWriter(new FileWriter(csv, true));
            String time[] = {" 00:00:00-05:59:59"," 06:00:00-11:59:59"," 12:00:00-17:59:59"," 18:00:00-23:59:59"};
            String activity[] = {"File Open","File Write","File Delete","File Copy"};
            String to_remove[]={"True,True","True,False","False,False","False,True"};
            String decoy_common[]={"1,1","1,0","0,1","0,0"};
            int total = 0;
            int sizes = 0;
                for (String key:set) {
                String value = "";
                for (int j = 0; j < time.length; j++) {
                    for (int i = 0; i <activity.length ; i++) {
                        //是诱饵文件且是公共的
                        for (int k = 0; k < to_remove.length; k++) {
                            for (int l = 0; l < decoy_common.length; l++) {
                                   String keys = to_remove[k]+","+key + time[j] + "," + activity[i] + "," +decoy_common[l];
                                if (map.get(keys) != null) {
                                    value += map.get(keys) + ",";
                                    String totals[]=map.get(keys).split(",");
                                    int aa=Integer.valueOf(totals[totals.length-1]);
                                    total +=aa ;
                                    sizes++;
                                } else {
                                    value += keys + "," + "0" + ",";
                                }

                            }
                        }
                    }

                }
                System.out.println(total);
                System.out.println("si" +sizes);
                String str = userMessage+value.substring(0,value.length()-1);
                bw.write(str);
                bw.newLine();
        }

            bw.close();
        } catch (Exception e) {
            // File对象的创建过程中的异常捕获
            e.printStackTrace();
        }
    }

}
