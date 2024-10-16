import logging

from japy.japy_file_scanner import from_relative_path
from japy.japy_server import get_japy_server_manager

logging.basicConfig(
    level=logging.DEBUG,
    format='%(asctime)s - %(name)s - %(levelname)s - %(message)s'
)

server_manager = get_japy_server_manager(from_relative_path("."))
server_manager.start_japy_server()