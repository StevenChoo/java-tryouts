
// New since Java 24; https://openjdk.org/jeps/495 - Simple Source files.
void main() {
    println("Hello World"); // println module is available due to atomatic import of java.base module

    // Below is showing extra and not part of the Java "shortest" hello world example
    //
    var name = readln("Give your name:\n");
    println("Your name is: " + name);
}
