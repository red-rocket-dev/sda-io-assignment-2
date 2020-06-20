package pl.sda.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) throws IOException {
        /*
        1. Znajdz w folderze my-files pliki, które zawierają słowo "coś" i wypisz nazwy tych plikow na konsole
        1*. Używając wyrażenia regularnego poza nazwa pliku wypisz też słowo występujące po "coś" (wystarczy po pierwszym wystąpieniu)
        2. Poza wypisaniem nazw plików wypisz też ilość wystąpień tego słowa w danym pliku w formacie <nazwa>:<ilość wystąpień>
        3. Zamiast wypisywać na konsolę zapis rezultat do pliku
         */


        //Animals.ofSound("miaumiau") -> new Cat();
        //Animals.ofSound("wodwof") -> new Dog();
        // "ich-troje-lyrics:10";


        // 2.
        String[] filesNames = {"my-files/ich-troje-lyrics.txt", "my-files/otherfile.txt", "my-files/somefile", "my-files/somefile.txt", "my-files/this-one-is-empty.txt"};
        for (int i = 0; i < filesNames.length; i++) {
            String lookingFor = "coś";
            Path filePath = Path.of(filesNames[i]);
            int counter = countWord(lookingFor, filePath);
            System.out.println(filePath.getFileName() + ": " + counter);
        }

        findGivenWordAndWordAfter("coś");


    }

    private static int countWord(String lookingFor, Path filePath) throws IOException {
        String contents = Files.readString(filePath);
        int counter = 0;
        String[] fileSplit = contents.split(" ");
        for (String word : fileSplit) {
            if (word.equalsIgnoreCase(lookingFor)) {
                counter++;
            }
        }
        return counter;
    }


    private static void findGiveWordInFiles(String givenWord) {
        try {
            //Jak czytac nazwy plikow z folderu
            List<Path> filePaths = Files.list(Paths.get("my-files")).collect(Collectors.toList());
            for (Path path : filePaths) {
                BufferedReader bufferedReader = Files.newBufferedReader(path);
                String content;
                while (bufferedReader.ready()) {
                    content = bufferedReader.readLine();
                    if (content.matches(" " + givenWord + "|" + givenWord + " ")) {
                        // coś|coś
                        System.out.println(path);
                        break;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void findGivenWordAndWordAfter(String givenWord) throws IOException {
        Pattern pattern = Pattern.compile(".* (" + givenWord + " +\\w+" + ").*");
        List<Path> filePaths = Files.list(Paths.get("my-files")).collect(Collectors.toList());
        for (Path path : filePaths) {
            BufferedReader bufferedReader = Files.newBufferedReader(path);
            String content;
            //(coś +\\w+)
            Matcher matcher;
            while (bufferedReader.ready()) {
                content = bufferedReader.readLine();
                if (content.contains(givenWord)) {
                    matcher = pattern.matcher(content);
                    if (matcher.matches()) {
                        System.out.println(path + " - " + matcher.group(1));
                        break;
                    }
                }
            }
        }

    }
}
