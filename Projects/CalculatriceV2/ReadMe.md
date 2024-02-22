## 1. Introduction
The objective of the project is to create a fully functional calculator app for Android devices. The app allows users to perform various mathematical operations conveniently, including basic calculations and complex scientific functions.

## 2. UI Design
The UI design involves creating layouts using XML files, which are placed in the res/layout folder. Separate layouts are provided for portrait and landscape orientations, ensuring optimal user experience. In portrait mode, the UI consists of a TextView to display user input and results, along with a layout containing numeric buttons and basic operators. In landscape mode, additional sections are included for scientific operators.

## 3. Logical Processing
The logical processing of the application is divided into two main sections:

- **Expression Handling**: A dedicated class, ExpressionEvaluator, handles the processing of mathematical expressions. It parses user input, evaluates expressions using stacks for numerical values and operators/functions, and applies various mathematical operations, including both basic and scientific functions.
  
- **Button Behavior**: The MainActivity class manages button interactions. Methods such as onButtonClick and evaluateExpression handle button clicks, appending input to the expression and evaluating expressions, respectively. Error handling is implemented to manage arithmetic errors and other exceptions.
