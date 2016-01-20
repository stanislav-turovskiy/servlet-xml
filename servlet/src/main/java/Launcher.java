import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;
import ru.project.controller.Servlet;

public class Launcher {

    public static void main(String[] args) throws Exception
    {
        Server server = new Server(8080);
        WebAppContext context = new WebAppContext("servlet/src/main/webapp", "/");
        context.addServlet(Servlet.class, "/servlet/*");
        server.setHandler(context);
        server.start();

        /*org.h2.tools.Server tcpServer = org.h2.tools.Server.createTcpServer("-tcpAllowOthers","-tcpPort","9092").start();
        System.out.println("tcpServer started and connection is open");
        System.out.println("URL: jdbc:h2:" + tcpServer.getURL() + "/servlet");
        */
        org.h2.tools.Server webServer = org.h2.tools.Server.createWebServer("-webAllowOthers","-webPort","8082").start();
        System.out.println("H2 WebServer started and connection is open");
        System.out.println("URL:" + webServer.getURL());
    }
}
