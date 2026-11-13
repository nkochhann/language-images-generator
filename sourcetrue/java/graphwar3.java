{
	public GlobalScreen(Graphwar graphwar, String confFile) throws InterruptedException, IOException
	{
		super();
		
		this.graphwar = graphwar;
		
		this.setLayout(null);		
		this.setBounds(0,0,Constants.WIDTH,Constants.HEIGHT);
		
		BufferedReader read =  new BufferedReader(new InputStreamReader(graphwar.getClass().getResourceAsStream(confFile)));
		
		// Stack used to add components in reverse order, because swing draws components added first first
		// and that is not intuitive to the way the data is read from the files
		Stack<Component> components = new Stack<Component>();
		
		/////	Main Screen		/////////////
			
			int numBackgroundImages = Integer.parseInt(GraphUtil.nextLine(read));			
			backgroundImages = new JLabel[numBackgroundImages];
			
			for(int i=0; i<numBackgroundImages; i++)
			{
				backgroundImages[i] = GraphUtil.makeBackgroundImage(graphwar, read);
				components.push(backgroundImages[i]);
			}						
			
			createButton = GraphUtil.makeButton(graphwar, read);
			gameRoomButton = GraphUtil.makeButton(graphwar, read);
			backButton = GraphUtil.makeButton(graphwar, read);
			chatField = GraphUtil.makeTextField(read);			
			chatBox = GraphUtil.makeTextBox(read);
			
			playerBoard = new GlobalPlayerBoard(graphwar, 180, 367);
			playerBoard.setPreferredSize(new Dimension(180,367));			
			JScrollPane playerPane = new JScrollPane(playerBoard);
			playerPane.setBounds(585, 50, 200, 370);
			
			playerPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);		
			playerPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			
			roomBoard = new RoomBoard(graphwar, 380, 197);
			roomBoard.setPreferredSize(new Dimension(380,197));	
			JScrollPane roomPane = new JScrollPane(roomBoard);
			roomPane.setBounds(15, 50, 400, 200);
			
			roomPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);		
			roomPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			
			components.push(createButton);
			components.push(gameRoomButton);
			components.push(backButton);
			components.push(chatField);
			components.push(chatBox);
			components.push(playerPane);
			components.push(roomPane);
			
		
			this.createButton.addActionListener(this);
			this.gameRoomButton.addActionListener(this);
			this.backButton.addActionListener(this);
			this.chatField.addActionListener(this);
	
			this.gameRoomButton.setVisible(false);
		//////////// Create Game ///////////////////////////////////////////
			
			numBackgroundImages = Integer.parseInt(GraphUtil.nextLine(read));			
			backgroundsCreate = new JLabel[numBackgroundImages];
			
			for(int i=0; i<numBackgroundImages; i++)
			{
				backgroundsCreate[i] = GraphUtil.makeBackgroundImage(graphwar, read);
				components.push(backgroundsCreate[i]);
			}		

			this.nameFieldCreate = GraphUtil.makeTextField(read);			
			this.portFieldCreate = GraphUtil.makeTextField(read);
			this.yesButtonCreate = GraphUtil.makeButton(graphwar, read);
			this.noButtonCreate = GraphUtil.makeButton(graphwar, read);
			
			components.push(nameFieldCreate);
			components.push(portFieldCreate);
			components.push(yesButtonCreate);
			components.push(noButtonCreate);
			
			showCreateGame(false);
			
			this.nameFieldCreate.addActionListener(this);
			this.portFieldCreate.addActionListener(this);
			this.yesButtonCreate.addActionListener(this);
			this.noButtonCreate.addActionListener(this);
			
///// Show Message //////////////
			
			numBackgroundImages = Integer.parseInt(GraphUtil.nextLine(read));			
			backgroundsShowMessage = new JLabel[numBackgroundImages];
			
			for(int i=0; i<numBackgroundImages; i++)
			{
				backgroundsShowMessage[i] = GraphUtil.makeBackgroundImage(graphwar, read);
				components.push(backgroundsShowMessage[i]);
			}				
			
			this.okButton = GraphUtil.makeButton(graphwar, read);			
			this.messageLabel = GraphUtil.makeTextLabel(read);
			
			components.push(okButton);
			components.push(messageLabel);
			
			messageLabel.setFont(new Font("Sans", Font.BOLD, 20));
			messageLabel.setForeground(Color.ORANGE);
			messageLabel.setHorizontalAlignment(JLabel.CENTER);
			messageLabel.setVerticalAlignment(JLabel.CENTER);
			
			showShowMessage(false);
			
			okButton.addActionListener(this);
		
			
		///////////////////////////////////////
			
		this.addComponentsReversed(this, components);
		this.revalidate();
	}
	

	private void addComponentsReversed(JPanel panel, Stack<Component> components)
	{	
    	while(components.empty() == false)
    	{
    		panel.add(components.pop());
    	}
	}
	
	private void showShowMessage(boolean s)
	{
		final boolean show = s;
		
		SwingUtilities.invokeLater(
		new Runnable() 
		{
			public void run()
			{		
				okButton.setVisible(show);
				messageLabel.setVisible(show);
				
				for(int i=0; i<backgroundsShowMessage.length; i++)
				{
					backgroundsShowMessage[i].setVisible(show);
				}
				
				showMessageVisible = show;
				
				repaint();
			}
		}
		);
	}
	
	public void showMessage(String message)
	{
		showMessage = true;
		this.message = message;
		
		FontMetrics fontMetrics = Toolkit.getDefaultToolkit().getFontMetrics (Constants.NAME_FONT);		
		int messageLength = fontMetrics.stringWidth(message);
		
		this.messageX = (Constants.WIDTH - messageLength)/2;
				
		this.repaint();
	}
	
	public void showDisconnectMessage(String message)
	{
		this.messageLabel.setText(message);
		
		showShowMessage(true);
	}	
	
	public void paintComponent(Graphics g)
	{			
		g.setColor(Constants.BACKGROUND);
		g.fillRect(0, 0, Constants.WIDTH, Constants.HEIGHT);
		paintMessage(g);
	}
	
	public void paintMessage(Graphics g)
	{
		if(showMessage)
		{
			g.setFont(font);
			g.setColor(Color.red);
			g.drawString(message, messageX, 15);
		}
	}
}