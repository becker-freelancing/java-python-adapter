from japy.method_return_value import MethodReturnValue


class JapyFunctionWrapper:

    def __init__(self, function_name, function_locals, ast_function):
        self.function_name = function_name
        self.function_locals = function_locals
        self.ast_function = ast_function

    def map_parameters(self, parameters):
        return [param['argument'] for param in parameters]

    def call_method(self, parameters=[]):
        if len(parameters) == 0:
            return_value = self.function_locals[self.function_name]()
        else:
            return_value = self.function_locals[self.function_name](*self.map_parameters(parameters))
        return MethodReturnValue(return_value)
