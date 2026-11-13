from pathlib import Path
from classes.uploader import Uploader

# NÃO UTILIZAR O ARQUIVO AINDA

# Configuração
uploader = Uploader()
uploader.loadProject("miworkspace", "Identificador de Linguagens")

DATASET_DIR = Path("dataset")

# 10 trechos para train, 2 para validation e 3 para test

# Percorre as pastas de classes
for class_dir in DATASET_DIR.iterdir():

    if not class_dir.is_dir():
        continue

    class_name = class_dir.name

    # Percorre as imagens da classe
    for image in class_dir.iterdir():

        if uploader.isSupportedExtension(image.suffix.lower()):
            continue

        try:
            uploader.uploadImage(image, class_name)

            print(f"Enviado: {image.name} [{class_name}]")

        except Exception as e:
            print(f"Erro em {image.name}: {e}")

print("Upload finalizado!")
