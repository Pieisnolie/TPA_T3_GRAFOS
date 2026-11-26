import controller.FilmesController;
import java.nio.file.Path;
import view.ConsoleView;

public class Main {

    public static void main(String[] args) {
        String nomeArquivo = "dados/filmes.txt";

        if (args.length > 0) {
            nomeArquivo = args[0];
        }

        ConsoleView view = new ConsoleView();
        FilmesController controller = new FilmesController(view);

        controller.executar(Path.of(nomeArquivo));
    }
}
