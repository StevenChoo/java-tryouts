package dev.choo;

import com.google.errorprone.annotations.Var;

/**
 * Hello world!
 *
 */
public class App
{
    public static void main( String[] args )
    {
        var test = "test";
        test = "changed"; // Should fail Error prone
        System.out.println( test );


        System.out.println( "Hello World!" );
    }
}
