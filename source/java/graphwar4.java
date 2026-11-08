{	
	private void updatePlayerButtons(Player player)
	{
		for(int i=0; i<numPlayers; i++)
		{
			if(playersEntries[i].player == player)
			{
				if(player.getTeam() == Constants.TEAM1)
				{
					if(player.isLocalPlayer() || graphwar.getGameData().isLeader())
					{
						playersEntries[i].switchSide.updateImages(switchNormal, switchOver, switchMask);
						playersEntries[i].removePlayer.updateImages(removeNormal, removeOver, removeMask);
					}
					else
					{
						playersEntries[i].switchSide.updateImages(switchNormal, switchGray, switchMask);
						playersEntries[i].removePlayer.updateImages(removeNormal, removeGray, removeMask);
					}
					
					for(int j=0; j<playersEntries[i].addSoldier.length; j++)
					{
						if(j<playersEntries[i].player.getNumSoldiers())
						{
							playersEntries[i].addSoldier[j].updateImages(soldierNormal, soldierOver, soldierMask);
						}
						else
						{
							playersEntries[i].addSoldier[j].updateImages(soldierGhost, soldierOver, soldierMask);
						}
					}
				}
				else
				{
					if(player.isLocalPlayer() || graphwar.getGameData().isLeader())
					{
						playersEntries[i].switchSide.updateImages(switchNormalMirror, switchOverMirror, switchMaskMirror);
						playersEntries[i].removePlayer.updateImages(removeNormalMirror, removeOverMirror, removeMaskMirror);
					}
					else
					{
						playersEntries[i].switchSide.updateImages(switchNormalMirror, switchGrayMirror, switchMaskMirror);
						playersEntries[i].removePlayer.updateImages(removeNormalMirror, removeGrayMirror, removeMaskMirror);
					}					
					
					for(int j=0; j<playersEntries[i].addSoldier.length; j++)
					{
						if(j<playersEntries[i].player.getNumSoldiers())
						{
							playersEntries[i].addSoldier[j].updateImages(soldierNormalMirror, soldierOverMirror, soldierMaskMirror);
						}
						else
						{
							playersEntries[i].addSoldier[j].updateImages(soldierGhostMirror, soldierOverMirror, soldierMaskMirror);
						}
					}
				}
				
				return;
			}
		}
	}
	
	public void updateBoard()
	{
		this.removeAll();
		this.numPlayers = 0;
		
		ListIterator<Player> itr = graphwar.getGameData().getPlayers().listIterator();
		
		while(itr.hasNext())
		{
			Player player = itr.next();
			
			addPlayer(player);
		}
		
		this.revalidate();
		this.repaint();
	}
	
	public void updatePlayer(Player player)
	{		
		updatePlayerButtons(player);
		rearrangePlayers();		// This will update the team
	}
	
	public void addPlayer(Player player)
	{
		playersEntries[numPlayers] = new PlayerEntry(	graphwar, entryWidth, entryHeight, player,
														switchNormal, switchOver, switchMask,
														soldierNormal, soldierOver, soldierMask,
														removeNormal, removeOver, removeMask);
		
		this.add(playersEntries[numPlayers]);
		this.revalidate();
		
		numPlayers++;		
		
		updatePlayerButtons(player);
		rearrangePlayers();
	}
	
	public void removePlayer(Player player)
	{
		int remove = -1;
		
		for(int i=0; i<numPlayers; i++)
		{
			if(playersEntries[i].player == player)
			{
				remove = i;
				break;
			}
		}
		
		if(remove != -1)
		{
			this.remove(playersEntries[remove]);
			
			for(int i=remove; i<numPlayers-1; i++)
			{
				playersEntries[i] = playersEntries[i+1];
			}
			
			numPlayers--;
			
			rearrangePlayers();
		}
	}
	
	private void rearrangePlayers()
	{
		int heightTeam1 = 0;
		int heightTeam2 = 0;
		
		for(int i=0; i<numPlayers; i++)
		{
			int x = 0;
			int y = heightTeam1;
			
			if(playersEntries[i].player.getTeam() == Constants.TEAM2)
			{
				x = this.team2Offset;
				y = heightTeam2;
				heightTeam2 += entryHeight;
			}
			else
			{
				heightTeam1 += entryHeight;
			}
			
			playersEntries[i].setBounds(x, y, entryWidth, entryHeight);
		}
		
		this.repaint();
	}
	
	public void restartPlayers()
	{
		/*for(int i=0; i<numPlayers; i++)
		{
			this.remove(playersEntries[i]);			
		}*/
		
		this.removeAll();
		this.revalidate();
		
		this.numPlayers = 0;
	}
	
}