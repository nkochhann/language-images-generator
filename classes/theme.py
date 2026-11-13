class Theme:
    def __init__(self, theme, foldername = ''):
        self.theme = theme
        self.foldername = foldername

        self.formatfoldername()

    def formatfoldername(self):
        if(self.foldername != ''):
            return
        
        self.foldername = ''.join(char for char in self.theme if char.isalpha())

THEMES = [
    Theme("catppuccin-mocha", "mocha"),
    Theme("catppuccin-macchiato", "macchiato"),
    Theme("catppuccin-frappe", "frappe"),
    Theme("catppuccin-latte", "latte"),

    Theme('abap'),
    Theme('algol'),
    Theme('algol_nu'),
    Theme('arduino'),
    Theme('autumn'),
    Theme('bw'),
    Theme('borland'),
    Theme('coffee'),
    Theme('colorful'),
    Theme('default'),
    Theme('dracula'),
    Theme('emacs'),
    Theme('friendly_grayscale'),
    Theme('friendly'),
    Theme('fruity'),
    Theme('github-dark'),
    Theme('gruvbox-dark'),
    Theme('gruvbox-light'),
    Theme('igor'),
    Theme('inkpot'),
    Theme('lightbulb'),
    Theme('lilypond'),
    Theme('lovelace'),
    Theme('manni'),
    Theme('material'),
    Theme('monokai'),
    Theme('murphy'),
    Theme('native'),
    Theme('night-owl'),
    Theme('nord-darker'),
    Theme('nord'),
    Theme('one-dark'),
    Theme('paraiso-dark'),
    Theme('paraiso-light'),
    Theme('pastie'),
    Theme('perldoc'),
    Theme('rainbow_dash'),
    Theme('rrt'),
    Theme('sas'),
    Theme('solarized-dark'),
    Theme('solarized-light'),
    Theme('staroffice'),
    Theme('stata-dark'),
    Theme('stata-light'),
    Theme('tango'),
    Theme('trac'),
    Theme('vim'),
    Theme('vs'),
    Theme('xcode'),
    Theme('zenburn'),
]