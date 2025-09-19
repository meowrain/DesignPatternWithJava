package org.designpattern.calc.expression;

public class DevisionExpression extends BinaryOperatorExpression {

    public DevisionExpression(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    public int getValue() {
        return left.getValue() / right.getValue();
    }
}