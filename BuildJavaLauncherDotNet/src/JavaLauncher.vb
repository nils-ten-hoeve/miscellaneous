Module JavaLauncher
    'Writen by Nils ten Hoeve on 17-4-2007
    '
    'This project creates a simple executable that starts a jar with the same name as the executable.
    'The JavaLauncher will try to start the jar with command: java -jar JavaLauncher.jar
    '
    'Note that:
    '- the jar file must have a manifest file with main-class and class-path variables
    '- all required jar files need to be in the same directory as the JavaLauncher.exe
    '- you need to rename this executable to the name of the executable jar file. I.e.:
    '  if the jar file is named MyApp.jar then rename JavaLauncher.exe to MyApp.exe 

    Sub main()

        Dim executablePath As String = Application.ExecutablePath
        Dim jarPath As String = Application.ExecutablePath.Replace("exe", "jar")
        Dim beginPos As Integer = executablePath.LastIndexOf("\") + 1
        Dim endPos As Integer = executablePath.LastIndexOf(".")
        Dim applicationName = executablePath.Substring(beginPos, endPos - beginPos)

        'check if jar file exists
        If Not System.IO.File.Exists(jarPath) Then
            MsgBox("Could not find " + jarPath, MsgBoxStyle.Critical, applicationName)
            End
        End If

        'shell
        Dim command = "java -jar """ + jarPath + ""
        Try
            Dim ProcID = Shell(command, AppWinStyle.Hide)
        Catch exception As Exception
            MsgBox("Error executing:" + command, MsgBoxStyle.Critical, applicationName)
            End
        End Try

    End Sub
End Module
