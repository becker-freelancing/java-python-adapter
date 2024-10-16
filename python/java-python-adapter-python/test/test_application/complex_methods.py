from japy.japy_function import japy_function


class ComplexBaseClass:

    def __init__(self, arg3):
        self.arg1 = "Arg1"
        self.arg2 = "Arg2"
        self.arg3 = arg3


class ComplexSubClass(ComplexBaseClass):

    def __init__(self, arg4, arg3="TEST", inner_complex=None):
        super().__init__(arg3)
        self.arg4 = arg4
        self.inner_complex = inner_complex


@japy_function
def create_complex_base_class():
    return ComplexBaseClass("func")


@japy_function
def create_complex_sub_class():
    return ComplexSubClass("abc")


@japy_function
def create_complex_sub_class_with_param(arg="abc2"):
    return ComplexSubClass(arg)


@japy_function
def create_complex_classes():
    return create_complex_base_class(), create_complex_sub_class()


@japy_function
def create_complex_sub_class_with_inner_class():
    return ComplexSubClass("abc3", inner_complex=create_complex_base_class())
