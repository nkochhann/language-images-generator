from pathlib import Path
import csv

from classes.render import Render

# Diretórios
INPUT_DIR = Path("source")
OUTPUT_DIR = Path("dataset")

render = Render(INPUT_DIR, OUTPUT_DIR)

# Extensões aceitas
SUPPORTED_EXTENSIONS = {
    ".php": "PHP",
    ".java": "Java",
    ".go": "Go",
    ".rs": "Rust",
    ".c": "C",
    ".h": "C",
}

def main():
    if not INPUT_DIR.exists():
        print(f"[ERROR] Diretório não encontrado: {INPUT_DIR}")
        return

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

        render.renderCode(source_file, relative_path, language)

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
        writer.writerows(render.manifest)

    print("\nProcessamento concluído!")
    print(f"Imagens geradas: {len(render.manifest)}")
    print(f"Manifest: {manifest_file}")


if __name__ == "__main__":
    main()