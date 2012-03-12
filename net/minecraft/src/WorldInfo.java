package net.minecraft.src;

import java.util.List;

public class WorldInfo
{
    /** Holds the seed of the currently world. */
    private long randomSeed;
    private WorldType terrainType;

    /** The spawn zone position (X) */
    private int spawnX;

    /** The spawn zone position (Y) */
    private int spawnY;

    /** The spawn zone position (Z) */
    private int spawnZ;

    /** The world time (in ticks) */
    private long worldTime;

    /** The last time the player was in this world. */
    private long lastTimePlayed;

    /** The size of entire save of current world on the disk, isn't exactly. */
    private long sizeOnDisk;
    private NBTTagCompound playerTag;
    private int dimension;

    /** The name of the save defined at world creation. */
    private String levelName;

    /** Introduced in beta 1.3, is the save version for future control. */
    private int saveVersion;

    /** hold the boolean if its raining or not */
    private boolean raining;

    /** holds the time of the rain */
    private int rainTime;

    /** Is thunderbolts failing now? */
    private boolean thundering;

    /** hold the during of the thunder */
    private int thunderTime;

    /** Indicates the type of the game. 0 for survival, 1 for creative. */
    private int gameType;

    /** Are map structures going to be generated? (e.g. strongholds) */
    private boolean mapFeaturesEnabled;

    /** Hardcore mode flag */
    private boolean hardcore;

    public WorldInfo(NBTTagCompound par1NBTTagCompound)
    {
        terrainType = WorldType.field_48457_b;
        hardcore = false;
        randomSeed = par1NBTTagCompound.getLong("RandomSeed");

        if (par1NBTTagCompound.hasKey("generatorName"))
        {
            String s = par1NBTTagCompound.getString("generatorName");
            terrainType = WorldType.parseWorldType(s);

            if (terrainType == null)
            {
                terrainType = WorldType.field_48457_b;
            }
            else if (terrainType.func_48453_c())
            {
                int i = 0;

                if (par1NBTTagCompound.hasKey("generatorVersion"))
                {
                    i = par1NBTTagCompound.getInteger("generatorVersion");
                }

                terrainType = terrainType.func_48451_a(i);
            }
        }

        gameType = par1NBTTagCompound.getInteger("GameType");

        if (par1NBTTagCompound.hasKey("MapFeatures"))
        {
            mapFeaturesEnabled = par1NBTTagCompound.getBoolean("MapFeatures");
        }
        else
        {
            mapFeaturesEnabled = true;
        }

        spawnX = par1NBTTagCompound.getInteger("SpawnX");
        spawnY = par1NBTTagCompound.getInteger("SpawnY");
        spawnZ = par1NBTTagCompound.getInteger("SpawnZ");
        worldTime = par1NBTTagCompound.getLong("Time");
        lastTimePlayed = par1NBTTagCompound.getLong("LastPlayed");
        sizeOnDisk = par1NBTTagCompound.getLong("SizeOnDisk");
        levelName = par1NBTTagCompound.getString("LevelName");
        saveVersion = par1NBTTagCompound.getInteger("version");
        rainTime = par1NBTTagCompound.getInteger("rainTime");
        raining = par1NBTTagCompound.getBoolean("raining");
        thunderTime = par1NBTTagCompound.getInteger("thunderTime");
        thundering = par1NBTTagCompound.getBoolean("thundering");
        hardcore = par1NBTTagCompound.getBoolean("hardcore");

        if (par1NBTTagCompound.hasKey("Player"))
        {
            playerTag = par1NBTTagCompound.getCompoundTag("Player");
            dimension = playerTag.getInteger("Dimension");
        }
    }

    public WorldInfo(WorldSettings par1WorldSettings, String par2Str)
    {
        terrainType = WorldType.field_48457_b;
        hardcore = false;
        randomSeed = par1WorldSettings.getSeed();
        gameType = par1WorldSettings.getGameType();
        mapFeaturesEnabled = par1WorldSettings.isMapFeaturesEnabled();
        levelName = par2Str;
        hardcore = par1WorldSettings.getHardcoreEnabled();
        terrainType = par1WorldSettings.getTerrainType();
    }

    public WorldInfo(WorldInfo par1WorldInfo)
    {
        terrainType = WorldType.field_48457_b;
        hardcore = false;
        randomSeed = par1WorldInfo.randomSeed;
        terrainType = par1WorldInfo.terrainType;
        gameType = par1WorldInfo.gameType;
        mapFeaturesEnabled = par1WorldInfo.mapFeaturesEnabled;
        spawnX = par1WorldInfo.spawnX;
        spawnY = par1WorldInfo.spawnY;
        spawnZ = par1WorldInfo.spawnZ;
        worldTime = par1WorldInfo.worldTime;
        lastTimePlayed = par1WorldInfo.lastTimePlayed;
        sizeOnDisk = par1WorldInfo.sizeOnDisk;
        playerTag = par1WorldInfo.playerTag;
        dimension = par1WorldInfo.dimension;
        levelName = par1WorldInfo.levelName;
        saveVersion = par1WorldInfo.saveVersion;
        rainTime = par1WorldInfo.rainTime;
        raining = par1WorldInfo.raining;
        thunderTime = par1WorldInfo.thunderTime;
        thundering = par1WorldInfo.thundering;
        hardcore = par1WorldInfo.hardcore;
    }

