# Language Images Generator

Generate syntax-highlighted PNG images from source files, using Catppuccin themes.

## What it does

- Scans the `source/` directory recursively.
- Picks supported files by extension.
- Renders one image per theme for each source file.
- Writes outputs under `dataset/`.
- Writes a CSV manifest at `dataset/manifest.csv`.

## Supported extensions

- `.c`, `.h` -> C
- `.rs` -> Rust
- `.go` -> Go
- `.java` -> Java
- `.php` -> PHP

## Themes generated

- mocha
- macchiato
- frappe
- latte

## Requirements

- Python 3.10+ (recommended)
- Dependencies in `requirements.txt`
- Font installed on your system:
  - `JetBrainsMonoNL Nerd Font Mono`

If the font is missing, image generation may fail depending on your environment.

## Install

```bash
python -m venv .venv
source .venv/bin/activate
pip install -r requirements.txt
```

## Usage

1. Put your code files inside `source/` (you can use nested folders).
2. Run:

```bash
python generator.py
```

3. Check generated images inside `dataset/` and metadata in `dataset/manifest.csv`.

## Output structure

For an input file like:

```text
source/c/git1.c
```

The generator creates:

```text
dataset/C/c/git1/mocha.png
dataset/C/c/git1/macchiato.png
dataset/C/c/git1/frappe.png
dataset/C/c/git1/latte.png
```

And one CSV row per generated image in `dataset/manifest.csv` with:

- `source_id`: language + relative source path (example: `C/c/git1.c`)
- `language`: normalized language name (example: `C`)
- `theme`: full theme id used by Pygments (example: `catppuccin-mocha`)
- `image_path`: output image path

## Notes

- Empty files are skipped.
- Unsupported extensions are ignored.
- If lexer detection or image rendering fails for a file/theme, the error is logged and processing continues.

## Customize

Edit constants in `generator.py`:

- `THEMES`: set which Catppuccin variants to render
- `SUPPORTED_EXTENSIONS`: map file extensions to language labels
- `FONT_NAME`, `FONT_SIZE`, `IMAGE_PADDING`: control visual style
