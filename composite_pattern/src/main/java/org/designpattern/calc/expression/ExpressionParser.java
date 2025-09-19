package org.designpattern.calc.expression;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * @author gongxuanzhangmelt@gmail.com
 **/
public class ExpressionParser {

    private final String infixExpression;
    int point = 0;

    public ExpressionParser(String infixExpression) {
        this.infixExpression = infixExpression;
    }

    public List<String> toSuffix() {
        List<String> suffix = new ArrayList<>();
        LinkedList<String> stack = new LinkedList<>();

        while (point < infixExpression.length()) {
            char c = infixExpression.charAt(point);
            if (c == '(') {
                stack.addLast(c + "");
            } else if (c == ')') {
                while (!stack.getLast().equals("(")) {
                    suffix.add(stack.removeLast());
                }
                stack.removeLast();
            } else if (c == '*' || c == '/') {
                while ((!stack.isEmpty()) && (stack.getLast().equals("*") || stack.getLast().equals("/"))) {
                    suffix.add(stack.removeLast());
                }
                stack.addLast(c + "");
            } else if (c == '+' || c == '-') {
                while (topIsOperator(stack)) {
                    suffix.add(stack.removeLast());
                }
                stack.addLast(c + "");
            } else if (Character.isDigit(c)) {
                StringBuilder stringBuilder = new StringBuilder();
                while (point < infixExpression.length() && Character.isDigit(infixExpression.charAt(point))) {
                    stringBuilder.append(infixExpression.charAt(point));
                    point++;
                }
                point--;
                suffix.add(stringBuilder.toString());
            } else {
                throw new IllegalStateException("非法字符！");
            }
            point++;
        }
        while (!stack.isEmpty()) {
            suffix.add(stack.removeLast());
        }

        return suffix;
    }

    public Expression parse() {
        List<String> suffix = this.toSuffix();
        LinkedList<Expression> stack = new LinkedList<>();
        for (String item : suffix) {
            if (item.equals("+")) {
                Expression right = stack.removeLast();
                stack.addLast(new AddExpression(stack.removeLast(), right));
            } else if (item.equals("-")) {
                Expression right = stack.removeLast();
                stack.addLast(new SubstractExpression(stack.removeLast(), right));
            } else if (item.equals("*")) {
                Expression right = stack.removeLast();
                stack.addLast(new MultiplyExpression(stack.removeLast(), right));
            } else if (item.equals("/")) {
                Expression right = stack.removeLast();
                stack.addLast(new DevisionExpression(stack.removeLast(), right));
            } else {
                int value = Integer.parseInt(item);
                stack.addLast(new NumberExpression(value));
            }
        }
        return stack.getLast();
    }


    private boolean topIsOperator(LinkedList<String> stack) {
        if (stack.isEmpty()) {
            return false;
        }
        return Set.of("+", "-", "*", "/").contains(stack.getLast());
    }

}