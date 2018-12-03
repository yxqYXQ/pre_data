import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * @author Yangxiaoqing
 * @ClassName: HttpNumber
 * @Desciption: 统计4个不同时间段每个用户的访问Http的情况
 * @date 2018/11/15 14:42
 * @Version 1.0
 */
public class HttpNumber {
    //find csv from folder

    private static HashMap<String,String> usermap = new HashMap<String,String>();
    private static ArrayList<String> list = new ArrayList<String>();
    private static String fileNameNew ="";
    //查找用户的公共文件
    @SuppressWarnings("unchecked")
    public static void search(String path){
        try{
            BufferedReader reader = new BufferedReader(new FileReader(path+"User_Http.csv"));
            BufferedReader reader1 = new BufferedReader(new FileReader(path+"User_Http_All.csv"));
            String line =null;
            String line1 = null;
            while ((line = reader.readLine()) != null) {
                String item[] = line.split(",");
                usermap.put(item[0],item[1]);//用户和文件对
            }
            while ((line1 = reader1.readLine()) != null) {
                String item[] = line1.split(",");//一行数组v
                list.add(item[0]);//公共pc
            }
        }catch (Exception e){
            System.out.println("错误1"+e.getStackTrace());
        }
    }
    public static void main(String[] args) {

        String path1= "http1/";
        search("");
        walk("http/" ,path1);
//        String path1= "G:\\开题\\data_new\\http1\\";
//        search("G:\\开题\\data_new\\");
//        walk("G:\\开题\\data_new\\http\\" ,path1);
    }
    public static void walk( String path ,String path1) {

        File root = new File( path );
        File[] list = root.listFiles();
        if (list == null) return;
        for ( File f : list ) {
            String filePath = f.getAbsolutePath();
            read(filePath,path1);
        }
    }
    public static void read(String path,String path1) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(path));//换成你的文件名
            String headerMessage = readFirst(path);
            File tempFile = new File(path.trim());
            fileNameNew = tempFile.getName();
            String line = null;
            HashMap<HashMap<String, String>, Integer> map1 = new HashMap<>();
            int timecol;
            int activitycol;
            while ((line = reader.readLine()) != null) {
                String item[] = line.split(",");//一行数组
                String userName = item[1];//用户id
                String https = item[item.length-2];//url地址
                String httpNews[] = https.split("//");
                String http1 = httpNews[0];
                String http2[] = httpNews[1].split("/");
                String url= http1+"//"+http2[0];
                activitycol = item.length-1;
                timecol = item.length-4;
                String activity = item[activitycol];//这里
                String time = item[timecol];//这里
                String time1[] = time.split(" ");
                int flag = timePro(time);
                String times[] = {" 00:00:00-05:59:59", " 06:00:00-11:59:59", " 12:00:00-17:59:59", " 18:00:00-23:59:59"};
                HashMap<String, String> map = new HashMap<>();
                String isCommon = "0";
                if(usermap.get(userName).equals(url)||list.contains(url)){
                    isCommon = "1";
                }
                String key =time1[0] + times[flag-1] + "," + isCommon;
                map.put(key, activity);
                if (map1.get(map) != null) {
                    int count = map1.get(map);
                    map1.put(map, count + 1);
                } else {
                    map1.put(map, 1);
                }
            }
            write(headerMessage, path1 + fileNameNew, map1);
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    //写入表头
    public static void writeHeader(String path){
        try {
            File csv = new File(path); // CSV数据文件
            BufferedWriter bw = new BufferedWriter(new FileWriter(csv, true));
            String header = "role,projects,business_unit,functional_unit,department,team,supervisor,activity,date,isCommon,count";
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
            for (int i = 3; i <10 ; i++) {
                userMesage += strNew[i]+",";
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return userMesage;
    }
    public static void write(String headerMeaage,String path, HashMap<HashMap<String,String>,Integer> map1) {
        try {
            writeHeader(path);
            File csv = new File(path); // CSV数据文件
            BufferedWriter bw = new BufferedWriter(new FileWriter(csv, true));
            for(HashMap<String,String> key:map1.keySet())
            {
                HashMap<String,String> map = key;
                int count = map1.get(map);
                for(String key1:map.keySet())
                {
                    String activity = map.get(key1);
                    String message = headerMeaage+activity+","+key1+","+count;
                    bw.write(message);
                    bw.newLine();
                }
            }
            bw.close();
        } catch (Exception e) {
            // File对象的创建过程中的异常捕获
            e.printStackTrace();
        }
    }

//    private static HashMap<String,String> usermap = new HashMap<String,String>();
//    private static ArrayList<String> list = new ArrayList<String>();
//    @SuppressWarnings("unchecked")
//    public static void search(){
//        try{
////
//            String path = "G:\\开题\\data_new\\User_Http.csv";//用户一对一的pc
//            String path1 ="G:\\开题\\data_new\\User_Http_All.csv";//用户公共的pc
////            String path = "User_Http.csv";//用户一对一的pc
////            String path1 ="User_Http_All.csv";//用户公共的pc
//            BufferedReader reader = new BufferedReader(new FileReader(path));
//            BufferedReader reader1 = new BufferedReader(new FileReader(path1));
//
//            String line =null;
//            String line1 = null;
//            while ((line = reader.readLine()) != null) {
//                String item[] = line.split(",");
//                usermap.put(item[0],item[1]);//用户和pc对
//            }
//            while ((line1 = reader1.readLine()) != null) {
//                String item[] = line1.split(",");//一行数组v
//                list.add(item[0]);//公共pc
//            }
//        }catch (Exception e){
//            System.out.println("错误1"+e.getStackTrace());
//        }
//    }
//    public static void main(String[] args) {
//        HttpNumber fw = new HttpNumber();
//
//        search();
//        //fw.walk("http" );
//      fw.walk("G:\\开题\\data_new\\http" );
//        System.out.println("ok");
//    }
//    //find csv from folder
//    public void walk( String path ) {
//
//        File root = new File( path );
//        File[] list = root.listFiles();
//
//        if (list == null) return;
//
//        for ( File f : list ) {
//            // System.out.println( "File:" + f.getAbsoluteFile() );
//            String filePath = f.getAbsolutePath();
//            read(filePath);
//        }
//    }
//
//
//    public static void read(String path) {
//        try {
//            //BufferedReader =new BufferedReader(new InputStreamReader(new FileInputStream(fileName),"UTF-8"));
//            BufferedReader reader = new BufferedReader(new FileReader(path));//换成你的文件名
//            String line = null;
//
//            HashMap<HashMap<String,String>,Integer> map1 = new HashMap<>();//时间在0-6//时间段、活动、次数
//            HashMap<HashMap<String,String>,Integer> map2 = new HashMap<>();//6-12
//            HashMap<HashMap<String,String>,Integer> map3 = new HashMap<>();//12-18
//            HashMap<HashMap<String,String>,Integer> map4 = new HashMap<>();//18-24
//            //设备信息
//            int timecol;
//            int activitycol;
//            while ((line = reader.readLine()) != null) {
//
//                String item[] = line.split(",");//一行数组
//                String userName = item[1];//用户id
//                String https = item[item.length-2];//url地址
//                String httpNews[] = https.split("//");
//                String http1 = httpNews[0];
//                String http2[] = httpNews[1].split("/");
//                String url= http1+"//"+http2[0];
//                activitycol = item.length-1;
//                timecol = item.length-4;
//                String activity = item[activitycol];//这里
//                String time = item[timecol];//这里
//                String time1[] = time.split(" ");
//                int flag = timePro(time);
//                //公共的url为1，否则为0
//                String isCommon = "0";
//                if(usermap.get(userName).equals(url)||list.contains(url)){
//                    isCommon = "1";
//                }
//                if(flag==1){
//                    HashMap<String,String> map = new HashMap<>();
//
//                    map.put(time1[0]+" 00:00:00-05:59:59",isCommon+","+activity);
//                    if(map1.get(map)!=null){
//                        int count = map1.get(map);
//                        map1.put(map,count+1);
//                    }else{
//                        map1.put(map,1);
//                    }
//                }
//                if(flag==2){
//                    HashMap<String,String> map = new HashMap<>();
//                    map.put(time1[0]+" 06:00:00-11:59:59",isCommon+","+activity);
//                    if(map2.get(map)!=null){
//                        int count = map2.get(map);
//                        map2.put(map,count+1);
//                    }else{
//                        map2.put(map,1);
//                    }
//                }
//                if(flag==3){
//                    HashMap<String,String> map = new HashMap<>();
//                    map.put(time1[0]+" 12:00:00-17:59:59",isCommon+","+activity);
//                    if(map3.get(map)!=null){
//                        int count = map3.get(map);
//                        map3.put(map,count+1);
//                    }else{
//                        map3.put(map,1);
//                    }
//                }
//                if(flag==4){
//                    HashMap<String,String> map = new HashMap<>();
//                    map.put(time1[0]+" 18:00:00-23:59:59",isCommon+","+activity);
//                    if(map4.get(map)!=null){
//                        int count = map4.get(map);
//                        map4.put(map,count+1);
//                    }else{
//                        map4.put(map,1);
//                    }
//                }
//            }
//            BufferedReader reader1 = new BufferedReader(new FileReader(path));//换成你的文件名
//            String line1 = null;
//            String strNew[] = null;
//            while ((line1 = reader1.readLine()) != null) {
//                strNew = line1.split(",");//一行数组
//                break;
//            }
//            File tempFile =new File( path.trim());
//            String fileName = tempFile.getName();
//            String header ="role,projects,business_unit,functional_unit,department,team,supervisor,date,isCommon,activity,count";
//            write(fileName,header,"");
//            for(HashMap<String,String> key:map1.keySet())
//            {
//                HashMap<String,String> map = key;
//                int count = map1.get(map);
//                for(String key1:map.keySet())
//                {
//                    String str ="";
//                    String activity = map.get(key1);
//                    for (int i = 3; i <strNew.length ; i++) {
//                        if(i== strNew.length-4){
//                            strNew[i] = key1;
//                        }
//                        if(i==strNew.length-1||i==strNew.length-2||i==strNew.length-3||i==strNew.length-5){
//                            continue;
//                        }
//                        str += strNew[i]+",";
//                    }
//
//                    str +=activity;
//                    write(fileName,str,String.valueOf(count));
//                }
//            }
//            for(HashMap<String,String> key:map2.keySet())
//            {
//                HashMap<String,String> map = key;
//                int count = map2.get(map);
//                for(String key1:map.keySet())
//                {
//                    String str ="";
//                    String activity = map.get(key1);
//                    for (int i = 3; i <strNew.length ; i++) {
//                        if(i==strNew.length-4){
//                            strNew[i] = key1;
//                        }
//                        if(i==strNew.length-1||i==strNew.length-2||i==strNew.length-3||i==strNew.length-5){
//                            continue;
//                        }
//                        str += strNew[i]+",";
//                    }
//
//                    str +=activity;
//
//                    write(fileName,str,String.valueOf(count));
//                }
//            }
//            for(HashMap<String,String> key:map3.keySet())
//            {
//                HashMap<String,String> map = key;
//                int count = map3.get(map);
//                for(String key1:map.keySet())
//                {
//                    String str ="";
//                    String activity = map.get(key1);
//                    for (int i = 3; i <strNew.length ; i++) {
//                        if(i==strNew.length-4){
//                            strNew[i] = key1;
//                        }
//                        if(i==strNew.length-1||i==strNew.length-2||i==strNew.length-3||i==strNew.length-5){
//                            continue;
//                        }
//                        str += strNew[i]+",";
//                    }
//
//                    str +=activity;
//
//                    write(fileName,str,String.valueOf(count));
//                }
//            }
//            for(HashMap<String,String> key:map4.keySet())
//            {
//                HashMap<String,String> map = key;
//                int count = map4.get(map);
//                for(String key1:map.keySet())
//                {
//                    String str ="";
//                    String activity = map.get(key1);
//                    for (int i = 3; i <strNew.length ; i++) {
//                        if(i==strNew.length-4){
//                            strNew[i] = key1;
//                        }
//                        if(i==strNew.length-1||i==strNew.length-2||i==strNew.length-3||i==strNew.length-5){
//                            continue;
//                        }
//                        str += strNew[i]+",";
//                    }
//
//                    str +=activity;
//                    write(fileName,str,String.valueOf(count));
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static void write(String path, String str1, String str2) {
//        try {
//            //File csv = new File("http1/" + path); // CSV数据文件
//            File csv = new File("G:\\开题\\data_new\\http1\\" + path); // CSV数据文件
//            BufferedWriter bw = new BufferedWriter(new FileWriter(csv, true)); // 附加
//            // 添加新的数据行
//            bw.write(str1 + "," + str2);
//            bw.newLine();
//            bw.close();
//        } catch (Exception e) {
//            // File对象的创建过程中的异常捕获
//            e.printStackTrace();
//        }
//    }


    public static int timePro(String time){
        int flag =0;
        SimpleDateFormat sim=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String time1[] = time.split(" ");
        String time2[] = time1[0].split("/");
        String newTime = time2[2]+"-"+time2[1]+"-"+time2[0];
        String date = newTime+" "+time1[1];
        if(isInDate(date,"00:00:00","05:59:59")){
            flag =1;
        }
        if(isInDate(date,"06:00:00","11:59:59")){
            flag =2;
        }
        if(isInDate(date,"12:00:00","17:59:59")){
            flag =3;
        }
        if(isInDate(date,"18:00:00","23:59:59")){
            flag =4;
        }
        return flag;
    }


    public static boolean isInDate(String strDate, String strDateBegin,
                                   String strDateEnd) {
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        String strDate = sdf.format(date);
        // 截取当前时间时分秒
        int strDateH = Integer.parseInt(strDate.substring(11, 13));
        int strDateM = Integer.parseInt(strDate.substring(14, 16));
        int strDateS = Integer.parseInt(strDate.substring(17, 19));
        // 截取开始时间时分秒
        int strDateBeginH = Integer.parseInt(strDateBegin.substring(0, 2));
        int strDateBeginM = Integer.parseInt(strDateBegin.substring(3, 5));
        int strDateBeginS = Integer.parseInt(strDateBegin.substring(6, 8));
        // 截取结束时间时分秒
        int strDateEndH = Integer.parseInt(strDateEnd.substring(0, 2));
        int strDateEndM = Integer.parseInt(strDateEnd.substring(3, 5));
        int strDateEndS = Integer.parseInt(strDateEnd.substring(6, 8));
        if ((strDateH >= strDateBeginH && strDateH <= strDateEndH)) {
            // 当前时间小时数在开始时间和结束时间小时数之间
            if (strDateH > strDateBeginH && strDateH < strDateEndH) {
                return true;
                // 当前时间小时数等于开始时间小时数，分钟数在开始和结束之间
            } else if (strDateH == strDateBeginH && strDateM >= strDateBeginM
                    && strDateM <= strDateEndM) {
                return true;
                // 当前时间小时数等于开始时间小时数，分钟数等于开始时间分钟数，秒数在开始和结束之间
            } else if (strDateH == strDateBeginH && strDateM == strDateBeginM
                    && strDateS >= strDateBeginS && strDateS <= strDateEndS) {
                return true;
            }
            // 当前时间小时数大等于开始时间小时数，等于结束时间小时数，分钟数小等于结束时间分钟数
            else if (strDateH >= strDateBeginH && strDateH == strDateEndH
                    && strDateM <= strDateEndM) {
                return true;
                // 当前时间小时数大等于开始时间小时数，等于结束时间小时数，分钟数等于结束时间分钟数，秒数小等于结束时间秒数
            } else if (strDateH >= strDateBeginH && strDateH == strDateEndH
                    && strDateM == strDateEndM && strDateS <= strDateEndS) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
}
