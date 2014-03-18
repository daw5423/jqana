package teste2;

public class ClassWithRecursion {
    public boolean method1(int a, int b) {
        int c = method2(a);
        return true;
    }
    
    public int method2(int z) {
        return method3(z);
    }
    
    public int method3(int x) {
        int val = x--;
        if (val <= 0) {
            return val;
        }
        else {
            val = method3(val);
        }
        return val;
    }
}