    /**
     * Gets the NBTTagCompound for the worldInfo
     */
    public NBTTagCompound getNBTTagCompound()
    {
        NBTTagCompound nbttagcompound = new NBTTagCompound();
        updateTagCompound(nbttagcompound, playerTag);
        return nbttagcompound;
    }

    /**
     * stores the current level's dat to an nbt tag for future saving in level.dat
     */
    public NBTTagCompound getNBTTagCompoundWithPlayers(List par1List)
    {
        NBTTagCompound nbttagcompound = new NBTTagCompound();
        EntityPlayer entityplayer = null;
        NBTTagCompound nbttagcompound1 = null;

        if (par1List.size() > 0)
        {
            entityplayer = (EntityPlayer)par1List.get(0);
        }

        if (entityplayer != null)
        {
            nbttagcompound1 = new NBTTagCompound();
            entityplayer.writeToNBT(nbttagcompound1);
        }

        updateTagCompound(nbttagcompound, nbttagcompound1);
        return nbttagcompound;
    }

    private void updateTagCompound(NBTTagCompound par1NBTTagCompound, NBTTagCompound par2NBTTagCompound)
    {
        par1NBTTagCompound.setLong("RandomSeed", randomSeed);
        par1NBTTagCompound.setString("generatorName", terrainType.func_48449_a());
        par1NBTTagCompound.setInteger("generatorVersion", terrainType.func_48452_b());
        par1NBTTagCompound.setInteger("GameType", gameType);
        par1NBTTagCompound.setBoolean("MapFeatures", mapFeaturesEnabled);
        par1NBTTagCompound.setInteger("SpawnX", spawnX);
        par1NBTTagCompound.setInteger("SpawnY", spawnY);
        par1NBTTagCompound.setInteger("SpawnZ", spawnZ);
        par1NBTTagCompound.setLong("Time", worldTime);
        par1NBTTagCompound.setLong("SizeOnDisk", sizeOnDisk);
        par1NBTTagCompound.setLong("LastPlayed", System.currentTimeMillis());
        par1NBTTagCompound.setString("LevelName", levelName);
        par1NBTTagCompound.setInteger("version", saveVersion);
        par1NBTTagCompound.setInteger("rainTime", rainTime);
        par1NBTTagCompound.setBoolean("raining", raining);
        par1NBTTagCompound.setInteger("thunderTime", thunderTime);
        par1NBTTagCompound.setBoolean("thundering", thundering);
        par1NBTTagCompound.setBoolean("hardcore", hardcore);

        if (par2NBTTagCompound != null)
        {
            par1NBTTagCompound.setCompoundTag("Player", par2NBTTagCompound);
        }
    }

    /**
     * Returns the seed of current world.
     */
    public long getSeed()
    {
        return randomSeed;
    }

    /**
     * Returns the x spawn position
     */
    public int getSpawnX()
    {
        return spawnX;
    }

    /**
     * Return the Y axis spawning point of the player.
     */
    public int getSpawnY()
    {
        return spawnY;
    }

    /**
     * Returns the z spawn position
     */
    public int getSpawnZ()
    {
        return spawnZ;
    }

    /**
     * Get current world time
     */
    public long getWorldTime()
    {
        return worldTime;
    }

    public int getDimension()
    {
        return dimension;
    }

    /**
     * Set current world time
     */
    public void setWorldTime(long par1)
    {
        worldTime = par1;
    }

    /**
     * Sets the spawn (zone) position
     */
    public void setSpawnPosition(int par1, int par2, int par3)
    {
        spawnX = par1;
        spawnY = par2;
        spawnZ = par3;
    }

    public void setWorldName(String par1Str)
    {
        levelName = par1Str;
    }

    /**
     * Returns the save version of this world
     */
    public int getSaveVersion()
    {
        return saveVersion;
    }

    /**
     * Sets the save version of the world
     */
    public void setSaveVersion(int par1)
    {
        saveVersion = par1;
    }

    /**
     * gets if it's thundering or not
     */
    public boolean isThundering()
    {
        return thundering;
    }

    /**
     * sets if it's thundering or not
     */
    public void setThundering(boolean par1)
    {
        thundering = par1;
    }

    /**
     * gets the during of the thunder
     */
    public int getThunderTime()
    {
        return thunderTime;
    }

    /**
     * sets the during of the Thunder
     */
    public void setThunderTime(int par1)
    {
        thunderTime = par1;
    }

    /**
     * returns whether it's raining
     */
    public boolean isRaining()
    {
        return raining;
    }

    /**
     * sets whether it's raining
     */
    public void setRaining(boolean par1)
    {
        raining = par1;
    }

    /**
     * gets the rainTime
     */
    public int getRainTime()
    {
        return rainTime;
    }

    /**
     * sets the rainTime
     */
    public void setRainTime(int par1)
    {
        rainTime = par1;
    }

    /**
     * Get the game type, 0 for survival, 1 for creative.
     */
    public int getGameType()
    {
        return gameType;
    }

    /**
     * Get whether the map features generation is enabled or disabled.
     */
    public boolean isMapFeaturesEnabled()
    {
        return mapFeaturesEnabled;
    }

    /**
     * Set the game type, <=0 for survival, >0 for creative.
     */
    public void setGameType(int par1)
    {
        gameType = par1;
    }

    /**
     * Returns true if hardcore mode is enabled, otherwise false
     */
    public boolean isHardcoreModeEnabled()
    {
        return hardcore;
    }

    public WorldType getTerrainType()
    {
        return terrainType;
    }

    public void func_48392_a(WorldType par1WorldType)
    {
        terrainType = par1WorldType;
    }
}
