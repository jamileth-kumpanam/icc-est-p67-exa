-injars lib/validaciones-temp.jar
-outjars lib/validadores-evaluacion.jar

-libraryjars /Library/Java/JavaVirtualMachines/temurin-18.jdk/Contents/Home/lib/modules
-libraryjars lib/junit-jupiter-api-5.7.0.jar
-libraryjars lib/hamcrest-core-1.3.jar
-libraryjars lib/apiguardian-api-1.1.0.jar

-keepattributes StackMapTable, LineNumberTable, LocalVariableTable, LocalVariableTypeTable, Signature, SourceFile, EnclosingMethod, InnerClasses, Exceptions, Deprecated

-dontwarn java.**
-dontwarn javax.**
-dontwarn org.junit.**
-dontwarn models.**
-ignorewarnings

-dontoptimize
-dontpreverify
-overloadaggressively

-keep public class validaciones.ValidacionesMaquina {
    public static <methods>;
}

-keep public class models.Maquina {
    public *;
}


