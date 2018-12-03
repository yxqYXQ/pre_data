
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;


public class CountCsv {
    public static void main(String[] args) throws IOException {
        CountCsv app = new CountCsv();
        app.compareBufferAndLineNumber();
    }

    public void compareBufferAndLineNumber() throws IOException {
        String fileName = "G:\\开题\\用户行为\\数据集\\r6.2\\email.csv";

       // String fileName = "http.csv";

        long time = System.currentTimeMillis();

        System.out.println("BufferedInputStream" + this.count(fileName));
        System.out.println(System.currentTimeMillis() - time);


    }
    public int count(String filename) throws IOException {
        InputStream is = new BufferedInputStream(new FileInputStream(filename));
        byte[] c = new byte[1024];
        int count = 0;
        int readChars = 0;
        while ((readChars = is.read(c)) != -1) {
            for (int i = 0; i < readChars; ++i) {
                if (c[i] == '\n')
                    ++count;
            }
        }
        is.close();
        return count;
    }
}