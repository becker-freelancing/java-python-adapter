from japy.japy_method import japy_method


@japy_method
def void_method_with_no_args():
    print("void_method_with_no_args")


@japy_method
def void_method_with_one_arg(name):
    print(f"void_method_with_one_arg\tARG: {name}")


@japy_method
def void_method_with_two_args(name, name_2):
    print(f"void_method_with_one_arg\tARG: {name}, {name_2}")


@japy_method
def return_method_with_no_args():
    print("void_method_with_no_args")
    return "TEST"


@japy_method
def return_method_with_one_arg(name):
    print(f"void_method_with_one_arg\tARG: {name}")
    return name


@japy_method
def return_method_with_two_args(name, name_2):
    print(f"void_method_with_one_arg\tARG: {name}, {name_2}")
    return name


@japy_method
def return_method_with_two_args_and_two_returns(name, name_2):
    print(f"void_method_with_one_arg\tARG: {name}, {name_2}")
    return name, name_2
