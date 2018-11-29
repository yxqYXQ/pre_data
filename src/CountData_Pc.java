import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * @author Yangxiaoqing
 * @ClassName: CountData_Pc
 * @Desciption: 合并一天的pc使用情况，以及使用公共的和非公共的次数
 * @date 2018/11/26 10:38
 * @Version 1.0
 */
public class CountData_Pc {
    private  static HashMap<String,String> map = new HashMap<String,String>();
    private  static ArrayList<String> list = new ArrayList<String>();
    @SuppressWarnings("unchecked")
    public static void main(String[] args){
        search();
        //walk("G:\\开题\\data_new\\pc_use1");
        walk("pc_use1");
    }
    @SuppressWarnings("unchecked")
    public static void search(){
        try{

//            String path = "G:\\开题\\data_new\\User_PC.csv";//用户一对一的pc
//            String path1 ="G:\\开题\\data_new\\User_PC_All.csv";//用户公共的pc
            String path = "User_PC.csv";//用户一对一的pc
            String path1 ="User_PC_All.csv";//用户公共的pc
            BufferedReader reader = new BufferedReader(new FileReader(path));
            BufferedReader reader1 = new BufferedReader(new FileReader(path1));

            String line =null;
            String line1 = null;
            while ((line = reader.readLine()) != null) {
                String item[] = line.split(",");
                map.put(item[0],item[1]);//用户和pc对
            }
            while ((line1 = reader1.readLine()) != null) {
                String item[] = line1.split(",");//一行数组v
                list.add(item[0]);//公共pc
            }
        }catch (Exception e){
            System.out.println("错误1"+e.getStackTrace());
        }
    }
    @SuppressWarnings("unchecked")
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
    @SuppressWarnings("unchecked")
    public static void write(String path,String userMeaasge,HashMap<String,String> map1,HashMap<String,String> map12,HashMap<String,String> map2, HashMap<String,String> map21,HashMap<String,String> map3,HashMap<String,String> map31, HashMap<String,String> map4, HashMap<String,String> map41,HashSet<String> set){
        try {

            File csv = new File("pc_use2/" + path ); // CSV数据文件
            //File csv = new File("G:\\开题\\data_new\\pc_use2\\" + path); // CSV数据文件
            BufferedWriter bw = new BufferedWriter(new FileWriter(csv, true)); // 附加
           //是公共的pc为1，否则为0
            for (String key : set) {
                String value = "";
                if (map12.get(key + " 00:00:00-05:59:59") != null) {

                    value += map12.get(key + " 00:00:00-05:59:59") + ",";
                } else {
                    value += key + " 00:00:00-05:59:59" + "," + "0" + "," + "0" + ",";
                }
                if (map1.get(key + " 00:00:00-05:59:59") != null) {
                    value += map1.get(key + " 00:00:00-05:59:59") + ",";
                } else {
                    value += key + " 00:00:00-05:59:59" + "," + "1" + "," + "0" + ",";
                }
                if (map21.get(key + " 06:00:00-11:59:59") != null) {
                    value += map21.get(key + " 06:00:00-11:59:59") + ",";
                } else {
                    value += key + " 06:00:00-11:59:59" + "," + "0" + "," + "0" + ",";
                }
                if (map2.get(key + " 06:00:00-11:59:59") != null) {
                    value += map2.get(key + " 06:00:00-11:59:59") + ",";
                } else {
                    value += key + " 06:00:00-11:59:59" + "," + "1" + "," + "0" + ",";
                }
                if (map31.get(key + " 12:00:00-17:59:59") != null) {
                    value += map31.get(key + " 12:00:00-17:59:59") + ",";
                } else {
                    value += key + " 12:00:00-17:59:59" + "," + "0" + "," + "0" + ",";
                }
                if (map3.get(key + " 12:00:00-17:59:59") != null) {
                    value += map3.get(key + " 12:00:00-17:59:59") + ",";
                } else {
                    value += key + " 12:00:00-17:59:59" + "," + "1" + "," + "0" + ",";
                }
                if (map41.get(key + " 18:00:00-23:59:59") != null) {
                    value += map41.get(key + " 18:00:00-23:59:59") + ",";
                } else {
                    value += key + " 18:00:00-23:59:59" + "," + "0" + "," + "0" + ",";
                }
                if (map4.get(key + " 18:00:00-23:59:59") != null) {
                    value += map4.get(key + " 18:00:00-23:59:59")+ ",";
                } else {
                    value += key + " 18:00:00-23:59:59" + "," + "1" + "," + "0" + ",";
                }
               String messgae = userMeaasge+","+value.substring(0,value.length()-1);
               bw.write(messgae);
               bw.newLine();
            }
            bw.close();
          //  System.out.println("sdf"+total);
        }catch(Exception e){
            System.out.println("错误2"+e.getMessage());
        }
    }
    @SuppressWarnings("unchecked")
    public static void writeHeader(String fileName){
        try {
           // File csv = new File("G:\\开题\\data_new\\pc_use2\\" + fileName); // CSV数据文件
            File csv = new File("pc_use2/" + fileName); // CSV数据文件
            BufferedWriter bw = new BufferedWriter(new FileWriter(csv, true)); // 附加
            String header = "role,projects,business_unit,functional_unit,department,team,supervisor,date,activity,count,date,activity,count,date,activity,count,date,activity,count,date,activity,count,date,activity,count,date,activity,count,date,activity,count";
            bw.write(header);
            bw.newLine();
            bw.close();
        }catch (Exception e){
            System.out.println("错误3"+e.getMessage());
        }
    }
    @SuppressWarnings("unchecked")
    public static void read(String path){
        try {
            HashMap<String,String> map1 = new HashMap<>();//时间在0-6使用公共的pc
            HashMap<String,String> map12 = new HashMap<>();//时间再0-6使用非公共的pc
            HashMap<String,String> map2 = new HashMap<>();//时间在6-12使用公共的pc
            HashMap<String,String> map21 = new HashMap<>();//时间再6-12使用非公共的pc
            HashMap<String,String> map3 = new HashMap<>();//时间在12-18使用公共的pc
            HashMap<String,String> map31 = new HashMap<>();//时间在12-18使用非公共的pc
            HashMap<String,String> map4 = new HashMap<>();//时间在18-24使用公共的pc
            HashMap<String,String> map41 = new HashMap<>();//时间再18-24使用非公共的pc
            HashSet<String> set = new HashSet<>();
            BufferedReader reader1 = new BufferedReader(new FileReader(path));//换成你的文件名
            String line1 = null;
            File tempFile =new File( path.trim());
            String fileName = tempFile.getName();
            writeHeader(fileName);
            String userName[] = fileName.split("_");
            while ((line1 = reader1.readLine()) != null) {
                String item[] = line1.split(",");//一行数组

                String time = item[item.length - 3];
                String pc = item[item.length-2];
                String time1[] = time.split(" ");
                set.add(time1[0]);
                if (time1[1].equals("00:00:00-05:59:59")) {
                    if ((map.get(userName[2])).equals(pc)||list.contains(pc)) {
                        String newCount =item[item.length-1];
                        if(map1.get(item[item.length - 3])!=null){
                            String str[]= map1.get(item[item.length - 3]).split(",");
                            int count = Integer.parseInt(str[2]);
                            newCount = String.valueOf(count+Integer.parseInt(item[item.length-1]));
                        }

                        String str = item[item.length - 3]+","+"1"+","+newCount;
                        map1.put(item[item.length - 3], str);
                    } else {
                        String str = item[item.length - 3]+","+"0"+","+item[item.length-1];
                        map12.put(item[item.length - 3], str);
                    }
                }
                else if (time1[1].equals("06:00:00-11:59:59")) {
                    if ((map.get(userName[2])).equals(pc)||list.contains(pc)) {
                        String newCount =item[item.length-1];
                        if(map2.get(item[item.length - 3])!=null){
                            String str[]= map2.get(item[item.length - 3]).split(",");
                            int count = Integer.parseInt(str[2]);
                            newCount = String.valueOf(count+Integer.parseInt(item[item.length-1]));
                        }
                        String str = item[item.length - 3]+","+"1"+","+newCount;
                        map2.put(item[item.length - 3], str);
                    } else {
                        String str = item[item.length - 3]+","+"0"+","+item[item.length-1];
                        map21.put(item[item.length - 3], str);
                    }
                }
                else if (time1[1].equals("12:00:00-17:59:59")) {
                    if ((map.get(userName[2])).equals(pc)||list.contains(pc)) {
                        String newCount =item[item.length-1];
                        if(map3.get(item[item.length - 3])!=null){
                            String str[]= map3.get(item[item.length - 3]).split(",");
                            int count = Integer.parseInt(str[2]);
                            newCount = String.valueOf(count+Integer.parseInt(item[item.length-1]));
                        }
                        String str = item[item.length - 3]+","+"1"+","+newCount;

                        map3.put(item[item.length - 3], str);
                    } else {
                        String str = item[item.length - 3]+","+"0"+","+item[item.length-1];
                        map31.put(item[item.length - 3], str);
                    }
                }
                else if (time1[1].equals("18:00:00-23:59:59")) {
                    if ((map.get(userName[2])).equals(pc)||list.contains(pc)) {
                        String newCount =item[item.length-1];
                        if(map4.get(item[item.length - 3])!=null){
                            String str[]= map4.get(item[item.length - 3]).split(",");
                            int count = Integer.parseInt(str[2]);
                            newCount = String.valueOf(count+Integer.parseInt(item[item.length-1]));
                        }
                        String str = item[item.length - 3]+","+"1"+","+newCount;

                        map4.put(item[item.length - 3], str);
                    } else {
                        String str = item[item.length - 3]+","+"0"+","+item[item.length-1];
                        map41.put(item[item.length - 3], str);
                    }
                }
            }
            BufferedReader reader = new BufferedReader(new FileReader(path));//换成你的文件名
            String line = null;
            String strNew[] = null;
            String userMessage="";
            while ((line = reader.readLine()) != null) {
                strNew = line.split(",");//一行数组
                userMessage = strNew[0]+","+strNew[1]+","+strNew[2]+","+strNew[3]+","+strNew[4]+","+strNew[5]+","+strNew[6];
                break;
            }
           write(fileName,userMessage,map1,map12,map2,map21,map3,map31,map4,map41,set);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
