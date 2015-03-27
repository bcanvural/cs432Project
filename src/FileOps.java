import java.io.*;

public class FileOps {

    static int fileCounter = 0;
        public static File[] findTxtFiles(String dirName){
            File dir = new File(dirName);
            return dir.listFiles(new FilenameFilter() {
                public boolean accept(File dir, String filename)
                { return filename.endsWith(".txt"); }
            } );
        }

    /**
     * Return the whole content of a .txt file
     * @param dirName
     * @return
     */

    public static String readFile(String dirName){
        BufferedReader br = null;
        String str = null;
        try {
            String sCurrentLine;
            br = new BufferedReader(new FileReader(dirName));
            while ((sCurrentLine = br.readLine()) != null) {
                str+=sCurrentLine;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null)br.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            return str;
        }
    }

    public static void writeFile(String filename,String content) throws IOException{
         Writer writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(filename), "utf-8"));
            writer.write(content);
    }
}
