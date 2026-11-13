public void addPlayer(EntityPlayerMP player)
    {
        if (this.players.contains(player))
        {
            LOGGER.debug("Failed to add player. {} already is in chunk {}, {}", player, Integer.valueOf(this.pos.x), Integer.valueOf(this.pos.z));
        }
        else
        {
            if (this.players.isEmpty())
            {
                this.lastUpdateInhabitedTime = this.playerChunkMap.getWorldServer().getTotalWorldTime();
            }

            this.players.add(player);

            if (this.sentToPlayers)
            {
                this.sendToPlayer(player);
            }
        }
    }

    public void removePlayer(EntityPlayerMP player)
    {
        if (this.players.contains(player))
        {
            if (this.sentToPlayers)
            {
                player.connection.sendPacket(new SPacketUnloadChunk(this.pos.x, this.pos.z));
            }

            this.players.remove(player);

            if (this.players.isEmpty())
            {
                this.playerChunkMap.removeEntry(this);
            }
        }
    }