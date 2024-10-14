# functions.py
import math

def square_root_and_greet(number, name):
    sqrt_value = math.sqrt(number)
    greeting = greet(name)
    return f"{greeting}, the square root of {number} is {sqrt_value}"

def greet(name):
    return f"Hello, {name}!"
