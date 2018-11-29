import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * @author Yangxiaoqing
 * @ClassName: FileNumber
 * @Desciption: 统计不同时间段file操作情况，放在file1文件下
 * @date 2018/11/15 14:41
 * @Version 1.0
 */
public class FileNumber {
    //find csv from folder

    private static HashMap<String,String> usermap = new HashMap<String,String>();
    private static ArrayList<String> list = new ArrayList<String>();
    private static String fileNameNew ="";
    //查找用户的公共文件
    @SuppressWarnings("unchecked")
    public static void search(String path){
        try{
            BufferedReader reader = new BufferedReader(new FileReader(path+"User_file.csv"));
            BufferedReader reader1 = new BufferedReader(new FileReader(path+"User_file_All.csv"));
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
        FileNumber fw = new FileNumber();
//        String path1= "file1/";
//        search("");
//        fw.walk("file" ,path1);
        String path1= "G:\\开题\\data_new\\file1\\";
        search("G:\\开题\\data_new\\");
        fw.walk("G:\\开题\\data_new\\file\\" ,path1);
    }
    public void walk( String path ,String path1) {

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
                activitycol = item.length - 3;
                timecol = item.length - 7;
                String activity = item[activitycol];//这里
                String time = item[timecol];//这里
                String isDecoy = item[item.length - 4];
                String time1[] = time.split(" ");
                String fileName = item[item.length - 5];
                String pc = item[item.length - 6];
                String userName = item[1];//用户id
                String isCommon = "0";//默认是非公共的，为1
                String to_remove=item[item.length - 2];//是移动到u盘
                String from_remove =item[item.length - 1];//是否来自到u盘
                String pc_fileName = pc + "_" + fileName;
                if (usermap.get(userName).equals(pc_fileName) || list.contains(pc_fileName)) {
                    isCommon = "1";
                }
                int flag = timePro(time);
                String times[] = {" 00:00:00-05:59:59", " 06:00:00-11:59:59", " 12:00:00-17:59:59", " 18:00:00-23:59:59"};
                HashMap<String, String> map = new HashMap<>();
                String key =to_remove+","+from_remove+","+time1[0] + times[flag-1] + "," + isDecoy + "," + isCommon;
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
            String header = "role,projects,business_unit,functional_unit,department,team,supervisor,activity,to_remove,from_remove,date,isdecoy,isCommon,count";
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