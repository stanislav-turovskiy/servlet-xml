import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.HttpVersion;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;


import java.io.IOException;

public class Client {

    public static final String URL = "http://localhost:8080/servlet/servlet";

    public static void main(String[] args)
    {
        final String s1 = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
                "<request>" +
                " <request-type>new-agt</request-type>" +
                " <login>+79261111111</login>" +
                " <password>sdfhgfhfd</password>" +
                "</request>";

        final String s2 = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
                "<request>" +
                " <request-type>new-agt</request-type>" +
                " <login>+79261111112</login>" +
                " <password>fjk</password>" +
                "</request>";

        final String s3 = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
                "<request>" +
                " <request-type>agt-bal</request-type>" +
                " <login>+79261111113</login>" +
                " <password>fdsfsdfsdfsd</password>" +
                "</request>";

        final String s4 = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
                "<request>" +
                " <request-type>agt-bal</request-type>" +
                " <login>+79261111111</login>" +
                " <password>sdfhgfhfd</password>" +
                "</request>";

        final String s5 = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
                "<request>" +
                " <request-type>agt-bal</request-type>" +
                " <login>+79261111111</login>" +
                " <password>sdfhgfhfd</password>" +
                "</request>";

        final String s6 = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
                "<request>" +
                " <request-type>new-agt</request-type>" +
                " <login>+7</login>" +
                " <password>sdfhgfhfd</password>" +
                "</request>";



        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                post(s1);
            }
        });
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                post(s2);
            }
        });
        Thread t3 = new Thread(new Runnable() {
            @Override
            public void run() {
                post(s3);
            }
        });
        Thread t4 = new Thread(new Runnable() {
            @Override
            public void run() {
                post(s4);
            }
        });
        Thread t5 = new Thread(new Runnable() {
            @Override
            public void run() {
                post(s5);
            }
        });
        Thread t6 = new Thread(new Runnable() {
            @Override
            public void run() {
                post(s6);
            }
        });

        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();
        t6.start();
    }


    private static void post(String xml) {
        HttpClient httpclient = new HttpClient();
        PostMethod postMethod = new PostMethod(URL);
        try {
            postMethod.setRequestEntity(new StringRequestEntity(xml, "application/xml", "UTF8"));

            if (httpclient.executeMethod(postMethod) != HttpStatus.SC_OK) {
                System.out.println("Error: " + postMethod.getStatusLine());
            }

            byte[] response = postMethod.getResponseBody();
            System.out.println(xml);
            System.out.println(new String(response));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
