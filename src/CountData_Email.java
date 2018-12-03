import java.io.*;
import java.util.HashMap;
import java.util.HashSet;

/**
 * @author Yangxiaoqing
 * @ClassName: CountData_Logon
 * @Desciption: 统计每个用户的一天的操作Email的情况，放在email2文件下
 * @date 2018/11/16 14:04
 * @Version 1.0
 */
public class CountData_Email {
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
        String path1= "email2/";
        walk("email1/" ,path1);
//          String path1= "G:\\开题\\data_new\\email2\\";
//          walk("G:\\开题\\data_new\\email1\\" ,path1);
    }
    //写入表头
    public static void writeHeader(String path){
        try {
            File csv = new File(path); // CSV数据文件
            BufferedWriter bw = new BufferedWriter(new FileWriter(csv, true));
            String header ="role,projects,business_unit,functional_unit,department,team,supervisor";
            for (int i = 0; i < 256; i++) {
                header+=",toIsComm,ccIsComm,bccIsComm,fromIsComm,date,activity,isAttr,count";
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
            reader1.readLine();//第一行信息，为标题信息，不用，如果需要，注释掉
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

                String time = item[item.length - 3];
                String isAttr = item[item.length-2];
                String time1[] = time.split(" ");
                String fromIsComm = item[item.length-4];
                String activity =item[item.length - 8];
                String toIsComm = item[item.length-7];
                String ccIsComm = item[item.length-6];
                String bccIsComm = item[item.length-5];
                set.add(time1[0]);
                String key=toIsComm+","+ccIsComm+","+bccIsComm+","+fromIsComm+","+time+ "," +activity + "," +  isAttr;
                String str= toIsComm+","+ccIsComm+","+bccIsComm+","+fromIsComm+","+time+ "," +activity + "," +  isAttr+","+item[item.length-1];
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
            String activity[] = {"View","Send"};
            String isAttr[]={"1","0"};
            String isTo[]={"1","0"};
            String isCc[]={"1","0"};
            String isBcc[]={"1","0"};
            String isFrom[]={"1","0"};
            int total = 0;
            int sizes = 0;
            for (String key:set) {
                String value = "";
                for (int j = 0; j < time.length; j++) {
                    for (int i = 0; i <activity.length ; i++) {
                        //是诱饵文件且是公共的
                        for (int k = 0; k < isAttr.length; k++) {
                            for (int l = 0; l < isTo.length; l++) {
                                for (int m = 0; m <isCc.length ; m++) {
                                    for (int n = 0; n <isBcc.length ; n++) {
                                        for (int o = 0; o <isFrom.length ; o++) {
                                            String keys = isTo[l]+","+isCc[m]+","+isBcc[n]+","+isFrom[o]+","+key + time[j] + "," + activity[i] + "," +isAttr[k];
                                            if (map.get(keys) != null) {
                                                value += map.get(keys) + ",";
                                                String totals[]=map.get(keys).split(",");
                                                int aa=Integer.valueOf(totals[totals.length-1]);
                                                total+=aa;
                                            } else {
                                                value += keys + "," + "0" + ",";
                                            }
                                        }

                                    }

                                }
                            }
                        }
                    }

                }
                String str = userMessage+value.substring(0,value.length()-1);
                bw.write(str);
                bw.newLine();
            }
           System.out.println(total);
            bw.close();
        } catch (Exception e) {
            // File对象的创建过程中的异常捕获
            e.printStackTrace();
        }
    }
//    public static  void walk( String path ) {
//
//        File root = new File( path );
//        File[] list = root.listFiles();
//
//        if (list == null) return;
//
//        for ( File f : list ) {
//            // System.out.println( "File:" + f.getAbsoluteFile() );
//            String filePath = f.getAbsolutePath();
//
//            read(filePath);
//        }
//    }
//    public static void read(String path){
//        try {
//            HashMap<String,String> map1 = new HashMap<>();//时间在0-6
//            HashMap<String,String> map12 = new HashMap<>();//6-12
//            HashMap<String,String> map2 = new HashMap<>();//时间在0-6
//            HashMap<String,String> map21 = new HashMap<>();//6-12
//            HashMap<String,String> map3 = new HashMap<>();//时间在0-6
//            HashMap<String,String> map31 = new HashMap<>();//6-12
//            HashMap<String,String> map4 = new HashMap<>();//时间在0-6
//            HashMap<String,String> map41 = new HashMap<>();//6-12
//            HashSet<String> set = new HashSet<>();
//            BufferedReader reader1 = new BufferedReader(new FileReader(path));//换成你的文件名
//
//            reader1.readLine();//第一行信息，为标题信息，不用，如果需要，注释掉
//
//            String line1 = null;
//            File tempFile =new File( path.trim());
//            String fileName = tempFile.getName();
//            String header ="role,projects,business_unit,functional_unit,department,team,supervisor,date,activity,count,date,activity,count,date,activity,count,date,activity,count,date,activity,count,date,activity,count,date,activity,count,date,activity,count";
//            write(fileName,header,"");
//            HashMap<String,String> map = new HashMap<>();
//
//            while ((line1 = reader1.readLine()) != null) {
//                String item[] = line1.split(",");//一行数组
//                String str = item[item.length - 3] + "," + item[item.length - 2] + "," + item[item.length - 1];
//                String time = item[item.length - 3];
//
//                String time1[] = time.split(" ");
//                set.add(time1[0]);
//                if (time1[1].equals("00:00:00-05:59:59")) {
//                    if (item[item.length - 2].equals("View")) {
//                        map1.put(item[item.length - 3], str);
//                    } else {
//                        map12.put(item[item.length - 3], str);
//                    }
//                }
//                else if (time1[1].equals("06:00:00-11:59:59")) {
//                    if (item[item.length - 2].equals("View")) {
//                        map2.put(item[item.length - 3], str);
//                    } else {
//                        map21.put(item[item.length - 3], str);
//                    }
//                }
//                else if (time1[1].equals("12:00:00-17:59:59")) {
//                    if (item[item.length - 2].equals("View")) {
//                        map3.put(item[item.length - 3], str);
//                    } else {
//                        map31.put(item[item.length - 3], str);
//                    }
//                }
//                else if (time1[1].equals("18:00:00-23:59:59")) {
//                    if (item[item.length - 2].equals("View")) {
//                        map4.put(item[item.length - 3], str);
//                    } else {
//                        map41.put(item[item.length - 3], str);
//                    }
//                }
//            }
//            BufferedReader reader = new BufferedReader(new FileReader(path));//换成你的文件名
//            String line = null;
//            reader.readLine();
//            String strNew[] = null;
//            while ((line = reader.readLine()) != null) {
//                strNew = line.split(",");//一行数组
//                break;
//            }
//            for (String key:set) {
//                String value = "";
//                if(map12.get(key+" 00:00:00-05:59:59")!=null){
//                    value+=map12.get(key+" 00:00:00-05:59:59")+",";
//                }else{
//                    value+=key+" 00:00:00-05:59:59" + "," + "Send" + "," + "0"+",";
//                }
//                if(map1.get(key+" 00:00:00-05:59:59")!=null){
//                   value+=map1.get(key+" 00:00:00-05:59:59")+",";
//                }else{
//                    value+=key+" 00:00:00-05:59:59" + "," + "View" + "," + "0"+",";
//                }
//                if(map21.get(key+" 06:00:00-11:59:59")!=null){
//                    value+=map21.get(key+" 06:00:00-11:59:59")+",";
//                }else{
//                    value+=key+" 06:00:00-11:59:59" + "," + "Send" + "," + "0"+",";
//                }
//                if(map2.get(key+" 06:00:00-11:59:59")!=null){
//                    value+=map2.get(key+" 06:00:00-11:59:59")+",";
//                }else{
//                    value+=key+" 06:00:00-11:59:59" + "," + "View" + "," + "0"+",";
//                }
//                if(map31.get(key+" 12:00:00-17:59:59")!=null){
//                    value+=map31.get(key+" 12:00:00-17:59:59")+",";
//                }else{
//                    value+=key+" 12:00:00-17:59:59" + "," + "Send" + "," + "0"+",";
//                }
//                if(map3.get(key+" 12:00:00-17:59:59")!=null){
//                    value+=map3.get(key+" 12:00:00-17:59:59")+",";
//                }else{
//                    value+=key+" 12:00:00-17:59:59" + "," + "View" + "," + "0"+",";
//                }
//                if(map41.get(key+" 18:00:00-23:59:59")!=null){
//                    value+=map41.get(key+" 18:00:00-23:59:59")+",";
//                }else{
//                    value+=key+" 18:00:00-23:59:59" + "," + "Send" + "," + "0"+",";
//                }
//                if(map4.get(key+" 18:00:00-23:59:59")!=null){
//                    value+=map4.get(key+" 18:00:00-23:59:59")+",";
//                }else{
//                    value+=key+" 18:00:00-23:59:59" + "," + "View" + "," + "0"+",";
//                }
//                String str = "";
//                for (int i = 0; i <strNew.length-3 ; i++) {
//                    str +=strNew[i]+",";
//                }
//
//                write(fileName,str,value.substring(0,value.length()-1));
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//    public static void main(String[] args) {
//        walk("email1");
//        //walk("G:\\开题\\data_new\\email1");
//    }
//
//    public static void write(String path, String str1, String str2) {
//
//        try {
//            File csv = new File("email2/" + path ); // CSV数据文件
//           // File csv = new File("G:\\开题\\data_new\\email2\\" + path ); // CSV数据文件
//            BufferedWriter bw = new BufferedWriter(new FileWriter(csv, true)); // 附加
//            // 添加新的数据行
//            bw.write(str1 + str2);
//            bw.newLine();
//            bw.close();
//        } catch (Exception e) {
//            // File对象的创建过程中的异常捕获
//            e.printStackTrace();
//        }
//    }
}
