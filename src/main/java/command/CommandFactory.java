package command;

/**
 * Фабрика создания Command
 * @author SergeyK
 */
public class CommandFactory {
	
	private static CommandFactory instance;
	
	private  CommandFactory() {
	}
	
	public static CommandFactory getInstance() {
		if (instance == null) {
			instance = new CommandFactory();
		}
		return instance;		
	}
	
	/**
	 * Получить Command
	 * @param command вид команда
	 * @return Command
	 */
	public Command getCommand(Commands command) {
		switch(command) {
			case LOGIN:
				return new LoginCommand();
			case REGISTRATION:
				return new RegistrationCommand();
			case GOODS:
				return new GoodsCommand();
			case CHECK:
				return new CheckCommand();
			case CHECKSPEC:
				return new CheckSpecCommand();
			case CANCEL:
				return new CancelCommand();
			default:
				return null;		
		}
	}
	
	public enum Commands {
		LOGIN, REGISTRATION, GOODS, CHECK, CANCEL, CHECKSPEC
	}
}
