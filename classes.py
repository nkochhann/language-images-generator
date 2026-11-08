class Theme:
    def __init__(self, theme, foldername = ''):
        self.theme = theme
        self.foldername = foldername

        self.formatfoldername()

    def formatfoldername(self):
        if(self.foldername != ''):
            return
        
        self.foldername = ''.join(char for char in self.theme if char.isalpha())