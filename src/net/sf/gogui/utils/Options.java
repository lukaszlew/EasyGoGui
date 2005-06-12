//----------------------------------------------------------------------------
// $Id$
// $Source$
//----------------------------------------------------------------------------

package net.sf.gogui.utils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;

//----------------------------------------------------------------------------

/** Parser for command line options. */
public class Options
{
    public Options(String[] args, String[] specs) throws ErrorMessage
    {
        for (int i = 0; i < specs.length; ++i)
        {
            String spec = specs[i];
            if (spec.length() > 0)
                m_map.put(spec, null);
        }
        parseArgs(args);
    }

    public boolean contains(String option)
    {
        return getValue(option) != null;
    }

    /** Get remaining arguments that are not options. */
    public Vector getArguments()
    {
        return m_args;
    }

    public double getDouble(String option) throws ErrorMessage
    {
        return getDouble(option, 0);
    }

    public double getDouble(String option, double defaultValue)
        throws ErrorMessage
    {
        String value = getString(option, Double.toString(defaultValue));
        if (value == null)
            return defaultValue;
        try
        {
            return Double.parseDouble(value);
        }
        catch (NumberFormatException e)
        {
            throw new ErrorMessage("Option -" + option
                                   + " needs float value");
        }
    }

    public int getInteger(String option) throws ErrorMessage
    {
        return getInteger(option, 0);
    }

    public int getInteger(String option, int defaultValue) throws ErrorMessage
    {
        String value = getString(option, Integer.toString(defaultValue));
        if (value == null)
            return defaultValue;
        try
        {
            return Integer.parseInt(value);
        }
        catch (NumberFormatException e)
        {
            throw new ErrorMessage("Option -" + option
                                   + " needs integer value");
        }
    }

    public int getInteger(String option, int defaultValue, int min)
        throws ErrorMessage
    {
        int value = getInteger(option, defaultValue);
        if (value < min)
            throw new ErrorMessage("Option -" + option
                                   + " must be greater than " + min);
        return value;
    }

    public int getInteger(String option, int defaultValue, int min, int max)
        throws ErrorMessage
    {
        int value = getInteger(option, defaultValue);
        if (value < min || value > max)
            throw new ErrorMessage("Option -" + option + " must be in ["
                                   + min + ".." + max + "]");
        return value;
    }

    public String getString(String option) throws ErrorMessage
    {
        return getString(option, "");
    }

    public String getString(String option, String defaultValue)
    {
        assert(isValidOption(option));
        String value = getValue(option);
        if (value == null)
            return defaultValue;
        return value;
    }

    /** Check if option is set. */
    public boolean isSet(String option) throws ErrorMessage
    {
        String value = getString(option, null);
        return (value != null);
    }

    /** Read options from a file given with the option "config".
        Requires that "config" is an allowed option.
    */
    public void handleConfigOption() throws ErrorMessage
    {
        if (! isSet("config"))
            return;
        String filename = getString("config");
        InputStream inputStream;
        try
        {
            inputStream = new FileInputStream(filename);
        }
        catch (FileNotFoundException e)
        {
            throw new ErrorMessage("File not found: " + filename);
        }
        Reader reader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(reader);
        try
        {
            StringBuffer buffer = new StringBuffer(256);
            String line;
            while (true)
            {
                line = bufferedReader.readLine();
                if (line == null)
                    break;
                buffer.append(line);
                buffer.append(' ');
            }
            parseArgs(StringUtils.tokenize(buffer.toString()));
        }
        catch (IOException e)
        {
            StringUtils.printException(e);
        }
        finally
        {
            try
            {
                bufferedReader.close();
            }
            catch (IOException e)
            {
                StringUtils.printException(e);
            }
        }
    }

    /** Creates a new Options instance from command line.
        Automatically calls handleConfigOption.
    */
    public static Options parse(String[] args, String[] specs)
        throws ErrorMessage
    {
        Options opt = new Options(args, specs);
        opt.handleConfigOption();
        return opt;
    }

    private final Vector m_args = new Vector();

    private final Map m_map = new TreeMap();

    private String getSpec(String option) throws ErrorMessage
    {
        if (m_map.containsKey(option))
            return option;
        else if (m_map.containsKey(option + ":"))
            return option + ":";
        throw new ErrorMessage("Unknown option -" + option);
    }

    private String getValue(String option)
    {
        assert(isValidOption(option));
        if (m_map.containsKey(option))
            return (String)m_map.get(option);
        return (String)m_map.get(option + ":");
    }

    private boolean isOptionKey(String s)
    {
        return (s.length() > 0 && s.charAt(0) == '-');
    }

    private boolean isValidOption(String option)
    {
        return (m_map.containsKey(option) || m_map.containsKey(option + ":"));
    }

    private boolean needsValue(String spec)
    {
        return (spec.length() > 0
                && spec.substring(spec.length() - 1).equals(":"));
    }

    private void parseArgs(String args[]) throws ErrorMessage
    {
        int n = 0;
        while (n < args.length)
        {
            String s = args[n];
            ++n;
            if (isOptionKey(s))
            {
                String spec = getSpec(s.substring(1));
                if (needsValue(spec))
                {
                    if (n >= args.length)
                        throw new ErrorMessage("Option " + s
                                               + " needs value");
                    String value = args[n];
                    if (isOptionKey(value))
                        throw new ErrorMessage("Option " + s
                                               + " needs value");
                    ++n;
                    m_map.put(spec, value);
                }
                else
                    m_map.put(spec, "1");
                
            }
            else
                m_args.add(s);
        }
    }
}

//----------------------------------------------------------------------------