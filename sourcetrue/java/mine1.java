{    
    for (Block block15 : REGISTRY)
        {
            if (block15.blockMaterial == Material.AIR)
            {
                block15.useNeighborBrightness = false;
            }
            else
            {
                boolean flag = false;
                boolean flag1 = block15 instanceof BlockStairs;
                boolean flag2 = block15 instanceof BlockSlab;
                boolean flag3 = block15 == block6 || block15 == block14;
                boolean flag4 = block15.translucent;
                boolean flag5 = block15.lightOpacity == 0;

                if (flag1 || flag2 || flag3 || flag4 || flag5)
                {
                    flag = true;
                }

                block15.useNeighborBrightness = flag;
            }
        }

        Set<Block> set = Sets.newHashSet(REGISTRY.getObject(new ResourceLocation("tripwire")));

        for (Block block16 : REGISTRY)
        {
            if (set.contains(block16))
            {
                for (int i = 0; i < 15; ++i)
                {
                    int j = REGISTRY.getIDForObject(block16) << 4 | i;
                    BLOCK_STATE_IDS.put(block16.getStateFromMeta(i), j);
                }
            }
            else
            {
                UnmodifiableIterator unmodifiableiterator = block16.getBlockState().getValidStates().iterator();

                while (unmodifiableiterator.hasNext())
                {
                    IBlockState iblockstate = (IBlockState)unmodifiableiterator.next();
                    int k = REGISTRY.getIDForObject(block16) << 4 | block16.getMetaFromState(iblockstate);
                    BLOCK_STATE_IDS.put(iblockstate, k);
                }
            }
        }
    }

    private static void registerBlock(int id, ResourceLocation textualID, Block block_)
    {
        REGISTRY.register(id, textualID, block_);
    }