//----------------------------------------------------------------------------
// $Id$
// $Source$
//----------------------------------------------------------------------------

package net.sf.gogui.gtp;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Vector;
import net.sf.gogui.go.GoColor;
import net.sf.gogui.go.GoPoint;
import net.sf.gogui.utils.StringUtils;

//----------------------------------------------------------------------------

/** GTP command. */
class Command
{
    public boolean m_hasId;
    
    public int m_id;
    
    public String m_command;

    public boolean isQuit()
    {
        return m_command.trim().toLowerCase().equals("quit");
    }
}

//----------------------------------------------------------------------------

/** Thread reading the command stream.
    Reading is done in a seperate thread to allow the notification
    of GtpServer about an asynchronous interrupt received using
    the special comment line '# interrupt'.
*/
class ReadThread
    extends Thread
{
    public ReadThread(GtpEngine gtpServer, InputStream in, boolean log)
    {
        m_in = new BufferedReader(new InputStreamReader(in));
        m_gtpServer = gtpServer;
        m_log = log;
    }

    public synchronized boolean endOfFile()
    {
        return m_endOfFile;
    }

    public Command getCommand()
    {
        synchronized (this)
        {
            assert(! m_waitCommand);
            m_waitCommand = true;
            notifyAll();
            try
            {
                wait();
            }
            catch (InterruptedException e)
            {
                System.err.println("Interrupted");
            }
            assert(m_endOfFile || ! m_waitCommand);
            Command result = m_command;
            m_command = null;
            return result;
        }
    }

    public void run()
    {
        try
        {            
            while (true)
            {
                String line = m_in.readLine();
                if (line == null)
                {
                    synchronized (this)
                    {
                        m_endOfFile = true;
                    }
                }
                else
                {
                    if (m_log)
                        m_gtpServer.log(line);
                    line = line.trim();
                    if (line.equals("# interrupt"))
                    {
                        m_gtpServer.interruptCommand();
                    }
                    if (line.equals("") || line.charAt(0) == '#')
                        continue;
                }
                synchronized (this)
                {
                    while (! m_waitCommand)
                    {
                        wait();
                    }
                    if (line == null)
                        m_command = null;
                    else
                        m_command = parseLine(line);
                    notifyAll();
                    m_waitCommand = false;
                    if (m_command == null || m_command.isQuit())
                        return;
                }
            }
        }
        catch (Throwable e)
        {
            StringUtils.printException(e);
        }
    }

    private boolean m_endOfFile;

    private final boolean m_log;

    private boolean m_waitCommand;

    private final BufferedReader m_in;

    private Command m_command;

    private final GtpEngine m_gtpServer;

    private Command parseLine(String line)
    {
        assert(! line.trim().equals(""));
        int len = line.length();
        StringBuffer buffer = new StringBuffer(len);
        boolean wasLastSpace = false;
        for (int i = 0; i < len; ++i)
        {
            char c = line.charAt(i);
            if (Character.isISOControl(c))
                continue;
            if (Character.isWhitespace(c))
            {
                if (! wasLastSpace)
                {
                    buffer.append(' ');
                    wasLastSpace = true;
                }
            }
            else
            {
                buffer.append(c);
                wasLastSpace = false;
            }
        }
        String[] array = StringUtils.tokenize(buffer.toString());
        assert(array.length > 0);
        String command = buffer.toString();
        Command result = new Command();
        result.m_hasId = false;
        result.m_command = command;
        try
        {
            result.m_hasId = true;
            result.m_id = Integer.parseInt(array[0]);
            result.m_command = buffer.substring(array[0].length());
        }
        catch (NumberFormatException e)
        {
            result.m_hasId = false;
        }
        return result;
    }
}

//----------------------------------------------------------------------------

/** Base class for Go programs and tools implementing GTP. */
public abstract class GtpEngine
{
    /** Returned by parseColorArgument. */
    public static class ColorArgument
    {
        public GoColor m_color;
    }

