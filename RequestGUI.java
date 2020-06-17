import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RequestGUI implements Serializable  {
    private static final long serialVersionUID = -7918451096468596888L;
    private URL url;
    private HttpURLConnection urlCon;
    private String method;
    private String body[];
    private String header[];
    private String HeaderRespond[];
    private int code;
    private String respondMessage;
    private String output;
    private byte [] Byte;


    /**
     * We use this method to creat a new HttpUriConnection
     * by these information and set all these by right method
     * @param url : link or address
     * @param method : method of HttpUrlConnection
     * @param body : string of all the form data
     * @param header: string of all the header
     */

    public RequestGUI(URL url, String method, String[] body, String[] header){
//        super();
        this.url = url;
        this.method = method;
        this.body = body;
        this.header = header;
    }

    public void setUrl(URL url) { this.url = url; }

    public URL getUrl() { return url; }

    public void setMethod(String method) { this.method = method; }

    public String getMethod() { return method; }

    public void setBody(String[] body) { this.body = body; }

    public String[] getBody() { return body; }

    public void setHeader(String[] header) { this.header = header; }

    public String [] getHeader() { return header; }

    public void setHeaderRespond(String[] headerRespond) { HeaderRespond = headerRespond; }

    public String[] getHeaderRespond() {
        return HeaderRespond;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setRespondMessage(String respondMessage) {
        this.respondMessage = respondMessage;
    }

    public String getOutput() {
        return output;
    }

    public HttpURLConnection getUrlCon() {
        return urlCon;
    }

    public String getRespondMessage() {
        return respondMessage;
    }

    public byte[] getByte() {
        return Byte;
    }


    /**
     * In this method we create a new HttpUrlConnection and also split the body
     * and header and set them in the right place (also method and url)
     * and shoe the respond of request
     * @param saveRespond : boolean that tell us we want to save a respond or not
     * @param nameOutput :if we want to save a respond whats the name
     * @param headerRespond  boolean that tell us we want to show a headerFields or not
     * @param followRedirect  boolean that tell us if re url is redirect chose the right url
     * @throws IOException
     */

    public void createRequest(boolean saveRespond, String nameOutput, boolean headerRespond, boolean followRedirect) throws IOException {

        urlCon = (HttpURLConnection) url.openConnection();
        urlCon.setRequestMethod(method);
        urlCon.setInstanceFollowRedirects(true);
        urlCon.setDoOutput(true);
        String boundary = System.currentTimeMillis() + "";
        if (!method.equals("GET"))
            urlCon.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);


        if (header != null) {
            for (String s : header) {
                if (s!=null) {
                    if (s.contains(":")) {
                        String nameValue[] = s.split(":");
                        urlCon.setRequestProperty(nameValue[0], nameValue[1]);
                    }
                }
            }
        }


        HashMap<String, String> Body = new HashMap<>();
        if (body != null) {
            for (String s : body) {
                if (s!=null) {
                    if (s.contains("=")) {
                        String test[] = s.split("=");
                        Body.put(test[0], test[1]);
                    }
                }
            }
        }

        if (!followRedirect){
            urlCon.setInstanceFollowRedirects(false);
        }


        if (!method.equals("GET")) {
            BufferedOutputStream bos = new BufferedOutputStream(urlCon.getOutputStream());
            bufferOutFormData(Body, boundary, bos);
        }

        try {
            BufferedInputStream bis = new BufferedInputStream(urlCon.getInputStream());
            Byte = bis.readAllBytes();
            output = new String(Byte);
        }
        catch (IOException e){
            System.out.println("NO body returned a respond");
        }
        if (saveRespond){
            String contentType = urlCon.getContentType().toString();
            String type[] = contentType.split(";");
            String type2[] = type[0].split("/");

            if (nameOutput.equals("")) {
                Date date = new Date();
                nameOutput = "output_[" +date.toString() +"]." +type2[1];
            }
            else {
                nameOutput += "."+ type2[1];
            }
            RequestGUI requestGui = new RequestGUI(url, method, body, header);
            Save save = new Save("", requestGui);
            save.SaveRespond(output, nameOutput);
            save.execute();
        }


        code = urlCon.getResponseCode();
        respondMessage = urlCon.getResponseMessage();
        System.out.println("***********************");
        System.out.println("Code: " +code +" " +urlCon.getResponseMessage());
        System.out.println("Method: " +urlCon.getRequestMethod());
        System.out.println("direct: " +urlCon.getInstanceFollowRedirects());
        System.out.println("***********************");

        Map<String, List<String>> map = urlCon.getHeaderFields();
        HeaderRespond = new String[map.keySet().size()];
        if (headerRespond){
            System.out.println("header:");
            int counter = 0;
            for (String key : map.keySet()) {
                HeaderRespond[counter++] = key +"___" +map.get(key);
            }
            for (String s :HeaderRespond)
                System.out.println(s);
        }
        System.out.println("***********************");
    }


    public static void bufferOutFormData(HashMap<String, String> body, String boundary, BufferedOutputStream bufferedOutputStream) throws IOException {
        for (String key : body.keySet()) {
            bufferedOutputStream.write(("--" + boundary + "\r\n").getBytes());
            bufferedOutputStream.write(("Content-Disposition: form-data; name=\"" + key + "\"\r\n\r\n").getBytes());
            bufferedOutputStream.write((body.get(key) + "\r\n").getBytes());
        }
        bufferedOutputStream.write(("--" + boundary + "--\r\n").getBytes());
        bufferedOutputStream.flush();
        bufferedOutputStream.close();
    }

}
