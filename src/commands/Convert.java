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
			event.getChannel().sendMessage(event.getAuthor().getAsMention()+", "+Reader.read(Lines.bad_usage).replace("%command%", "!convert q/f [value]")).complete();
			return;
		}
		if (args[1].toLowerCase().equals("f")) {
			event.getChannel().sendMessage("Coming soon !").complete();return;
			/*
			double a = 0.00000124349d;
			double b = -0.00170458d;
			double c = 1.30982d;
			double d = -4.0731d;
			int F = 0;
			double Q = 0;
			
			try {
				F = Integer.valueOf(args[2]);
			} catch (Exception e) {event.getChannel().sendMessage("Values must be a valid number!").complete();return;}
			if (F < 110 || F > 400) {event.getChannel().sendMessage("Value must be between 110 and 400").complete();return;}
			
			Q = a * Math.pow(F, 3) + b * Math.pow(F, 2) + c * F + d;
			event.getChannel().sendMessage(+F+"F compares to **"+Math.round(Q)+"**Q").complete();
			*/
		}
		else if (args[1].toLowerCase().equals("q")) {
			double a = -0.0000036008d;
			double b = 0.00483095d;
			double c = -0.239935d;
			double d = 80.817d;
			int Q = 0;
			double F = 0;
			
			try {
				Q = Integer.valueOf(args[2]);
			} catch (Exception e) {event.getChannel().sendMessage("Values must be a valid number!").complete();return;}
			if (Q < 110 || Q > 400) {event.getChannel().sendMessage("Value must be between 110 and 400").complete();return;}
			
			F = a * Math.pow(Q, 3) + b * Math.pow(Q, 2) + c * Q + d;
			event.getChannel().sendMessage(+Q+"Q compares to **"+Math.round(F)+"**F").complete();
		} else {
			event.getChannel().sendMessage(event.getAuthor().getAsMention()+", "+Reader.read(Lines.bad_usage).replace("%command%", "!convert q/f [value]")).complete();
		}
	}
}
