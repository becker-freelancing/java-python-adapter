import os
import inspect
import importlib.util
from japy.method import Method


class JapyFileScanner:
    __root_dir: str

    def __init__(self, base_dir_path):
        self.__root_dir = base_dir_path

    def __find_python_files(self, root_package):
        python_files = []
        for dirpath, _, filenames in os.walk(root_package):
            for filename in filenames:
                if filename.endswith('.py'):
                    full_path = os.path.join(dirpath, filename)
                    python_files.append(full_path)
        return python_files

    def __load_module_from_file(self, filepath):
        module_name = os.path.splitext(os.path.basename(filepath))[0]
        spec = importlib.util.spec_from_file_location(module_name, filepath)
        module = importlib.util.module_from_spec(spec)
        spec.loader.exec_module(module)
        return module

    def __find_decorated_methods(self, module):
        decorated_methods = []
        for name, func in inspect.getmembers(module, inspect.isfunction):
            if hasattr(func, 'is_japy_method'):
                decorated_methods.append((name, func))
        return decorated_methods

    def __parse_method(self, func):
        signature = inspect.signature(func)
        parameters = signature.parameters
        return Method(func, parameters)

    def scan_for_decorated_methods(self):
        python_files = self.__find_python_files(self.__root_dir)
        all_decorated_methods = {}

        for filepath in python_files:
            try:
                module = self.__load_module_from_file(filepath)
                decorated_methods = self.__find_decorated_methods(module)
                if decorated_methods:
                    for name, func in decorated_methods:
                        all_decorated_methods[name] = self.__parse_method(func)
            except Exception as e:
                print(f"Fehler beim Laden von {filepath}: {e}")

        return all_decorated_methods


def from_relative_path(relative_to) -> JapyFileScanner:
    """
    Builds a JapyFileScanner from a path relative to the path of the caller file.

    Example:
        - Caller filepath: /home/python/src/japy/scanner/scanner.py
        - relative_to: ../../methods
        -> /home/python/src/methods/
    """
    frame = inspect.currentframe()
    caller_frame = frame.f_back
    caller_file_path = caller_frame.f_code.co_filename
    base_dir = os.path.dirname(caller_file_path)
    base_dir = os.path.normpath(os.path.join(base_dir, relative_to))
    return JapyFileScanner(base_dir)
