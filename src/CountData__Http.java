import java.io.*;
import java.util.HashMap;
import java.util.HashSet;

/**
 * @author Yangxiaoqing
 * @ClassName: CountData_Logon
 * @Desciption: 统计每个用户的每一天的操作http的情况,放在http2文件下
 * @date 2018/11/16 14:04
 * @Version 1.0
 */
public class CountData__Http {
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
        String path1= "http2/";
        walk("http1/" ,path1);
//          String path1= "G:\\开题\\data_new\\http2\\";
//          walk("G:\\开题\\data_new\\http1\\" ,path1);
    }
    //写入表头
    public static void writeHeader(String path){
        try {
            File csv = new File(path); // CSV数据文件
            BufferedWriter bw = new BufferedWriter(new FileWriter(csv, true));
            String header ="role,projects,business_unit,functional_unit,department,team,supervisor";
            for (int i = 0; i < 24; i++) {
                header+=",date,activity,isCommon,count";
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
            reader1.readLine();//第一行信息，为标题信息，不用，如果需要，注释掉
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
            int total = 0;
            HashMap<String,String> map = new HashMap<>();
            while ((line1 = reader1.readLine()) != null) {
                String item[] = line1.split(",");//一行数组
                String str = item[item.length - 3] + "," + item[item.length - 4] + "," + item[item.length - 2] + "," + item[item.length - 1];
                String time = item[item.length - 3];
                String isCommon = item[item.length - 2];
                String time1[] = time.split(" ");
                String activity = item[item.length-4];
                set.add(time1[0]);
                String key=time+ "," +activity + "," + isCommon;
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
            String activity[] = {"WWW Upload","WWW Visit","WWW Download"};
            String isCommon[]={"1","0"};

            for (String key:set) {
                String value = "";
                for (int j = 0; j < time.length; j++) {
                    for (int i = 0; i <activity.length ; i++) {
                        //是诱饵文件且是公共的

                            for (int l = 0; l < isCommon.length; l++) {
                                String keys = key + time[j] + "," + activity[i] + "," +isCommon[l];
                                if (map.get(keys) != null) {
                                    value += map.get(keys) + ",";
//                                    String totals[]=map.get(keys).split(",");
//                                    int aa=Integer.valueOf(totals[totals.length-1]);
//                                    total+=aa;
                                } else {
                                    value += keys + "," + "0" + ",";
                                }
                        }
                    }

                }
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
//    public static  void walk( String path ) {
//
//        File root = new File( path );
//        File[] list = root.listFiles();
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
//            HashMap<String,String> map12 = new HashMap<>();//
//            HashMap<String,String> map13 = new HashMap<>();//
//            HashMap<String,String> map111 = new HashMap<>();//时间在0-6
//            HashMap<String,String> map121 = new HashMap<>();//
//            HashMap<String,String> map131 = new HashMap<>();//
//            HashMap<String,String> map2 = new HashMap<>();//时间在6-12
//            HashMap<String,String> map21 = new HashMap<>();//
//            HashMap<String,String> map22 = new HashMap<>();//
//            HashMap<String,String> map231 = new HashMap<>();//时间在6-12
//            HashMap<String,String> map211 = new HashMap<>();//
//            HashMap<String,String> map221 = new HashMap<>();//
//            HashMap<String,String> map3 = new HashMap<>();//时间在12-18
//            HashMap<String,String> map31 = new HashMap<>();//
//            HashMap<String,String> map32 = new HashMap<>();//
//            HashMap<String,String> map311 = new HashMap<>();//时间在12-18
//            HashMap<String,String> map331= new HashMap<>();//
//            HashMap<String,String> map321= new HashMap<>();//
//            HashMap<String,String> map4 = new HashMap<>();//时间在18-24
//            HashMap<String,String> map41 = new HashMap<>();//
//            HashMap<String,String> map42 = new HashMap<>();//
//            HashMap<String,String> map411 = new HashMap<>();//时间在18-24
//            HashMap<String,String> map431 = new HashMap<>();//
//            HashMap<String,String> map421 = new HashMap<>();//
//            HashSet<String> set = new HashSet<>();
//            BufferedReader reader1 = new BufferedReader(new FileReader(path));//换成你的文件名
//
//            reader1.readLine();//第一行信息，为标题信息，不用，如果需要，注释掉
//
//            String line1 = null;
//            File tempFile =new File( path.trim());
//            String fileName = tempFile.getName();
//            String header ="role,projects,business_unit,functional_unit,department,team,supervisor,date,isCommom,activity,count,date,isCommom,activity,count,date,isCommom,activity,count,date,isCommom,activity,count,date,isCommom,activity,count,date,isCommom,activity,count,date,isCommom,activity,count,date,isCommom,activity,count,date,isCommom,activity,count,date,isCommom,activity,count,date,isCommom,activity,count,date,isCommom,activity,count,date,isCommom,activity,count,date,isCommom,activity,count,date,isCommom,activity,count,date,isCommom,activity,count,date,isCommom,activity,count,date,isCommom,activity,count,date,isCommom,activity,count,date,isCommom,activity,count,date,isCommom,activity,count,date,isCommom,activity,count,date,isCommom,activity,count,date,isCommom,activity,count";
//            write(fileName,header,"");
//            HashMap<String,String> map = new HashMap<>();
//
//            while ((line1 = reader1.readLine()) != null) {
//                String item[] = line1.split(",");//一行数组
//                String str = item[item.length - 4] + "," + item[item.length - 3] + "," + item[item.length - 2] + "," + item[item.length - 1];
//                String time = item[item.length - 4];
//                String isCommom = item[item.length - 3];
//                String time1[] = time.split(" ");
//                set.add(time1[0]);
//                if (time1[1].equals("00:00:00-05:59:59")) {
//                    if (item[item.length - 2].equals("WWW Visit")) {
//                        if(isCommom.equals("1")) {
//                            map1.put(item[item.length - 4], str);
//                        }else{
//                            map111.put(item[item.length - 4], str);
//                        }
//                    } else if (item[item.length - 2].equals("WWW Upload")) {
//                        if(isCommom.equals("1")) {
//                            map12.put(item[item.length - 4], str);
//                        }else{
//                            map121.put(item[item.length - 4], str);
//                        }
//                    }else {
//                        if(isCommom.equals("1")) {
//                            map13.put(item[item.length - 4], str);
//                        }else{
//                            map131.put(item[item.length - 4], str);
//                        }
//                    }
//                }
//                else if (time1[1].equals("06:00:00-11:59:59")) {
//                    if (item[item.length - 2].equals("WWW Visit")) {
//                        if(isCommom.equals("1")) {
//                            map2.put(item[item.length - 4], str);
//                        }else{
//                            map211.put(item[item.length - 4], str);
//                        }
//                    }  else if (item[item.length - 2].equals("WWW Upload")) {
//                        if(isCommom.equals("1")) {
//                            map21.put(item[item.length - 4], str);
//                        }else{
//                            map221.put(item[item.length - 4], str);
//                        }
//                    }else {
//                        if(isCommom.equals("1")) {
//                            map22.put(item[item.length - 4], str);
//                        }else{
//                            map231.put(item[item.length - 4], str);
//                        }
//                    }
//                }
//                else if (time1[1].equals("12:00:00-17:59:59")) {
//                    if (item[item.length - 2].equals("WWW Visit")) {
//                        if(isCommom.equals("1")) {
//                            map3.put(item[item.length - 4], str);
//                        }else{
//                            map311.put(item[item.length - 4], str);
//                        }
//                    }  else if (item[item.length - 2].equals("WWW Upload")) {
//                        if(isCommom.equals("1")) {
//                            map31.put(item[item.length - 4], str);
//                        }else{
//                            map321.put(item[item.length - 4], str);
//                        }
//                    }else {
//                        if(isCommom.equals("1")) {
//                            map32.put(item[item.length - 4], str);
//                        }else{
//                            map331.put(item[item.length - 4], str);
//                        }
//                    }
//                }
//                else if (time1[1].equals("18:00:00-23:59:59")) {
//                    if (item[item.length - 2].equals("WWW Visit")) {
//                        if(isCommom.equals("1")) {
//                            map4.put(item[item.length - 4], str);
//                        }else{
//                            map411.put(item[item.length - 4], str);
//                        }
//                    }  else if (item[item.length - 2].equals("WWW Upload")) {
//                        if(isCommom.equals("1")) {
//                            map41.put(item[item.length - 4], str);
//                        }else{
//                            map421.put(item[item.length - 4], str);
//                        }
//                    }else {
//                        if(isCommom.equals("1")) {
//                            map42.put(item[item.length - 4], str);
//                        }else{
//                            map431.put(item[item.length - 4], str);
//                        }
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
//
//                if(map12.get(key+" 00:00:00-05:59:59")!=null){
//                    value+=map12.get(key+" 00:00:00-05:59:59")+",";
//                }else{
//                    value+=key+" 00:00:00-05:59:59" + ","  + "1"+","+ "WWW Upload" + "," + "0"+",";
//                }
//                if(map121.get(key+" 00:00:00-05:59:59")!=null){
//                    value+=map121.get(key+" 00:00:00-05:59:59")+",";
//                }else{
//                    value+=key+" 00:00:00-05:59:59" + "," + "0"+"," + "WWW Upload" + "," + "0"+",";
//                }
//                if(map1.get(key+" 00:00:00-05:59:59")!=null){
//                    value+=map1.get(key+" 00:00:00-05:59:59")+",";
//                }else{
//                    value+=key+" 00:00:00-05:59:59" + "," + "1"+"," + "WWW Visit" + "," + "0"+",";
//                }
//                if(map111.get(key+" 00:00:00-05:59:59")!=null){
//                    value+=map111.get(key+" 00:00:00-05:59:59")+",";
//                }else{
//                    value+=key+" 00:00:00-05:59:59" + "," + "0"+"," + "WWW Visit" + "," + "0"+",";
//                }
//                if(map13.get(key+" 00:00:00-05:59:59")!=null){
//                    value+=map13.get(key+" 00:00:00-05:59:59")+",";
//                }else{
//                    value+=key+" 00:00:00-05:59:59" + ","  + "1"+","+ "WWW Download" + "," + "0"+",";
//                }
//                if(map131.get(key+" 00:00:00-05:59:59")!=null){
//                    value+=map131.get(key+" 00:00:00-05:59:59")+",";
//                }else{
//                    value+=key+" 00:00:00-05:59:59" + ","  + "0"+","+ "WWW Download" + "," + "0"+",";
//                }
//                if(map21.get(key+" 06:00:00-11:59:59")!=null){
//                    value+=map21.get(key+" 06:00:00-11:59:59")+",";
//                }else{
//                    value+=key+" 06:00:00-11:59:59" + "," + "1"+"," + "WWW Upload" + "," + "0"+",";
//                }
//                if(map221.get(key+" 06:00:00-11:59:59")!=null){
//                    value+=map221.get(key+" 06:00:00-11:59:59")+",";
//                }else{
//                    value+=key+" 06:00:00-11:59:59" + "," + "0"+"," + "WWW Upload" + "," + "0"+",";
//                }
//                if(map2.get(key+" 06:00:00-11:59:59")!=null){
//                    value+=map2.get(key+" 06:00:00-11:59:59")+",";
//                }else{
//                    value+=key+" 06:00:00-11:59:59" + "," + "1"+"," + "WWW Visit" + "," + "0"+",";
//                }
//                if(map211.get(key+" 06:00:00-11:59:59")!=null){
//                    value+=map211.get(key+" 06:00:00-11:59:59")+",";
//                }else{
//                    value+=key+" 06:00:00-11:59:59" + "," + "0"+"," + "WWW Visit" + "," + "0"+",";
//                }
//                if(map22.get(key+" 06:00:00-11:59:59")!=null){
//                    value+=map22.get(key+" 06:00:00-11:59:59")+",";
//                }else{
//                    value+=key+" 06:00:00-11:59:59" + ","  + "1"+","+ "WWW Download" + "," + "0"+",";
//                }
//                if(map231.get(key+" 06:00:00-11:59:59")!=null){
//                    value+=map231.get(key+" 06:00:00-11:59:59")+",";
//                }else{
//                    value+=key+" 06:00:00-11:59:59" + "," + "0"+"," + "WWW Download" + "," + "0"+",";
//                }
//                if(map31.get(key+" 12:00:00-17:59:59")!=null){
//                    value+=map31.get(key+" 12:00:00-17:59:59")+",";
//                }else{
//                    value+=key+" 12:00:00-17:59:59" + "," + "1"+"," + "WWW Upload" + "," + "0"+",";
//                }
//                if(map321.get(key+" 12:00:00-17:59:59")!=null){
//                    value+=map321.get(key+" 12:00:00-17:59:59")+",";
//                }else{
//                    value+=key+" 12:00:00-17:59:59" + ","  + "0"+","+ "WWW Upload" + "," + "0"+",";
//                }
//                if(map3.get(key+" 12:00:00-17:59:59")!=null){
//                    value+=map3.get(key+" 12:00:00-17:59:59")+",";
//                }else{
//                    value+=key+" 12:00:00-17:59:59" + "," + "1"+"," + "WWW Visit" + "," + "0"+",";
//                }
//                if(map311.get(key+" 12:00:00-17:59:59")!=null){
//                    value+=map311.get(key+" 12:00:00-17:59:59")+",";
//                }else{
//                    value+=key+" 12:00:00-17:59:59" + ","  + "0"+","+ "WWW Visit" + "," + "0"+",";
//                }
//                if(map32.get(key+" 12:00:00-17:59:59")!=null){
//                    value+=map32.get(key+" 12:00:00-17:59:59")+",";
//                }else{
//                    value+=key+" 12:00:00-17:59:59" + ","  + "1"+","+ "WWW Download" + "," + "0"+",";
//                }
//                if(map331.get(key+" 12:00:00-17:59:59")!=null){
//                    value+=map331.get(key+" 12:00:00-17:59:59")+",";
//                }else{
//                    value+=key+" 12:00:00-17:59:59" + ","  + "0"+","+ "WWW Download" + "," + "0"+",";
//                }
//                if(map41.get(key+" 18:00:00-23:59:59")!=null){
//                    value+=map41.get(key+" 18:00:00-23:59:59")+",";
//                }else{
//                    value+=key+" 18:00:00-23:59:59" + ","  + "1"+","+ "WWW Upload" + "," + "0"+",";
//                }
//                if(map421.get(key+" 18:00:00-23:59:59")!=null){
//                    value+=map421.get(key+" 18:00:00-23:59:59")+",";
//                }else{
//                    value+=key+" 18:00:00-23:59:59" + ","  + "0"+","+ "WWW Upload" + "," + "0"+",";
//                }
//                if(map4.get(key+" 18:00:00-23:59:59")!=null){
//                    value+=map4.get(key+" 18:00:00-23:59:59");
//                }else{
//                    value+=key+" 18:00:00-23:59:59" + "," + "1"+","+ "WWW Visit" + "," + "0"+",";
//                }
//                if(map411.get(key+" 18:00:00-23:59:59")!=null){
//                    value+=map411.get(key+" 18:00:00-23:59:59");
//                }else{
//                    value+=key+" 18:00:00-23:59:59" + "," + "0"+"," + "WWW Visit" + "," + "0"+",";
//                }
//                if(map42.get(key+" 18:00:00-23:59:59")!=null){
//                    value+=map42.get(key+" 18:00:00-23:59:59")+",";
//                }else{
//                    value+=key+" 18:00:00-23:59:59" + "," + "1"+","+ "WWW Download" + "," + "0"+",";
//                }
//                if(map431.get(key+" 18:00:00-23:59:59")!=null){
//                    value+=map431.get(key+" 18:00:00-23:59:59")+",";
//                }else{
//                    value+=key+" 18:00:00-23:59:59" + "," + "0"+"," + "WWW Download" + "," + "0"+",";
//                }
//                String str = "";
//                for (int i = 0; i <strNew.length-4; i++) {
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
//
//       walk("http1");
//       //walk("G:\\开题\\data_new\\http1");
//    }
//
//    public static void write(String path, String str1, String str2) {
//
//        try {
//            File csv = new File("http2/" + path ); // CSV数据文件
//            //File csv = new File("G:\\开题\\data_new\\http2\\" + path ); // CSV数据文件
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
