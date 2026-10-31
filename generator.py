
from pathlib import Path
import csv

from pygments import highlight
from pygments.lexers import get_lexer_for_filename
from pygments.formatters import ImageFormatter
from pygments.util import ClassNotFound


# Diretórios
INPUT_DIR = Path("source")
OUTPUT_DIR = Path("dataset")

# Temas Catppuccin
THEMES = [
    "catppuccin-mocha",
    "catppuccin-macchiato",
    "catppuccin-frappe",
    "catppuccin-latte"
]

# Extensões aceitas
SUPPORTED_EXTENSIONS = {
    ".php": "PHP",
    ".java": "Java",
    ".go": "Go",
    ".rs": "Rust",
    ".c": "C",
    ".h": "C",
}

# Configurações visuais
FONT_NAME = "JetBrainsMonoNL Nerd Font Mono"
FONT_SIZE = 16
IMAGE_PADDING = 20


def render_code(
    source_file: Path,
    relative_path: Path,
    language: str,
    manifest_rows: list,
):
    """Gera uma imagem do código para cada tema."""

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

    # Cada arquivo original possui sua própria pasta.
    output_folder = (
        OUTPUT_DIR
        / language
        / relative_path.parent
        / source_file.stem
    )

    output_folder.mkdir(parents=True, exist_ok=True)

    for theme in THEMES:
        formatter = ImageFormatter(
            style=theme,
            font_name=FONT_NAME,
            font_size=FONT_SIZE,
            line_numbers=False,
            image_pad=IMAGE_PADDING,
            line_pad=2
        )

        try:
            image = highlight(code, lexer, formatter)

            output_file = output_folder / f"{theme.removeprefix('catppuccin-')}.png"

            output_file.write_bytes(image)

            manifest_rows.append({
                "source_id": f"{language}/{relative_path.as_posix()}",
                "language": language,
                "theme": theme,
                "image_path": str(output_file),
            })

            print(f"[OK] {output_file}")

        except Exception as error:
            print(f"[ERROR] {source_file} ({theme}): {error}")


def main():
    if not INPUT_DIR.exists():
        print(f"[ERROR] Diretório não encontrado: {INPUT_DIR}")
        return

    manifest_rows = []

    files = sorted(INPUT_DIR.rglob("*"))

    for source_file in files:
        if not source_file.is_file():
            continue

        language = SUPPORTED_EXTENSIONS.get(
            source_file.suffix.lower()
        )

        if language is None:
            continue

        relative_path = source_file.relative_to(INPUT_DIR)

        render_code(
            source_file=source_file,
            relative_path=relative_path,
            language=language,
            manifest_rows=manifest_rows,
        )

    # Salva um CSV para identificar a origem de cada imagem.
    manifest_file = OUTPUT_DIR / "manifest.csv"
    OUTPUT_DIR.mkdir(parents=True, exist_ok=True)

    with manifest_file.open("w", newline="", encoding="utf-8") as file:
        writer = csv.DictWriter(
            file,
            fieldnames=[
                "source_id",
                "language",
                "theme",
                "image_path",
            ],
        )

        writer.writeheader()
        writer.writerows(manifest_rows)

    print("\nProcessamento concluído!")
    print(f"Imagens geradas: {len(manifest_rows)}")
    print(f"Manifest: {manifest_file}")


if __name__ == "__main__":
    main()