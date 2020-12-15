import java.io.IOException;

public class runRscript {
    public static void exec (String inputScriptRelativePath) {
        try {
            Runtime.getRuntime().exec("E:\\R-4.0.0\\bin\\Rscript.exe "+ inputScriptRelativePath).getOutputStream();
            // TODO : add print exec Runtime trace (see : https://stackoverflow.com/questions/5711084/java-runtime-getruntime-getting-output-from-executing-a-command-line-program)
        }
        catch (IOException io) {
            System.out.println(io+ " in class runRscript in method exec");
        }
    }
}
