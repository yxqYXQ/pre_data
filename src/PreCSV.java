/**
 * @author Yangxiaoqing
 * @ClassName: PreCSV
 * @Desciption: TODO
 * @date 2018/11/14 10:52
 * @Version 1.0
 */
import java.io.*;
import java.util.Arrays;
import java.util.HashMap;


public class PreCSV {
    public static  void walk( String path ) {

        File root = new File( path );
        File[] list = root.listFiles();

        if (list == null) return;

//        for ( File f : list ) {
//            // System.out.println( "File:" + f.getAbsoluteFile() );
//            String filePath = f.getAbsolutePath();
//          //  read(filePath);
//
//
//        }
        read("G:\\开题\\用户行为\\数据集\\r6.2\\device.csv");
    }
    public static void read(String path){
        try {

            BufferedReader reader1 = new BufferedReader(new FileReader(path));//换成你的文件名

            reader1.readLine();//第一行信息，为标题信息，不用，如果需要，注释掉

            String line1 = null;
             int i=0;
            while ((line1 = reader1.readLine()) != null) {
                String item[] = line1.split(",");//一行数组

                if(item[2].equals("AAC0610")){

                   i++;
                }

            }
            System.out.println(i);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        walk("G:\\开题\\data_new\\device");
    }

    public static void write(String path, String str1, String str2) {

        try {
            File csv = new File("G:\\开题\\用户行为\\数据集\\" + path + ".csv"); // CSV数据文件

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