    /** Returned by parseColorPointArgument. */
    public static class ColorPointArgument
    {
        public GoColor m_color;

        public GoPoint m_point;
    }

    /** Returned by parseDoubleArgument. */
    public static class DoubleArgument
    {
        public double m_double;
    }

    /** Returned by parseIntegerArgument. */
    public static class IntegerArgument
    {
        public int m_integer;
    }

    /** Returned by parsePointArgument. */
    public static class PointArgument
    {
        public GoPoint m_point;
    }

    /** Returned by parsePointListArgument. */
    public static class PointListArgument
    {
        public Vector m_pointList = new Vector();
    }

    public GtpEngine(InputStream in, OutputStream out, PrintStream log)
    {
        m_out = new PrintStream(out);
        m_in = in;
        m_log = log;
    }

    /** Callback for interrupting commands.
        This callback will be invoked if the special comment line
        "# interrupt" is received. It will be invoked from a different thread.
    */
    public abstract void interruptCommand();

    /** Handle command..
        This method has to be implemented by the subclass.
        It should return true for success, false for failure and
        the response in the StringBuffer argument.
    */
    public abstract boolean handleCommand(String command,
                                          StringBuffer response);

    public synchronized void log(String line)
    {
        assert(m_log != null);
        m_log.println(line);
    }

    public void mainLoop() throws IOException
    {
        ReadThread readThread = new ReadThread(this, m_in, m_log != null);
        readThread.start();
        while (true)
        {
            Command command = readThread.getCommand();
            if (command == null)
                return;
            sendResponse(command);
            if (command.isQuit())
                return;
        }
    }

    /** Utility function for parsing a color argument.
        @param cmdArray Command line split into words.
        @param response Empty string buffer filled with GTP error message
        if parsing fails.
        @return Color argument or null if parsing fails.
    */
    public static ColorArgument parseColorArgument(String[] cmdArray,
                                                   StringBuffer response)
    {
        if (cmdArray.length != 2)
        {
            response.append("Missing color argument");
            return null;
        }
        ColorArgument argument = new ColorArgument();
        String arg1 = cmdArray[1].toLowerCase();
        if (arg1.equals("w") || arg1.equals("white"))
            argument.m_color = GoColor.WHITE;
        else if (arg1.equals("b") || arg1.equals("black"))
            argument.m_color = GoColor.BLACK;
        else
        {
            response.append("Invalid color argument");
            return null;
        }
        return argument;
    }

    /** Utility function for parsing a color and point argument.
        @param cmdArray Command line split into words.
        @param response Empty string buffer filled with GTP error message
        if parsing fails.
        @param boardSize Board size is needed for parsing the point
        @return ColorPoint argument or null if parsing fails.
    */
    public static ColorPointArgument
        parseColorPointArgument(String[] cmdArray, StringBuffer response,
                                int boardSize)
    {
        if (cmdArray.length != 3)
        {
            response.append("Missing color and point argument");
            return null;
        }
        ColorPointArgument argument = new ColorPointArgument();
        String arg1 = cmdArray[1].toLowerCase();
        if (arg1.equals("w") || arg1.equals("white"))
            argument.m_color = GoColor.WHITE;
        else if (arg1.equals("b") || arg1.equals("black"))
            argument.m_color = GoColor.BLACK;
        else
        {
            response.append("Invalid color argument");
            return null;
        }
        try
        {
            GoPoint point = GtpUtils.parsePoint(cmdArray[2], boardSize);
            argument.m_point = point;
            return argument;
        }
        catch (GtpError e)
        {
            response.append("Invalid point argument");
            return null;
        }
    }

