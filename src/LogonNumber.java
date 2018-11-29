import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

/**
 * @author Yangxiaoqing
 * @ClassName: LogonNumber
 * @Desciption:统计用户登陆/注销在4个不同时间段的次数
 * @date 2018/11/15 14:20
 * @Version 1.0
 */
public class LogonNumber {
    //find csv from folder
    public void walk( String path ) {

        File root = new File( path );
        File[] list = root.listFiles();

        if (list == null) return;

        for ( File f : list ) {
            // System.out.println( "File:" + f.getAbsoluteFile() );
            String filePath = f.getAbsolutePath();
            read(filePath);
        }
       // read("G:\\开题\\data_new\\logon\\logon_AAC06102009_12.csv");

    }
    public static void read(String path) {
        try {
            //BufferedReader =new BufferedReader(new InputStreamReader(new FileInputStream(fileName),"UTF-8"));
            BufferedReader reader = new BufferedReader(new FileReader(path));//换成你的文件名
            String line = null;

            HashMap<HashMap<String,String>,Integer> map1 = new HashMap<>();//时间在0-6
            HashMap<HashMap<String,String>,Integer> map2 = new HashMap<>();//6-12
            HashMap<HashMap<String,String>,Integer> map3 = new HashMap<>();//12-18
            HashMap<HashMap<String,String>,Integer> map4 = new HashMap<>();//18-24
            //设备信息
            int timecol;
            int activitycol ;

            while ((line = reader.readLine()) != null) {
                String item[] = line.split(",");//一行数组
                activitycol = item.length-1;
                timecol = item.length-3;
                String activity = item[activitycol];//这里
                String time = item[timecol];//这里
                String time1[] = time.split(" ");
                int flag = timePro(time);
                if(flag==1){
                    HashMap<String,String> map = new HashMap<>();
                    map.put(time1[0]+" 00:00:00-05:59:59",activity);
                    if(map1.get(map)!=null){
                        int count = map1.get(map);
                        map1.put(map,count+1);
                    }else{
                        map1.put(map,1);
                    }
                }
                if(flag==2){
                    HashMap<String,String> map = new HashMap<>();
                    map.put(time1[0]+" 06:00:00-11:59:59",activity);
                    if(map2.get(map)!=null){
                        int count = map2.get(map);
                        map2.put(map,count+1);
                    }else{
                        map2.put(map,1);
                    }
                }
                if(flag==3){
                    HashMap<String,String> map = new HashMap<>();
                    map.put(time1[0]+" 12:00:00-17:59:59",activity);
                    if(map3.get(map)!=null){
                        int count = map3.get(map);
                        map3.put(map,count+1);
                    }else{
                        map3.put(map,1);
                    }
                }
                if(flag==4){
                    HashMap<String,String> map = new HashMap<>();
                    map.put(time1[0]+" 18:00:00-23:59:59",activity);
                    if(map4.get(map)!=null){
                        int count = map4.get(map);
                        map4.put(map,count+1);
                    }else{
                        map4.put(map,1);
                    }
                }
            }
            BufferedReader reader1 = new BufferedReader(new FileReader(path));//换成你的文件名
            String line1 = null;
            String strNew[] = null;

            while ((line1 = reader1.readLine()) != null) {
                strNew = line1.split(",");//一行数组
                break;
            }
            File tempFile =new File( path.trim());
            String fileName = tempFile.getName();
            String header ="role,projects,business_unit,functional_unit,department,team,supervisor,date,activity,count";
            write(fileName,header,"");
            for(HashMap<String,String> key:map1.keySet())
            {
                HashMap<String,String> map = key;
                int count = map1.get(map);
                for(String key1:map.keySet())
                {
                    String str ="";
                    String activity = map.get(key1);
                    for (int i = 3; i <strNew.length ; i++) {
                        if(i==strNew.length-3){
                            strNew[i] = key1;
                        }
                        if(i==strNew.length-1||i==strNew.length-2||i==strNew.length-4){
                            continue;
                        }
                        str += strNew[i]+",";
                    }

                    //String str1 = str.substring(0,str.length()-1);
                    str += activity;
                    write(fileName,str,String.valueOf(count));
                }
            }
            for(HashMap<String,String> key:map2.keySet())
            {
                HashMap<String,String> map = key;
                int count = map2.get(map);
                for(String key1:map.keySet())
                {
                    String str ="";
                    String activity = map.get(key1);
                    for (int i = 3; i <strNew.length-1 ; i++) {
                        if(i==strNew.length-3){
                            strNew[i] = key1;
                        }
                        if(i==strNew.length-1||i==strNew.length-2||i==strNew.length-4){
                            continue;
                        }
                        str += strNew[i]+",";
                    }
                    //String str1 = str.substring(0,str.length()-1);
                    str += activity;
                    write(fileName,str,String.valueOf(count));
                }
            }
            for(HashMap<String,String> key:map3.keySet())
            {
                HashMap<String,String> map = key;
                int count = map3.get(map);
                for(String key1:map.keySet())
                {
                    String str ="";
                    String activity = map.get(key1);
                    for (int i = 3; i <strNew.length-1 ; i++) {
                        if(i==strNew.length-3){
                            strNew[i] = key1;
                        }
                        if(i==strNew.length-1||i==strNew.length-2||i==strNew.length-4){
                            continue;
                        }
                        str += strNew[i]+",";
                    }
                    //String str1 = str.substring(0,str.length()-1);
                    str += activity;
                    write(fileName,str,String.valueOf(count));
                }
            }
            for(HashMap<String,String> key:map4.keySet())
            {
                HashMap<String,String> map = key;
                int count = map4.get(map);
                for(String key1:map.keySet())
                {
                    String str ="";
                    String activity = map.get(key1);
                    for (int i = 3; i <strNew.length-1 ; i++) {
                        if(i==strNew.length-3){
                            strNew[i] = key1;
                        }
                        if(i==strNew.length-1||i==strNew.length-2||i==strNew.length-4){
                            continue;
                        }
                        str += strNew[i]+",";
                    }
                    //String str1 = str.substring(0,str.length()-1);
                    str += activity;
                    write(fileName,str,String.valueOf(count));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void write(String path, String str1, String str2) {
        try {
            File csv = new File("logon1/" + path); // CSV数据文件
           // File csv = new File("G:\\开题\\data_new\\logon1/" + path); // CSV数据文件
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

    public static void main(String[] args) {
        LogonNumber fw = new LogonNumber();
        //read("G:\\开题\\data_new\\logon");
        fw.walk("logon" );
        //fw.walk("G:\\开题\\data_new\\logon" );
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
