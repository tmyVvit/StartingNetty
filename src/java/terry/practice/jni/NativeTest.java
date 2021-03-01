package terry.practice.jni;

public class NativeTest {
    public native int test(int i);
    public static void main(String[] args) {
        // System.loadLibrary("test") 在 Mac OS系统中load的是java.library.path路径下名字为 libtest.jnilib/libtest.dylib的文件
        System.loadLibrary("test");
        System.out.println(new NativeTest().test(3));
    }
    /*
        在当前目录使用 javac -h . NativeTest.java 编译，并生成C/C++头文件
        生成的头文件中有对应方法的声明
        新建文件 helloTest.c，编写c代码实现对应功能
        使用命令 gcc -I"$JAVA_HOME/include" -I"$JAVA_HOME/include/darwin/include" -dynamiclib -o libtest.dylib helloTest.c 编译c代码并生成动态库文件 libtest.dylib
        返回目录 ~/Public/project/StartingNetty/src/java，使用命令 java -Djava.library.path=./terry/practice/jni terry.practice.jni.NativeTest 运行

     */
}