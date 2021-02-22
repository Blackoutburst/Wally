package commands;

import core.Command;
import core.CommandExecutable;
import utils.MessageSender;


public class CommandConvert extends CommandExecutable {

	private enum Type {
		Q,
		F
	}
	
	public CommandConvert(Command command, boolean admin, String errorMessage) {
		super(command, admin, errorMessage);
	}

	@Override
	protected boolean execute() {
		if (command.getArgs().length == 0) return (badUsage());
		if (command.getArgs().length == 1) splitArg();
		
		Type type;
		int typeArg = getTypeArgIndex();
		double value = 0;
		
		if (typeArg == -1) return (badUsage());
		switch(command.getArgs()[typeArg].toLowerCase()) {
			case "q": type = Type.Q; break;
			case "f": type = Type.F; break;
			default: return (badUsage());
		}
		try {
			value = Integer.valueOf(command.getArgs()[Math.abs(typeArg-1)]);
		} catch(Exception e) {
			return (badUsage());
		}
		
		switch(type) {
			case Q: displayQualificationValue(value); break;
			case F: displayFinalValue(value); break;
		}
		
		return (true);
	}
	
	/**
	 * If both argument come in one, separate them
	 */
	private void splitArg() {
		String arg1 = command.getArgs()[0].substring(0, command.getArgs()[0].length()-1);
		String arg2 = String.valueOf(command.getArgs()[0].charAt(command.getArgs()[0].length()-1));
		String[] rgs = {arg1, arg2};
		
		command.setArgs(rgs);
	}
	
	/**
	 * Show final score conversion
	 * @param value
	 */
	private void displayFinalValue(double value) {
		double a = 0.00166673145332d;
		double b = 0.751487776094d;
		double RF = 0;
		double F = value;
		int Q = 0;
		boolean searching = true;
		
		if (F < 0 || F > 500) {
			MessageSender.messageMention(command, "Value must be between 0 and 500");
			return;
		}
		
		while (searching) {
			for (Q = 0; Q < 401; Q++) {
				RF = b*((a*Math.pow(Q, 2))+(1-a)*Q);
				if (Math.round(RF) == Math.round(F)) {
					searching = false;
					break;
				}
			}
			F--;
		}
		MessageSender.message(command, Math.round(value) + "F compares to **" + Math.round(Q) + "**Q");
	}
	
	/**
	 * Show qualification score conversion
	 * @param value
	 */
	private void displayQualificationValue(double value) {
		double a = 0.00166673145332d;
		double b = 0.751487776094d;
		double Q = value;
		double F = 0;
		
		if (Q < 0 || Q > 400) {
			MessageSender.messageMention(command, "Value must be between 0 and 400");
			return;
		}
		
		F = b*((a*Math.pow(Q, 2))+(1-a)*Q);
		MessageSender.message(command, Math.round(Q) + "Q compares to **" + Math.round(F) + "**F");
	}
	
	/**
	 * Get which argument is the type argument
	 * @return
	 */
	private int getTypeArgIndex() {
		int index = 0;
		
		for (String arg : command.getArgs()) {
			if (arg.length() == 0) return (-1);
			if (arg.toLowerCase().charAt(0) == 'q' || arg.toLowerCase().charAt(0) == 'f') {
				return index;
			}
			index++;
		}
		return (-1);
	}
}
