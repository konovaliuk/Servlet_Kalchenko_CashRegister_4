package command;

/**
 * @author SergeyK
 */
public final class CommandFactory {
	
	private static CommandFactory instance;
	
	private  CommandFactory() {
	}
	
	public static CommandFactory getInstance() {
		if (instance == null) {
			instance = new CommandFactory();
		}
		return instance;		
	}
	
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
			case CANCEL:
				return new CancelCommand();
			default:
				return null;		
		}
	}
	
	public enum Commands {
		LOGIN, REGISTRATION, GOODS, CHECK, CANCEL
	}
}
