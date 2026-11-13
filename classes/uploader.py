from roboflow import Roboflow

from dotenv import load_dotenv
import os
from pathlib import Path

# load_dotenv()

EXTENSIONS = {".png"}
API_KEY = os.getenv("API_KEY")

class Uploader(Roboflow):
    def __init__(self):
        super()

        self.api_key = API_KEY
        self.project = None

    def loadProject(self, workspace: str, project: str):
        self.project = self.workspace(workspace).project(project)

    def uploadImage(self, image: Path, class_name: str):
        self.project.upload(
            image_path=str(image),
            annotation_path=class_name,
            split="train"
        )

    def isSupportedExtension(self, suffix: str):
        return suffix in EXTENSIONS