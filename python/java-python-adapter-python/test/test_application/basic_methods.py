from japy.japy_function import japy_function


@japy_function
def void_method_with_no_args():
    print("void_method_with_no_args")


@japy_function
def void_method_with_one_arg(name):
    print(f"void_method_with_one_arg\tARG: {name}")


@japy_function
def void_method_with_two_args(name, name_2):
    print(f"void_method_with_one_arg\tARG: {name}, {name_2}")


@japy_function
def return_method_with_no_args():
    print("void_method_with_no_args")
    return "TEST"


@japy_function
def return_method_with_one_arg(name):
    print(f"void_method_with_one_arg\tARG: {name}")
    return name


@japy_function
def return_method_with_two_args(name, name_2):
    print(f"void_method_with_one_arg\tARG: {name}, {name_2}")
    return name


@japy_function
def return_method_with_two_args_and_two_returns(name, name_2):
    print(f"void_method_with_one_arg\tARG: {name}, {name_2}")
    return name, name_2
