package net.minecraft.src;

import java.io.*;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PropertyManager
{
    public static Logger logger = Logger.getLogger("Minecraft");
    private Properties serverProperties;
    private File serverPropertiesFile;

    public PropertyManager(File file)
    {
        serverProperties = new Properties();
        serverPropertiesFile = file;
        if (file.exists())
        {
            try
            {
                serverProperties.load(new FileInputStream(file));
            }
            catch (Exception exception)
            {
                logger.log(Level.WARNING, (new StringBuilder()).append("Failed to load ").append(file).toString(), exception);
                generateNewProperties();
            }
        }
        else
        {
            logger.log(Level.WARNING, (new StringBuilder()).append(file).append(" does not exist").toString());
            generateNewProperties();
        }
    }

    public void generateNewProperties()
    {
        logger.log(Level.INFO, "Generating new properties file");
        saveProperties();
    }

    public void saveProperties()
    {
        try
        {
            serverProperties.store(new FileOutputStream(serverPropertiesFile), "Minecraft server properties");
        }
        catch (Exception exception)
        {
            logger.log(Level.WARNING, (new StringBuilder()).append("Failed to save ").append(serverPropertiesFile).toString(), exception);
            generateNewProperties();
        }
    }

    public File getPropertiesFile()
    {
        return serverPropertiesFile;
    }

    public String getStringProperty(String s, String s1)
    {
        if (!serverProperties.containsKey(s))
        {
            serverProperties.setProperty(s, s1);
            saveProperties();
        }
        return serverProperties.getProperty(s, s1);
    }

    public int getIntProperty(String s, int i)
    {
        try
        {
            return Integer.parseInt(getStringProperty(s, (new StringBuilder()).append("").append(i).toString()));
        }
        catch (Exception exception)
        {
            serverProperties.setProperty(s, (new StringBuilder()).append("").append(i).toString());
        }
        return i;
    }

    public boolean getBooleanProperty(String s, boolean flag)
    {
        try
        {
            return Boolean.parseBoolean(getStringProperty(s, (new StringBuilder()).append("").append(flag).toString()));
        }
        catch (Exception exception)
        {
            serverProperties.setProperty(s, (new StringBuilder()).append("").append(flag).toString());
        }
        return flag;
    }

    public void setProperty(String s, Object obj)
    {
        serverProperties.setProperty(s, (new StringBuilder()).append("").append(obj).toString());
    }

    public void setProperty(String s, boolean flag)
    {
        serverProperties.setProperty(s, (new StringBuilder()).append("").append(flag).toString());
        saveProperties();
    }
}
