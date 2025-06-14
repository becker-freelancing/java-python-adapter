from unittest import TestCase

from japy.japy_file_scanner import from_relative_path, from_relative_paths


class TestJapyFileScanner(TestCase):

    def test_scan_for_decorated_methods(self):
        self.file_scanner = from_relative_path("../test_application")

        decorated_methods = self.file_scanner.scan_for_decorated_functions()

        method_names = decorated_methods.keys()

        self.assertTrue(7, len(method_names))
        self.assertTrue(method_names.__contains__("return_method_with_no_args"))
        self.assertTrue(method_names.__contains__("return_method_with_one_arg"))
        self.assertTrue(method_names.__contains__("return_method_with_two_args"))
        self.assertTrue(method_names.__contains__("return_method_with_two_args_and_two_returns"))
        self.assertTrue(method_names.__contains__("void_method_with_no_args"))
        self.assertTrue(method_names.__contains__("void_method_with_one_arg"))
        self.assertTrue(method_names.__contains__("void_method_with_two_args"))

    def test_scan_for_decorated_methods_multiple_paths(self):
        self.file_scanner = from_relative_paths(["../test_application", "../test_application_2"])

        decorated_methods = self.file_scanner.scan_for_decorated_functions()

        method_names = decorated_methods.keys()

        self.assertTrue(7, len(method_names))
        self.assertTrue(method_names.__contains__("return_method_with_no_args"))
        self.assertTrue(method_names.__contains__("return_method_with_one_arg"))
        self.assertTrue(method_names.__contains__("return_method_with_two_args"))
        self.assertTrue(method_names.__contains__("return_method_with_two_args_and_two_returns"))
        self.assertTrue(method_names.__contains__("void_method_with_no_args"))
        self.assertTrue(method_names.__contains__("void_method_with_one_arg"))
        self.assertTrue(method_names.__contains__("void_method_with_two_args"))
        self.assertTrue(method_names.__contains__("internal_function"))
