from japy.japy_server import get_japy_server_manager
from japy.japy_file_scanner import from_relative_path

server_manager = get_japy_server_manager(from_relative_path("."))
server_manager.start_japy_server()