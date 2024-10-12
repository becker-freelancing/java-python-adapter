class MethodReturnValue:

    def __init__(self, value=None):
        self.return_value = value

    def to_dict(self):
        return {
            "returnValue": self.return_value
        }
