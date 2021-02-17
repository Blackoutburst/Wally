package commands;

import core.Lines;
import core.Reader;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class Convert {
	
	/**
	 * Convert Finals score to Qualification score and
	 * Qualification score to Finals score
	 * @param event
	 * @uthor Blackoutburst
	 */
	public static void convert(MessageReceivedEvent event) {
		String[] args = event.getMessage().getContentDisplay().split(" ");
		
		if (args.length < 3) {
			event.getChannel().sendMessage(event.getAuthor().getAsMention()+", "+Reader.read(Lines.BAD_USAGE).replace("%command%", "!convert q/f [value]")).complete();
			return;
		}
		if (args[1].toLowerCase().equals("f")) {
			double a = 0.00166673145332d;
			double b = 0.751487776094d;
			double F = 0;
			double RF = 0;
			int Q = 0;
			boolean searching = true;
			
			try {
				F = Integer.valueOf(args[2]);
			} catch (Exception e) {
				event.getChannel().sendMessage("Values must be a valid number!").complete();
				return;
			}
			if (F < 0 || F > 500) {
				event.getChannel().sendMessage("Value must be between 0 and 500").complete();
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
			event.getChannel().sendMessage(args[2]+"F compares to **"+Q+"**Q").complete();
			
		}
		else if (args[1].toLowerCase().equals("q")) {
			double a = 0.00166673145332d;
			double b = 0.751487776094d;
			int Q = 0;
			double F = 0;
			
			try {
				Q = Integer.valueOf(args[2]);
			} catch (Exception e) {
				event.getChannel().sendMessage("Values must be a valid number!").complete();
				return;
			}
			if (Q < 0 || Q > 400) {
				event.getChannel().sendMessage("Value must be between 0 and 400").complete();
				return;
			}
			
			F = b*((a*Math.pow(Q, 2))+(1-a)*Q);
			event.getChannel().sendMessage(+Q+"Q compares to **"+Math.round(F)+"**F").complete();
		} else {
			event.getChannel().sendMessage(event.getAuthor().getAsMention()+", "+Reader.read(Lines.BAD_USAGE).replace("%command%", "!convert q/f [value]")).complete();
		}
	}
}
