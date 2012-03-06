package net.minecraft.src;

import java.io.*;
import java.util.*;

public class AnvilChunkLoader implements IChunkLoader, IThreadedFileIO
{
    private List field_48469_a;
    private Set field_48467_b;
    private Object field_48468_c;
    private final File field_48466_d;

    public AnvilChunkLoader(File par1File)
    {
        field_48469_a = new ArrayList();
        field_48467_b = new HashSet();
        field_48468_c = new Object();
        field_48466_d = par1File;
    }

    public Chunk loadChunk(World par1World, int par2, int par3) throws IOException
    {
        NBTTagCompound nbttagcompound = null;
        ChunkCoordIntPair chunkcoordintpair = new ChunkCoordIntPair(par2, par3);

        synchronized (field_48468_c)
        {
            if (field_48467_b.contains(chunkcoordintpair))
            {
                int i = 0;

                do
                {
                    if (i >= field_48469_a.size())
                    {
                        break;
                    }

                    if (((AnvilChunkLoaderPending)field_48469_a.get(i)).field_48581_a.equals(chunkcoordintpair))
                    {
                        nbttagcompound = ((AnvilChunkLoaderPending)field_48469_a.get(i)).field_48580_b;
                        break;
                    }

                    i++;
                }
                while (true);
            }
        }

        if (nbttagcompound == null)
        {
            java.io.DataInputStream datainputstream = RegionFileCache.getChunkInputStream(field_48466_d, par2, par3);

            if (datainputstream != null)
            {
                nbttagcompound = CompressedStreamTools.read(datainputstream);
            }
            else
            {
                return null;
            }
        }

        return func_48464_a(par1World, par2, par3, nbttagcompound);
    }

    protected Chunk func_48464_a(World par1World, int par2, int par3, NBTTagCompound par4NBTTagCompound)
    {
        if (!par4NBTTagCompound.hasKey("Level"))
        {
            System.out.println((new StringBuilder()).append("Chunk file at ").append(par2).append(",").append(par3).append(" is missing level data, skipping").toString());
            return null;
        }

        if (!par4NBTTagCompound.getCompoundTag("Level").hasKey("Sections"))
        {
            System.out.println((new StringBuilder()).append("Chunk file at ").append(par2).append(",").append(par3).append(" is missing block data, skipping").toString());
            return null;
        }

        Chunk chunk = func_48465_a(par1World, par4NBTTagCompound.getCompoundTag("Level"));

        if (!chunk.isAtLocation(par2, par3))
        {
            System.out.println((new StringBuilder()).append("Chunk file at ").append(par2).append(",").append(par3).append(" is in the wrong location; relocating. (Expected ").append(par2).append(", ").append(par3).append(", got ").append(chunk.xPosition).append(", ").append(chunk.zPosition).append(")").toString());
            par4NBTTagCompound.setInteger("xPos", par2);
            par4NBTTagCompound.setInteger("zPos", par3);
            chunk = func_48465_a(par1World, par4NBTTagCompound.getCompoundTag("Level"));
        }

        chunk.removeUnknownBlocks();
        return chunk;
    }

