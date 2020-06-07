import javax.swing.*;
import java.io.*;
import java.util.Date;

/**
 * We use this class to save the request or respond into the file
 */
public class Save  extends SwingWorker{

    private String name;
    private RequestGUI request;

    public Save(String name, RequestGUI request){
        this.request = request;
        this.name = name;
    }

//    public void SaveRequest() {
//        String address = "request_"+name +".txt";
//        File save = new File(address);
//
//        if (save.exists()) {
//            save.delete();
//        }
//
//        try {
//            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(save));
//            out.writeObject(request);
//            out.close();
//
//        } catch (IOException e){
//            e.printStackTrace();
//        }
//
//    }

    /**
     * We use this method to save the respond in file by specific name
     * @param respond String that we want to save
     * @param address name of file
     */

    public void SaveRespond(String respond, String address) {
        File save = new File(address);

        if (save.exists())
            save.delete();

        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(save));
            out.writeObject(respond);
            out.close();

        } catch (IOException e){
            e.printStackTrace();
        }
    }


    /**
     * We use this method to save the request in file by specific name
     */

    @Override
    protected Object doInBackground() throws Exception {
        String address = "request_"+name +".txt";
        File save = new File(address);

        if (save.exists()) {
            save.delete();
        }

        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(save));
            out.writeObject(request);
            out.close();

        } catch (IOException e){
            e.printStackTrace();
        }

        return null;
    }
}
