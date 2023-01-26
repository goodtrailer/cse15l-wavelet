import java.io.IOException;
import java.net.URI;

class StringHandler implements URLHandler
{
    private String string = "";

    @Override
    public String handleRequest(URI url)
    {
        String[] params = url.getQuery() != null ? url.getQuery().split("=") : new String[0];
        String[] rawPath = url.getPath().split("/");
        int pathCount = 0;
        for (String p : rawPath)
        {
            if (!p.isEmpty())
                pathCount++;
        }

        String[] path = new String[pathCount];
        pathCount = 0;
        for (String p : rawPath)
        {
            if (!p.isEmpty())
            {
                path[pathCount] = p;
                pathCount++;
            }
        }

        if (path.length == 0)
            return "nothing here: root";

        if (path[0].equals("add-message"))
        {
            if (params.length == 0)
                return "couldn't parse params: no params";

            if (params.length > 2)
                return "couldn't parse params: too many params";
            
            if (params[0].equals("s"))
            {
                string += params[1] + "\n";
                return string;
            }

            return "unrecognized param: " + params[0];
        }
        
        return "nothing here: unrecognized path";
    }
}

public class StringServer
{
    public static void main(String[] args) throws IOException
    {
        if(args.length == 0)
        {
            System.out.println("Missing port number! Try any number between 1024 to 49151");
            return;
        }

        int port = Integer.parseInt(args[0]);

        Server.start(port, new StringHandler());
    }
}
