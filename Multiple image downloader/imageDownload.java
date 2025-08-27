import java.util.*;
import java.io.*;
import java.net.*;
class JFile extends Thread{
    private static final String outputFile = "Downloads.txt";
    private static final String downloadFolder = "Images";
    private String fileUrl;
    private String saveAs;
    public JFile(String fileUrl,String saveAs){
        this.fileUrl=fileUrl;
        this.saveAs=saveAs;
    }
    public void run(){
        try {
        for (int i = 1; i <= 5; i++) {
            Thread.sleep(500);
            System.out.println(saveAs + "  " + (i * 20) + "% downloaded");
        }
        File folder = new File(downloadFolder);
            if (!folder.exists()) {
                folder.mkdir();
            }
        URL url = new URL(fileUrl);
        URLConnection connection = url.openConnection();
        connection.setRequestProperty("User-Agent", "Mozilla/5.0");
        InputStream in = connection.getInputStream();
        File outputFilePath = new File(folder, saveAs); 
        FileOutputStream out = new FileOutputStream(outputFilePath);


        byte[] buffer = new byte[2048];
        int length;
        while ((length = in.read(buffer)) != -1) {
            out.write(buffer, 0, length);
        }
        in.close();
        out.close();
        synchronized (JFile.class) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile, true))) {
                writer.write("*** Downloaded Details *** \n");
                writer.write(outputFilePath.getAbsolutePath() + " downloaded successfully from: " + fileUrl + "\n");
                writer.write("*** End *** \n\n");
            }
        }

        System.out.println(saveAs + " Downloaded Successfully!");

    } catch (Exception e) {
        System.out.println("Error downloading " + saveAs + ": " + e.getMessage());
    }
    }
    }
public class imageDownload{
    public static void main(String[] args){
        try (PrintWriter writer = new PrintWriter("Downloads.txt")) {
            writer.print("");
        } catch (IOException e) {
            System.out.println("Error clearing log file: " + e.getMessage());
        }
        Scanner sc=new Scanner(System.in);
        System.out.println("Enter the number of files to download");
        int n=sc.nextInt();
        sc.nextLine();
        String[] urls=new String[n];
        String[] saveNames=new String[n];
        Random rand=new Random();

        for (int i = 0; i < n; i++) {
    System.out.print("Enter URL for file " + (i + 1) + ": ");
    urls[i] = sc.nextLine();

    System.out.print("Enter name to save as (e.g., image" + (i + 1) + ".jpg): ");

    saveNames[i] = sc.nextLine();
        }
        for (int i = 0; i < n; i++) {
    JFile j = new JFile(urls[i], saveNames[i]);
    j.start();
        }
        
    }
}