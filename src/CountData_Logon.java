import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

/**
 * @author Yangxiaoqing
 * @ClassName: CountData_Logon
 * @Desciption: 合并每个用户一天的登陆。注销情况，放在logon文件下
 * @date 2018/11/16 14:04
 * @Version 1.0
 */
public class CountData_Logon {

    public static  void walk( String path ) {

        File root = new File( path );
        File[] list = root.listFiles();

        if (list == null) return;

        for ( File f : list ) {
            // System.out.println( "File:" + f.getAbsoluteFile() );
            String filePath = f.getAbsolutePath();

            read(filePath);
        }
    }
    public static void read(String path){
        try {
            HashMap<String,String> map1 = new HashMap<>();//时间在0-6
            HashMap<String,String> map12 = new HashMap<>();//
            HashMap<String,String> map2 = new HashMap<>();//时间在0-6
            HashMap<String,String> map21 = new HashMap<>();//
            HashMap<String,String> map3 = new HashMap<>();//时间在0-6
            HashMap<String,String> map31 = new HashMap<>();//
            HashMap<String,String> map4 = new HashMap<>();//时间在0-6
            HashMap<String,String> map41 = new HashMap<>();//
            HashSet<String> set = new HashSet<>();
            BufferedReader reader1 = new BufferedReader(new FileReader(path));//换成你的文件名

            reader1.readLine();//第一行信息，为标题信息，不用，如果需要，注释掉

            String line1 = null;
            File tempFile =new File( path.trim());
            String fileName = tempFile.getName();
            String header ="role,projects,business_unit,functional_unit,department,team,supervisor,date,activity,count,date,activity,count,date,activity,count,date,activity,count,date,activity,count,date,activity,count,date,activity,count,date,activity,count";
            write(fileName,header,"");
            HashMap<String,String> map = new HashMap<>();

            while ((line1 = reader1.readLine()) != null) {
                String item[] = line1.split(",");//一行数组
                String str = item[item.length - 3] + "," + item[item.length - 2] + "," + item[item.length - 1];
                String time = item[item.length - 3];

                String time1[] = time.split(" ");
                set.add(time1[0]);
                if (time1[1].equals("00:00:00-05:59:59")) {
                    if (item[item.length - 2].equals("Logon")) {
                        map1.put(item[item.length - 3], str);
                    } else {
                        map12.put(item[item.length - 3], str);
                    }
                }
                else if (time1[1].equals("06:00:00-11:59:59")) {
                    if (item[item.length - 2].equals("Logon")) {
                        map2.put(item[item.length - 3], str);
                    } else {
                        map21.put(item[item.length - 3], str);
                    }
                }
                else if (time1[1].equals("12:00:00-17:59:59")) {
                    if (item[item.length - 2].equals("Logon")) {
                        map3.put(item[item.length - 3], str);
                    } else {
                        map31.put(item[item.length - 3], str);
                    }
                }
                else if (time1[1].equals("18:00:00-23:59:59")) {
                    if (item[item.length - 2].equals("Logon")) {
                        map4.put(item[item.length - 3], str);
                    } else {
                        map41.put(item[item.length - 3], str);
                    }
                }
            }
            BufferedReader reader = new BufferedReader(new FileReader(path));//换成你的文件名
            String line = null;
            reader.readLine();
            String strNew[] = null;
            while ((line = reader.readLine()) != null) {
                strNew = line.split(",");//一行数组
                break;
            }
            for (String key:set) {
                String value = "";
                if(map12.get(key+" 00:00:00-05:59:59")!=null){
                    value+=map12.get(key+" 00:00:00-05:59:59")+",";
                }else{
                    value+=key+" 00:00:00-05:59:59" + "," + "Logoff" + "," + "0"+",";
                }
                if(map1.get(key+" 00:00:00-05:59:59")!=null){
                   value+=map1.get(key+" 00:00:00-05:59:59")+",";
                }else{
                    value+=key+" 00:00:00-05:59:59" + "," + "Logon" + "," + "0"+",";
                }
                if(map21.get(key+" 06:00:00-11:59:59")!=null){
                    value+=map21.get(key+" 06:00:00-11:59:59")+",";
                }else{
                    value+=key+" 06:00:00-11:59:59" + "," + "Logoff" + "," + "0"+",";
                }
                if(map2.get(key+" 06:00:00-11:59:59")!=null){
                    value+=map2.get(key+" 06:00:00-11:59:59")+",";
                }else{
                    value+=key+" 06:00:00-11:59:59" + "," + "Logon" + "," + "0"+",";
                }
                if(map31.get(key+" 12:00:00-17:59:59")!=null){
                    value+=map31.get(key+" 12:00:00-17:59:59")+",";
                }else{
                    value+=key+" 12:00:00-17:59:59" + "," + "Logoff" + "," + "0"+",";
                }
                if(map3.get(key+" 12:00:00-17:59:59")!=null){
                    value+=map3.get(key+" 12:00:00-17:59:59")+",";
                }else{
                    value+=key+" 12:00:00-17:59:59" + "," + "Logon" + "," + "0"+",";
                }
                if(map41.get(key+" 18:00:00-23:59:59")!=null){
                    value+=map41.get(key+" 18:00:00-23:59:59")+",";
                }else{
                    value+=key+" 18:00:00-23:59:59" + "," + "Logoff" + "," + "0"+",";
                }
                if(map4.get(key+" 18:00:00-23:59:59")!=null){
                    value+=map4.get(key+" 18:00:00-23:59:59")+",";
                }else{
                    value+=key+" 18:00:00-23:59:59" + "," + "Logon" + "," + "0"+",";
                }
                String str = "";
                for (int i = 0; i <strNew.length-3 ; i++) {
                    str +=strNew[i]+",";
                }

                write(fileName,str,value.substring(0,value.length()-1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        walk("logon1");
    }

    public static void write(String path, String str1, String str2) {

        try {
            File csv = new File("logon2/" + path ); // CSV数据文件
            //File csv = new File("G:\\开题\\data_new\\logon2\\" + path ); // CSV数据文件
            BufferedWriter bw = new BufferedWriter(new FileWriter(csv, true)); // 附加
            // 添加新的数据行
            bw.write(str1 + str2);
            bw.newLine();
            bw.close();
        } catch (Exception e) {
            // File对象的创建过程中的异常捕获
            e.printStackTrace();
        }
    }
}
