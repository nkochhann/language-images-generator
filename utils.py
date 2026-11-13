from PIL import Image
from io import BytesIO

from config import TRANSFORM_SIZE

def resizeImage(image) -> Image:
    img = Image.open(BytesIO(image))
    
    new_size = TRANSFORM_SIZE
    
    img = img.resize(new_size, Image.Resampling.LANCZOS)
    
    return img