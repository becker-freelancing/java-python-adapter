from japy.method_return_value import MethodReturnValue

class Method:

    def __init__(self, method, parameters):
        self.parameters = parameters
        self.method = method

    def map_parameters(self, parameters):
        return [param['argument'] for param in parameters]

    def call_method(self, parameters=[]):
        return_value = None
        if len(parameters) == 0:
            return_value = self.method()
        else:
            return_value = self.method(*self.map_parameters(parameters))
        return MethodReturnValue(return_value)