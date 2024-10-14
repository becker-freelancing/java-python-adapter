# main.py
import ast
import importlib

# Schritt 1: Quellcode aus der Datei laden
with open("functions.py", "r") as file:
    source_code = file.read()

# Schritt 2: Den Quellcode in einen AST umwandeln
tree = ast.parse(source_code)

# Schritt 3: Extrahiere die Importe und die Funktionen
func_locals = {}

# Importiere Module aus dem AST
for node in tree.body:
    if isinstance(node, ast.Import):
        for alias in node.names:
            module_name = alias.name
            func_locals[module_name] = importlib.import_module(module_name)
    elif isinstance(node, ast.ImportFrom):
        module_name = node.module
        for alias in node.names:
            func_locals[alias.name] = importlib.import_module(module_name)

# Schritt 4: Finde alle Funktionsdefinitionen und füge sie zum lokalen Namensraum hinzu
for node in tree.body:
    if isinstance(node, ast.FunctionDef):
        # Kompiliere die Funktion in einen Codeblock
        func_code = compile(ast.Module(body=[node], type_ignores=[]), filename="<ast>", mode="exec")
        exec(func_code, func_locals)

# Schritt 5: Die Funktion aufrufen
result = func_locals['square_root_and_greet'](16, 'ChatGPT')
print(result)
