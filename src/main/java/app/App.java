package app;

import views.MainMenu;

/**
 * Application class, responsible for running the system.
 * Calls on the {@link MainMenu} class.
 *
 * @author Lucas da Paz
 */
public class App {
	public static void main(String... args) {
    /*
    - Nacionalidade como ENUM;
    - refatoração:
    	- utilizar document (?) para permitir valores vazios no preço e na data de compra
      - remover atributos desnecessários;
      - deixar horizontal e vertical size como default (responsividade);
      - avaliar se deve ser utilizado preferred size
    - avaliar a utilização de interfaces para Services e Controller;
    - Testar conversão e validação de ISBN

    updating the model from a thread sounds dangerous. In my opinion, this should be done
    via invokeLater. Because the general rule is: changes to objects that are used in the
    UI may only happen in the event dispatching thread.
     */

		new MainMenu().setVisible(true);
	}
}