    /** Utility function for parsing an integer argument.
        @param cmdArray Command line split into words.
        @param response Empty string buffer filled with GTP error message
        if parsing fails.
        @return Double argument or null if parsing fails.
    */
    public static DoubleArgument parseDoubleArgument(String[] cmdArray,
                                                     StringBuffer response)
    {
        if (cmdArray.length != 2)
        {
            response.append("Missing float argument");
            return null;
        }
        try
        {
            double f = Double.parseDouble(cmdArray[1]);
            DoubleArgument doubleArgument = new DoubleArgument();
            doubleArgument.m_double = f;
            return doubleArgument;
        }
        catch (NumberFormatException e)
        {
            response.append("Invalid float argument");
            return null;
        }
    }

    /** Utility function for parsing an integer argument.
        @param cmdArray Command line split into words.
        @param response Empty string buffer filled with GTP error message
        if parsing fails.
        @return Integer argument or null if parsing fails.
    */
    public static IntegerArgument parseIntegerArgument(String[] cmdArray,
                                                       StringBuffer response)
    {
        if (cmdArray.length != 2)
        {
            response.append("Missing integer argument");
            return null;
        }
        try
        {
            int integer = Integer.parseInt(cmdArray[1]);
            IntegerArgument integerArgument = new IntegerArgument();
            integerArgument.m_integer = integer;
            return integerArgument;
        }
        catch (NumberFormatException e)
        {
            response.append("Invalid integer argument");
            return null;
        }
    }

    /** Utility function for parsing an point argument.
        @param cmdArray Command line split into words.
        @param response Empty string buffer filled with GTP error message
        if parsing fails.
        @param boardSize Board size is needed for parsing the point
        @return GoPoint argument or null if parsing fails.
    */
    public static PointArgument parsePointArgument(String[] cmdArray,
                                                   StringBuffer response,
                                                   int boardSize)
    {
        if (cmdArray.length != 2)
        {
            response.append("Missing point argument");
            return null;
        }
        try
        {
            GoPoint point = GtpUtils.parsePoint(cmdArray[1], boardSize);
            PointArgument argument = new PointArgument();
            argument.m_point = point;
            return argument;
        }
        catch (GtpError e)
        {
            response.append("Invalid point argument");
            return null;
        }
    }

    /** Utility function for parsing an point list argument.
        @param cmdArray Command line split into words.
        @param response Empty string buffer filled with GTP error message
        if parsing fails.
        @param boardSize Board size is needed for parsing the points
        @return Point list argument or null if parsing fails.
    */
    public static PointListArgument
        parsePointListArgument(String[] cmdArray, StringBuffer response,
                               int boardSize)
    {
        int length = cmdArray.length;
        assert(length >= 1);
        try
        {
            PointListArgument argument = new PointListArgument();
            for (int i = 1; i < length; ++i)
            {
                GoPoint point = GtpUtils.parsePoint(cmdArray[i], boardSize);
                argument.m_pointList.add(point);
            }
            return argument;
        }
        catch (GtpError e)
        {
            response.append("Invalid point list argument");
            return null;
        }
    }

    /** Print invalid response directly to output stream.
        Should only be used for simulationg broken GTP implementations
        like used in GtpDummy's dummy_invalid command.
        @param text Text to print to output stream.
        No newline will be appended.
    */
    public void printInvalidResponse(String text)
    {
        m_out.print(text);
    }

    public void respond(boolean status, boolean hasId, int id,
                        String response)
    {
        StringBuffer fullResponse = new StringBuffer(256);
        if (status)
            fullResponse.append('=');
        else
            fullResponse.append('?');
        if (hasId)
            fullResponse.append(id);
        fullResponse.append(' ');
        fullResponse.append(response);
        if (response.length() == 0
            || response.charAt(response.length() - 1) != '\n')
            fullResponse.append('\n');
        m_out.println(fullResponse);
        if (m_log != null)
            m_log.println(fullResponse);
    }

    private final InputStream m_in;

    private final PrintStream m_log;

    private final PrintStream m_out;

    private void sendResponse(Command cmd)
    {
        StringBuffer response = new StringBuffer();
        boolean status = handleCommand(cmd.m_command.trim(), response);
        respond(status, cmd.m_hasId, cmd.m_id, response.toString());
    }
}

//----------------------------------------------------------------------------