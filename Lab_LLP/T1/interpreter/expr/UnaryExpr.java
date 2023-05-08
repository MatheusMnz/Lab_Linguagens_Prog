package interpreter.expr;

import interpreter.InterpreterException;
import interpreter.value.BoolValue;
import interpreter.value.NumberValue;
import interpreter.value.Value;

public class UnaryExpr extends Expr {

    public static enum Op {
        Not,
        Pos,
        Neg,
        PreInc,
        PosInc,
        PreDec,
        PosDec
    }

    private Expr expr;
    private Op op;

    public UnaryExpr(int line, Expr expr, Op op) {
        super(line);
        this.expr = expr;
        this.op = op;
    }

    public Value<?> expr() {

        
        Value<?> value = this.expr.expr();
        switch (this.op)
        {
            case Not:
                return notOp(value);
            case Pos:
                return posOp(value);
            case Neg:
                return negOp(value);
            case PreInc:
                return preIncOp(value);
            case PosInc:
                return posIncOp(value);
            case PreDec:
                return preDecOp(value);
            case PosDec:
            default:
                return posDecOp(value);
        }
    }

    private Value<?> notOp(Value<?> v) {
        boolean b = BoolValue.convert(v);
        return new BoolValue(!b);
    }

    private Value<?> posOp(Value<?> v) {
        double n = NumberValue.convert(v);
        return new NumberValue(n);
    }

    private Value<?> negOp(Value<?> v) {
        double n = NumberValue.convert(v);
        return new NumberValue(-n);
    }

    private Value<?> preIncOp(Value<?> value){
        
       value = expr.expr();
       
       double d = NumberValue.convert(value);
       double d1 = d + 1;

       Value<?> v2 = new NumberValue(d1);

       if(expr instanceof SetExpr)
       {
        SetExpr se = (SetExpr) expr;
        se.setValue(v2);
        return v2;
       } 

       throw new InterpreterException(super.getLine());
    }

    private Value<?> posIncOp(Value<?> v) {
        v = expr.expr();
        double d = NumberValue.convert(v);
        double d1 = d + 1;
        Value<?> v1 = new NumberValue(d);
        Value<?> v2 = new NumberValue(d1);


        if (expr instanceof SetExpr) {
            SetExpr aux = (SetExpr) expr;
            aux.setValue(v2);
            return v1;
        }
        throw new InterpreterException(super.getLine());
    }

    private Value<?> preDecOp(Value<?> v) {
                
       v = expr.expr();
       double d = NumberValue.convert(v);
       double d1 = d - 1;
       Value<?> v2 = new NumberValue(d1);

       if(expr instanceof SetExpr){
        SetExpr se = (SetExpr) expr;
        se.setValue(v2);
        return v2;
       } 
       throw new InterpreterException(super.getLine());
    }

    private Value<?> posDecOp(Value<?> v) {
        v = expr.expr();
        double d = NumberValue.convert(v);
        double d1 = d - 1;
        Value<?> v1 = new NumberValue(d);
        Value<?> v2 = new NumberValue(d1);


        if (expr instanceof SetExpr) {
            SetExpr aux = (SetExpr) expr;
            aux.setValue(v2);
            return v1;
        }
        throw new InterpreterException(super.getLine());
    }
}
