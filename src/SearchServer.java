import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;

class SearchHandler implements URLHandler
{
    private ArrayList<String> strings;

    public SearchHandler()
    {
        strings = new ArrayList<String>();
    }

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
            return "nothing here";

        if (path[0].equals("add"))
        {
            if (params.length == 0)
                return "couldn't parse params: no params";

            if (params.length > 2)
                return "couldn't parse params: too many params";
            
            if (!params[0].equals("s"))
                return "unrecognized param: " + params[0];

            strings.add(params[1]);

            return "added string: " + params[1];
        }

        if (path[0].equals("search"))
        {
            if (params.length == 0)
                return "couldn't parse params: no params";

            if (params.length > 2)
                return "couldn't parse params: too many params";
            
            if (!params[0].equals("s"))
                return "unrecognized param: " + params[0];

            String display = "";
            
            for (String s : strings)
            {
                if (s.contains(params[1]))
                    display += s + "\n";
            }

            return display;
        }
        
        return "nothing here";
    }
}

class SearchServer
{
    public static void main(String[] args) throws IOException
    {
        if(args.length == 0)
        {
            System.out.println("Missing port number! Try any number between 1024 to 49151");
            return;
        }

        int port = Integer.parseInt(args[0]);

        Server.start(port, new SearchHandler());
    }
}

