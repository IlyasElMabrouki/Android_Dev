package com.example.first_app;

import java.util.Stack;

public class ExpressionEvaluator {

    public static double evaluate(String expression) {
        char[] tokens = expression.toCharArray();
        Stack<Double> values = new Stack<>();
        Stack<String> operators = new Stack<>();

        for (int i = 0; i < tokens.length; i++) {
            if (tokens[i] == ' ')
                continue;

            if (Character.isDigit(tokens[i]) || tokens[i] == '.') {
                StringBuilder sb = new StringBuilder();
                while (i < tokens.length && (Character.isDigit(tokens[i]) || tokens[i] == '.'))
                    sb.append(tokens[i++]);
                i--;
                values.push(Double.parseDouble(sb.toString()));
            } else if (Character.isLetter(tokens[i])) {
                StringBuilder sb = new StringBuilder();
                while (i < tokens.length && Character.isLetter(tokens[i]))
                    sb.append(tokens[i++]);
                i--;
                operators.push(sb.toString());
            } else if (tokens[i] == '(')
                operators.push("(");
            else if (tokens[i] == ')') {
                while (!operators.peek().equals("("))
                    applyOperator(operators.pop(), values);
                operators.pop(); // Remove '('
            } else if (tokens[i] == '+' || tokens[i] == '-' || tokens[i] == '*' || tokens[i] == '/') {
                while (!operators.empty() && hasPrecedence(tokens[i], operators.peek()))
                    applyOperator(operators.pop(), values);
                operators.push(String.valueOf(tokens[i]));
            }
        }

        while (!operators.empty())
            applyOperator(operators.pop(), values);

        return values.pop();
    }

    private static void applyOperator(String op, Stack<Double> values) {
        if (op.equals("("))
            return;

        // Check if the operator is a unary operation
        boolean isUnary = op.equals("sin") || op.equals("cos") || op.equals("tan") ||
                op.equals("ln") || op.equals("sqrt") || op.equals("log") ||
                op.equals("e") || op.equals("rad") || op.equals("%") || op.equals("1/x");

        if (isUnary) {
            if (values.isEmpty())
                throw new IllegalArgumentException("Insufficient operands for unary operator: " + op);

            double a = values.pop();
            values.push(applyScientificOperator(op, a));
        } else {
            // Binary operation
            if (values.size() < 2)
                throw new IllegalArgumentException("Insufficient operands for binary operator: " + op);

            double b = values.pop();
            double a = values.pop();
            switch (op) {
                case "+":
                    values.push(a + b);
                    break;
                case "-":
                    values.push(a - b);
                    break;
                case "*":
                    values.push(a * b);
                    break;
                case "/":
                    if (b == 0)
                        throw new ArithmeticException("Division by zero");
                    values.push(a / b);
                    break;
                default:
                    throw new IllegalArgumentException("Unknown operator: " + op);
            }
        }
    }

    private static boolean hasPrecedence(char op1, String op2) {
        if (op2.equals("(") || op2.equals(")"))
            return false;
        if ((op1 == '*' || op1 == '/') && (op2.equals("+") || op2.equals("-")))
            return false;
        return true;
    }

    private static double applyScientificOperator(String operator, double value) {
        switch (operator) {
            case "sin":
                return Math.sin(Math.toRadians(value));
            case "cos":
                return Math.cos(Math.toRadians(value));
            case "tan":
                return Math.tan(Math.toRadians(value));
            case "ln":
                if (value <= 0)
                    throw new ArithmeticException("ln(x) is undefined for x <= 0");
                return Math.log(value);
            case "e":
                return Math.exp(1);
            case "sqrt":
                if (value < 0)
                    throw new ArithmeticException("sqrt(x) is undefined for x < 0");
                return Math.sqrt(value);
            case "log":
                if (value <= 0)
                    throw new ArithmeticException("log(x) is undefined for x <= 0");
                return Math.log10(value);
            case "rad":
                return Math.toRadians(value);
            case "%":
                return value / 100;
            case "1/x":
                if (value == 0)
                    throw new ArithmeticException("Division by zero");
                return 1 / value;
            default:
                throw new IllegalArgumentException("Unknown operator: " + operator);
        }
    }
}