    public void saveChunk(World par1World, Chunk par2Chunk) throws IOException
    {
        par1World.checkSessionLock();

        try
        {
            NBTTagCompound nbttagcompound = new NBTTagCompound();
            NBTTagCompound nbttagcompound1 = new NBTTagCompound();
            nbttagcompound.setTag("Level", nbttagcompound1);
            func_48462_a(par2Chunk, par1World, nbttagcompound1);
            func_48463_a(par2Chunk.getChunkCoordIntPair(), nbttagcompound);
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }

    protected void func_48463_a(ChunkCoordIntPair par1ChunkCoordIntPair, NBTTagCompound par2NBTTagCompound)
    {
        synchronized (field_48468_c)
        {
            if (field_48467_b.contains(par1ChunkCoordIntPair))
            {
                for (int i = 0; i < field_48469_a.size(); i++)
                {
                    if (((AnvilChunkLoaderPending)field_48469_a.get(i)).field_48581_a.equals(par1ChunkCoordIntPair))
                    {
                        field_48469_a.set(i, new AnvilChunkLoaderPending(par1ChunkCoordIntPair, par2NBTTagCompound));
                        return;
                    }
                }
            }

            field_48469_a.add(new AnvilChunkLoaderPending(par1ChunkCoordIntPair, par2NBTTagCompound));
            field_48467_b.add(par1ChunkCoordIntPair);
            ThreadedFileIOBase.threadedIOInstance.queueIO(this);
            return;
        }
    }

    /**
     * Returns a boolean stating if the write was unsuccessful.
     */
    public boolean writeNextIO()
    {
        AnvilChunkLoaderPending anvilchunkloaderpending = null;

        synchronized (field_48468_c)
        {
            if (field_48469_a.size() > 0)
            {
                anvilchunkloaderpending = (AnvilChunkLoaderPending)field_48469_a.remove(0);
                field_48467_b.remove(anvilchunkloaderpending.field_48581_a);
            }
            else
            {
                return false;
            }
        }

        if (anvilchunkloaderpending != null)
        {
            try
            {
                func_48461_a(anvilchunkloaderpending);
            }
            catch (Exception exception)
            {
                exception.printStackTrace();
            }
        }

        return true;
    }

    private void func_48461_a(AnvilChunkLoaderPending par1AnvilChunkLoaderPending) throws IOException
    {
        DataOutputStream dataoutputstream = RegionFileCache.getChunkOutputStream(field_48466_d, par1AnvilChunkLoaderPending.field_48581_a.chunkXPos, par1AnvilChunkLoaderPending.field_48581_a.chunkZPos);
        CompressedStreamTools.write(par1AnvilChunkLoaderPending.field_48580_b, dataoutputstream);
        dataoutputstream.close();
    }

    /**
     * Save extra data associated with this Chunk not normally saved during autosave, only during chunk unload.
     * Currently unused.
     */
    public void saveExtraChunkData(World world, Chunk chunk) throws IOException
    {
    }

    /**
     * Called every World.tick()
     */
    public void chunkTick()
    {
    }

    /**
     * Save extra data not associated with any Chunk.  Not saved during autosave, only during world unload.  Currently
     * unused.
     */
    public void saveExtraData()
    {
    }

    private void func_48462_a(Chunk par1Chunk, World par2World, NBTTagCompound par3NBTTagCompound)
    {
        par2World.checkSessionLock();
        par3NBTTagCompound.setInteger("xPos", par1Chunk.xPosition);
        par3NBTTagCompound.setInteger("zPos", par1Chunk.zPosition);
        par3NBTTagCompound.setLong("LastUpdate", par2World.getWorldTime());
        par3NBTTagCompound.func_48446_a("HeightMap", par1Chunk.field_48562_f);
        par3NBTTagCompound.setBoolean("TerrainPopulated", par1Chunk.isTerrainPopulated);
        ExtendedBlockStorage aextendedblockstorage[] = par1Chunk.func_48553_h();
        NBTTagList nbttaglist = new NBTTagList("Sections");
        ExtendedBlockStorage aextendedblockstorage1[] = aextendedblockstorage;
        int i = aextendedblockstorage1.length;

        for (int k = 0; k < i; k++)
        {
            ExtendedBlockStorage extendedblockstorage = aextendedblockstorage1[k];

            if (extendedblockstorage == null || extendedblockstorage.func_48587_f() == 0)
            {
                continue;
            }

            NBTTagCompound nbttagcompound = new NBTTagCompound();
            nbttagcompound.setByte("Y", (byte)(extendedblockstorage.func_48597_c() >> 4 & 0xff));
            nbttagcompound.setByteArray("Blocks", extendedblockstorage.func_48590_g());

            if (extendedblockstorage.func_48601_h() != null)
            {
                nbttagcompound.setByteArray("Add", extendedblockstorage.func_48601_h().data);
            }

            nbttagcompound.setByteArray("Data", extendedblockstorage.func_48594_i().data);
            nbttagcompound.setByteArray("SkyLight", extendedblockstorage.func_48605_k().data);
            nbttagcompound.setByteArray("BlockLight", extendedblockstorage.func_48600_j().data);
            nbttaglist.appendTag(nbttagcompound);
        }

        par3NBTTagCompound.setTag("Sections", nbttaglist);
        par3NBTTagCompound.setByteArray("Biomes", par1Chunk.func_48552_l());
        par1Chunk.hasEntities = false;
        NBTTagList nbttaglist1 = new NBTTagList();
        label0:

        for (int j = 0; j < par1Chunk.field_48563_j.length; j++)
        {
            Iterator iterator = par1Chunk.field_48563_j[j].iterator();

            do
            {
                if (!iterator.hasNext())
                {
                    continue label0;
                }

                Entity entity = (Entity)iterator.next();
                par1Chunk.hasEntities = true;
                NBTTagCompound nbttagcompound1 = new NBTTagCompound();

                if (entity.addEntityID(nbttagcompound1))
                {
                    nbttaglist1.appendTag(nbttagcompound1);
                }
            }
            while (true);
        }

        par3NBTTagCompound.setTag("Entities", nbttaglist1);
        NBTTagList nbttaglist2 = new NBTTagList();
        NBTTagCompound nbttagcompound2;

        for (Iterator iterator1 = par1Chunk.chunkTileEntityMap.values().iterator(); iterator1.hasNext(); nbttaglist2.appendTag(nbttagcompound2))
        {
            TileEntity tileentity = (TileEntity)iterator1.next();
            nbttagcompound2 = new NBTTagCompound();
            tileentity.writeToNBT(nbttagcompound2);
        }

        par3NBTTagCompound.setTag("TileEntities", nbttaglist2);
        List list = par2World.getPendingBlockUpdates(par1Chunk, false);

        if (list != null)
        {
            long l = par2World.getWorldTime();
            NBTTagList nbttaglist3 = new NBTTagList();
            NBTTagCompound nbttagcompound3;

            for (Iterator iterator2 = list.iterator(); iterator2.hasNext(); nbttaglist3.appendTag(nbttagcompound3))
            {
                NextTickListEntry nextticklistentry = (NextTickListEntry)iterator2.next();
                nbttagcompound3 = new NBTTagCompound();
                nbttagcompound3.setInteger("i", nextticklistentry.blockID);
                nbttagcompound3.setInteger("x", nextticklistentry.xCoord);
                nbttagcompound3.setInteger("y", nextticklistentry.yCoord);
                nbttagcompound3.setInteger("z", nextticklistentry.zCoord);
                nbttagcompound3.setInteger("t", (int)(nextticklistentry.scheduledTime - l));
            }

            par3NBTTagCompound.setTag("TileTicks", nbttaglist3);
        }
    }

    private Chunk func_48465_a(World par1World, NBTTagCompound par2NBTTagCompound)
    {
        int i = par2NBTTagCompound.getInteger("xPos");
        int j = par2NBTTagCompound.getInteger("zPos");
        Chunk chunk = new Chunk(par1World, i, j);
        chunk.field_48562_f = par2NBTTagCompound.func_48445_l("HeightMap");
        chunk.isTerrainPopulated = par2NBTTagCompound.getBoolean("TerrainPopulated");
        NBTTagList nbttaglist = par2NBTTagCompound.getTagList("Sections");
        byte byte0 = 16;
        ExtendedBlockStorage aextendedblockstorage[] = new ExtendedBlockStorage[byte0];

        for (int k = 0; k < nbttaglist.tagCount(); k++)
        {
            NBTTagCompound nbttagcompound = (NBTTagCompound)nbttaglist.tagAt(k);
            byte byte1 = nbttagcompound.getByte("Y");
            ExtendedBlockStorage extendedblockstorage = new ExtendedBlockStorage(byte1 << 4);
            extendedblockstorage.func_48596_a(nbttagcompound.getByteArray("Blocks"));

            if (nbttagcompound.hasKey("Add"))
            {
                extendedblockstorage.func_48593_a(new NibbleArray(nbttagcompound.getByteArray("Add"), 4));
            }

            extendedblockstorage.func_48586_b(new NibbleArray(nbttagcompound.getByteArray("Data"), 4));
            extendedblockstorage.func_48589_d(new NibbleArray(nbttagcompound.getByteArray("SkyLight"), 4));
            extendedblockstorage.func_48606_c(new NibbleArray(nbttagcompound.getByteArray("BlockLight"), 4));
            extendedblockstorage.func_48599_d();
            aextendedblockstorage[byte1] = extendedblockstorage;
        }

        chunk.func_48558_a(aextendedblockstorage);

        if (par2NBTTagCompound.hasKey("Biomes"))
        {
            chunk.func_48559_a(par2NBTTagCompound.getByteArray("Biomes"));
        }

        NBTTagList nbttaglist1 = par2NBTTagCompound.getTagList("Entities");

        if (nbttaglist1 != null)
        {
            for (int l = 0; l < nbttaglist1.tagCount(); l++)
            {
                NBTTagCompound nbttagcompound1 = (NBTTagCompound)nbttaglist1.tagAt(l);
                Entity entity = EntityList.createEntityFromNBT(nbttagcompound1, par1World);
                chunk.hasEntities = true;

                if (entity != null)
                {
                    chunk.addEntity(entity);
                }
            }
        }

        NBTTagList nbttaglist2 = par2NBTTagCompound.getTagList("TileEntities");

        if (nbttaglist2 != null)
        {
            for (int i1 = 0; i1 < nbttaglist2.tagCount(); i1++)
            {
                NBTTagCompound nbttagcompound2 = (NBTTagCompound)nbttaglist2.tagAt(i1);
                TileEntity tileentity = TileEntity.createAndLoadEntity(nbttagcompound2);

                if (tileentity != null)
                {
                    chunk.addTileEntity(tileentity);
                }
            }
        }

        if (par2NBTTagCompound.hasKey("TileTicks"))
        {
            NBTTagList nbttaglist3 = par2NBTTagCompound.getTagList("TileTicks");

            if (nbttaglist3 != null)
            {
                for (int j1 = 0; j1 < nbttaglist3.tagCount(); j1++)
                {
                    NBTTagCompound nbttagcompound3 = (NBTTagCompound)nbttaglist3.tagAt(j1);
                    par1World.scheduleBlockUpdateFromLoad(nbttagcompound3.getInteger("x"), nbttagcompound3.getInteger("y"), nbttagcompound3.getInteger("z"), nbttagcompound3.getInteger("i"), nbttagcompound3.getInteger("t"));
                }
            }
        }

        return chunk;
    }
}
