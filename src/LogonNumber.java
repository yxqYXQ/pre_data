import java.io.*;
import java.text.SimpleDateFormat;

import java.util.HashMap;

/**
 * @author Yangxiaoqing
 * @ClassName: LogonNumber
 * @Desciption:统计用户登陆/注销在4个不同时间段的次数
 * @date 2018/11/15 14:20
 * @Version 1.0
 */
public class LogonNumber {
    private static String fileNameNew ="";
    public static void main(String[] args) {
        String path1= "logon1/";
        walk("logon/" ,path1);
//        String path1= "G:\\开题\\data_new\\logon1\\";
//        walk("G:\\开题\\data_new\\logon\\" ,path1);
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
                activitycol = item.length-1;
                timecol = item.length-3;
                String activity = item[activitycol];//这里
                String time = item[timecol];//这里
                String time1[] = time.split(" ");
                int flag = timePro(time);
                String times[] = {" 00:00:00-05:59:59", " 06:00:00-11:59:59", " 12:00:00-17:59:59", " 18:00:00-23:59:59"};
                HashMap<String, String> map = new HashMap<>();
                String key =time1[0] + times[flag-1];
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
            String header = "role,projects,business_unit,functional_unit,department,team,supervisor,activity,date,count";
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
