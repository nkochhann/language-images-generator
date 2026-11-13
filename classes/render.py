from pathlib import Path

from pygments import highlight
from pygments.lexers import get_lexer_for_filename
from pygments.formatters import ImageFormatter
from pygments.util import ClassNotFound

from classes.theme import THEMES

from config import FONT_SIZE, FONT_NAME, IMAGE_PADDING
from utils import resizeImage

EXTENSIONS = {
    ".php": "PHP",
    ".java": "Java",
    ".go": "Go",
    ".rs": "Rust",
    ".c": "C",
    ".h": "C",
}

class Render:
    def __init__(self, input_dir: Path, output_dir: Path):
        self.input_dir = input_dir
        self.output_dir = output_dir
        self.manifest = []

    def renderCode(self, 
        source_file: Path,
        relative_path: Path,
        language: str
    ):
        try:
            code = source_file.read_text(
                encoding="utf-8",
                errors="replace"
            )
        
            if not code.strip():
                print(f"[SKIP] Arquivo vazio: {source_file}")
                return
        
            lexer = get_lexer_for_filename(str(source_file))
        
        except (OSError, ClassNotFound) as error:
            print(f"[ERROR] {source_file}: {error}")
            return

        output_folder = (self.output_dir / language)

        output_folder.mkdir(parents=True, exist_ok=True)

        for theme in THEMES:
            formatter = ImageFormatter(
                style=theme.theme,
                font_size=FONT_SIZE,
                line_numbers=False,
                image_pad=IMAGE_PADDING,
                line_pad=2
            )
        
            try:
                image = highlight(code, lexer, formatter)
        
                img = resizeImage(image)
        
                output_file = output_folder / f"{theme.foldername + source_file.stem}.png"
        
                img.save(output_file)
        
                self.manifest.append({
                    "source_id": f"{language}/{relative_path.as_posix()}",
                    "language": language,
                    "theme": theme.theme,
                    "image_path": str(output_file),
                })
        
                print(f"[OK] {output_file}")
        
            except Exception as error:
                print(f"[ERROR] {source_file} ({theme.theme}): {error}")