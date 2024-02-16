def add(*args):
    return sum(args)

def subtract(*args):
    result = args[0]
    for num in args[1:]:
        result -= num
    return result

def multiply(*args):
    result = 1
    for num in args:
        result *= num
    return result

def divide(*args):
    result = args[0]
    for num in args[1:]:
        if num == 0:
            return "Error! Division by zero."
        result /= num
    return result

def main():
    operation = input("Choose operation (+, -, *, /): ")
    while operation not in ['+', '-', '*', '/']:
        print("Unknown operation. Please try again.")
        operation = input("Choose operation (+, -, *, /): ")

    numbers = []
    while True:
        try:
            number = float(input("Enter a number (or leave blank to finish): "))
            numbers.append(number)
        except ValueError:
            break

    if operation == '+':
        result = add(*numbers)
    elif operation == '-':
        result = subtract(*numbers)
    elif operation == '*':
        result = multiply(*numbers)
    elif operation == '/':
        result = divide(*numbers)

    print("Result:", result)

if __name__ == "__main__":
    main()
