import time
from unittest import TestCase
from unittest.mock import MagicMock
from japy.japy_file_scanner import JapyFileScanner
from japy.japy_server import get_japy_server_manager
import threading


class TestJapyServer(TestCase):

    def test_server_starts(self):
        japy_file_scanner = MagicMock(JapyFileScanner)

        server_manager = get_japy_server_manager(japy_file_scanner)

        server_manager.start_japy_server()

        server_manager.stop_japy_server()
